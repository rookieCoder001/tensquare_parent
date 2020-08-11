package com.tensquare.notice.feign;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 文章服务feign接口
 * @Author luo
 * @Date 2020/4/6 19:34
 */
@FeignClient(name = "tensquare-article")
public interface ArticleFeign {

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    Result findById(@PathVariable(value = "id") String id);
}
