package com.applet.common.wxace;

import com.alibaba.fastjson.JSONObject;

public class Demo {


    public static void main(String[] args) {
        String appid = "wx252f27b1d5b6dca5";
        String sessionKey = "x739+dhe4DkAE0KZTStMhw==";
        String encryptedData = "doqtdQX84AVmAvpKafrrVkwqnkS8nmhzYzzOPVuzFXfwYiXP3z0RE7f6Q73q/2/QG8snJFwPeN4pZxOgsAw/CGV+Me3cmQNWQshF1XuhFnZ66s+iYeeDIMoe4HHyHq7va5rrD/d+w4ajWIGTxRgk/6k5lKhDTSGll7SF+TATaWI7zxl/KgspQ/TAnivhu8v+bHDJpkGLs/3xKoqrZEHOmgZnlFeXikCytWAkXZAD/jShVlkaL0bkWEZT3Sh5FP7ldJiWlGioo4b6UbMvoXWQsmmN/RIee8xgYF2HlGpL6jxXEzWHTPhgZ5hSclxmrBsWhMTmyho3WQb3AYijh1RY58XubZWmG1W6aOMBtBYjUmnT27zlaZ72ngnfH/YZs1jmhQSJmsVgOHRayz8NR+M74XpCn5XAEruKM8wQYBh++xFhTVKkSdgW5uuAjeg1YO1hsLDeUJHzSqXkd6i+rdqSTre1lzAvgEwc4t5/8PHLn5c=";
        String iv = "Ck090ux9vA6N9n39fe3w+A==";


        try {
            WXBizDataCrypt wxBizDataCrypt = new WXBizDataCrypt(appid,sessionKey);
            JSONObject jsonObject = wxBizDataCrypt.decryptData(encryptedData, iv);
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
