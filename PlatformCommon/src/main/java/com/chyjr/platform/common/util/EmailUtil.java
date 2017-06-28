package com.chyjr.platform.common.util;

import com.chyjr.platform.common.constants.PlatformConstants;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;


/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.EmailUtil.java] 
 * @ClassName:    [EmailUtil]  
 * @Description:  [邮件操作工具类]  
 * @Author:       [Qiulingdong]  
 * @CreateDate:   [Oct 14, 2014 4:57:36 PM]  
 * @UpdateUser:   [Qiulingdong]  
 * @UpdateDate:   [Oct 14, 2014 4:57:36 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class EmailUtil {
    private static final Logger logger = Logger.getLogger(EmailUtil.class);
    public  HtmlEmail email;

    private  void load() {
       
       email = new HtmlEmail();
        try {
            Properties mail = PropertiesUtil.loadP("mail.properties", PlatformConstants.VITA);
            email.setHostName(mail.getProperty("mail_host"));
            email.setCharset(mail.getProperty("mail_charset"));
            email.setFrom(mail.getProperty("mail_host_user"));
            email.setAuthentication(mail.getProperty("mail_host_user"), mail.getProperty("mail_host_pwd"));
        } catch (Exception ex) {
            logger.error("创建邮件对象出错！" + ex.toString());
        }
    }

    public  void send(String to, String title, String content, List<EmailAttachment> attachments, String cc) {
        if(email==null){
            load();
        }
        if (email.getHostName() == null){
           load();
        }
        if (cc == null)
            cc = "";
        try {
            String tos[] = to.split(";");// 收件人的邮箱
            for (int i = 0; i < tos.length; i++) {
                email.addTo(tos[i]);
            }
            email.getFromAddress();
            email.setSubject(title); // 邮件标题
            email.setMsg(content); // 要发送的信息
            if (attachments != null) // 添加附件
                for (EmailAttachment attachment : attachments) {
                    email.attach(attachment);
                }
            if (cc.length() > 0) {
                String ccs[] = cc.split(";");
                for (int i = 0; i < ccs.length; i++)
                    email.addCc(ccs[i]);
            }
            String send = email.send();
            logger.info("Send Email:"+send);
        } catch (EmailException e) {
            logger.error("邮件连接出错！" + e.toString());
            e.printStackTrace();
        }
    }
}
