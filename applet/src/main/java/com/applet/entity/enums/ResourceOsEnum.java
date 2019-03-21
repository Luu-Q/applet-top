package com.applet.entity.enums;

/**
 * 注册登录来源
 */
public enum ResourceOsEnum {

    PC("PC", 1),
    IOS("IOS", 2),
    ANDROID("Android", 3),
    WECHAT("WeChat", 4),
    H5("H5", 5),
    APPLETS("Applets", 6),
    UNKNOWN("Unknown", 99);

    private String name;
    private int index;

    ResourceOsEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer index) {
        for (ResourceOsEnum c : ResourceOsEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

}
