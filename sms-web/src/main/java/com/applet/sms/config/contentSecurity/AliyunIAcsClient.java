package com.applet.sms.config.contentSecurity;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

/**
 * @description: todo
 * @author: LUNING
 * @create: 2019-04-25 15:23
 */
@Component
public class AliyunIAcsClient {

    /**
     * ALIYUN_ACCESS_KEY_ID和ALIYUN_ACCESS_KEY_SECRET,请替换成您自己的aliyun ak.
     * 访问regionId支持: cn-shanghai,cn-beijing,ap-southeast-1, us-west-1, 其他区域暂不支持, 请勿使用
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
//    @Value("aliyun.accessKey")
    private String ALIYUN_ACCESS_KEY_ID = "LTAITWiaMZ6SysPS";

//    @Value("aliyun.accessKeySecret")
    private String ALIYUN_ACCESS_KEY_SECRET = "sDcpapZl4BvY5qCGgyhHUyhZq6DZPB";

//    @Value("aliyun.regionId")
    private String REGION_ID = "cn-beijing";

    public IAcsClient initIAcsClient(){
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ALIYUN_ACCESS_KEY_ID, ALIYUN_ACCESS_KEY_SECRET);
        IAcsClient recognitionClient = new DefaultAcsClient(profile);
        return recognitionClient;
    }
}
