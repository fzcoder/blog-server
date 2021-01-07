package com.fzcoder.utils;

/**
 * @ClassName EmailCodeUtils
 * @Description 邮箱验证码工具类
 * @Author Frank Fang
 * @Date 2020/1/31 16:25
 * @Version 1.0
 **/
public class MailCodeUtils {

    /**
     * @Description生成6位随机验证码
     * @return
     */
    public static String getCode(){
        String str = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";
        for(int i= 0;i<6;i++){
            int index = (int)(Math.random()*str.length());
            code+=str.charAt(index);
        }
        return code;
    }

    /**
     * 发送邮箱验证码
     * @param receiverEmail
     * @param subject
     * @param msg
     */
    /* public static void sendEmailCode(String EMAIL_HOST_NAME,String EMAIL_FORM_MAIL,
                              String EMAIL_FORM_NAME,String EMAIL_AUTHENTICATION_USERNAME,
                              String EMAIL_AUTHENTICATION_PASSWORD,String receiverEmail,
                              String subject,String msg){
        try{
            HtmlEmail email = new HtmlEmail();
            email.setHostName(EMAIL_HOST_NAME);
            email.setCharset("utf-8");
            email.setFrom(EMAIL_FORM_MAIL,EMAIL_FORM_NAME);
            email.setAuthentication(EMAIL_AUTHENTICATION_USERNAME,EMAIL_AUTHENTICATION_PASSWORD);
            email.addTo(receiverEmail);
            email.setSubject(subject);
            email.setMsg(msg);
            email.send();
        }catch (Exception ex){
            throw new Exception("发送验证码失败，原因："+ex.getMessage());
        }
    } */
}

