package com.applet.common.utils.img;

import lombok.Data;

@Data
public class FontText {

    public static final int times = 2;
    public static final int baseWidth = 650;
    public static final int baseHeight = 860;
    public static final int qrSize = 310;

    private int wm_text_pos;
    //颜色
    private String wm_text_color;
    //字体类型
    private String wm_text_font;
    //字体大小
    private int wm_text_size;
    //Y轴位置
    private int wm_text_height;
    //文本内容
    private String text;

    public FontText(int wm_text_pos, String wm_text_color, String wm_text_font, int wm_text_size, int wm_text_height, String text) {
        this.wm_text_pos = wm_text_pos;
        this.wm_text_color = wm_text_color;
        this.wm_text_font = wm_text_font;
        this.wm_text_size = wm_text_size;
        this.wm_text_height = wm_text_height;
        this.text = text;
    }
}