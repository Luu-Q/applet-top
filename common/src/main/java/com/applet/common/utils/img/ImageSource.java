package com.applet.common.utils.img;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageSource {

    private String imgUrl;

    //Y轴位置
    private int imgHeight;
    //X轴位置
    private int imgWidth;

    private boolean isHorizontal;


}
