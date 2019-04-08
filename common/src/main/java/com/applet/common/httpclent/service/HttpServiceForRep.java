package com.applet.common.httpclent.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.applet.common.httpclent.base.BaseServiceResp;
import com.applet.common.httpclent.base.HttpForErpServiceIdEnum;
import com.applet.common.httpclent.request.HotelCancelOrderRequest;
import com.applet.common.httpclent.response.HotelCancelOrderResponse;
import com.applet.common.utils.date.DateUtil;
import com.applet.common.utils.sign.DigitalUtils;
import com.applet.common.utils.sign.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * author:luning
 * Date:2018/11/12
 * Time:13:35
 * Description:  ${description}
 */
@Service
public class HttpServiceForRep {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${erp.securety.app.api_key}")
    private  String ERP_APP_API_KEY;
    @Value("${erp.securety.app.secret_key}")
    private  String ERP_APP_SECRET_KEY;
    @Value("${erp.base.service.url}")
    private String requestUrl;
//    @Autowired
//    private RestTemplateUtils restTemplateUtils;

    private BaseServiceResp coreHttp(Class response, String serviceId, Object request){
        try {
            String paramJson = paramsJson(request, ERP_APP_API_KEY, ERP_APP_SECRET_KEY);
            logger.info("++++++++++++++++++++++++Post请求开始++++++++++++++++++++++++++++++");
            logger.info("Post请求参数：" + paramJson);
            logger.info("Post请求接口：" + requestUrl+serviceId);
//            String resultJson = restTemplateUtils.lPostBody(requestUrl+serviceId, null, paramJson, null);
            String resultJson = "";
            logger.info("Post请求返回值：" + resultJson);
            BaseServiceResp bodyClass = JSON.parseObject(resultJson, BaseServiceResp.class);
            if(bodyClass!=null&&bodyClass.getCode()==0){
                if(null != bodyClass.getData()){
                    Object bodyClassResponse  = JSON.parseObject(bodyClass.getData().toString(), response);
                    bodyClass.setData(bodyClassResponse);
                }
            }
            logger.info("返回{}",JSON.toJSONString(bodyClass));
            return bodyClass;
        } catch (Exception e) {
            logger.warn("请求核心异常，接口交易码：{}, {}",serviceId,e);
        }
        return null;
    }



    /**
     * 创建签名返回JSON
     *
     * @param apiKey
     * @param secretKey
     * @return
     */
    public static String paramsJson(Object paramObj, String apiKey, String secretKey) {
        // 组织数据格式，获取签名
        Map<String, Object> temp = new HashMap<String, Object>();
        if (apiKey != null && !"".equals(apiKey)) {
            temp.put("api_key", apiKey);
        } else {
            System.out.println("***********  error message  ************");
            System.out.println("apiKey 不能为空。");
            return null;
        }

        if (secretKey != null && !"".equals(secretKey)) {

        } else {
            System.out.println("***********  error message  ************");
            System.out.println("密钥[secretKey]不能为空。");
            return null;
        }

        temp.put("timestamp", DateUtil.getCurrentDateTime19());

        if (paramObj != null) {
            temp.put("data", JSON.toJSON(paramObj));
        } else {
            temp.put("data", JSON.toJSON(new HashMap<String, Object>()));
        }
        String params = null;
        try {
            // 签名后的url
            params = SignUtil.getSignatures(JSONObject.parseObject(JSONObject.toJSONString(temp)), secretKey);
            System.out.println("***********  获取签名成功   ************");
            System.out.println(params);
            temp.put("sign", params);
        } catch (Exception e) {
            System.out.println("***********  获取签名异常   ************");
            e.printStackTrace();
        }
        return JSONObject.toJSONString(temp);
    }

    //-------------------------------- 线路 --------------------------------//
    public BaseServiceResp<HotelCancelOrderResponse> tongxingOrder(HotelCancelOrderRequest request) {
        logger.info("[线路]发送直客下单请求...");
        return coreHttp(HotelCancelOrderResponse.class, HttpForErpServiceIdEnum.TongxingOrderService.getServiceId(), request);
    }

}
