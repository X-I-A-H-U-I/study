package com.github.xia.security.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 接口对象转换类
 *
 * @ClassName: ValueConvert
 * @author XIA
 * @date 2020-1-15
 */
public class ValueConvert {
    
    private Log log = LogFactory.getLog(this.getClass());
    
    /**
     *
    //接口最后更新时间延迟时间段
     */
    private final static int DELAY_MIN = 5;
    
    /**
     * 由数据库取出值转换成String类型
     * 
     * @param mapvalue
     * @return
     * @throws Exception
     */
    public static String dbValueToString(Object mapvalue) throws Exception {
        if (mapvalue != null) {
            return (String) mapvalue;
        }
        return null;
    }
    
    /**
     * 由数据库取出值转换成String类型
     * 
     * @param mapvalue
     * @return
     * @throws Exception
     */
    public static String dbValueToString2(Object mapvalue) throws Exception {
        if (mapvalue != null) {
            return mapvalue.toString();
        }
        return null;
    }
    
    /**
     * 由数据库取出值转换成Long类型
     * 
     * @param mapvalue
     * @return
     * @throws Exception
     */
    public static Long dbValueToLong(Object mapvalue) throws Exception {
        if (mapvalue != null) {
            return (Long) mapvalue;
        }
        return null;
    }
    
    /**
     * 由数据库取出值转换成Double类型
     * 
     * @param mapvalue
     * @return
     * @throws Exception
     */
    public static Double dbValueToDouble(Object mapvalue) {
        if (mapvalue != null) {
            return Double.valueOf(mapvalue.toString());
        }
        return null;
    }
    
    /**
     * 由数据库取出值转换成BigDecimal类型
     * 
     * @param mapvalue
     * @return
     * @throws Exception
     */
    public static BigDecimal dbValueToBigDecimal(Object mapvalue) throws Exception {
        if (mapvalue != null) {
            return (BigDecimal) mapvalue;
        }
        return null;
    }
    
    /**
     * 由数据库取出值转换成XMLGregorianCalendar类型，
     * 
     * @param mapvalue 必须是xxxx
     *            -xx-xx格式的日期字串
     * @return
     * @throws Exception
     */
    public static XMLGregorianCalendar dbValueToXMLGregorianCalendar(Object mapvalue) throws Exception {
        if (mapvalue != null) {
            String datestr = (String) mapvalue;
            Date date = DateFormatUtil.parse(datestr, "yyyy-MM-dd");
            DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return dataTypeFactory.newXMLGregorianCalendar(gc);
        }
        return null;
    }
    
    /**
     * XMLGregorianCalendar转Date
     * 
     * @param obj 必须是xxxx
     *            -xx-xx格式的日期字串
     * @return
     * @throws Exception
     */
    public static Date xMLGregorianCalendarToDate(XMLGregorianCalendar obj) {
        return obj.toGregorianCalendar().getTime();
    }
    
    /**
     * Date转String
     * 
     * @param date
     *            日期
     * @return
     */
    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    /**
     * XMLGregorianCalendar转String
     * 
     * @param obj 必须是xxxx
     *            -xx-xx格式的日期字串
     * @return
     * @throws Exception
     */
    public static String xMLGregorianCalendarToString(XMLGregorianCalendar obj) {
        Date date = obj.toGregorianCalendar().getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    
    /**
     * Date转换成XMLGregorianCalendar类型，
     * 
     * @param date 必须是xxxx
     *            -xx-xx格式的日期字串
     * @return
     * @throws Exception
     */
    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) throws Exception {
        if (date != null) {
            DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return dataTypeFactory.newXMLGregorianCalendar(gc);
        }
        return null;
    }
    
    public static XMLGregorianCalendar stringToXMLGregorianCalendar(String dateStr) throws Exception {
        if (dateStr != null && !"".equals(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int  dateLen=19;
            if (dateStr.length() < dateLen) {
                dateStr = dateStr + ":00";
            }
            Date date = sdf.parse(dateStr);
            ;
            DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.getTime());
            return dataTypeFactory.newXMLGregorianCalendar(gc);
        }
        return null;
    }
    
