package com.applet.wxace;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;

@Slf4j
public class WXBizDataCrypt {

    private String appid;
    private String sessionKey;

    public WXBizDataCrypt(String appid, String sessionKey) throws AesException {

        if (sessionKey.length() != 24) {
            throw new AesException(AesException.IllegalSessionKey);
        }

        this.appid = appid;
        this.sessionKey = sessionKey;
    }

    public JSONObject decryptData(String encryptedData, String iv) throws Exception {

        if (iv.length() != 24) {
            throw new AesException(AesException.IllegalIV);
        }

        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }

        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        // 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null == resultByte || resultByte.length <= 0) {
            throw new AesException(AesException.IllegalBuffer);
        }

        String result = new String(resultByte, "UTF-8");
        return JSONObject.parseObject(result);

    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @throws AesException
     */
    public String getSHA1(String str) throws Exception {

        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();

    }
}

