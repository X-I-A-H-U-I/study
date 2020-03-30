package com.github.xia.security.common.util;

import com.github.xia.security.common.exception.BaseException;
import org.apache.commons.lang.StringUtils;

import javax.sql.rowset.serial.SerialException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用对象处理类
 * 
 * @author XIA
 * @date 2020.01.15
 */
public class DfUtils {
	/**
	 * 整数正则
	 */
	private static Pattern NUMBER_PATTERN=Pattern.compile("^[-\\+]?[\\d]*$");
	/**
	 * 四位小数正则
	 */
	private static Pattern FOUR_NUMBER_PATTERN=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,4})?$");

	public static String dateFM3(Date d) {
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
		return sdf3.format(d);
	}

	public static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

	public static String getYYYYMMDD(Object obj) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		if (obj == null) {
			return "";
		}
		if (obj instanceof Date) {
			return sdf2.format((Date) obj);
		}

		return obj.toString().trim();
	}

	/**
	 * 生成随机数
	 */
	public static String getRandom() {
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdfDay.format(new Date());
		return dateStr+String.valueOf((int)(Math.random()*10000));
	}


	public static String trim(Object obj) {

		if (obj == null) {
			return "";
		}
		if (obj instanceof String) {
			if (!StringUtils.isNotBlank((String) obj)) {
				return "";
			}
			if ("null".equals(obj.toString()) || "NULL".equals(obj.toString()) || "undefined".equals(obj.toString())) {
				return "";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (obj instanceof XMLGregorianCalendar) {
			Date date = ((XMLGregorianCalendar) obj).toGregorianCalendar().getTime();
			return sdf.format(date);
		}
		if (obj instanceof Date) {
			return sdf.format((Date) obj);
		}

		return obj.toString().trim();
	}

	public static String replaceBlank(Object str) {
		String newstr = trim(str);
		String dest = "";
		if (str != null) {

			Matcher m = p.matcher(newstr);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static Double doubleVal(Object obj) {
		if (obj == null) {
			return 0D;
		}
		if (obj instanceof String) {
			if ("".equals(obj)) {
				return 0D;
			}
			return Double.valueOf((String) obj);
		}
		if (obj instanceof Long) {
			return Double.valueOf((Long) obj);
		}
		if (obj instanceof Integer) {
			return Double.valueOf((Integer) obj);
		}
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).doubleValue();
		}
		return Double.valueOf(obj.toString());
	}

	public static Long longVal(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof String) {
			if (StringUtils.isBlank((String) obj)) {
				return null;
			}
			if ("".equals(obj)) {
				return null;
			}
			if ("是".equals(obj)) {
				return 1L;
			} else if ("否".equals(obj)) {
				return 0L;
			}
			return Long.valueOf((String) obj);
		}
		if (obj instanceof Long) {
			return Long.valueOf((Long) obj);
		}
		if (obj instanceof Integer) {
			return Long.valueOf((Integer) obj);
		}
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).longValue();
		}
		return Long.valueOf(obj.toString());
	}

	public static Date dateVal(Object obj) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if ("".equals(trim(obj))) {
			return null;
		}

		if (obj == null) {
			return null;
		}

		if (obj instanceof Date) {
			return (Date) obj;
		}
		if (obj instanceof String) {
			int lenMax = 10;
			if (obj.toString().length() <= lenMax) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				if (obj.toString().contains("T")) {
					int lenTMax = 19;
					if (obj.toString().length() >= lenTMax) {
						String newStr = obj.toString().substring(0, 18).replace("T", " ");
						return sdf.parse(newStr);
					}
				}
			}
			return sdf.parse((String) obj);
		}
		if (obj instanceof Calendar) {
			return ((Calendar) obj).getTime();
		}
		if (obj instanceof XMLGregorianCalendar) {
			return ValueConvert.xMLGregorianCalendarToDate((XMLGregorianCalendar) obj);
		}

		return (Date) obj;
	}

	public static XMLGregorianCalendar xMLGregorianCalendarVal(Object obj) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		if (obj == null) {
			return null;
		}

		if (obj.toString().length() <= 10) {
			Date objD = sdf2.parse((String) obj);
			return ValueConvert.dateToXMLGregorianCalendar((Date) objD);
		}
		if (obj instanceof String) {
			Date objD = sdf.parse((String) obj);
			return ValueConvert.dateToXMLGregorianCalendar((Date) objD);
		}
		if (obj instanceof Date) {
			return ValueConvert.dateToXMLGregorianCalendar((Date) obj);
		}
		if (obj instanceof XMLGregorianCalendar) {
			return (XMLGregorianCalendar) obj;
		}
		return (XMLGregorianCalendar) obj;
	}

	public static BigDecimal bigDecimalVal(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		if (obj instanceof String) {
			return new BigDecimal((String) obj);
		}
		if (obj instanceof Long) {
			return new BigDecimal((Long) obj);
		}
		if (obj instanceof Double) {
			return new BigDecimal((Double) obj);
		}
		return (BigDecimal) obj;
	}

	public static Calendar calendarVal(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Date) {
			return ValueConvert.getCalendarVal((Date) obj);
		}
		return (Calendar) obj;
	}

	public static Boolean booleanVal(Object obj) throws Exception {
		if (obj == null) {
			return Boolean.FALSE;
		}
		if (obj instanceof String) {
			if ("1".equals((String) obj) || "true".equals((String) obj)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
		if (obj instanceof Long) {
			if ((Long) obj == 1L) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
		return (Boolean) obj;
	}

	public static Integer integerVal(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		return new Integer(obj.toString());
	}

	public static String toObjsString(Object[] obj) {
		if (null == obj) {
			return null;
		}
		String rs = "";
		for (int i = 0; i < obj.length; i++) {
			String s = obj[i] != null ? obj[i].toString() : null;
			if (i != obj.length - 1) {
				s += ",";
			}
			rs += s;
		}
		return rs;

	}

	/**
	 * Clob读取和转换 2017-4-26
	 * 
	 * @param cl
	 * @return
	 * @throws SQLException
	 * @throws SerialException
	 */
	public static Clob strToClob(String cl) throws SerialException, SQLException {
		if (cl == null) {
			return null;
		}
		if (!StringUtils.isNotBlank(cl)) {
			return new javax.sql.rowset.serial.SerialClob("".toCharArray());
		}
		return new javax.sql.rowset.serial.SerialClob(cl.toCharArray());
	}

	public static String clobToStr(Clob clob) throws SQLException, IOException {
		String reString = "";
		// 得到流
		Reader is = clob.getCharacterStream();
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		if (null != br) {
			br.close();
		}
		reString = sb.toString();
		return reString;
	}

	public static boolean isNotBank(Object obj) {
		boolean rs = Boolean.TRUE;
		if ("".equals(DfUtils.trim(obj)) || "null".equals(DfUtils.trim(obj))) {
			rs = Boolean.FALSE;
		}
		return rs;
	}

	public static boolean isBank(Object obj) {
		boolean rs = Boolean.TRUE;
		if (!"".equals(DfUtils.trim(obj))) {
			rs = Boolean.FALSE;
		}
		return rs;
	}

	/**
	 * 中文解码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlDecode(String str) {
		String rsMsg = "";
		try {
			rsMsg = URLDecoder.decode(URLDecoder.decode(str, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rsMsg;
	}

	/**
	 * 中文编码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlEncode(String str) {
		String rsMsg = "";
		try {
			rsMsg = URLEncoder.encode(URLEncoder.encode(str, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rsMsg;
	}

	/**
	 * 复制源对象和目标对象的属性值(除ID) source源对象 target新对象 objs不复制的属性
	 */
	public static void copyObject(Object source, Object target, String[] objs) throws Exception {
		// 得到源对象的Class
		Class sourceClass = source.getClass();
		getClassFieldAndMethod(source, target, sourceClass, objs, false);
	}

	public static void copyObjectContainNull(Object source, Object target, String[] objs) throws Exception {
		// 得到源对象的Class
		Class sourceClass = source.getClass();
		getClassFieldAndMethod(source, target, sourceClass, objs, true);
	}

	public static void copyMapToObject(Object source, Map<String, Object> map, String[] objs) throws Exception {
		setClassFiled(source, map, objs);
	}

	private static void setClassFiled(Object source, Map<String, Object> map, String[] objs) throws Exception {
		// 得到源对象的Class
		Class sourceClass = source.getClass();
		// 得到源Class对象的所有属性
		Field[] sourceFields = sourceClass.getDeclaredFields();
		for (Field sourceField : sourceFields) {
			// 属性名
			String name = sourceField.getName();
			// 不复制属性
			if (objs != null && objs.length > 0) {
				for (int i = 0; i < objs.length; i++) {
					if (name.equals(objs[i])) {
						break;
					}
				}
			}
			sourceField.setAccessible(true);
			// 属性类型
			Class type = sourceField.getType();
			Object value = map.get(name);
			if (DfUtils.isNotBank(value)) {
				String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
				// 属性对应的set方法
				Method setMethod = sourceClass.getMethod("set" + methodName, type);
				// 执行目标对象的set方法
				setMethod.invoke(source, value);
			}
		}
	}

	/**
	 * 
	 * @param source      源对象
	 * @param target      目标对象
	 * @param sourceClass 源对象class
	 * @param objs        不复制的属性
	 * @param isCopyNull  是否复制数据为空的属性
	 * @throws Exception
	 */
	private static void getClassFieldAndMethod(Object source, Object target, Class sourceClass, String[] objs,
			boolean isCopyNull) throws Exception {
		// 得到目标对象的Class
		Class targetClass = target.getClass();

		// 得到源Class对象的所有属性
		Field[] sourceFields = sourceClass.getDeclaredFields();
		// 得到目标Class对象的所有属性
		// Field[] targetFields = targetClass.getDeclaredFields();
		// 获取目标类的所有方法-基类+本类【不考虑源的父类属性，如果需要可以参考目标类父类方法进行扩展】
		List<Field> fieldList = new ArrayList<>();
		while (targetClass != null && !targetClass.getName().toLowerCase().equals("java.lang.object")) {
			fieldList.addAll(Arrays.asList(targetClass.getDeclaredFields()));
			targetClass = targetClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		Field[] targetFields = new Field[fieldList.size()];
		fieldList.toArray(targetFields);
		for (Field sourceField : sourceFields) {
			sourceField.setAccessible(true);
			// 判断属性是否静态
			if (Modifier.isStatic(sourceField.getModifiers())) {
				continue;
			}
			// 属性名
			String name = sourceField.getName();
			// 属性类型
			@SuppressWarnings("rawtypes")
			Class type = sourceField.getType();
			String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
			// 得到属性对应get方法
			@SuppressWarnings("unchecked")
			Method getMethod = sourceClass.getMethod("get" + methodName);
			// 执行源对象的get方法得到属性值
			Object value = getMethod.invoke(source);
			for (Field targetField : targetFields) {
				// 目标对象的属性名
				String targetName = targetField.getName();
				boolean isCheck = false;
				if (objs != null && objs.length > 0) {
					for (int i = 0; i < objs.length; i++) {
						if (targetName.equals(objs[i])) {
							isCheck = true;
							break;
						}
					}
				}
				if (!isCheck && targetName.equals(name)) {
					if (isCopyNull || (!isCopyNull && null != value)) {
						Method setMethod;
						try {
							// 属性对应的set方法
							setMethod = target.getClass().getDeclaredMethod("set" + methodName, type);
						} catch (Exception e) {
							setMethod =target.getClass().getSuperclass().getDeclaredMethod("set" + methodName, type);
						}
						// 执行目标对象的set方法
						setMethod.invoke(target, value);
					}
					break;
				}
			}
		}
		if (sourceClass.getSuperclass() != null) {
			// 递归查询父类
			getClassFieldAndMethod(source, target, sourceClass.getSuperclass(), objs, isCopyNull);
		}
	}
	
	/**
	 * 获取当前时间前day的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取当前时间前year的日期（前几年）
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date getNextYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -year);
		date = calendar.getTime();
		return date;
	}

	public static String getObjsVal(Object[] objs) {
		String rs = "";
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				rs += objs[i];
				if (i != objs.length - 1) {
					rs += ",";
				}
			}
		}
		return rs;
	}

	/**
	 * 获取大写字母位置数组
	 * 
	 * @param filedName
	 * @return
	 */
	public static Object[] getDBCloumnByfiledName(String filedName) {
		if (isNotBank(filedName)) {
			List<Integer> indexs = new ArrayList<Integer>();
			// 1.获取大写字母对应序号
			char[] chars = filedName.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (Character.isUpperCase(c)) {
					indexs.add(i);
				}
			}
			return indexs.toArray();
		}
		return null;
	}

	public static String getDBCloumByIndex(String filedName) throws Exception {
		Object[] objs = getDBCloumnByfiledName(filedName);
		StringBuffer sbf = new StringBuffer(10);
		if (objs != null && objs.length > 0) {
			int k = 0;
			for (int i = 0; i < objs.length; i++) {
				int index = DfUtils.integerVal(objs[i]);
				if (index != 0) {
					sbf.append(filedName.substring(k, index));
					k = index;
					sbf.append("_");
					if (i == objs.length - 1) {
						sbf.append(filedName.substring(index, filedName.length()));
					}
				}
			}
		} else {
			sbf.append(filedName);
		}
		return sbf.toString();
	}

	/**
	 * map转换为字符串xml
	 * 
	 * @param rsMap
	 * @return
	 */
	public static String bulidMapToString(Map<String, Object> rsMap) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		Set<String> set = rsMap.keySet();
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = rsMap.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * map转换为List
	 * 
	 * @param rsMap
	 * @return
	 */
	public static List<Object> bulidMapToList(Map<String, Object> rsMap) {
		List<Object> paramList = new ArrayList<Object>();
		if (null != rsMap && !rsMap.isEmpty()) {
			for (String key : rsMap.keySet()) {
				paramList.add(rsMap.get(key));
			}
		}
		return paramList;
	}

	/**
	 * 查询最近10年
	 * 
	 * @return
	 */
	public static List<Map<String, Long>> findTenYear() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Long year = DfUtils.longVal(sdf.format(date));
		List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
		for (int i = 0; i < 10; i++) {
			@SuppressWarnings("AlibabaCollectionInitShouldAssignCapacity")
			Map<String, Long> map = new HashMap<String, Long>(10);
			if (i == 0) {
				map.put("year", year);
				list.add(map);
			} else {
				year = year - 1;
				map.put("year", year);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 查询当前时间前后几年
	 * 
	 * @param m 前几年
	 * @param n 后几年
	 * @return
	 */
	public static List<Map<String, Long>> findAroundYear(int m, int n) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Long year = DfUtils.longVal(sdf.format(date));
		List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
		for (int i = 1; i < n; i++) {
			@SuppressWarnings("AlibabaCollectionInitShouldAssignCapacity")
			Map<String, Long> map = new HashMap<String, Long>(16);
			if (i == 1) {
				year = year + n - 1;
			} else {
				year = year - 1;
			}
			map.put("year", year);
			list.add(map);
		}
		year = DfUtils.longVal(sdf.format(date));
		for (int i = 0; i < m; i++) {
			@SuppressWarnings("AlibabaCollectionInitShouldAssignCapacity")
			Map<String, Long> map = new HashMap<String, Long>(16);
			if (i == 0) {
				map.put("year", year);
				list.add(map);
			} else {
				year = year - 1;
				map.put("year", year);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 提供截取字符串方法，多余的用...展示
	 * 
	 * @param inS
	 * @param length
	 * @return
	 */
	public static String split(Object inS, int length) {
		String inStr = trim(inS);
		if (isBank(inStr)) {
			return "";
		}
		if (inStr.length() > length) {
			return inStr.substring(0, length) + "...";
		} else {
			return inStr;
		}
	}

	/**
	 * 提供对 ' & 特殊符号的处理
	 * 
	 * @param inS
	 * @return
	 */
	public static String chr3839(Object inS) {
		String inStr = trim(inS);
		if (isBank(inStr)) {
			return "";
		}
		if (inStr.contains("'")) {
			inStr = inStr.replace("'", "'||chr(39)||'");
		}
		if (inStr.contains("&")) {
			inStr = inStr.replace("'", "'||chr(38)||'");
		}
		return inStr;

	}

	/**
	 * 文件分隔符分割
	 * 
	 * @param filePath
	 * @return
	 */
	public static String pathTrim(String filePath) {
		if (File.separatorChar == '/') {
			filePath = StringUtils.replace(filePath, "\\", "/");
		} else {
			filePath = StringUtils.replace(filePath, "/", "\\");
		}
		return filePath;
	}

	/**
	 * double 科学计数法
	 * 
	 * @param d
	 * @return
	 */
	public static String doubleParse(Double d) {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		return nf.format(d);
	}

	/**
	 * 获取UUID字符串
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 处理特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String stringParse(String str) {
		str = str.replace("<", "＜");
		str = str.replace(">", "＞");
		str = str.replace("=", "＝");
		str = str.replace("+", "＋");
		str = str.replace("*", "＊");
		str = str.replace("&", "＆");
		str = str.replace("#", "＃");
		str = str.replace("%", "％");
		return str;
	}

	/***
	 * 把中文替换为指定字符<br>
	 * 
	 * @param source
	 * @return
	 */
	public static String replaceChinese(String source) {
		if (DfUtils.isBank(source)) {
			return null;
		}
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(source);
		String repickStr = mat.replaceAll("G");
		repickStr = repickStr.replace(" ", "");
		repickStr = repickStr.replace(".", "");
		repickStr = repickStr.replace("（", "");
		repickStr = repickStr.replace("）", "");
		return repickStr;
	}

	/**
	 * 根据操作数据返回影响行数判断操作是否成功(单行)
	 * 
	 * @param flag 执行单行增改操作的返回影响行数
	 * @throws BaseException
	 */
	public static void checkReturn(int flag) throws BaseException {
		if (flag < 1) {
			throw new BaseException(ErrorCode.DATA_ERR.getErrCode(), "操作失败,请联系管理员");
		}
	}

	/**
	 * 校验是否为整数
	 * 
	 * @param input
	 * @return true 是 false否
	 */
	public static boolean checkNum(CharSequence input) {

		return NUMBER_PATTERN.matcher(input).matches();
	}

	/**
	 * 判断小数点后4位的数字的正则表达式 (大于0)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String str) {
		if (DfUtils.isBank(str)) {
			return false;
		}
		if ("0".equals(str)) {
			return false;
		}

		Matcher match = FOUR_NUMBER_PATTERN.matcher(str);
		if (!match.matches()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 校验字符串是否由数字和字母组成
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}

	/**
	 * 华为短信校验是否电话号码
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1) {
            if (chars[start] == '0' && chars[start + 1] == 'x') {
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                        && (chars[i] < 'a' || chars[i] > 'f')
                        && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
              // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent   
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                || chars[i] == 'L') {
                // not allowing L with an exponent
                return foundDigit && !hasExp;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }
	
	 /**
     * bean copy 方法，提供sourceClazz 到 targetClazz 的拷贝方法
     * 拷贝原则：
     * 1.排除ID的拷贝
     * 2.属性值统一大写匹配（即：属性值不区分大小写）
     * @param sourceObj 来源class
     * @param targetObj 目标class
     * @param isAllUpper 是否都大写
     * @throws Exception 
     */
    public static void BeansCopy(Object sourceObj,Object targetObj,boolean isAllUpper){
        Field[] fields_t = targetObj.getClass().getDeclaredFields();
        Field[] fields_s = sourceObj.getClass().getDeclaredFields();
        for (int i = 0; i < fields_t.length; i++) {
            Field t = fields_t[i];
            String tName = t.getName();
            for (int j = 0; j < fields_s.length; j++) {
                Field s = fields_s[j];
                String sName = s.getName();
                if(tName.toUpperCase().equals(sName.toUpperCase()) && !sName.toUpperCase().equals("ID")){
                    t.setAccessible(true);
                    try {
						setVoValue(t,s,sourceObj,targetObj,isAllUpper);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    continue;
                }
            }
        }
    }
    
    /**
                * 设置对象的值
     * @param t
     * @param s
     * @param sourceObj
     * @param targetObj
     * @throws Exception
     */
    private static void setVoValue(Field t,
                                   Field s,
                                   Object sourceObj,
                                   Object targetObj,
			boolean isAllUpper) throws Exception {
		// 目标field
		String typet = t.getType().getSimpleName();
		// 源field
		String types = s.getType().getSimpleName();

		// 源字符首字母大写
		String name = s.getName();
		String title = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
		if (isAllUpper) {
			title = title.toUpperCase();
		}
//		if (!typet.equals(types)) {
//			logger.error("bean copy error: " + t.getName() + "字段类型不一致，存在copy失败风险");
//		}
		Object value = getValue(title, typet, s, sourceObj);
		if (value == null) {
			return;
		}
		if (typet.equals("String")) {
			t.set(targetObj, DfUtils.trim(value));
		} else if (typet.equals("BigDecimal")) {
			t.set(targetObj, DfUtils.bigDecimalVal(value));
		} else if (typet.equals("Calendar")) {
			t.set(targetObj, DfUtils.calendarVal(value));
		} else if (typet.equals("Date")) {
			t.set(targetObj, DfUtils.dateVal(value));
		} else if (typet.equals("Long")) {
			t.set(targetObj, DfUtils.longVal(value));
		} else if (typet.equals("Double")) {
			t.set(targetObj, DfUtils.doubleVal(value));
		} else if (typet.equals("Boolean")) {
			t.set(targetObj, DfUtils.booleanVal(value));
		} else if (typet.equals("Integer")) {
			t.set(targetObj, DfUtils.integerVal(value));
		} else if (typet.equals("int")) {
			t.set(targetObj, DfUtils.integerVal(value));
		} else if (typet.equals("long")) {
			t.set(targetObj, DfUtils.longVal(value));
		} else if (typet.equals("double")) {
			t.set(targetObj, DfUtils.doubleVal(value));
		} else if (typet.equals("XMLGregorianCalendar")) {
			t.set(targetObj, DfUtils.xMLGregorianCalendarVal(value));
		} else if (typet.equals("Clob")) {
			t.set(targetObj, DfUtils.StrToClob(value.toString()));
		}
	}
    /**
     * 获取filed对应值
     * @param title
     * @param typet
     * @param s
     * @param sourceObj
     * @return
     * @throws Exception
     */
    private static Object getValue(String title,String typet,Field s,Object sourceObj) throws Exception{
        Object value = null;
        Method m = sourceObj.getClass().getMethod("get" + title);
//        if(typet.equals("String")){
//            value = (String) m.invoke(sourceObj);
//        }else if(typet.equals("BigDecimal")){
//            value = (BigDecimal) m.invoke(sourceObj);
//        }else if(typet.equals("Calendar")){
//            value = (Calendar) m.invoke(sourceObj);
//        }else if(typet.equals("Date")){
//            value = (Date) m.invoke(sourceObj);
//        }else if(typet.equals("Long") || typet.equals("long")){
//            value = longVal(m.invoke(sourceObj));
//        }else if(typet.equals("Double") || typet.equals("double")){
//            value = (Double) m.invoke(sourceObj);
//        }else if(typet.equals("Boolean")){
//            value = (Boolean) m.invoke(sourceObj);
//        }else if(typet.equals("Integer") || typet.equals("int")){
//            value = (Integer) m.invoke(sourceObj);
//        }else if(typet.equalsIgnoreCase("XMLGregorianCalendar")){
//            value = (Date) m.invoke(sourceObj);
//        }else if(typet.equals("Clob")){
//            value = (Clob) m.invoke(sourceObj);
//        }
        return m.invoke(sourceObj);
    }
    /**
     * Clob读取和转换
     * 2017-4-26
     * @param cl
     * @return
     * @throws SQLException 
     * @throws SerialException 
     */
    public static Clob StrToClob(String cl) throws SerialException, SQLException {
        if(cl == null){
            return null;
        }
        if(!StringUtils.isNotBlank(cl)){
            return new javax.sql.rowset.serial.SerialClob("".toCharArray());
        }
        return new javax.sql.rowset.serial.SerialClob(cl.toCharArray());
    }
}
