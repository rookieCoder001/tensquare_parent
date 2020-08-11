package com.tensquare.notice.controller;

import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

/**
 * 消息通知对外接口
 * @Author luo
 * @Date 2020/4/5 23:14
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",noticeService.findById(id));
    }

    /**
     * 分页条件查询所有消息通知
     * @param notice
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/search/{pageNum}/{pageSize}")
    public PageResult<Notice> findPage(@RequestBody Notice notice, @PathVariable Integer pageNum,@PathVariable Integer pageSize){
        return noticeService.findPage(notice,pageNum,pageSize);
    }


    /**
     * 新增
     * @param notice
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice){
        noticeService.add(notice);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /**
     * 修改
     * @param notice
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Notice notice){
        noticeService.update(notice);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /**
     * 分页条件查询所有消息通知
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/fresh/{pageNum}/{pageSize}")
    public PageResult<NoticeFresh> findPageByUserId(String userId, @PathVariable Integer pageNum, @PathVariable Integer pageSize){
        return noticeService.findPageByUserId(userId,pageNum,pageSize);
    }

    /**
     * 删除待推送消息
     * @param noticeFresh
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestBody NoticeFresh noticeFresh){
        noticeService.deleteNoticeFresh(noticeFresh);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
