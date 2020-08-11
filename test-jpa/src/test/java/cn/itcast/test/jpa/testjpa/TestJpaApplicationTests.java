package cn.itcast.test.jpa.testjpa;

import cn.itcast.test.jpa.testjpa.bean.Label;
import cn.itcast.test.jpa.testjpa.dao.LabelDao;
import cn.itcast.test.jpa.testjpa.service.LabelRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@SpringBootTest
class TestJpaApplicationTests {

    @Autowired
    LabelRepositoryService labelRepositoryService;

    @Autowired
    private LabelDao labelDao;

    @Test
    @Transactional
    @Commit
    public void contextUpdate() {
        Optional<Label> byId = labelDao.findById(1L);
        Label label = byId.get();
        label.setCount(2000L);
    }

    @Test
    public void test01(){
//        labelDao.findAll().forEach(System.out::println);
        labelDao.chaxunsuoyou().forEach(System.out::println);
    }


    @Test
    @Rollback(false)
    public void contextLoads() {
        Label label = new Label();
        label.setLabelname("dddddddddddddd");
        labelRepositoryService.insert(label);

        //Label byId = labelRepositoryService.findById(1L);
        //System.out.println(byId);
       // System.out.println(labelRepositoryService.getList());
    }

    public void test02(){
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < 17; i++) {
            map.put(i+"次","i="+i);
        }
        System.out.println();
    }

}
