package com.article.service;

import com.article.feign.NoticeFeign;
import com.article.mapper.ArticleMapper;
import com.article.pojo.Article;
import com.article.pojo.Notice;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.entity.IdWorker;

import com.tensquare.entity.PageResult;
import org.apache.catalina.Pipeline;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private NoticeFeign noticeFeign;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static final String USE_KEY = "user_key_";

    private static final String AUTHOR_KEY = "author_key_";

    private static final String ARTICLE_SUBSCRIBE_EXCHANGE = "article_subscribe_exchange";

    private static final String ARTICLE_SUBSCRIBE_QUEUE_ = "article_subscribe_queue_";

    private static final String ARTICLE_THUMBUP_QUEUE_="article_thumbup_queue_";


    /**
     * 查询所有文章
     *
     * @return
     */
    public List<Article> findAll() {
        return articleMapper.selectList(null);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        return articleMapper.selectById(id);
    }

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    public int add(Article article) {

        //idword生成id  避免重复
        article.setId(idWorker.nextId() + "");

        //部分数据初始化
        article.setComment(0);//评论数
        article.setThumbup(0);//点赞数
        article.setVisits(0);//浏览数

        //注 正常开发中 用户名可以在jwt令牌中获取 本案例为简化开发 直接把用户名写死
        String authorId = "3";

        //作者新增文章后  发送通知消息到订阅该文章作者的用户

        //从redis中获取订阅了该作者的所有用户id
        Set<String> userIds = redisTemplate.boundSetOps(AUTHOR_KEY + authorId).members();

        Notice notice = new Notice();
        for (String userId : userIds) {

            //接收人的id
            notice.setReceiverId(userId);

            notice.setId(idWorker.nextId() + "");
            //操作人id
            notice.setOperatorId(authorId);
            //操作对象  文章
            notice.setTargetType("article");
            //通知类型
            notice.setType("sys");
            //操作对象id
            notice.setTargetId(article.getId());
            //操作类型 发布
            notice.setAction("publish");

            //添加通知消息
            noticeFeign.add(notice);
        }

        //像消息队列中发送消息 内容为消息通知id
        rabbitTemplate.convertAndSend(ARTICLE_SUBSCRIBE_EXCHANGE, authorId, notice.getId());

        //添加
        return articleMapper.insert(article);
    }

    /**
     * 用户订阅 文章作者
     *
     * @param userId
     * @param articleId
     * @return
     */
    public boolean subscribe(String userId, String articleId) {

        //查询该文章的作者id
        String authorId = articleMapper.selectById(articleId).getUserid();

        //代码改善  新增通知时带推送消息 全部存入到notice_fresh表中 访问压力较大
        // 可以通过消息队列将带推送消息存入到消息队列中

        //声明消息管理器
        RabbitAdmin admin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());

        //声明交换机 默认开启持久化(durable=true) 和不自动删除(autoDelete=false)
        DirectExchange exchange = new DirectExchange(ARTICLE_SUBSCRIBE_EXCHANGE);
        admin.declareExchange(exchange);

        //声明队列
        Queue queue = new Queue(ARTICLE_SUBSCRIBE_QUEUE_ + userId, true);

        //队列绑定交换机 routingKey为文章作者id  保证每一个用户都有自己得消息通知队列 并且只接受订阅了得作者消息
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);

        //查询用户是否订阅该作者
        Boolean flag = redisTemplate.boundSetOps(USE_KEY + userId).isMember(authorId);

        new Thread().start();

        if (flag) {
            //已订阅
            //保留 取消订阅  删除redis中的关系数据
//            redisTemplate.boundSetOps(USE_KEY+userId).remove(authorId);
//            redisTemplate.boundSetOps(AUTHOR_KEY+authorId).remove(userId);

            //用户取消订阅 取消队列和交换机得绑定
            admin.removeBinding(binding);

            return false;
        }
        //没有订阅  添加关系
        //用户到作者
        redisTemplate.boundSetOps(USE_KEY + userId).add(authorId);

        //作者和用户
        redisTemplate.boundSetOps(AUTHOR_KEY + authorId).add(userId);

        //声明绑定和队列
        admin.declareQueue(queue);

        admin.declareBinding(binding);
        return true;
    }


    /**
     * 修改文章
     *
     * @param article
     * @return
     */
    public int update(Article article) {
        return articleMapper.updateById(article);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int delete(String id) {
        return articleMapper.deleteById(id);
    }

    /**
     * 条件查询和分页
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult<Article> findByCondition(Map<String, Object> map, Integer pageNum, Integer pageSize) {

        //给pageSize和pageNum赋默认值
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        Page<Article> page = new Page<>(pageNum, pageSize);

        //创建条件封装对象
        EntityWrapper<Article> wrapper = new EntityWrapper<>();

        //封装条件
        for (String key : map.keySet()) {
            //key有值时才封装
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }

        //分页条件查询
        List<Article> articleList = articleMapper.selectPage(page, wrapper);

        page.setRecords(articleList);
        return new PageResult<Article>(page.getRecords(), page.getTotal());
    }

    /**
     * 文章点赞
     *
     * @param articleId 文章id
     * @param userId    用户id
     * @return
     */
    public boolean thumbup(String articleId, String userId) {

        //根据id查询到该文章
        Article article = articleMapper.selectById(articleId);

        //思路分析 点赞关系存入到redis 结构为set集合 key为(用户id+thumbup)  中 首先判断用户对该文章是否已经点赞
        Boolean flag = redisTemplate.boundSetOps(userId + "thumbup").isMember(articleId);

        //如果已点赞 则取消点赞
        if (flag) {
            redisTemplate.boundSetOps(userId + "thumbup").remove(articleId);

            //数据库点赞数-1
            article.setThumbup(article.getThumbup() - 1);
            articleMapper.updateById(article);
            return true;
        }
        //如果没有点赞 则将关系存入到redis中，然后让数据库数据加1
        redisTemplate.boundSetOps(userId + "thumbup").add(articleId);

        //数据库中 点赞数+1
        article.setThumbup(article.getThumbup() + 1);

        articleMapper.updateById(article);

        //同时添加消息通知
        Notice notice = new Notice();
        //接收人的id 作者id
        notice.setReceiverId(article.getUserid());
        //操作人id 用户id
        notice.setOperatorId(userId);
        //操作对象  文章
        notice.setTargetType("article");
        //通知类型
        notice.setType("sys");
        //操作对象id
        notice.setTargetId(articleId);
        //操作类型 点赞
        notice.setAction("thumbup");

        //添加
        noticeFeign.add(notice);

        //创建rabbitmq管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);

        //声明队列
        rabbitAdmin.declareQueue(new Queue(ARTICLE_THUMBUP_QUEUE_+article.getUserid(),true));

        //发送消息
        rabbitTemplate.convertAndSend("",ARTICLE_THUMBUP_QUEUE_+article.getUserid(),notice);

        return false;
    }
}