    public static XMLGregorianCalendar getEndDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, -1);
        return dateToXMLGregorianCalendar(calendar.getTime());
    }
    
    /**
     * 新增1个方法，传入时间间隔获取数据
     * 
     * @return
     * @throws Exception
     */
    public static XMLGregorianCalendar getEndDateForHours() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 0);
        return dateToXMLGregorianCalendar(calendar.getTime());
    }
    
    /**
     * 转换xmlDate 为 日期字符串 xml date to date
     * 
     * @param obj
     * @return datestr
     */
    public static String xmlDateToString(XMLGregorianCalendar obj) {
        if (null != obj) {
            StringBuffer str = new StringBuffer(20);
            str.append("REPLACE(SUBSTR('")
               .append(obj)
               .append("',1,19),'T',' ')");
            return str.toString();
        }
        return "null";
    }
    
    /**
     * 转换xmlDate 为 日期 xml date to date
     * 
     * @param obj
     * @return date
     */
    public static String xmlDateToDate(XMLGregorianCalendar obj) {
        if (null != obj) {
            StringBuffer str = new StringBuffer(20);
            str.append("TO_DATE(REPLACE(SUBSTR('")
               .append(StringUtils.trim(obj.toString()))
               .append("',1,19),'T',''),'yyyy-mm-dd hh24:mi:ss')");
            return str.toString();
        }
        return "null";
    }
    
    /**
     * 根据接口传递的对象，处理为数据库写入对象
     * 
     * @param obj
     *            传入对象
     * @param isToDate
     *            是否保存为时间格式 ( true返回date格式, false返回String格式)
     * @return
     */
    public static Object itObjToObj(Object obj, Boolean isToDate) {
        if (null == isToDate) {
            isToDate = false;
        }
        if (null != obj) {
            if (obj instanceof XMLGregorianCalendar) {
                if (isToDate) {
                    return ValueConvert.xmlDateToDate((XMLGregorianCalendar) obj);
                }
                else {
                    return ValueConvert.xmlDateToString((XMLGregorianCalendar) obj);
                }
            }
            else if (obj instanceof String) {
                return "'" + obj + "'";
            }
            else {
                return obj;
            }
        }
        return "NULL";
    }
    
    /**
     * obj2Model:( ). <br/>
     * TODO( 对象转换为字符，数字型方法 ).<br/>
     * 
     * @author XIA
     * @param itObj
     *            1 字符型， 2 Long， 3 Date， 4 Double
     * @param type
     * @return
     * @throws ParseException
     */
    public static Object obj2Model(Object itObj, int type) throws Exception {
        Object obj = null;
        if (itObj != null) {
            if (type == 1L) {
                obj = itObj.toString();
            }
            else if (type == 2L) {
                if ((itObj instanceof String) && (StringUtils.isNotBlank((String) itObj))) {
                    obj = Long.valueOf(itObj.toString());
                }
                else if (itObj instanceof BigDecimal) {
                    obj = new Long(itObj.toString());
                }
                else {
                    obj = new Long(0L);
                }
            }
            else if (type == 3L) {
                if (itObj instanceof XMLGregorianCalendar) {
                    XMLGregorianCalendar c = (XMLGregorianCalendar) itObj;
                    obj = ValueConvert.xMLGregorianCalendarToDate(c);
                }
            }
            else if (type == 4L) {
                obj = Double.valueOf(itObj.toString());
            }
        }
        return obj;
    }
    

    /**
     *
    //添加延迟
     * @param time
     * @return
     * @throws Exception
     */
    public static Date addDelayTime(Date time) throws Exception{
        if(time != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.MINUTE,Integer.valueOf(DELAY_MIN));
            return calendar.getTime();
        }
        return null;
    }
    
    public static Date getStartDateForHours(Date date,Integer beforeTime) throws Exception{
        //加入时间补偿
        int delayTime = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,beforeTime);
        delayTime=Integer.valueOf(DELAY_MIN);
        calendar.add(Calendar.MINUTE, delayTime);
        return calendar.getTime();
    }

    public static Calendar getCalendarVal(Date date) {
        if(date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        
        return null;
    }
}
