package com.applet.common.utils.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.applet.common.utils.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

/**
 * scm验签工具
 * @author luning
 * @version 2018年6月26日15:43:44
 */
public class SignUtil {
	/**
	 *  创建签名返回JSON
	 * 
	 * @param apiKey
	 * @param secretKey
	 * @return
	 */
	public static String paramsJson(Object paramObj, String apiKey, String secretKey){
		// 组织数据格式，获取签名
		Map<String, Object> temp = new HashMap<String, Object>();
		if(apiKey != null && !"".equals(apiKey)){
			temp.put("api_key", apiKey);
		}else{
			System.out.println("***********  error message  ************");
			System.out.println("apiKey 不能为空。");
			return null;
		}
		
		if(secretKey != null && !"".equals(secretKey)){
			
		}else{
			System.out.println("***********  error message  ************");
			System.out.println("密钥[secretKey]不能为空。");
			return null;
		}
		
		temp.put("timestamp", DateUtil.getCurrentDateTime19());
		
		if(paramObj != null){
			temp.put("data", JSON.toJSON(paramObj));
		}else{
			temp.put("data", JSON.toJSON(new HashMap<String, Object>()));
		}
		String params = null;
		try {
			// 签名后的url
			params = getSignatures(JSONObject.parseObject(JSONObject.toJSONString(temp)), secretKey);
			System.out.println("***********  获取签名成功   ************");
			System.out.println(params);
			temp.put("sign", params);
		} catch (Exception e) {
			System.out.println("***********  获取签名异常   ************");
			e.printStackTrace();
		}
		return JSONObject.toJSONString(temp);
	}


	/**
	* 根据入参和密钥获取签名
	*
	* @param data
	* @param secretKey
	* @return
	* @throws Exception
	*/
	public static String getSignatures(JSONObject data, String secretKey) {
		//logger.info("data:" +  data);
		// 第一步：获取并排序json数据
		//忽略签名
		data.remove("sign");
		//递归获取json结构中的键值对，组合键值并保存到列表中
		List<String> keyValueList = new ArrayList<String>();
		propertyFilter(data, keyValueList);
		
		//对列表进行排序，区分大小写
		Collections.sort(keyValueList);
		Object json = JSON.toJSON(keyValueList);
		System.out.println("keyvalue:" + keyValueList);
		// 第二步：格式化数据，用&分割
		String formatText = StringUtils.join(keyValueList, "&");
		//在首尾加上秘钥，用&分割
		String finalText = secretKey + "&" + formatText + "&" + secretKey;
	
	//	logger.info("finalText:\t" + finalText);
		System.out.println(finalText);
		// 第三步：MD5加密并转换成大写的16进制(finalText为utf-8编码)
		String md5 = getMD5(finalText).toUpperCase();
		
		return md5;
	}



	/**
	* 生成md5
	*
	* @param message
	* @return
	*/
	private static String getMD5(String message) {
		String md5str = "";
		try {
		    //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
		    MessageDigest md = MessageDigest.getInstance("MD5");
	
		    //2 将消息变成byte数组
		    byte[] input = message.getBytes(Charset.forName("UTF-8"));
	
		    //3 计算后获得字节数组,这就是那128位了
		    byte[] buff = md.digest(input);
//		    System.out.println("md5:");
		    //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
//		    md5str = bytesToHex(buff);
		    md5str = DigitalUtils.bytesToHexString(buff);
	
		} catch (Exception e) {
			
		}
		
		return md5str;
	}
		
