package com.article.controller;

import com.article.pojo.Comment;
import com.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章评论对外接口
 * @Author luo
 * @Date 2020/4/4 16:24
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 查询所有
     * @return
     */
    @GetMapping
    public Result finaAll() {
        return new Result(true, StatusCode.OK, "查询成功", commentService.findAll());
    }

    /**
     * id 查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result finaById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", commentService.findById(id));
    }

    /**
     * 添加
     * @param comment
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改
     * @param comment
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Comment comment) {
        commentService.update(comment);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * id 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        commentService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 通过文章id查询评论
     * @param articleId 文章id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result findByArticleId(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "删除成功",commentService.findByArticleId(articleId));
    }

    /**
     * 评论点赞
     * @param id 评论id
     * @return
     */
    @PutMapping("/thumbup/{id}")
    public Result thumbup(@PathVariable String id) {
        String result = commentService.thumbup(id);
        return new Result(true, StatusCode.OK, result);
    }

}
