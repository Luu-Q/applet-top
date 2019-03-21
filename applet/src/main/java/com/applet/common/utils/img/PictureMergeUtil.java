package com.applet.common.utils.img;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class PictureMergeUtil {


    /**
     * @param fileUrl 文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl) throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }


    /**
     * <p>Title: getImageStream</p>
     * <p>Description: 获取图片InputStream</p>
     *
     * @param destImg
     * @return
     */
    public static InputStream getImageStream(BufferedImage destImg, String format) {
        InputStream is = null;
        BufferedImage bi = destImg;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bi, format, imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static byte[] getImageByte(BufferedImage destImg, String format) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(destImg, format, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();

    }

    public static BufferedImage changeImgSize(String imgUrl, int widthPx, int heightPx) {
        BufferedImage imgNew = null;
        try {
            URL url = new URL(imgUrl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5000);
            //通过输入流获取图片数据
            InputStream qrInputStream = conn.getInputStream();
            BufferedImage imgOld = ImageIO.read(qrInputStream);
            imgNew = changePxSize(imgOld, widthPx, heightPx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgNew;
    }

    /**
     * 压缩图片
     *
     * @param widthPx
     * @param heightPx
     */
    public static BufferedImage changePxSize(BufferedImage imgOld, int widthPx, int heightPx) {
        BufferedImage imgNew = new BufferedImage(widthPx, heightPx, BufferedImage.TYPE_INT_RGB);
        //创建图片
        Graphics g = imgNew.getGraphics();
        //开启画图
        g.drawImage(imgOld.getScaledInstance(heightPx, heightPx, Image.SCALE_DEFAULT), 0, 0, null);
        g.dispose();
        return imgNew;
    }


    public static boolean saveImage(BufferedImage savedImg, String saveDir, String fileName, String format) {
        boolean flag = false;
        // 先检查保存的图片格式是否正确
        String[] legalFormats = {"jpg", "JPG", "png", "PNG", "bmp", "BMP"};
        int i = 0;
        for (i = 0; i < legalFormats.length; i++) {
            if (format.equals(legalFormats[i])) {
                break;
            }
        }
        if (i == legalFormats.length) {
            // 图片格式不支持
            System.out.println("不是保存所支持的图片格式!");
            return false;
        }
        // 再检查文件后缀和保存的格式是否一致
        String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!postfix.equalsIgnoreCase(format)) {
            System.out.println("待保存文件后缀和保存的格式不一致!");
            return false;
        }
        String fileUrl = saveDir + fileName;
        File file = new File(fileUrl);
        try {
            flag = ImageIO.write(savedImg, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     *
     * @param img1         待合并的第一张图
     * @param img2         带合并的第二张图
     * @param isHorizontal 为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal, int startX, int startY) throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        // 逐行扫描图像中各个像素的RGB到数组中
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
        // 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) {
            // 水平方向合并
            DestImage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            // 设置下半部分的RGB
            DestImage.setRGB(startX, startY, w2, h2, ImageArrayTwo, 0, w2);
        } else {
            // 垂直方向合并
            DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
            // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            // 设置下半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2);
        }
        return DestImage;
    }


    /**
     * 图片上添加业务文字
     *
     * @param bimage 新图片对象
     * @param text   文字对象
     * @return
     */
    public static BufferedImage drawTextInImg(BufferedImage bimage, FontText text) {
        // 获取Graphics2D
        Graphics2D g2d = bimage.createGraphics();
        // 画图
        g2d.setBackground(new Color(255, 255, 255));
        g2d.setPaint(new Color(0, 0, 0));
        g2d.setColor(Color.black);
        Font font = new Font(text.getWm_text_font(), Font.PLAIN, text.getWm_text_size());
        g2d.setFont(font);
        // 抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g2d.getFontMetrics(font);
        int textWidth = fm.stringWidth(text.getText());
        int widthX = (bimage.getWidth() - textWidth) / 2;
        int heightY = text.getWm_text_height();
        // 表示这段文字在图片上的位置(x,y) .第一个是设置的内容。
        g2d.drawString(text.getText(), widthX, heightY);
        // 释放对象
        g2d.dispose();
        return bimage;
    }

    // color #2395439
    public static Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }


    /**
     * 合成图片
     *
     * @param width   底图宽
     * @param height  底图高
     * @param qrSize  小程序码宽高
     * @param wxqrUrl 小程序码url
     * @return
     */
    public static BufferedImage generateQr(int width, int height, int qrSize, String wxqrUrl, java.util.List<FontText> fontTexts) throws IOException {
        /*1. 生成白色底图 */
        BufferedImage baseImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D
        Graphics2D g2d = baseImg.createGraphics();
        // 画图
        g2d.setBackground(new Color(255, 255, 255));
        g2d.setPaint(new Color(0, 0, 0));
        // 清除背景色
        g2d.clearRect(0, 0, width, height);
        g2d.dispose();

        /*2. 压缩太阳码图片 */
        BufferedImage wxqrImg = changeImgSize(wxqrUrl, qrSize, qrSize);

        /*3. 调用mergeImage方法获得合并后的图像 */
        int w1 = baseImg.getWidth();
        int h1 = baseImg.getHeight();
        int w2 = wxqrImg.getWidth();
        int h2 = wxqrImg.getHeight();
        int startX = (w1 / 2 - w2 / 2);
        int startY = (int) (h1 * 28.38) / 100;
        BufferedImage destImg = mergeImage(baseImg, wxqrImg, true, startX, startY);

        /*4. 为图片添加业务文字 */
        for (FontText fontText : fontTexts) {
            drawTextInImg(destImg, fontText);
        }

        return destImg;
    }

    public static void main(String[] args) throws IOException {
        java.util.List<FontText> fontTexts = Lists.newArrayList();
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  60, "扫一扫"));
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  134, "扫一扫进入我的名片"));
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  600, "咚咚咚杨"));
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  694, "手机：15710098442"));
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  740, "邮箱：chenwenyi@yingketravel.cn"));
        fontTexts.add(new FontText(3, "#000000","宋体", 26,  786, "地址：北京北京市朝阳区百子湾"));
        BufferedImage bufferedImage = generateQr(650, 860, 310, "https://prefile.yktour.com.cn/group1/M00/02/09/wKiAFFuh632AWGXeAAFuGG0481U122.jpg", fontTexts);
        saveImage(bufferedImage,"F:","/card.jpg","jpg");
    }
}