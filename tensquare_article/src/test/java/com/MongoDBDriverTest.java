package com;

import com.article.ArticleApplication;
import com.article.pojo.Article;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.security.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/*
MongoDBDriver测试类
 */
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class MongoDBDriverTest {

    private MongoClient client;

    private MongoCollection<Document> comment;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Before
    public void init() {
        //1.注册驱动
        client = new MongoClient("192.168.200.128",27017);
        //2.指定数据库
        MongoDatabase commentdb = client.getDatabase("commentdb");
        //3.指定集合
        comment = commentdb.getCollection("comment");

        HashMap<Object, Object> map = new HashMap<>();
    }

    @After
    public void close() {
        client.close();
    }

    /**
     * 查询所有得测试
     */
    @Test
    public void test01() {

//        //1.注册驱动
//        MongoClient client = new MongoClient("192.168.200.128");
//        //2.选择数据库
//        MongoDatabase commentdb = client.getDatabase("commentdb");
//        //3.选择集合
//        MongoCollection<Document> comment = commentdb.getCollection("comment");

        //查询所有
        FindIterable<Document> documents = comment.find();

        //遍历
//       documents.forEach(d-> System.out.println(d.get()));
        for (Document document : documents) {
            System.out.print(document.get("_id") + "\t");
            System.out.print(document.get("name") + "\t");
            System.out.print(document.get("content") + "\t");
            System.out.print(document.get("userid") + "\t");
            System.out.print(document.get("thumbup") + "\t");
            System.out.println();
        }
    }

    /**
     * 条件查询
     */
    @Test
    public void test02() {

        FindIterable<Document> documents = comment.find(new BasicDBObject("userid", "1012"));

        for (Document document : documents) {
            System.out.print(document.get("_id") + "\t");
            System.out.print(document.get("name") + "\t");
            System.out.print(document.get("content") + "\t");
            System.out.print(document.get("userid") + "\t");
            System.out.print(document.get("thumbup") + "\t");
            System.out.println();
        }
    }

    /**
     * 添加
     */
    @Test
    public void test03() {
        HashMap<Object, Object> map = new HashMap<>();
        System.out.println(map.put("", ""));

        //添加
        Document document = new Document();
        document.put("_id", "6");
        document.put("content", "新增数据测试");
        document.put("userid", "154");
        document.put("thumbup", 12);
        comment.insertOne(document);
//
//        //修改
        comment.updateOne(new BasicDBObject("_id", "4"), new BasicDBObject("$set", new Document("thumbup", "15000")));

        //删除
//        comment.deleteOne(new BasicDBObject("_id","5"));
    }

    @Test
    public void test04(){
//        int[] a=null;
////        System.out.println(a[0]);
        StringBuffer stringBuffer = new StringBuffer(2);
        StringBuilder stringBuilder = new StringBuilder(10);
        stringBuilder.append("1234");
        System.out.println(stringBuilder.length());
        System.out.println(stringBuilder.capacity());
        System.out.println(12^2^2);
    }


    @Test
    public void test06(){
        System.out.println(rabbitTemplate);
    }

    @Test
    public void test07(){
        mongoTemplate.insert(1);
    }
}
