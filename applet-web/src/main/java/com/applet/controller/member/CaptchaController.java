package com.applet.controller.member;

import com.applet.constant.MemberConstant;
import com.applet.common.result.ResultModel;
import com.applet.common.utils.RandomUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

//@Api(value = "member-api", description = "图形验证码、短信验证码")
@Slf4j
@Controller
@RequestMapping(value = "/captcha", produces = "application/json;charset=UTF-8")
public class CaptchaController {

    @Autowired
    private Producer producer;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/info/get")
    public void kaptcha(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
        HttpSession session = req.getSession();
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        rsp.setDateHeader("Expires", 0);
        rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        rsp.setHeader("Pragma", "no-cache");
        rsp.setContentType("image/jpeg");

        String capText = producer.createText();
        log.info("old 图形验证码: " + code + "，新的图形验证码: " + capText);

        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage image = producer.createImage(capText);
        ServletOutputStream out = rsp.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }


    /**
     * 登录短信验证码
     */
    @GetMapping("/login/info/get")
    @ResponseBody
    public Object sendLoginShortMsg(HttpServletRequest req) {
        String mobile = req.getParameter("mobile");
        if (StringUtils.isEmpty(mobile)) {
            return ResultModel.fail("手机号不能为空");
        }

        // 查询缓存
        String memKey = "captcha-mobile:" + mobile;
        String oldCode = stringRedisTemplate.opsForValue().get(memKey);
        // 失效之前不能重复发送验证码
        if (StringUtils.isNotEmpty(oldCode)) {
            return ResultModel.fail("请等待60秒验证码失效后重新发送。");
        }

        String verificationCode = RandomUtil.getSimpleRandomCodeNotRepeat(6);   // 6位随机数
        log.info("手机号：" + mobile + "，登陆验证码:" + verificationCode);

        stringRedisTemplate.opsForValue().set(memKey, verificationCode, MemberConstant.LOGIN_VERIFICATION_CODE__EXPIRES_TIME, TimeUnit.SECONDS); // 60秒

        // 发送短信
        /*String content = ShortMsgConstant.verification_code_content.replace("xxxx", verificationCode);
        String sendContent = "mobile=" + mobile + "&content=" + content;
        String result = HttpRequestUtil.sendPost("http://smsapi.yktour.com.cn/msg/info/send", sendContent);
        if (StringUtils.isEmpty(result) || (StringUtils.isNotEmpty(result) && Integer.parseInt(JSON.parseObject(result).get("code").toString()) != ResultStatusEnum.SUCCESS.getStatus())) {
            return ResultModel.fail(JSON.parseObject(result).get("msg").toString());
        }*/

        return ResultModel.succ();
    }

    /**
     * 验证短信验证码
     *
     * @param request
     * @return
     */
    @GetMapping("/verify/code")
    @ResponseBody
    public Object verifyCode(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String verificationCode = request.getParameter("verificationCode");

        if (StringUtils.isEmpty(mobile)) {
            return ResultModel.fail("手机号不能为空");
        }

        // 查询缓存
        String code = stringRedisTemplate.opsForValue().get("captcha-mobile:" + mobile);
        if (StringUtils.isEmpty(code) || !verificationCode.equals(code)) {
            return ResultModel.fail("验证码不正确");
        }
        return ResultModel.succ();
    }

    /**
     * 验证图形验证码
     *
     * @param request
     * @return
     */
    @GetMapping("/verify/kaptchaCode")
    @ResponseBody
    public Object verifyKaptchaCode(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            return "请输入验证码";
        }
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        log.info("old 图形验证码: " + vcode);

        if (!code.equalsIgnoreCase(vcode)) {
//             session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            return ResultModel.fail("验证失败");
        }
        return ResultModel.succ();
    }

}
