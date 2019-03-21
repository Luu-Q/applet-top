package com.applet.controller.member;

import com.applet.entity.request.member.DecodeWxUserInfo;
import com.applet.entity.result.ResultModel;
import com.applet.service.member.AppletMemberService;
import com.applet.service.wxauth.WxAuthService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(value = "applet-member-api", description = "小程序用户")
@RestController
@RequestMapping("/applet/member")
public class AppletMemberController {

    @Autowired
    AppletMemberService appletMemberService;
    @Autowired
    WxAuthService wxAuthService;

    /**
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     * @param jsCode
     * @return
     */
    @GetMapping("/jscode2session")
    @ApiOperation(produces = MediaType.MULTIPART_FORM_DATA_VALUE,value = "获取openid ,通过 wx.login() 接口获得临时登录凭证 jscode")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    public ResultModel<?> jscode2session(
            @ApiParam(value = "登录时获取的code", name = "js_code")
            @RequestParam(value = "js_code") String jsCode) {

        ResultModel<?> result = wxAuthService.jscode2session(jsCode);

        return result;
    }

    @PostMapping("/decodeWxUserInfo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @ApiOperation(value = "微信开放数据校验与解密,获取用户信息", notes = "微信开放数据校验与解密,获取用户信息", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultModel<?> decodeWxUserInfo(@RequestBody @Valid DecodeWxUserInfo req, BindingResult result) {
        ResultModel<?> resMsg = null;

        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        try {
            resMsg = appletMemberService.decodeWxUserInfo(req);
        } catch (Exception e) {
            resMsg = ResultModel.unknowWithMsg(e.getMessage());
            log.error("[微信开放数据校验与解密异常 --> {} ]",req,e);
        }

        return resMsg;
    }

    @GetMapping("/getUserInfoByOpenid")
    @ApiOperation(produces = MediaType.MULTIPART_FORM_DATA_VALUE,value = "根据openid获取用户详情")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    public ResultModel<?> getUserInfoByOpenid(
            @RequestParam(value = "openid") String openid) {

        ResultModel<?> result = appletMemberService.getUserInfoByOpenid(openid);

        return result;
    }

}
