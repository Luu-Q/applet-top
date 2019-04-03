package com.applet.common.utils.sign;

import java.util.ArrayList;
import java.util.List;

/**
 * 2017/3/30
 * @author zzh
 *
 */
public class DigitalUtils {

    /** 
    * @Title:intToHexString 
    * @Description:10进制数字转成16进制 
    * @param a 转化数据 
    * @param len 占用字节数 
    * @return 
    * @throws 
    */  
	public static String intToHexString(int data, int len){  
		String hexString = Integer.toHexString(data);  
       
		int b = len -hexString.length();  
       
		if(b > 0){  
			for(int i = 0; i < b; i++)  {  
				hexString = "0" + hexString;  
			}  
		}
       
		return hexString.toUpperCase();  
	} 
 
	public static String byteToComplementHexString(byte data, int len){  
		int result;
		StringBuilder sb=new StringBuilder();
	    for(int j=7;j>=0;j--){
	    	if(((1<<j)& data)!=0)
	    		sb.append("1");
	    	else
	    		sb.append("0");
	    	  
	    }
	      
	    result=Integer.parseInt(sb.toString(), 2);
	      
		return DigitalUtils.intToHexString(result, 2);
	} 
	
   /** 
    * @Title:string2HexString 
    * @Description:字符串转16进制字符串 
    * @param strPart 
    *            字符串 
    * @return 16进制字符串 
    * @throws 
    */  
   public static String string2HexString(String strPart) {  
       StringBuffer hexString = new StringBuffer();  
       for (int i = 0; i < strPart.length(); i++) {  
           int ch = (int) strPart.charAt(i);  
           String strHex = Integer.toHexString(ch);  
           hexString.append(strHex);  
       }  
       return hexString.toString();  
   }  
 
   public static String string2HexString(String str, int len) {	   
	   int num;
	   try{ 
		   num = Integer.valueOf(str);
       }catch(Exception e){ 
           e.printStackTrace(); 
           num = 0;
       }
	   
	   return intToHexString(num, len);
   }
 
   
   /** 
    * @Title:hexString2String 
    * @Description:16进制字符串转字符串 
    * @param src 
    *            16进制字符串 
    * @return 字节数组 
    * @throws 
    */  
   public static String hexString2String(String src) {  
	   Integer data = Integer.valueOf(src, 16);
	   return data.toString().toUpperCase();

   }
   
   	
   	public static byte[] hexString2Bytes(String hex){
   		int len = hex.length()/2;
   		if(len == 0){
   			return null;
   		}
   		
		byte[] result = new byte[len];
		
		for(int i = 0; i < len; i++) {
			String n = hex.substring(i*2, i*2+2);
			int num = Integer.parseInt(n, 16);
			result[i] = (byte)num;
		} 
		
		return result;
   	}

   	public static final String bytesToHexString(byte[] bArray) {   
   		StringBuffer sb = new StringBuffer(bArray.length);   
   	    String sTemp;   
   	    for (int i = 0; i < bArray.length; i++) {   
   	    	sTemp = Integer.toHexString(0xFF & bArray[i]);   
   	    	if (sTemp.length() < 2)   
   	    		sb.append(0);   
   	    	sb.append(sTemp.toUpperCase());   
   	    }   
   	    return sb.toString();   
   	}  
   	
   	public static Long hexStringToLong(String hex){
   		if(hex == null || hex.isEmpty()){
   			return null;
   		}
   		
   		return Long.parseLong(hex, 16); 
   		
   	}
   	
   	public static Integer hexStringToInt(String hex){
   		if(hex == null || hex.isEmpty()){
   			return null;
   		}
   		
   		return Integer.parseInt(hex, 16);    		
   	}
   	   	
   	public static int compareHex(String srcHex, String destHex){
   		if(srcHex == null || destHex == null)
   			return -1;
   		
   		Long src = DigitalUtils.hexStringToLong(srcHex);
   		Long dest = DigitalUtils.hexStringToLong(destHex);
   		long rlt = src - dest;
   		if(rlt < 0)
   			return -1;
   		else if(rlt == 0)
   			return 0;
   		else 
   			return 1;
   	}

   
	public static void main(String[] args) {  

		String str = DigitalUtils.byteToComplementHexString((byte) -100, 2);
		System.out.println(str);
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < list.size(); i++){
			System.out.println(i);
		}
	}
}
