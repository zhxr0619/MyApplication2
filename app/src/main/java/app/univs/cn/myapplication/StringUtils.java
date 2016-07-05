package app.univs.cn.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    // 正则-验证账号、密码
    // private static String regexAccountAndPwd =
    // "^((?![0-9]+$)|((?![0-9]+$)(?![a-zA-Z]+$)))[0-9A-Za-z]{6,20}$";
    // ==密码由6-20位数字或英文组成
    private static String regexAccountAndPwd = "^[0-9a-zA-Z_]{6,20}$";

    // public static Boolean validatePwd(String checkStr) {
    // if (null == checkStr) {
    // return false;
    // }
    // Pattern pattern = Pattern.compile(regexAccountAndPwd);
    // return validatePublic(pattern, checkStr.toString());
    // }


    private static Boolean validatePublic(Pattern pattern, String checkStr) {
        Matcher matcher = pattern.matcher(checkStr);
        return matcher.matches() ? true : false;
    }

    /**
     * 登录密码由6-20个字符组成，字母区分大小写
     *
     * @param checkStr
     * @return
     */
    public static Boolean validatePwd_(String checkStr) {
        if (null == checkStr) {
            return false;
        }
        if (checkStr.length() >= 6 && checkStr.length() <= 20) {
            return true;
        }
        return false;
    }

    /**
     * 保留一位小数
     *
     * @param d
     * @return
     */
    public static String subOneDecimal(Double d) {
        if (d == null) {
            return "0";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("0");
        return df.format(d);
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String subToDecimal(Double d) {
        if (d == null) {
            return "0.00";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 保留四位小数
     *
     * @param d
     * @return
     */
    public static String subFourDecimal(Double d) {
        if (d == null) {
            return "0.0000";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.0000");
        return df.format(d);
    }

    /***
     * 判断身份证号
     *
     * @param checkStr
     * @return
     */
    public static Boolean validateIdentity(String checkStr) {
        if (null == checkStr) {
            return false;
        }
        String regex = "(\\d{14}[0-9x|X])|(\\d{17}[0-9x|X])";
        // String regex = "\\d{15}|\\d{17}[0-9Xx]";
        // String regex =
        // "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkStr);
        return matcher.matches() ? true : false;
    }

    /**
     * 判断手机号码 是否合法
     *
     * @param mobiles
     * @return
     */
    //regexMoblie="^0?(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$";
    private static String regexMoblie = "^0?(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$";

    public static boolean isMobileNO(String mobiles) {
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        if (mobiles != null) {
            Pattern p = Pattern.compile(regexMoblie);
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }

    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
//    public static Boolean isMobileNo(String str) {
//        Boolean isMobileNo = false;
//        try {
//            Pattern p = Pattern
//                    .compile("^1(7[0-9]|3[0-9]|5[0-35-9]|8[0-35-9])\\d{8}$");
//            Matcher m = p.matcher(str);
//            isMobileNo = m.matches();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isMobileNo;
//    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public static Integer stringToInteger(String str) {
        if (null != str || str.length() > 0) {
            Integer money = null;
            try {
                money = Integer.valueOf(str);
                return money;
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

    public static String dataToString(long str) {
        String date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date da = new Date(str);
        try {
            date = df.format(da);

        } catch (NumberFormatException e) {
            return " ";
        }

        return date;
    }


    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getAcceptTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String acceptTime = formatter.format(currentTime);
        return acceptTime;
    }

    public static long dateToLong(String str) {
        // 将字符串类型转化成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d2 = null;
        try {
            if (str != null) {
                d2 = sdf.parse(str);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 将String to Date类型
        return d2.getTime();
    }

    //Java 对字符串数据进行SHA1哈希散列运算
    public static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0)
                a += 256;
            if (a < 16)
                buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * MD5加密
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < m.length; i++) {
            sb.append(m[i]);
        }
        return sb.toString();
    }

    /**
     * 96 * 把Web站点返回的响应流转换为字符串格式 97 * 98 * @param inputStream 99 * 响应流 100 * @param
     * encode 101 * 编码格式 102 * @return 转换后的字符串 103
     */
    public static String changeInputStream(InputStream inputStream, String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


    /**
     *
     * @param str yyyyMMddhhmmss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String str2DateStr(String str,int flag){

        SimpleDateFormat sdf1 = new SimpleDateFormat(flag==0?"yyyyMMddhhmmss":flag==1?"yyyyMMdd":"hhmmss");
        Date date = null;
        try {
            date = (Date) sdf1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(flag==0?"yyyy-MM-dd HH:mm:ss":flag==1?"yyyy-MM-dd":"HH:mm:ss");
        String dateStr=sdf2.format(date);

        return dateStr;
    }


    /**
     * 格式化数字为千分位显示；
     * @param  text 要格式化的数字；
     * @return
     */
    public static String fmtMicrometer(String text)
    {
        DecimalFormat df = null;
        if(text.indexOf(".") > 0)
        {
            if(text.length() - text.indexOf(".")-1 == 0)
            {
                df = new DecimalFormat("###,##0.");
            }else if(text.length() - text.indexOf(".")-1 == 1)
            {
                df = new DecimalFormat("###,##0.0");
            }else
            {
                df = new DecimalFormat("###,##0.00");
            }
        }else
        {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    //  工具类（只适合不带正负号的数字的字符串）,此方法是保留两位小数位，整数位是千分位格式
    //千分位方法
    public static String fmtMicrometer2(String text)
    {
        DecimalFormat df = null;
//        if(text.indexOf(".") > 0)
//        {
//            if(text.length() - text.indexOf(".")-1 == 0)
//            {
//                df = new DecimalFormat("###,##0.");
//            }else if(text.length() - text.indexOf(".")-1 == 1)
//            {
//                df = new DecimalFormat("###,##0.0");
//            }else
//            {
//                df = new DecimalFormat("###,##0.00");
//            }
//        }else
//        {
//            df = new DecimalFormat("###,##0");
//        }

        df = new DecimalFormat("###,##0.00");
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

}
