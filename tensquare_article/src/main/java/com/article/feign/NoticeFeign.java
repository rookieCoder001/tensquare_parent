package com.article.feign;

import com.article.pojo.Notice;
import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author luo
 * @Date 2020/4/6 21:09
 */
@FeignClient(name="tensquare-notice")
public interface NoticeFeign {

    /**
     * 新增消息通知
     * @param notice
     * @return
     */
    @PostMapping("/notice/add")
    Result add(@RequestBody Notice notice);
}
