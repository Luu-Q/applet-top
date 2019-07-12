package com.applet.common.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;


/**
 * 利用谷歌zxing开源工具生成二维码
 *
 * @author ln
 * @date 2019年07月10日14:53:43
 */
public class GenerateQRCode {

    public static void main(String[] args) throws WriterException, IOException {
        File file = new File("/mydata/test.jpeg");
        QRCodeWriter writer = new QRCodeWriter();
//        String contents = "https://www.duyaya.com?bindCode=c799acd2f11bf627";
        String contents = "测试二维码乱码问题，测试二维码乱码问题&测试二维码乱码问题/";
        contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
        BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 430, 430);
        MatrixToImageConfig config = new MatrixToImageConfig(0xff000000,0xFFFFFFFF);
        MatrixToImageWriter.writeToFile(bitMatrix, "jpeg", file,config);

    }


}