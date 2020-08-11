package com.article.controller;

import com.article.pojo.Article;
import com.article.service.ArticleService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",articleService.findAll());
    }


    /**
     * 用户订阅/取消订阅 文章作者
     * @param userId 用户id
     * @param articleId 文章id 由此查到作业id 添加对应关系
     * @return
     */
    @PostMapping("/subscribe")
    public Result subscribe(String userId,String articleId){

      boolean flag = articleService.subscribe(userId,articleId);

      String message;
      if (flag){
          //未订阅
          message="订阅成功";
      }else{
          //已订阅
          message="您已经订阅该作者";
      }

      return new Result(flag,StatusCode.OK,message);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",articleService.findById(id));
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Article article){
        return new Result(true, StatusCode.OK,"添加成功",articleService.add(article));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result DeleteById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"删除成功",articleService.delete(id));
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update/{id}")
    public Result update(@RequestBody Article article,@PathVariable String id){
        //设置id
        article.setId(id);
        return new Result(true, StatusCode.OK,"修改成功",articleService.update(article));
    }

    /**
     * 条件查询和分页
     * @return
     */
    @PostMapping("/search/{pageNum}/{pageSize}")
    public Result findByCondition(@RequestBody Map<String,Object> map,@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        return new Result(true, StatusCode.OK,"查询成功",articleService.findByCondition(map,pageNum,pageSize));
    }

    /**
     * 文章点赞
     * @param articleId 文章id
     * @return
     */
    @PostMapping("/thumbup")
    public Result thumbup(String articleId){

        //注 正常开发中 用户名可以在jwt令牌中获取 本案例为简化开发 直接把用户名写死
        String userId="1";

       boolean flag = articleService.thumbup(articleId,userId);

       if (flag){
           //已点赞 取消
           return new Result(true,StatusCode.OK,"取消点赞成");
       }
       return new Result(true,StatusCode.OK,"点赞成功");

    }

}
