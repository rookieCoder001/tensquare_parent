package com.article.service;

import com.article.pojo.Comment;
import com.article.repository.CommentRepository;
import com.tensquare.entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author luo
 * @Date 2020/4/4 16:27
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 查询所有
     *
     * @return
     */
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Comment findById(String id) {
        return commentRepository.findById(id).get();
    }

    /**
     * 修改
     *
     * @param comment
     */
    public void update(Comment comment) {
        //修改和新增 api一样
        commentRepository.save(comment);
    }

    /**
     * 添加
     *
     * @param comment
     */
    public void add(Comment comment) {

        //设置id
        comment.set_id("" + idWorker.nextId());

        //数据初始化
        comment.setPublishdate(new Date());//发布日期
        comment.setThumbup(0);//点赞数

        commentRepository.save(comment);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {
        commentRepository.deleteById(id);
    }

    /**
     * 根据articleId查询
     *
     * @param articleId
     * @return
     */
    public List<Comment> findByArticleId(String articleId) {

        mongoTemplate.insert(new ArrayList<>(),"");

        return commentRepository.findByArticleid(articleId);
    }


    /**
     * 评论点赞功能
     *
     * @param commentId
     */
    public String thumbup(String commentId) {

        //方案一：先通过id查询到对应得评论 然后设置点赞数加1 不足之处至于操作两个数据库  性能浪费
        Comment comment = findById(commentId);
//       comment.setThumbup(comment.getThumbup()+1);
//       update(comment);

        //方法2 通过mongodb得列值自增 来显现点赞数加1操作

        //因为同一个用户只能点赞一次  通过redis判断用户是否重复点赞

        Object value = redisTemplate.boundValueOps("userid" + comment.getUserid()).get();
         //判断用户是否点赞
        if (value != null) return "点赞失败";

            //构建查询对象
            Query query = new Query();
            //添加查询条件
            query.addCriteria(Criteria.where("_id").is(commentId));
            //修改参数对象
            Update update = new Update();
            //列值自增 步进为1
            update.inc("thumbup", 1);

            //updateFirst 修改给定条件查询到得第一个对象 与之对应得是updateMulti 修改给定条件得查询到得全部对象
            mongoTemplate.updateFirst(query, update, Comment.class);
//            mongoTemplate.insert(new Comment());

            //存入到redis 有效期一天 一天之内只能点赞一次
            redisTemplate.boundValueOps("userid"+comment.getUserid()).set(comment.getThumbup(),24, TimeUnit.HOURS);
            return "点赞成功";



    }
}
