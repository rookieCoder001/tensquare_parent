import com.mail.MailApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author luo
 * @Date 2020/4/21 19:05
 */
@SpringBootTest(classes = MailApplication.class)
@RunWith(SpringRunner.class)
public class MailTest {

    @Autowired
    private  JavaMailSender javaMailSender;


    @Test
    public  void sendMail () {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发件人邮箱
        simpleMailMessage.setFrom("15071446772@163.com");

        //收件人邮箱
        simpleMailMessage.setTo("2996451009@qq.com");

        //设置主题
        simpleMailMessage.setSubject("springboot的测试邮件");

        //文本内容
        simpleMailMessage.setText("hello,world!");

        //发送邮件
        javaMailSender.send(simpleMailMessage);

    }
}
