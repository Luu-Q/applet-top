package com.applet.service.member;

import com.alibaba.fastjson.JSONObject;
import com.applet.common.result.ResultModel;
import com.applet.dao.member.AppletMemberMapper;
import com.applet.entity.request.member.DecodeWxUserInfo;
import com.applet.entity.wx.Jscode2Session;
import com.applet.model.member.AppletMember;
import com.applet.service.wxauth.WxAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppletMemberService {

    @Value("${wx.appid}")
    private String appid;
    @Autowired
    AppletMemberMapper appletMemberMapper;
    @Autowired
    WxAuthService wxAuthService;

    public ResultModel<?> decodeWxUserInfo(DecodeWxUserInfo req) {
        ResultModel<?> jscode2session = wxAuthService.jscode2session(req.getJsCode());
        if(!jscode2session.succeed()){
            return jscode2session;
        }

        //获取用户openID
        Jscode2Session jscodeJson = (Jscode2Session) jscode2session.getData();

        JSONObject userJson = JSONObject.parseObject(req.getRawData());

        AppletMember appletMember = new AppletMember();
        appletMember.setAppid(appid);
        appletMember.setOpenid(jscodeJson.getOpenid());
        appletMember.setNickname(userJson.getString("nickName"));
        appletMember.setGender(userJson.getInteger("gender"));
        appletMember.setCity(userJson.getString("city"));
        appletMember.setProvince(userJson.getString("province"));
        appletMember.setHeadUrl(userJson.getString("avatarUrl"));

        //获取用户手机号
        ResultModel<?> decodeUserInfo = wxAuthService.decodeUserInfo(req, jscodeJson.getSessionKey());
        if(decodeUserInfo.succeed()){
            JSONObject mobileJson = (JSONObject) decodeUserInfo.getData();
            appletMember.setMobile(mobileJson.getString("phoneNumber"));
        }

        AppletMember memberByOpenid = appletMemberMapper.getMemberByOpenid(jscodeJson.getOpenid());
        if(null!=memberByOpenid){
            appletMemberMapper.updateByOpendid(appletMember);
        }else{
            appletMemberMapper.insert(appletMember);
        }


        return ResultModel.succWithData(appletMemberMapper.getMemberByOpenid(jscodeJson.getOpenid()));
    }

    public ResultModel<?> getUserInfoByOpenid(String openid) {

        return ResultModel.succWithData(appletMemberMapper.getMemberByOpenid(openid));
    }
}
