package com.applet.service.member;

import com.applet.common.constant.MemberConstant;
import com.applet.common.result.ResultModel;
import com.applet.common.utils.RandomUtil;
import com.applet.common.utils.secrity.Digests;
import com.applet.common.utils.secrity.Encodes;
import com.applet.dao.member.CleanMemberMapper;
import com.applet.common.enums.ResourceOsEnum;
import com.applet.entity.request.member.CMemberLoginByCaptchaReq;
import com.applet.entity.request.member.CMemberLoginByPwdReq;
import com.applet.entity.request.member.CleanMemberRegistReq;
import com.applet.entity.request.member.CrearteTokenReq;
import com.applet.entity.response.MemberRep;
import com.applet.model.member.CleanMember;
import com.applet.model.member.TokenBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
@Slf4j
public class MemberService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CleanMemberMapper cleanMemberMapper;
    @Autowired
    TokenService tokenService;

    /**
     * 注册
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultModel regist(CleanMemberRegistReq req) {
        ResultModel resultModel = null;
        try {
            Optional<CleanMember> memberOp = this.getCMemberByLoginName(req.getLoginName());
            if (memberOp.isPresent()) {
                return ResultModel.fail("用户已存在");
            }

            /*String oldCode = stringRedisTemplate.opsForValue().get("captcha-mobile:" + req.getLoginName());
            if (!req.getVerificationCode().equals(oldCode)) {
                return ResultModel.fail("验证码不正确");
            }*/

            CleanMember cMember = new CleanMember();
            BeanUtils.copyProperties(req, cMember);

            byte[] salt = Digests.generateSalt(MemberConstant.SALT_SIZE);
            byte[] hashPassword = Digests.sha1(cMember.getLoginPwd().getBytes(), salt, MemberConstant.HASH_INTERATIONS);

            cMember.setLoginPwd(Encodes.encodeHex(hashPassword));
            cMember.setSalt(Encodes.encodeHex(salt));

            cMember.setResourceOs(req.getResourceOs());
            cMember.setCleanCode(getCleanCode(req.getResourceOs()));
            cMember.setIsDel(0);
            cMember.setMobile(req.getLoginName());

            cleanMemberMapper.insert(cMember);
            // 查询对象
            CleanMember member = getCMemberByLoginName(req.getLoginName()).get();
            // 返回token
            TokenBean token = tokenService.createToken(member.getId().toString(), ResourceOsEnum.getName(req.getResourceOs()));

            resultModel = ResultModel.succWithData(token);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[注册错误] --> {}", req, e);
            resultModel = ResultModel.fail(e.getMessage());

        }
        return resultModel;
    }

    public Optional<CleanMember> getCMemberByLoginName(String loginName) {
        CleanMember cMember = new CleanMember();
        cMember.setLoginName(loginName);
        List<CleanMember> list = cleanMemberMapper.selectListByCondition(cMember);
        return list.size() > 0 ? Optional.of(cMember) : Optional.empty();
    }

    public Optional<CleanMember> getCMemberByMobile(String mobile){
        CleanMember cMember = new CleanMember();
        cMember.setLoginName(mobile);
        List<CleanMember> list = cleanMemberMapper.selectListByCondition(cMember);
        return list.size() > 0 ? Optional.of(cMember) : Optional.empty();
    }

    /**
     * 账号密码登录
     */
    public ResultModel login(CMemberLoginByPwdReq req) {
        ResultModel resultModel = null;
        Optional<CleanMember> memberOp = getCMemberByLoginName(req.getLoginName());
        if (!memberOp.isPresent()) {
            return ResultModel.fail("用户不存在");
        }

        CleanMember member = memberOp.get();
        byte[] salt = Digests.generateSalt(MemberConstant.SALT_SIZE);
        byte[] hashPassword;
        if (StringUtils.isEmpty(member.getSalt())) {
            //给个默认的
            hashPassword = Digests.sha1(req.getLoginPwd().getBytes(), salt, MemberConstant.HASH_INTERATIONS);
        } else {
            hashPassword = Digests.sha1(req.getLoginPwd().getBytes(), Encodes.decodeHex(member.getSalt()), MemberConstant.HASH_INTERATIONS);
        }

        // 密码验证
        String checkPwd = Encodes.encodeHex(hashPassword);
        if (checkPwd.equals(member.getLoginPwd())) {
            log.info("登录成功");

            // 更新最新一次登录时间
            member.setLastLoginTime(System.currentTimeMillis() / 1000);
            cleanMemberMapper.update(member);

            TokenBean token = tokenService.createToken(member.getId().toString(), ResourceOsEnum.getName(req.getResourceOs()));
            resultModel = ResultModel.succWithData(token);
        } else {
            resultModel = ResultModel.fail("登录失败");
        }
        return resultModel;
    }

    public int resetCMemberPwd(Long memberId, String password) {
        CleanMember member = cleanMemberMapper.selectById(memberId);
        if (member == null) return 0;

        byte[] hashPassword = Digests.sha1(password.getBytes(), Encodes.decodeHex(member.getSalt()), MemberConstant.HASH_INTERATIONS);
        member.setLoginPwd(Encodes.encodeHex(hashPassword));

        return cleanMemberMapper.update(member);
    }

    public ResultModel<?> getMemberInfo(String mobile) {
        Optional<CleanMember> memberOp = getCMemberByMobile(mobile);
        if (!memberOp.isPresent()) {
            return ResultModel.fail("用户不存在");
        }
        MemberRep memberRep = new MemberRep();
        BeanUtils.copyProperties(memberOp.get(),memberRep);

        return ResultModel.succWithData(memberRep);
    }

    public TokenBean login(CMemberLoginByCaptchaReq req) {
        Optional<CleanMember> memberOp = getCMemberByLoginName(req.getLoginName());
        if (!memberOp.isPresent()) return null;
        log.info("验证码登录成功");

        CleanMember member = memberOp.get();
        // 更新最新一次登录时间
        member.setLastLoginTime(System.currentTimeMillis() / 1000);
        cleanMemberMapper.update(member);

        return tokenService.createToken(member.getId().toString(), ResourceOsEnum.getName(req.getResourceOs()));
    }

    public TokenBean getToken(CrearteTokenReq req) {
        return tokenService.getToken(req.getMemberId(), ResourceOsEnum.getName(req.getResourceOs()));
    }

    public int bindMobile(Long memberId, String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", memberId);
        map.put("mobile", mobile);
        map.put("updateTime", System.currentTimeMillis() / 1000);
        return cleanMemberMapper.bindMobile(map);
    }

    public boolean checkCMemberByMobileAndPwd(String mobile, String pwd) {
        CleanMember member = null;
        if (member != null) {
            byte[] hashPassword = Digests.sha1(pwd.getBytes(), Encodes.decodeHex(member.getSalt()), MemberConstant.HASH_INTERATIONS);

            // 密码验证
            String checkPwd = Encodes.encodeHex(hashPassword);
            if (checkPwd.equals(member.getLoginPwd())) {
                return true;
            }
        }
        return false;
    }

    public boolean existedCleanCode(String code) {
        return cleanMemberMapper.existedCleanCode(code) == null;
    }

    public String getCleanCode(Integer resourceOs) {
        String code;
        while (true) {
            code = resourceOs + RandomUtil.getSimpleRandomCode(8);
            if (existedCleanCode(code)) {
                break;
            }
        }
        return code;
    }

}
