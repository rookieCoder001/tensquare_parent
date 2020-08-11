package com.article.repository;

import com.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/*
文章评论
 */
public interface CommentRepository extends MongoRepository<Comment,String> {

    //MongoDB支持方法名定义查询方法  和ESRepository相似 同时支持分页 排序等操作

    /**
     * 通过文章id查询评论
     * @param articleId
     * @return
     */
    List<Comment> findByArticleid(String articleId);

    /**
     * 通过用户id查询所有评论 并按发布时间逆序排列
     * @return
     */
    List<Comment> findByUseridOrderByPublishdateDesc();

}
