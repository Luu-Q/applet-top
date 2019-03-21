package com.applet.controller.member;

import com.applet.common.domain.BaseController;
import com.applet.entity.request.member.*;
import com.applet.entity.result.ResultModel;
import com.applet.model.member.CleanMember;
import com.applet.model.member.TokenBean;
import com.clean.applet.entity.request.member.*;
import com.applet.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

//@Api(value = "member-api", description = "用户操作接口")
@Slf4j
@RestController
@RequestMapping("/app/member")
public class MemberController extends BaseController {

    @Autowired
    MemberService memberService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "用户注册", notes = "用户注册", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/register")
    public ResultModel<?> register(@RequestBody @Valid CleanMemberRegistReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        return memberService.regist(req);
    }

    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/login")
    public ResultModel<?> login(@RequestBody @Valid CMemberLoginByPwdReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        return memberService.login(req);
    }

    @ApiOperation(value = "重置密码", notes = "重置密码", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/resetPwd")
    public ResultModel<?> resetPwd(@RequestParam("pwd") String pwd, HttpServletRequest request) {

        memberService.resetCMemberPwd(getMemberId(request), pwd);

        return ResultModel.succ();
    }

    @ApiOperation(value = "获取登录用户信息", notes = "获取登录用户信息", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @GetMapping("/info")
    public ResultModel<?> memberInfo(@RequestParam("mobile") String mobile) {

        return memberService.getMemberInfo(mobile);
    }

    @ApiOperation(value = "生成token", notes = "生成token", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/createToken")
    public Object createToken(@RequestBody @Valid CrearteTokenReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        CleanMember member = null;
        if (member == null) {
            return ResultModel.fail("非法的用户Id");
        }

        TokenBean tokenBean = memberService.getToken(req);
        if (tokenBean == null) {
            return ResultModel.fail("Token 生成失败");
        }
        return ResultModel.succWithData(tokenBean);
    }


    @ApiIgnore
    @ApiOperation(value = "绑定手机号", notes = "绑定手机号", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/bindMobile")
    public Object bindMobile(@RequestBody @Valid CMemberBingMobileReq req, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        // 查询缓存验证码
        String code = stringRedisTemplate.opsForValue().get("captcha-mobile:" + req.getMobile());
        if (StringUtils.isEmpty(code) || !req.getVerificationCode().equals(code)) {
            return ResultModel.fail("验证码不正确");
        }

        Long memberId = getMemberId(request);

        Optional<CleanMember> member = memberService.getCMemberByMobile(req.getMobile());
        if (member.isPresent()) {
            return ResultModel.fail("手机号已绑定其他账户，请先解绑");
        }

        // 绑定操作
        memberService.bindMobile(memberId, req.getMobile());

        return ResultModel.succ();
    }

    @ApiIgnore
    @ApiOperation(value = "短信验证码登录", notes = "短信验证码登录", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/login/captcha")
    public Object loginByCaptcha(@RequestBody @Valid CMemberLoginByCaptchaReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        String oldCode = stringRedisTemplate.opsForValue().get("captcha-mobile:" + req.getLoginName());
        if (!req.getVerificationCode().equals(oldCode)) {
            return ResultModel.fail("验证码不正确");
        }
        Optional<CleanMember> member = memberService.getCMemberByLoginName(req.getLoginName());
        if (!member.isPresent()) {
            return ResultModel.fail("无此账户");
        }

        TokenBean tokenBean = memberService.login(req);
        if (tokenBean == null) {
            return ResultModel.fail("登录失败");
        }
        return ResultModel.succWithData(tokenBean);
    }

    @ApiIgnore
    @ApiOperation(value = "短信验证码登录，用户不存在注册新用户", notes = "短信验证码登录，用户不存在注册新用户", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/pc/login/captcha")
    public Object loginByCaptcha4Pc(@RequestBody @Valid CMemberLoginByCaptchaReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        String oldCode = stringRedisTemplate.opsForValue().get("captcha-mobile:" + req.getLoginName());
        if (!req.getVerificationCode().equals(oldCode)) {
            return ResultModel.fail("验证码不正确");
        }

        TokenBean tokenBean = null;

        Optional<CleanMember> member = memberService.getCMemberByLoginName(req.getLoginName());
        if (!member.isPresent()) {
            // 注册新用户
            CleanMemberRegistReq registReq = new CleanMemberRegistReq();
            registReq.setLoginName(req.getLoginName());
            registReq.setLoginPwd("1qazse4rfv");
            registReq.setResourceOs(req.getResourceOs());
            memberService.regist(registReq);

            log.info("['" + registReq.getLoginName() + "']无此用户，新创建");
        } else {

            tokenBean = memberService.login(req);
        }
        if (tokenBean == null) {
            return ResultModel.fail("登录失败");
        }
        return ResultModel.succWithData(tokenBean);
    }

    @ApiIgnore
    @ApiOperation(value = "手机号密码验证", notes = "手机号密码验证", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功时,data示例", response = ResultModel.class)})
    @PostMapping("/mobile/pwd/check")
    public Object loginByMobileAndPwd(@RequestBody @Valid CMemberCheckByMobilePwdReq req, BindingResult result) {
        if (result.hasErrors()) {
            return ResultModel.fail(result.getFieldError().getDefaultMessage());
        }

        if (!memberService.checkCMemberByMobileAndPwd(req.getMobile(), req.getLoginPwd())) {
            return ResultModel.fail("账户或密码不正确");
        }
        return ResultModel.succ();
    }


}