	/**
	 * 递归解析所包含的键值对
	 * @param dataObj
	 * @param keyValueList
	 * @return
	 */
	public static void propertyFilter(JSONObject dataObj, List<String> keyValueList) {
		if(keyValueList == null || dataObj == null) {
			return;
		}
		
		Set<String> keys = dataObj.keySet();  
		
		Iterator<String> keyit = keys.iterator();  
		
        while(keyit.hasNext()){  
            String key=keyit.next();  
            Object value = dataObj.get(key);
            
            if(value == null) {
            	continue;
            }
            
            if(value instanceof JSONObject) {
            	propertyFilter((JSONObject)value, keyValueList);
            }else if(value instanceof JSONArray) {
            	JSONArray array = (JSONArray) value;
            	for(Object aryobj: array) {
            		if(aryobj instanceof Integer) {
            			JSONObject obj = new JSONObject();
            			obj.put(key, aryobj);
            			propertyFilter(obj, keyValueList);
            		}else {
            			propertyFilter((JSONObject) aryobj, keyValueList);
            		}
            	}
            	
            }else {
            	if(value.equals("")) {
            		continue;
            	}
            	
            	keyValueList.add(key + "=" + value);
            }
        } 
	}
	
	
	public static void main(String[] args) {
//		String json = "{" + "\"test\":null," +
//				"    \"sign\":	\"fdsffawww\",\r\n" + 
//				"	\"timestamp\":\"2018-11-1\",\r\n" + 
//				"	\"data\":{\r\n" + 
//				"		\"search_key\":\"漳州\"\r\n" + 
//				"	},\r\n" + 
//				"	\"api_key\":\"kskskfd\",\r\n" + 
//				"	\"api_key1\":123,\r\n" + 
//				"\"segment_list\": [\r\n" + 
//						"      {\r\n" + 
//						"        \"arrive_city_code\": \"CKG\",\r\n" + 
//						"        \"depart_date\": \"2017-04-02\",\r\n" + 
//						"        \"depart_city_code\": \"XMN\"\r\n" + 
//						"      },\r\n" + "{\r\n" + 
//							"        \"arrive_city_code\": \"CKG\",\r\n" + 
//							"        \"depart_date\": \"2017-04-02\",\r\n" + 
//							"        \"depart_city_code\": \"XMN1\"\r\n" + 
//							"      }\r\n" + 
//						"    ]"+
//				"}";
//		
//		JSONObject obj = JSON.parseObject(json);
//
//		
//		List<String> list = new ArrayList<String>();
//		SignUtil.propertyFilter(obj, list);
//		
////		System.out.println(list.toString());
//		JSON.toJSON(list);
//		System.out.println(JSONObject.toJSONString(list));
		
//		String jsonstr = "{\"api_key\":\"train_test\",\"data\":{\"callbackurl\":\"https://31640334.ngrok.io/app/train/trainCall\",\"checi\":\"K554\",\"choose_seats\":\"\",\"contact_email\":\"18401586891@163.com\",\"contact_name\":\"刘亭亭\",\"contact_telphone\":\"18401586891\",\"from_station_code\":\"ACB\",\"from_station_name\":\"阿城\",\"is_accept_standing\":true,\"is_choose_seats\":false,\"order_source\":3,\"orderid\":\"201807120454557358\",\"passengers\":[{\"checi\":\"K554\",\"passengerid\":\"4765156\",\"passengersename\":\"卢明\",\"passportseno\":\"410523198806260018\",\"passporttypeseid\":\"1\",\"passporttypeseidname\":\"身份证\",\"piaotype\":\"1\",\"piaotypename\":\"成人票\",\"price\":9,\"reason\":0,\"ticket_no\":\"\",\"zwcode\":\"1\",\"zwname\":\"硬座\"}],\"to_station_code\":\"XFB\",\"to_station_name\":\"香坊\",\"train_date\":\"2018-07-30 13:03\"},\"sign\":\"FCFED128AAD35D8C17D8F822EB4F60CD\",\"timestamp\":\"2018-07-12 16:54:55\"}";
		String jsonstr = "{\"sign\":\"26BB4663BBDD7005E41BA3506F5E62BD\",\"api_key\":\"train_test\",\"timestamp\":\"2018-11-19 15:17:39\",\"data\":{\"passengers\":[{\"passengersename\":\"张福强\",\"passportseno\":\"350623198903190030\",\"passporttypeseidname\":\"二代身份证\",\"passporttypeseid\":\"1\",\"passengerid\":\"1811191517387zhjig\",\"ticket_no\":null,\"piaotype\":\"1\",\"piaotypename\":\"成人票\",\"zwcode\":\"O\",\"zwname\":\"二等座\",\"cxin\":null,\"checi\":null,\"price\":10.0,\"reason\":0,\"province_name\":null,\"province_code\":null,\"school_code\":null,\"school_name\":null,\"student_no\":null,\"school_system\":null,\"enter_year\":null,\"preference_from_station_name\":null,\"preference_from_station_code\":null,\"preference_to_station_name\":null,\"preference_to_station_code\":null}],\"order_source\":\"4\",\"train_date\":\"2018-12-08\",\"checi\":\"D3333\",\"from_station_name\":\"漳浦\",\"from_station_code\":\"ZCS\",\"to_station_name\":\"云霄\",\"to_station_code\":\"YBS\",\"is_choose_seats\":true,\"choose_seats\":\"1A1B\",\"callbackurl\":\"https://testerp.yktour.com.cn/bom/api/notify/occupy_train_notfy\",\"orderid\":\"1811191517380554632\",\"is_accept_standing\":false,\"contact_name\":\"小张啊\",\"contact_telphone\":\"13400666482\",\"contact_email\":\"110@qq.com\"}}";
		
		JSONObject obj1 = JSON.parseObject(jsonstr);
		try {
			String md5 = SignUtil.getSignatures(obj1, "DuA0=pSd5RXTes!s%=Wv");
			
//			String parms = paramsJson(jsonstr,"train_test","DuA0=pSd5RXTes!s%=Wv");
			System.out.println(md5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
	}
}
