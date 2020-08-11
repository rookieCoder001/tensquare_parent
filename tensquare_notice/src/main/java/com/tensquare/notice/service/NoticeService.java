package com.tensquare.notice.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.tensquare.entity.IdWorker;
import com.tensquare.entity.PageResult;
import com.tensquare.notice.feign.ArticleFeign;
import com.tensquare.notice.feign.UserFeign;
import com.tensquare.notice.mapper.NoticeFreshMapper;
import com.tensquare.notice.mapper.NoticeMapper;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author luo
 * @Date 2020/4/5 23:16
 */
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeFreshMapper noticeFreshMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private ArticleFeign articleFeign;

    /**
     * 设置notice的targetName(目标名称) 和operatorName(操作人昵称)
     *
     * @param notice
     */
    public Notice setNoticeInfo(Notice notice) {
        //获取操作人昵称
        String nickname = (String) ((LinkedHashMap) userFeign.findById(notice.getOperatorId()).getData()).get("nickname");
        notice.setOperatorName(nickname);

        //获取目标
        LinkedHashMap target = (LinkedHashMap) articleFeign.findById(notice.getTargetId()).getData();
        if ("article".equals(notice.getTargetType())) {
            //获取操作目标
            String title = (String) (target.get("title"));
            //赋值
            notice.setTargetName(title);
        }

        return notice;
    }


    /**
     * id查询
     *
     * @param id
     * @return
     */
    public Notice findById(String id) {
        return setNoticeInfo(noticeMapper.selectById(id));
    }

    /**
     * 分页条件查询
     *
     * @param notice
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult<Notice> findPage(Notice notice, Integer pageNum, Integer pageSize) {

        //给pageSize和pageNum赋默认值
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        //分页条件封装
        Page<Notice> page = new Page<>(pageNum, pageSize);

        //分页条件查询
        List<Notice> noticeList = noticeMapper.selectPage(page, new EntityWrapper<>(notice));

        //设置operatorName和targetType
        noticeList.forEach(notice1 -> setNoticeInfo(notice1));

        page.setRecords(noticeList);
        return new PageResult<Notice>(page.getRecords(), page.getTotal());
    }

    /**
     * 修改
     *
     * @param notice
     */
    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    /**
     * 新增
     *
     * @param notice
     */
    public void add(Notice notice) {

        //数据初始化
        notice.setCreatetime(new Date());
        notice.setState("0");// 0未读 1已读

        //生成分布式id 这块儿不能设置id 因为新增消息是系统自动添加得  传入得参数中id已经设置
        notice.setId(idWorker.nextId() + "");
        noticeMapper.insert(notice);

        //待推送消息得存储和发送 由消息队列完成

        //像带推送消息表中插入该条记录
//        NoticeFresh noticeFresh = new NoticeFresh();
//        noticeFresh.setNoticeId(notice.getId());
//        noticeFresh.setUserId(notice.getOperatorId());

//        noticeFreshMapper.insert(noticeFresh);
    }

    /**
     * 查询带推送的消息
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult<NoticeFresh> findPageByUserId(String userId, Integer pageNum, Integer pageSize) {

        //给pageSize和pageNum赋默认值
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        //分页条件封装
        Page<NoticeFresh> page = new Page<>(pageNum, pageSize);

        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(userId);

        //分页条件查询
        List<NoticeFresh> noticeFreshList = noticeFreshMapper.selectPage(page, new EntityWrapper<NoticeFresh>(noticeFresh));

        page.setRecords(noticeFreshList);
        return new PageResult<NoticeFresh>(page.getRecords(), page.getTotal());
    }


    /**
     * 删除带推送消息
     *
     * @param noticeFresh
     */
    public void deleteNoticeFresh(NoticeFresh noticeFresh) {

        noticeFreshMapper.delete(new EntityWrapper<>(noticeFresh));
    }
}
