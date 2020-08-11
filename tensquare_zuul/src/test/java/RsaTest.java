import com.tensquare.ZuulApplication;
import com.tensquare.rsa.RsaKeys;
import com.tensquare.service.RsaService;
import com.tensquare.service.RsaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;

/**
 * @Author luo
 * @Date 2020/4/5 20:32
 */
@SpringBootTest(classes = ZuulApplication.class)//因为包路径不一样,需要指定启动类
@RunWith(SpringRunner.class)
public class RsaTest {

    @Autowired
    private RsaService rsaService;


    /**
     * rsa 公钥加密
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {

        String data="{\"userid\":\"3\"}";
        System.out.println(rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey()));
    }

    /**
     * rsa私钥解密
     */
    @Test
    public void test02() throws Exception {

        String decodeData="k/XyrKueS5FfdFAslPj9xJdu4VhAUH4BDaoUxuEg7KJ8X3to5/xKDjD7ufnlEZXf4jkPVHWV+CEcdk7+mTxS646zulFC+435jqPqx9hlualnuzf88QqMgpzozSzpKHFJ/I/UPJGJOvkYjtPPYabMUIdOna9GFoDQ4Pqi7lyEgvA8Lhd2davEooKcdiQElgxUDU1llTI1PiP5gs4nLpxvkRJC1yLczLU4r0eXXZ5oqFZb2EHVJrJnO7mOLinrUohOP5p0SdMqbX6l9TYdwZjF5XwCLMSmYPZqe58wKOhAwHWPB8hJuMS7ayoU65fwEJmzeJ0NAxSdymiokGa1erOedQ==";
        System.out.println(rsaService.RSADecryptDataPEM(decodeData,RsaKeys.getServerPrvKeyPkcs8()));

    }


    @Test
    public void test03(){
        try {
            Object call = new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    System.out.println(Thread.currentThread().getName());
                    return "呵呵哒";
                }
            }.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }

}
