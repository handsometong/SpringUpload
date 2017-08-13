package utils;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * 字符串处理工具类
 */
public class StringUtils {

    private StringUtils() {
    }
    
    
    /**
	 * 格式化字符串
	 * 
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 * 
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}
    
    /**
     * 对URL中传递的参数进行转码，避免传递中文时出现乱码
     * add by chenmk 2010.12.08
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String decodeUrl(String str) throws Exception{
        if(org.apache.commons.lang3.StringUtils.isEmpty(str))
            return null;
        return new String(str.getBytes("ISO-8859-1"), "utf-8");
    }
    
    /**
     * 将双引号转换成ASCII码，防止其外层嵌套双引号时出问题（即引号后面的内容被截取）
     * add by chenmk 2010.10.11
     * 
     * @param str
     * @return
     */
    public static String convertDoubleQuotation2Ascii(String str){
        if(StringUtils.isEmpty(str))
            return null;
        
        return str.replaceAll("\"", "&#34;");
    }

    /**
     * 将传入的ID列表转换成数据库的字符串，如：'aaaa','bbbb'
     * add by chenmk 2008.07.23
     *
     * @param strList
     * @return
     */
    public static String collToDbString(Collection<String> strList) {
        StringBuffer result = new StringBuffer();
        if (strList == null || strList.size() == 0) {
            return result.toString();
        }
        for (String str : strList) {
            str = replaceString(str, "'", "''", false);
            result.append("'").append(str).append("'").append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * 将字符串数据转变成SQL 中IN的语句，如
     * [a,b,c]->'a','b','c'
     *
     * @param array
     * @return
     */
    public static String arrayToDBStr(String[] array) {
        if (array == null) {
            return "''";
        }
        StringBuffer result = new StringBuffer();
        String split = "";
        for (String str : array) {
            str = replaceString(str, "'", "''", false);
            result.append(split).append("'").append(str).append("'");
            split = ",";
        }
        return result.toString();
    }

    /**
     * 将逗号分隔的字符串SQL 中IN的语句，如
     * a,b,c->'a','b','c'
     *
     * @param srcStr
     * @return
     */
    public static String stringToDBStr(String srcStr) {
        String[] tempAttr = StringUtils.stringToArray(srcStr, ',', true);
        return arrayToDBStr(tempAttr);
    }

    /**
     * 将字符串数据转换为一个List集合
     *
     * @param array
     * @return
     */
    public static List<String> arrayToList(String[] array) {
        List<String> result = new ArrayList<String>();

        if (array == null || array.length == 0)
            return result;


        for (String item : array) {
            result.add(item);
        }
        return result;
    }


    /**
     * 数字字符串转化为整数
     *
     * @param srcStr       String 待转化的数字字符串
     * @param defaultValue int 当srcStr为null,空字符串,或者不能转换为数字时返回的缺省值
     * @return int 返回由数字字符串转化成的数字，当srcStr为空或空字符串时，返回缺省值defaultValue
     */
    public static int getInt(String srcStr, int defaultValue) {
        if (StringUtils.isEmpty(srcStr)) return defaultValue;
        int result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数字字符串转化为整数，在转化时发生异常，则返回0
     *
     * @param srcStr String 待转化的数字字符串
     * @return int 返回由数字字符串转化成的整数，当srcStr为空或空字符串时，返回0
     */
    public static int getInt(String srcStr) {
        return getInt(srcStr, 0);
    }

    /**
     * 功能描述:
     * 判断传入的字符串是否为数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!Character.isDigit(a)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数字字符串转化为双精数字
     *
     * @param srcStr       String 待转化的数字字符串
     * @param defaultValue double 当srcStr为空或空字符串时返回的缺省值
     * @return double 返回由数字字符串转化成的数字，当srcStr为空或空字符串时，则返回缺省值defaultValue
     */
    public static double getDouble(String srcStr, double defaultValue) {
        if (isEmpty(srcStr)) return defaultValue;
        double result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.doubleValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数字字符串转化为双精数字，在转化时发生异常，则返回0
     *
     * @param srcStr String 待转化的数字字符串
     * @return double 返回由数字字符串转化成的数字，当srcStr为空或空字符串时，则返回0。
     */
    public static double getDouble(String srcStr) {
        return getDouble(srcStr, 0);
    }

    /**
     * 数字字符串转化为长整型
     *
     * @param srcStr       String 待转化的数字字符串
     * @param defaultValue long 当srcStr为空或空字符串时返回的缺省值
     * @return long 返回由数字字符串转化成的数字，当srcStr为空或空字符串时，则返回缺省值defaultValue
     */
    public static long getLong(String srcStr, long defaultValue) {
        if (isEmpty(srcStr)) return defaultValue;
        long result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.longValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数字字符串转化为长整型，在转化时发生异常，则返回0
     *
     * @param srcStr String 待转化的数字字符串
     * @return long 返回由数字字符串转化成的数字，当srcStr为空或空字符串时，则返回0。
     */
    public static long getLong(String srcStr) {
        return getLong(srcStr, 0);
    }

    /**
     * 字符串转化为布尔型
     *
     * @param srcStr       String 待转化的字符串
     * @param defaultValue boolean 当字符串为空或为null时返回的值
     * @return boolean 返回转化化的布尔值，只有当srcStr为字符串“true”(忽略大小写)时才返回true， <BR>
     *         如果在转化时发生异常（只有为null时），则返回缺省值defaultValue
     */
    public static boolean getBoolean(String srcStr, boolean defaultValue) {
        if (isEmpty(srcStr)) return defaultValue;
        boolean result = defaultValue;
        if ("true".equalsIgnoreCase(srcStr.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 字符串转化为布尔型. <BR>
     * 只有当srcStr为字符串“true”(忽略大小写)时才返回true，其它都返回false, 包括srcStr为null
     *
     * @param srcStr String 待转化的字符串
     * @return boolean 返回转化化的布尔值，
     */
    public static boolean getBoolean(String srcStr) {
        return getBoolean(srcStr, false);
    }

    /**
     * 双精数字格式化，返回字符串
     *
     * @param db  double 待格式化的双精数字
     * @param fmt String 格式化样式，参见类说明， <BR>
     *            fmt非法时，db将按原样转化为字符串后返回。
     * @return String 格式化后的数字，以字符串方式返回
     */
    public static String getNumFormat(double db, String fmt) {
        String retu = "";
        if (null == fmt || "".equals(fmt.trim())) {
            return Double.toString(db);
        }

        try {
            DecimalFormat decimalformat = new DecimalFormat(fmt);
            retu = decimalformat.format(db);
            decimalformat = null;
        } catch (IllegalArgumentException iaex) {
            // iaex.printStackTrace();
            retu = Double.toString(db);
        }
        return retu;
    }

    /**
     * 双精数字格式化，把入参中的双精数格式化为带两位小数的数字字符串
     *
     * @param db double 待格式化的双精数字
     * @return String 格式化为两位小数的数字字符串
     */
    public static String getNumFormat(double db) {
        return getNumFormat(db, "0.00");
    }

    /**
     * 数字字符串格式化，返回字符串
     *
     * @param numStr String 待格式化的数字字符串， <BR>
     *               如果numStr参数不是的数字则不进行格式化，按原样返回
     * @param fmt    String 格式化样式，参见类说明 <BR>
     *               fmt非法时，将把numStr按原样返回。
     * @return String 格式化后的字符串
     */
    public static String getNumFormat(String numStr, String fmt) {
        double db = getDouble(numStr, 0);
        return getNumFormat(db, fmt);
    }

    /**
     * 数字字符串格式化，把入参中的数字字符串格式化为带两位小数的数字字符串
     *
     * @param numStr String 待格式化的数字字符串， <BR>
     *               如果numStr参数不是的数字则不进行格式化，按原样返回
     * @return String 格式化为两位小数的数字字符串
     */
    public static String getNumFormat(String numStr) {
        return getNumFormat(numStr, "0.00");
    }

    /**
     * 普通字符串转化为网页中可显示的，如回车转化为&lt;br&gt;.
     *
     * @param str String 待转化的字符串
     * @return String 转化后的字符串
     */
    public static String htmlEncode(String str) {
        String retu = null;
        if (null == str || "".equals(str.trim())) {
            retu = str;
        } else {
            String html = str;
            html = replaceString(html, "&", "&amp;");
            html = replaceString(html, "<", "&lt;");
            html = replaceString(html, ">", "&gt;");
            html = replaceString(html, "\r\n", "\n");
            html = replaceString(html, "\n", "<br>");
            html = replaceString(html, "\t", "    ");
            html = replaceString(html, " ", "&nbsp;");
            html = replaceString(html, "\"", "&quot;");
            retu = html;
            html = null;
        }
        return retu;
    }
    
    public static String htmlDecode(String str){
   	 String retu = null;
        if (null == str || "".equals(str.trim())) {
            retu = str;
        } else {
            String html = str;
            html = replaceString(html, "&amp;", "&");
            html = replaceString(html, "&lt;", "<");
            html = replaceString(html, "&gt;", ">");
            html = replaceString(html, "\n", "\r\n");
            html = replaceString(html, "<br>", "\n");
            html = replaceString(html, "    ", "\t");
            html = replaceString(html, "&nbsp;", " ");
            html = replaceString(html, "&quot;", "\"");
            retu = html;
            html = null;
        }
        return retu;
   }

    /**
     * 字符串替换，把str字符串中的所有oldStr子串替换为newStr字串
     *
     * @param srcStr     String 将被替换的字符串，为null时不执行任何替换操作
     * @param oldStr     String 将被替换的子串，为null或为空字符串时不执行替换操作
     * @param newStr     String 将被替换成的子串，为null时不执行替换操作
     * @param ignoreCase boolean 是否忽略大小写，true表忽略大小写，false表大小写敏感。
     * @return String 替换后的字符串
     */
    public static String replaceString(String srcStr, String oldStr,
                                       String newStr, boolean ignoreCase) {

        if (srcStr == null || oldStr == null) {
            return null;
        }
        StringBuffer resultSB = new StringBuffer();

        String tempStr = srcStr;
        if (ignoreCase) {
            tempStr = srcStr.toUpperCase();
            oldStr = oldStr.toUpperCase();
        }
        int startPos = 0;
        int pos = tempStr.indexOf(oldStr);

        //如果没有需要替换的，则返回原始值
        if (pos == -1)
            return srcStr;

        while (pos != -1) {
            resultSB.append(srcStr.substring(startPos, pos));
            resultSB.append(newStr);

            startPos = pos + oldStr.length();
            pos = tempStr.indexOf(oldStr, pos + newStr.length());

            if (pos == -1) {
                resultSB.append(srcStr.substring(startPos, srcStr.length()));
            }
        }

        return resultSB.toString();
    }

    /**
     * 字符串替换，把str字符串中的所有oldStr子串替换为newStr字串(大小写敏感)
     *
     * @param srcStr String 将被替换的字符串，为null时不执行任何替换操作
     * @param oldStr String 将被替换的子串，为null或为空字符串时不执行替换操作
     * @param newStr String 将被替换成的子串，为null时不执行替换操作
     * @return String 替换后的字符串
     */
    public static String replaceString(String srcStr, String oldStr, String newStr) {
        return org.apache.commons.lang3.StringUtils.replace(srcStr, oldStr, newStr);
    }

    /**
     * 字符串按指定分隔符分解为一个数组（大小写敏感）. <BR>
     * 例子： <BR>
     * String[] array = StringUtils.splictString("AA/BBB/CCC//DDDD//" , "/" );
     * <BR>
     * 结果： <BR>
     * array[0]="AA" array[1]="BBB" array[2]="CCC" array[3]="DDDD"
     *
     * @param srcStr String 源字符串
     * @param split  String 分隔符
     * @return String[] 字符串数组, <BR>
     *         源字符串为null或为空字符串时，返回长度为1的数组，元素值为空字符串 <BR>
     *         分隔符为null或为空字符串时，返回长度为1的数组，元素值为源字符串
     */
    public static String[] split(String srcStr, String split,
                                 boolean trim) {
        if (isEmpty(srcStr) || isEmpty(split)) {
            return null;
        }
        if (trim) {
            srcStr = trim(srcStr, split);
        }
        return split(srcStr, split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     *
     * @param srcStr 要被放入的字符串
     * @param split  间隔符
     * @return 转换后的数组,srcStr或split ＝ null 或 "" 返回null
     */
    public static String[] split(String srcStr, String split) {
        if (isEmpty(srcStr) || isEmpty(split)) {
            return null;
        }
        int int_ArraySize = subStringCount(srcStr, split);
        // 如果为-1则返回
        if (int_ArraySize == -1) {
            return null;
        }

        srcStr += split;

        String[] str_RetArr = new String[int_ArraySize + 1];
        int int_pos = srcStr.indexOf(split);
        int int_ArrayPos = 0;
        while (int_pos != -1) {
            str_RetArr[int_ArrayPos++] = srcStr.substring(0, int_pos);
            srcStr = srcStr.substring(int_pos + split.length());
            int_pos = srcStr.indexOf(split);
        }

        return str_RetArr;
    }

    /**
     * 功能简述 :在一个字符串中查找字符串个数(不区分大小写)
     *
     * @param srcStr 要被查询的字符串
     * @param subStr 要查找的字符串
     * @return 找到的个数
     */
    public static int subStringCount(String srcStr, String subStr) {
        if (isEmpty(srcStr) || isEmpty(subStr)) {
            return 0;
        }
        int result = 0;
        int int_pos = srcStr.toUpperCase().indexOf(subStr.toUpperCase());
        while (int_pos != -1) {
            result++;
            int_pos = srcStr.toUpperCase().indexOf(subStr.toUpperCase(),
                    int_pos + subStr.length());
        }
        return result;
    }

    /**
     * 用指定的分隔符把字符串数组合并成一个字符串. 数组为null或长度为0时返回空字符串 <BR>
     * 例子： <BR>
     * String[] array = {"1",null,"","3"}; <BR>
     * StringUtils.arrayToString(array,"|"); <BR>
     * 返回结果：1|||3
     *
     * @param array String[] 待合并的数组
     * @param split String 数组各元素合并后，它们之间的分隔符，为null时用空字符串代替
     * @return String 合并后的字符串
     */
    public static String arrayToString(String[] array, String split) {
        if (null == array || 0 == array.length) {
            return "";
        }
        if (null == split) {
            split = "";
        }
        StringBuffer result = new StringBuffer("");
        String localSplit = "";
        
        for (int i = 0; i < array.length; i++) {
            if(isEmpty(array[i]))
                continue;
            
            result.append(localSplit).append(array[i].toString());
            localSplit = split;
        }

        return result.toString();
    }

    /**
     * 用默认分隔符 , 把字符串数组合并成一个字符串. 数组为null或长度为0时返回空字符串
     *
     * @param array String[] 待合并的数组
     * @return String 合并后的字符串
     */
    public static String arrayToString(String[] array) {
        return arrayToString(array, ",");
    }

    /**
     * 判断字符串是否为null或为空字符串（包括只含空格的字符串）
     *
     * @param str String 待检查的字符串
     * @return boolean 如果为null或空字符串（包括只含空格的字符串）则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str String
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从字符串中得到指定位置的子串（按分隔符分隔，大小写敏感）. <BR>
     * 例子： <BR>
     * StringUtils.decomposeString("a||b|c|d","|",1); <BR>
     * StringUtils.decomposeString("a||b|c|d","|",2); <BR>
     * StringUtils.decomposeString("a||b|c|d","|",3); <BR>
     * StringUtils.decomposeString("a||b|c|d","|",4); <BR>
     * StringUtils.decomposeString("a||b|c|d","|",5); <BR>
     * 返回结果： <BR>
     * a,空字符串,b,c,d
     *
     * @param srcStr String 源字符串
     * @param split  String 分隔符
     * @param pos    int 位置，从1开始
     * @return String 返回指定位置的子串。 <BR>
     *         当分隔符为null或为空字符串时返回源字符串； <BR>
     *         当源字符串为null或为空字符串时返回空字符串； <BR>
     *         当指定位置小于1或大于分隔符个数-1时返回空字符串。
     */
    public static String decomposeString(String srcStr, String split,
                                         int pos) {
        String retu = "";
        if (null == srcStr || "".equals(srcStr.trim())) {
            return "";
        }

        if (pos <= 0) {
            return "";
        }

        if (null == split || "".equals(split)) {
            return srcStr;
        }

        srcStr = srcStr + split;
        String tmpStr = srcStr;

        int splitLen = split.length();
        int tmpLen = tmpStr.length();

        for (int i = 0; i < pos - 1; i++) {
            int tmpPosi = tmpStr.indexOf(split);
            if (tmpPosi < 0 || tmpPosi + splitLen >= tmpLen) {
                tmpStr = split;
                break;
            } else {
                tmpStr = tmpStr.substring(tmpPosi + splitLen);
            }
        }
        retu = tmpStr.substring(0, tmpStr.indexOf(split));
        return retu;
    }

    /**
     * 从字符串中得到指定位置的子串（按分隔符 | 分隔）.
     *
     * @param srcStr String 源字符串
     * @param pos    int 位置，从1开始
     * @return String 返回指定位置的子串。 <BR>
     *         当分隔符为null或为空字符串时返回源字符串； <BR>
     *         当源字符串为null或为空字符串时返回空字符串； <BR>
     *         当指定位置小于1或大于分隔符个数-1时返回空字符串。
     */
    public static String decomposeString(String srcStr, int pos) {
        return decomposeString(srcStr, "|", pos);
    }

    /**
     * 删除指定的前导、后导子串（大小写敏感）. <br>
     * 例子： <br>
     * StringUtils.trim("and and username = '123' and password = 'abc' and
     * ","and "); <br>
     * 结果：username = '123' and password = 'abc'
     *
     * @param srcStr     String 待删除的字符串
     * @param removeChar char 子串
     * @return String 处理后的字符串
     */
    public static String trim(String srcStr, char removeChar) {
        if (srcStr == null) {
            return null;
        }
        srcStr = srcStr.trim();
        int loInt_begin = 0, loInt_end = 0;
        int loInt_len = srcStr.length();
        for (int i = 0; i < loInt_len; i++) {
            if (srcStr.charAt(i) == removeChar) {
                loInt_begin++;
            } else {
                break;
            }
        }
        for (int i = 0; i < loInt_len; i++) {
            if (srcStr.charAt(loInt_len - 1 - i) == removeChar) {
                loInt_end++;
            } else {
                break;
            }
        }
        return srcStr.substring(loInt_begin, loInt_len - loInt_end);
    }

    /**
     * 从源字符串中从第一个字符开始取出给定长度的字串. <BR>
     * 源字符串长度大于len时，尾巴追加一个appendStr串
     *
     * @param srcStr  String 源字符串
     * @param len     int 取出的长度
     * @param omitStr String 追加的字符串（常用的appendStr为...）
     * @return String 取出的子串
     */
    public static String clip(String srcStr, int len, String omitStr) {
        if (null == srcStr || "".equals(srcStr)) {
            return srcStr;
        }
        if (len <= 0) {
            return "";
        }

        if (null == omitStr) {
            omitStr = "";
        }

        int sourceLen = srcStr.length();
        if (len >= sourceLen) {
            return srcStr;
        } else {
            return srcStr.substring(0, len) + omitStr;
        }
    }

    /**
     * 将null字符串转换为空串，方便HTML的显示
     *
     * @param srcStr 待处理的字符串
     * @return String 处理的的字符串
     */
    public static String toVisualHtmlString(String srcStr) {
        if (srcStr == null || srcStr.equals("")) {
            return "&nbsp;";
        } else {
            return srcStr;
        }
    }

    /**
     * 将null字符串转换为空串
     *
     * @param srcStr 待处理的字符串
     * @return String 处理的的字符串
     */
    public static String toVisualString(String srcStr) {
        if (srcStr == null || srcStr.equals("")) {
            return "";
        } else {
            return srcStr;
        }
    }


    /**
     * 将字段填充到指定的长度
     *
     * @param srcStr     String 操作字符串
     * @param length     int 指定长度
     * @param withChar   char 填充的字符
     * @param isPadToEnd boolean 是否填充在字符的尾部 true ：是 ,false:填充在头部
     * @return String
     */
    public static String pad(String srcStr, int length, char withChar,
                             boolean isPadToEnd) {
        if (srcStr == null) {
            return null;
        }
        if (srcStr.length() >= length) {
            return srcStr;
        }

        StringBuffer sb = new StringBuffer(srcStr);
        for (int i = 0; i < length - srcStr.length(); i++) {
            if (isPadToEnd) {
                sb.append(withChar);
            } else {
                sb.insert(0, withChar);
            }
        }
        return sb.toString();

    }

    /**
     * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
     *
     * @param srcStr 要被放入的字符串
     * @param split  间隔符
     * @param trim   自动删除首尾的间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, char split,
                                         boolean trim) {
        if (isEmpty(srcStr)) return null;
        if (trim) {
            srcStr = trim(srcStr, "" + split);
        }
        return stringToArray(srcStr, "" + split);
    }

    /**
     * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
     *
     * @param srcStr 要被放入的字符串
     * @param split  间隔符
     * @param trim   自动删除首尾的间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, String split,
                                         boolean trim) {
        if (StringUtils.isEmpty(srcStr)) return null;
        if (trim) srcStr = trim(srcStr, split + "");
        return stringToArray(srcStr, split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     *
     * @param srcStr 要被放入的字符串
     * @param split  间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, char split) {
        return stringToArray(srcStr, "" + split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     *
     * @param srcStr 要被放入的字符串
     * @param split  间隔符
     * @return 转换后的数组,失败返回 null
     */
    public static String[] stringToArray(String srcStr, String split) {
        if (StringUtils.isEmpty(srcStr)) return null;
        srcStr = trim(srcStr, split + "");

        int int_ArraySize = subStringCount(srcStr, split);
        // 如果为-1则返回
        if (int_ArraySize == -1) {
            return null;
        }

        srcStr += split;

        String[] str_RetArr = new String[int_ArraySize + 1];
        int int_pos = srcStr.indexOf(split);
        int int_ArrayPos = 0;
        while (int_pos != -1) {
            str_RetArr[int_ArrayPos++] = srcStr.substring(0, int_pos);
            srcStr = srcStr.substring(int_pos + split.length());
            int_pos = srcStr.indexOf(split);
        }

        return str_RetArr;
    }

    /**
     * 将字符分隔的字符串转换为List
     *
     * @param srcStr    字符分隔的字符串
     * @param splitChar 分隔字符
     * @param trim      是否去除首尾多余字符
     * @return
     */
    public static List<String> stringToList(String srcStr, char splitChar,
                                            boolean trim) {
        return stringToList(srcStr, "" + splitChar, trim);
    }

    /**
     * 将字符分隔的字符串转换为List
     *
     * @param srcStr    字符分隔的字符串
     * @param splitChar 分隔字符
     * @return
     */
    public static List<String> stringToList(String srcStr, char splitChar) {
        return stringToList(srcStr, "" + splitChar, false);
    }

    /**
     * 将字符分隔的字符串转换为List
     *
     * @param srcStr    字符分隔的字符串
     * @param splitChar 分隔字符
     * @return
     */
    public static List<String> stringToList(String srcStr, String splitStr) {
        return stringToList(srcStr, splitStr, false);
    }

    /**
     * 将字符分隔的字符串转换为List
     *
     * @param srcStr    字符分隔的字符串
     * @param splitChar 分隔字符
     * @param trim      是否去除首尾多余字符
     * @return
     */
    public static List<String> stringToList(String srcStr, String splitStr, boolean trim) {
        if (srcStr == null) {
            return null;
        }
        List<String> resultList = new ArrayList<String>();
        if (!isNotEmpty(srcStr)) {
            return resultList;
        } else {
            String[] srcStrArr = stringToArray(srcStr, splitStr, trim);
            for (String item : srcStrArr) {
                resultList.add(item);
            }
            return resultList;
        }
    }

    /**
     * 将字符串集成转换为分隔符分隔的字符串
     *
     * @param collection 字符串集成
     * @param split      分隔字符
     * @return
     */
    public static final String collToString(Collection<String> collection, String split) {
        StringBuffer sb = new StringBuffer();
        String tempSplit = "";
        for (String item : collection) {
            sb.append(tempSplit + item);
            tempSplit = split;
        }
        return sb.toString();
    }

    // 以下是对js的escape,进行解码
    private final static byte[] val = {0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x00, 0x01, 0x02,
            0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D,
            0x0E, 0x0F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
            0x0F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F};

    /**
     * 对js的escape进行解码
     *
     * @param sourceUrl
     * @return
     */
    public static String urlEncode(String sourceUrl) {
        Assert.notNull(sourceUrl);
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = sourceUrl.length();
        while (i < len) {
            int ch = sourceUrl.charAt(i);
            if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != sourceUrl.charAt(i + 1)) { // %XX : map to ascii(XX)
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 1)];
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 2)];
                    i += 2;
                } else { // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 2)];
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 3)];
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 4)];
                    cint = (cint << 4) | val[sourceUrl.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            } else { // 对应的字符未经过编码
                sbuf.append((char) ch);
            }
            i++;
        }
        return sbuf.toString();
    }


    /* 删除指定的前导、后导子串（大小写敏感）.
     * <br>例子：
     * <br>StringUtils.trim("and and username = '123' and password = 'abc' and ","and ");
     * <br>结果：username = '123' and password = 'abc'
     * @param srcStr String
     * 待删除的字符串
     * @param ch String
     * 子串
     * @return String
     * 处理后的字符串
     */

    public static String trim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        //删除前导
        int chLen = ch.length();

        /*
                StringBuffer strBuf = new StringBuffer(srcStr);
                while(strBuf.indexOf(ch,0) == 0){ //表示还有前导
            strBuf.replace(0,chLen,"");
                }

                //删除后导
                int strBufLen = strBuf.length();
         while(strBuf.indexOf(ch,(strBufLen - chLen)) == strBufLen - chLen){
            strBuf.replace(strBuf.length() - chLen,strBuf.length(),"");
            strBufLen = strBuf.length();
                }*/

        while (srcStr.indexOf(ch, 0) == 0) { //表示还有前导
            srcStr = srcStr.substring(chLen);
        }

        int strLen = srcStr.length();
        while (srcStr.indexOf(ch, (strLen - chLen)) == (strLen - chLen)) { //表示还有后导
            srcStr = srcStr.substring(0, strLen - chLen);
            strLen = srcStr.length();
        }

        return srcStr;
    }


    /* 删除指定的前导（大小写敏感）.
     * <br>例子：
     * <br>StringUtils.trim("and and username = '123' and password = 'abc' and ","and ");
     * <br>结果：username = '123' and password = 'abc'
     * @param srcStr String
     * 待删除的字符串
     * @param ch String
     * 子串
     * @return String
     * 处理后的字符串
     */

    public static String ltrim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        //删除前导
        int chLen = ch.length();

        if (srcStr.indexOf(ch, 0) == 0) { //表示还有前导
            srcStr = srcStr.substring(chLen);
        }

        return srcStr;
    }


