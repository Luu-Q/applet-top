package com.applet.common;
 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateTest {
	public static void main(String[] args) throws ParseException {
		String start_time = "2019-05-27";
		String end_time = "2020-04-26";
		getDateOfWeek(start_time, end_time);  
	}
	
	/** 
     * @Title: getDateOfWeek 
     * @Description: 获取两个时间内所有周之间的时间段，并且获得是今年第几周（同一年内） 
     * @param start_time    开始时间 
     * @param end_time      结束时间 
     */  
    public static void getDateOfWeek(String start_time, String end_time) {  
    	try {  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            Calendar s_c = Calendar.getInstance();  
            Calendar e_c = Calendar.getInstance();  
            java.util.Date s_time = sdf.parse(start_time);  
            java.util.Date e_time = sdf.parse(end_time);  
            
            String first_week = getFirstDayOfWeek(start_time);//取得开始日期指定日期所在周的第一天
	        String last_week = getLastDayOfWeek(end_time);//取得结束日期指定日期所在周的最后一天
	        
            s_c.setTime(s_time);  
            e_c.setTime(e_time);  
            int year = s_c.get(Calendar.YEAR);  
  
            int currentWeekOfYear = s_c.get(Calendar.WEEK_OF_YEAR);  
            int currentWeekOfYear_e = e_c.get(Calendar.WEEK_OF_YEAR);  
              
            if (currentWeekOfYear_e == 1) {  
                currentWeekOfYear_e = 53;  
            }  
              
            int j = 12;  
            for (int i=0; i < currentWeekOfYear_e; i++) {  
                int dayOfWeek = e_c.get(Calendar.DAY_OF_WEEK) - 2;  
                e_c.add(Calendar.DATE, - dayOfWeek); //得到本周的第一天  
                   
                String s_date = sdf.format(e_c.getTime());  
                e_c.add(Calendar.DATE, 6);  //得到本周的最后一天  
                   
                String e_date = sdf.format(e_c.getTime());  
                e_c.add(Calendar.DATE, -j); //减去增加的日期  
                  
                //只取两个日期之间的周  
                if(currentWeekOfYear == currentWeekOfYear_e - i + 2){  
                    break;  
                }  
                
                //只取两个日期之间的周
	            if(compareDate(first_week, s_date) && compareDate(s_date, last_week)
	            		&& compareDate(first_week, e_date) && compareDate(e_date, last_week)){
	            	//超过选择的日期，按选择日期来算
	            	if(!compareDate(start_time, s_date)){
	 	            	s_date = start_time;
	 	            }
	            	if(!compareDate(e_date, end_time)){
	 	            	e_date = end_time;
	 	            }
	            	String s = year + "年的第" + (currentWeekOfYear - i) + "周" + "(" + s_date + "至" + e_date + ")";     
	                System.out.println(s);    
	            }
               
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
	/** 
   	 * 取得指定日期所在周的第一天 
   	 */ 
   	 public static String getFirstDayOfWeek(String date) { 
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date time = sdf.parse(date);
			Calendar c = new GregorianCalendar(); 
		   	c.setFirstDayOfWeek(Calendar.MONDAY); 
		   	c.setTime(time); 
		   	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
		   	return sdf.format(c.getTime()); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
        
	   	
   	 }
 
   	 /** 
   	 * 取得指定日期所在周的最后一天 
   	 */ 
   	 public static String getLastDayOfWeek(String date) { 
   		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date time = sdf.parse(date);
			Calendar c = new GregorianCalendar(); 
		   	c.setFirstDayOfWeek(Calendar.MONDAY); 
		   	c.setTime(time); 
		   	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday 
		   	return sdf.format(c.getTime()); 
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
   	 }
   	 
   	/**
 	 * compareDate方法
 	 * <p>方法说明：
 	 * 		比较endDate是否是晚于startDate；
 	 * 			如果是，返回true， 否则返回false
 	 * </p>
 	 */
 	public static boolean compareDate(String startDate, String endDate) {
 		try {
 			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 			java.util.Date date1 = dateFormat.parse(startDate);
 			java.util.Date date2 = dateFormat.parse(endDate);
 			if (date1.getTime() > date2.getTime())
 				return false;
 			return true; //startDate时间上早于endDate
 			
 		} catch (Exception e) {
 			return false; 
 		}
 	}
}