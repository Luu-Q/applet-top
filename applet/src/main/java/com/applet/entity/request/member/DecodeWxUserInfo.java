package com.applet.entity.request.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
@ApiModel(value = "微信小程序注册入参")
public class DecodeWxUserInfo {


    /*

    {
  "encryptedData": "doqtdQX84AVmAvpKafrrVkwqnkS8nmhzYzzOPVuzFXfwYiXP3z0RE7f6Q73q/2/QG8snJFwPeN4pZxOgsAw/CGV+Me3cmQNWQshF1XuhFnZ66s+iYeeDIMoe4HHyHq7va5rrD/d+w4ajWIGTxRgk/6k5lKhDTSGll7SF+TATaWI7zxl/KgspQ/TAnivhu8v+bHDJpkGLs/3xKoqrZEHOmgZnlFeXikCytWAkXZAD/jShVlkaL0bkWEZT3Sh5FP7ldJiWlGioo4b6UbMvoXWQsmmN/RIee8xgYF2HlGpL6jxXEzWHTPhgZ5hSclxmrBsWhMTmyho3WQb3AYijh1RY58XubZWmG1W6aOMBtBYjUmnT27zlaZ72ngnfH/YZs1jmhQSJmsVgOHRayz8NR+M74XpCn5XAEruKM8wQYBh++xFhTVKkSdgW5uuAjeg1YO1hsLDeUJHzSqXkd6i+rdqSTre1lzAvgEwc4t5/8PHLn5c=",
  "iv": "Ck090ux9vA6N9n39fe3w+A==",
  "jsCode": "021FAy972JBo1S01sTc72i3L972FAy9e",
  "rawData": "{\"nickName\":\"詹梦乐\",\"gender\":1,\"language\":\"en\",\"city\":\"Haidian\",\"province\":\"Beijing\",\"country\":\"China\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epMPicMn1l2HD6K05a5ZlFVrGIhiamKObBCaHvWVAeY9Dm3CBOanWmQhGNic0Wblg4ubTsqyxqgBKWbA/132\"}",
  "signature": "12a0c96587f9cda078a1c46a6f31bebcc77f7321"
}

     */



    @NotEmpty(message = "jsCode不能为空")
    @ApiModelProperty(value = "jsCode，wx.login获取")
    private String jsCode;

    @NotEmpty(message = "iv不能为空")
    @ApiModelProperty(value = "加密算法的初始向量，getPhoneNumber获取")
    private String iv;

    @NotEmpty(message = "encryptedData不能为空")
    @ApiModelProperty(value = "包括敏感数据在内的完整用户信息的加密数据，getPhoneNumber获取")
    private String encryptedData;

    @NotEmpty(message = "rawData不能为空")
    @ApiModelProperty(value = "不包括敏感信息的原始数据字符串，用于计算签名，wx.getUserInfo获取")
    private String rawData;

    @NotEmpty(message = "signature不能为空")
    @ApiModelProperty(value = "使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息，wx.getUserInfo获取")
    private String signature;


}
