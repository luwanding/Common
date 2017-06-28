package com.chyjr.platform.common.util;

import com.chyjr.platform.common.validator.IDCard;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.NumUtil.java] 
 * @ClassName:    [NumUtil]  
 * @Description:  [数字类型操作类]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 5:06:12 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 5:06:12 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */

@SuppressWarnings({"all"})
public class NumUtil {
    private static String reg = "^[0-9a-z]{8}[0-9a-z]{4}[0-9a-z]{4}[0-9a-z]{4}[0-9a-z]{12}$";
    public static final String ZIP_regexp = "^[0-9]{6}$"; // 匹配邮编代码
    
    
    public static String getSolrLikeStr(String keyword){
   	 StringBuilder likeStr = new StringBuilder("");
  	     if(!StringUtils.isEmpty(keyword)){
  	    	likeStr.append("*");
  	    	likeStr.append(keyword);
  	    	/*char[] words = keyword.toCharArray();
  	    	for(int i = 0; i < words.length; i++){
  	    		likeStr.append(words[i]);
  	    		if((i +1) < words.length){
  	    			likeStr.append("*");
  	    		}
  	    	}*/
  	    	likeStr.append("*");
  	     }
  	     return likeStr.toString();
   }

    public static boolean isMobileNO(String mobiles) {

        //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[34578]\\d{9}$");
        Matcher m = p.matcher(mobiles);


        return m.matches();

    }
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static boolean isUUID(String uuid) {
        return uuid.matches(reg);
    }

    /**
     * 隐藏卡号 格式888888xxxxxx0830
     *
     * @param cardNo
     * @return
     */
    public static String hiddenCardNo(String cardNo) {
        String card = "";
        if (cardNo != null && !cardNo.equals("")) {
            card = cardNo;
            if (card.length() == 16) {
                card = card.substring(0, 6) + "******" + card.substring(12, card.length());
            }
            if (card.length() == 19) {
                card = card.substring(0, 6) + "*********" + card.substring(15, card.length());
            }
        }
        return card;
    }