    /* 删除指定的后导（大小写敏感）.
     * <br>例子：
     * <br>StringUtils.trim("and and username = '123' and password = 'abc' and ","and ");
     * <br>结果：username = '123' and password = 'abc'
     * @param srcStr String
     * 待删除的字符串
     * @param ch String
     * 子串
     * @return String
     * 处理后的字符串
     */

    public static String rtrim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        //删除后导
        int chLen = ch.length();
        int strLen = srcStr.length();
        if (srcStr.indexOf(ch, (strLen - chLen)) == (strLen - chLen)) { //表示还有后导
            srcStr = srcStr.substring(0, strLen - chLen);
            strLen = srcStr.length();
        }

        return srcStr;
    }


    /**
     * 功能描述:
     * 判断字符串是否为null或为空字符串，则返回空字符串""
     *
     * @param str String
     *            待检查的字符串
     * @return String
     *         如果为null或空字符串（包括只含空格的字符串）则返回""，否则返回原字符串去空格
     */
    public static String getNullBlankStr(Object obj) {

        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }


    /**
     * 按指定宽度返回字符串，超过指定的长度则截取指定长度，并加"..."
     *
     * @param str   String 处理的字符
     * @param width int 指定的宽度
     * @return string 返回指定的宽度
     */
    public static String getSubStr(String str, int width) {
        if (str == null || str.length() <= width) {
            return str;
        } else {
            return str.substring(0, width) + "...";
        }
    }

    /**
     * 右补齐
     *
     * @param s
     * @param len 补齐到的目标长度
     * @param c   补齐使用的字符
     * @return
     */
    public static String rightPad(String s, int len, char c) {
        if (s != null) {
            int needLen = len - s.length();
            if (needLen > 0) {
                for (int i = 0; i < needLen; i++) {
                    s += c;
                }
            }
        }
        return s;
    }

    /**
     * 左补齐
     *
     * @param s
     * @param len 左补齐到的目标长度
     * @param c   补齐使用的字符
     * @return
     */
    public static String leftPad(String s, int len, char c) {
        if (s != null) {
            int needLen = len - s.length();
            if (needLen > 0) {
                for (int i = 0; i < needLen; i++) {
                    s = c + s;
                }
            }
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
//        String result = replaceString("01,02,03", ",", "','", false);
//        System.out.println(replaceString("abkabkAb", "ab", "hh", true));
//        System.out.println("result:" + result);

//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMaximumFractionDigits(2);
//        nf.setMaximumIntegerDigits(3);
//        nf.setParseIntegerOnly(false);
//
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.UK);
//
//        System.out.println(nf.parse("23154.333"));
//        System.out.println(nf.format(23154.333));

        df.applyPattern("#,###");
        System.out.println(df.format(23153324.0580));
    }

    /**
     * 将集合中的元素转为字符串数组
     *
     * @param itemIds
     * @return
     */
    public static String[] collToArray(Collection itemIds) {
        if (itemIds == null) return null;
        return (String[]) itemIds.toArray(new String[0]);
    }

    /**
     * 将str以List形式返回
     *
     * @param str
     * @return
     */
    public static List<String> asList(String str) {
        List<String> list = new ArrayList<String>();
        list.add(str);
        return list;
    }
    
    /**
	 * 如果传入的串中有单引号，则在单引号前再加一个单引号
	 * 
	 * @param str
	 * @return
	 */
	public static String converSingQuotation(String str) {
		String result = "";
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (String.valueOf(str.charAt(i)).equals("'")) {
				result += "'" + String.valueOf(str.charAt(i));
			} else {
				result += String.valueOf(str.charAt(i));
			}
		}
		return result;
	}
	
	/**
	 * 取整数部分，不四舍五入
	 * @param d
	 * @return
	 */
	public static int takeInteger(double d){
		
	   	BigDecimal b = new BigDecimal(d);
	   	int f1 = b.setScale(0, BigDecimal.ROUND_FLOOR).intValue();
	   	//System.out.println(f1);
	   	return f1;
	}

}
