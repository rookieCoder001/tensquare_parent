package com.tensquare.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @Author luo
 * @Date 2020/4/5 23:17
 */
@TableName("tb_notice_fresh")
public class NoticeFresh {

    private String userId;
    private String noticeId;

    //set get...

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
