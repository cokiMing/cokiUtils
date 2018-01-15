package coki.utils.mail;

import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/1/15.
 */
public class MailManager {

    public final static Server DEFAULT_SERVER = new Server(
            "january.4th@163.com",
            "wym081656",
            "smtp.163.com",
            "系统账号"
    );

    public static Server createMailServer(String account,String password,String smtpHost) {
        return new Server(account,password,smtpHost);
    }

    private static MimeMessage createMimeMessage(Session session,String name, String subject,String content, String sendMail, List<String> receiveMails, List<String> fileLocations) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, name, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        for (String receiveMail : receiveMails) {
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));
        }

        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 添加附件
        if (fileLocations != null) {
            message.setContent(setMultipart(fileLocations));
        }

        // 7. 设置发件时间
        message.setSentDate(new Date());

        // 8. 保存设置
        message.saveChanges();

        return message;
    }

    private static Multipart setMultipart(List<String> fileLocations) throws Exception {
        Multipart multipart = new MimeMultipart();
        for (String fileLocation : fileLocations) {
            File file = new File(fileLocation);
            MimeBodyPart fileBody = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            fileBody.setDataHandler(new DataHandler(source));
            BASE64Encoder enc = new BASE64Encoder();
            fileBody.setFileName("=?GBK?B?"
                    + enc.encode(file.getName().getBytes()) + "?=");
            multipart.addBodyPart(fileBody);
        }

        return multipart;
    }

    public static class Server {
        private String account;
        private String password;
        private String smtpHost;
        private String name;
        private Properties mailConfig;

        public Server(String account, String password, String smtpHost) {
            this(account,password,smtpHost,account);
        }

        public Server(String account, String password, String smtpHost, String name) {
            this.account = account;
            this.password = password;
            this.smtpHost = smtpHost;
            this.name = name;
            mailConfig = new Properties();
            mailConfig.setProperty("mail.transport.protocol", "smtp");
            mailConfig.setProperty("mail.smtp.host", smtpHost);
            mailConfig.setProperty("mail.smtp.auth", "true");
        }

        public Server serverName(String name) {
            this.name = name;
            return this;
        }

        public boolean sendMail(String content, String subject, List<String> receivers,List<String> fileLocations) {
            Session session = Session.getInstance(mailConfig);
            session.setDebug(true);
            Transport transport = null;
            try {
                MimeMessage mimeMessage = createMimeMessage(session,name, subject,content,account,receivers,fileLocations);
                transport = session.getTransport();
                transport.connect(account, password);
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                if (transport != null) {
                    try {
                        transport.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