    /**
     * 方法 isInt 判断字符串是否为整型数。
     *
     * @param str 输入字符串
     * @返回布尔值。
     */
    public static boolean isInt(String str) {
        if (str == null) return false;
        str = str.trim();
        if (str.length() == 0) return false;
        try {
            Integer int_num = new Integer(0);
            int int_out = int_num.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isDate(String str) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * String to Int
     *
     * @param str String
     * @return int
     */
    public static int toInt(String str) {
        if (str == null) return 0;
        if (str.indexOf(".") != -1) {
            str = str.substring(0, str.indexOf("."));
        }
        str = str.trim();
        if (str.length() == 0) return 0;
        int int_out = 0;
        try {
            Integer int_num = new Integer(0);
            int_out = int_num.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
        return int_out;
    }

    /**
     * String to Long
     *
     * @param str String
     * @return int
     */
    public static Long toLong(String str) {
        if (str == null)
            return 0l;
        str = str.trim();
        if (str.length() == 0)
            return 0l;
        Long int_out = 0l;
        try {
            Long int_num = new Long(0l);
            int_out = int_num.parseLong(str);
        } catch (NumberFormatException e) {
            return 0l;
        }
        return int_out;
    }

    public static boolean isCard(String card) {
        if (card == null || card.equals(""))
            return false;
        String pattern = "[0-9]+(.[0-9]+)?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(card);
        boolean b = m.matches();
        if (b) {
            return true;
        }
        return false;
    }

    public static boolean hasTxn(Set<String> txnTypeSet, String method) {
        for (String e : txnTypeSet) {
            if (e.equals(method)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法 isFloat 判断字符串是否为浮点数。
     *
     * @param str 输入字符串
     * @返回布尔值。
     */
    public static boolean isFloat(String str) {
        if (str == null) return false;
        str = str.trim();
        if (str.length() == 0) return false;
        try {
            float value = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 取2位小数
     *
     * @param str
     * @return
     */
    public static BigDecimal toBigdecimal(String str) {

        if (str == null) return new BigDecimal("0.00");
        str = str.trim();
        if (str.length() == 0) return new BigDecimal("0.00");
        try {
            return new BigDecimal(str).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal("0.00");
        }

    }

    public static String toBigdecimalString(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(toBigdecimal(str));

    }

    public static double toDouble(String str) {
        if (str == null) return 0.0;
        str = str.trim();
        if (str.length() == 0) return 0.0;
        double value = 0.0;
        try {
            value = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
        return value;
    }

    /**
     * 方法 isPositive 判断字符串是否为正整数。
     *
     * @param str 输入字符串
     * @返回布尔值。
     */
    public static boolean isPositive(String str) {
        if (str == null) return false;
        str = str.trim();
        if (str.length() == 0) return false;
        try {
            Integer int_num = new Integer(0);
            int int_out = int_num.parseInt(str);
            if (int_out <= 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 去掉=
     *
     * @param str
     * @return
     */
    public static boolean isPositive1(String str) {
        if (str == null) return false;
        str = str.trim();
        if (str.length() == 0) return false;
        try {
            Integer int_num = new Integer(0);
            int int_out = int_num.parseInt(str);
            if (int_out < 0)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String notBlankNull(String str) {
        if (!isFloat(str)) return "null";
        return str;
    }

    public static String floatNotBlankNull(String str) {
        if (!isFloat(str)) return "0.0";
        return str;
    }

    /**
     * 两个Double数相除，并保留scale位小数
     *
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static String getMID(Long trid) {
        String merchantTransactionID = String.valueOf(trid);
        int smi = 12 - merchantTransactionID.length();
        for (int i = 0; i < smi; i++) {
            merchantTransactionID = "0" + merchantTransactionID;
        }
        return merchantTransactionID;
    }

    /**
     * 四舍五入
     *
     * @param dSource double
     * @return int
     */
    public static int getRound(double dSource) {
        double iRound;
        BigDecimal deSource = new BigDecimal(dSource);
        iRound = deSource.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        return new Double(iRound).intValue();
    }

    private static Random random = new Random();

    public static String getRandomNumber(String[] range, int rangeLength, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {

            sb.append(String.valueOf(range[random.nextInt(rangeLength)]));
        }
        return sb.toString();

    }

    public static void getI(int tt) {
        int number = 20;// 控制随机数产生的范围

        List<Integer> arr = new ArrayList<Integer>();

        int arr2[] = new int[20];// 用于存放所取的值的数组

        for (int i = 0; i < 20; i++) {

            arr.add(i + 1);// 为ArrayList添加元素

        }


        for (int j = 0; j < 8; j++) {

            int index = (int) (Math.random() * number);// 产生一个随机数作为索引


            arr2[j] = (int) arr.get(index);

            arr.remove(index);// 移除已经取过的元素

            number--;// 将随机数范围缩小1

            System.out.print(arr2[j] + "  ");// 打印取得的元素


        }
    }

    public static String getDomainIp(String url) {
        if (url.indexOf("http://") != -1) {
            url = url.substring(7);
        }
        if (url.indexOf("https://") != -1) {
            url = url.substring(8);
        }
        if (url.indexOf("/") != -1) {
            url = url.substring(0, url.indexOf("/"));
        }
        if (url.indexOf(":") != -1) {
            url = url.substring(0, url.indexOf(":"));
        }
        return url;
    }

    public static boolean checkFormat(String userName, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return 是否合法
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 =
                "\\w+@(\\w+.)+[a-z]{2,3}";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email.trim());
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
    /**
     * 判断日期格式:yyyy-mm-dd
     *
     * @param sDate
     * @return
     */
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean nameFormat(String email) {
        boolean tag = true;
        final String pattern1 = "[\u4e00-\u9fa5\\w]+";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    public static String generateWord() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }

    /**
     * 过滤html标签
     *
     * @param strSource
     * @return
     */
    public static String toHtmlOnly(String strSource) {
        if (StringUtils.isBlank(strSource)) {
            return strSource;
        }
        char charLt = '<';
        char charGt = '>';
        StringBuffer StrBufReturn = new StringBuffer();
        for (int i = 0; i < strSource.length(); i++) {
            if (strSource.charAt(i) == charLt)
                StrBufReturn.append("&lt;");
            else if (strSource.charAt(i) == charGt)
                StrBufReturn.append("&gt;");
            else
                StrBufReturn.append(strSource.charAt(i));
        }
        return StrBufReturn.toString();
    }

    /**
     * 过滤搜索特殊字符
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        if(str!=null){
            if(str.startsWith("-")){
                str = str.replaceAll("-","");
            }

        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
        }else{
            return  str;
        }
    }

    /**
     * 匹配用户名 中文英文
     * @param s
     * @return
     */
    public static boolean checkChinese(String s){
//        s=new String(s.getBytes());
            String pattern="(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,10}))";
    Pattern p=Pattern.compile(pattern);
        Matcher result=p.matcher(s);
        return result.find();
    }

    /**
     * 身份证验证
     * @param idNumber
     * @return
     */
    public static boolean checkIdCard(String idNumber){
//        String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
//        Pattern p = Pattern.compile(pattern);
//        Matcher m = p.matcher(idNumber);
//        return m.matches();
        return new IDCard().verify(idNumber);
    }

    /**
     * 获取IP
     * @param request
     * @return
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 实体
     * @param model
     * @param body
     * @param files
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws InstantiationException
     */
    public static void reflect(Object model,Map<String, Object> body,String[] files) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, InstantiationException
    {
        // 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // 获取属性的名字
        String[] modelName = new String[field.length];
        String[] modelType = new String[field.length];
        for (int i = 0; i < field.length; i++)
        {
            boolean flag = false;
            String name = field[i].getName();
            for(String f:files){
                if(f.equals(name)){
                    flag = true;
                }
            }
            modelName[i] = name;
            // 获取属性类型
            String type = field[i].getGenericType().toString();
            modelType[i] = type;
            //可访问私有变量
            field[i].setAccessible(true);
            // 将属性的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
            if (body.get(name.toLowerCase()) != null&&flag)
            {
                field[i].set(model,  field[i].getType().getConstructor(field[i].getType()).newInstance((String)body.get(name.toLowerCase())));
            }
        }
    }
}
