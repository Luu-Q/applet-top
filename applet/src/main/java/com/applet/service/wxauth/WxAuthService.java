package com.applet.service.wxauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.applet.common.constant.WxConstants;
import com.applet.common.wxace.WXBizDataCrypt;
import com.applet.config.rest.RestTemplateUtils;
import com.applet.entity.request.member.DecodeWxUserInfo;
import com.applet.entity.result.ResultModel;
import com.applet.entity.wx.Jscode2Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WxAuthService {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.AppSecret}")
    private String appSecret;
    @Autowired
    RestTemplateUtils restTemplateUtils;

    /**
     * 第三方平台开发者的服务器使用登录凭证 code 以及第三方平台的component_access_token 获取 session_key 和
     * openid。其中 session_key 是对用户数据进行加密签名的密钥。为了自身应用安全，session_key 不应该在网络上传输。
     *
     * @param jsCode 登录时获取的 code
     */
    public ResultModel<?> jscode2session(String jsCode) {
        StringBuffer url = new StringBuffer(WxConstants.GET_JSCODE2SESSION_URL);
        url.append("appid=").append(appid).append("&");
        url.append("secret=").append(appSecret).append("&");
        url.append("js_code=").append(jsCode).append("&");
        url.append("grant_type=").append("authorization_code");

        log.info("jscode2session request url : {}", url.toString());

        String response = restTemplateUtils.lGet(url.toString(), null, null);

        log.info("jscode2session response : {}", response);

        Jscode2Session jscode2Session = JSON.parseObject(response, Jscode2Session.class);
        //微信接口成功不会返会0，code为空
        if (!"0".equals(jscode2Session.getErrcode()) && StringUtils.isNotEmpty(jscode2Session.getErrcode())) {
            return ResultModel.fail(jscode2Session.getErrcode(),jscode2Session.getErrmsg());
        }
        if (StringUtils.isNotEmpty(jscode2Session.getOpenid())) {
            return ResultModel.succWithData(jscode2Session);
        }

        return ResultModel.unknowWithMsg("jsCode未知返回值, 请检查!");
    }

    public ResultModel<?> decodeUserInfo(DecodeWxUserInfo decodeWxUserInfo, String sessionKey){
        ResultModel resultModel = null;

        try {
            WXBizDataCrypt wxBizDataCrypt = new WXBizDataCrypt(appid,sessionKey);
            String signature = wxBizDataCrypt.getSHA1(decodeWxUserInfo.getRawData() + sessionKey);
            if(!decodeWxUserInfo.getSignature().equals(signature)){
                return ResultModel.fail("signature、rawData校验失败");
            }

            JSONObject jsonObject = wxBizDataCrypt.decryptData(decodeWxUserInfo.getEncryptedData(), decodeWxUserInfo.getIv());

            resultModel = ResultModel.succWithData(jsonObject);
        } catch (Exception e) {
            log.error("[WXBizDataCrypt 解密异常 ]", e);
            resultModel = ResultModel.unknowWithMsg("WXBizDataCrypt解密异常");
        }

        return resultModel;
    }


}
