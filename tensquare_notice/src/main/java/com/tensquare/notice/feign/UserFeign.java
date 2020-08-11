package com.tensquare.notice.feign;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户服务feign接口
 * @Author luo
 * @Date 2020/4/6 19:33
 */
@FeignClient(name = "tensquare-user")
public interface UserFeign {

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    Result findById(@PathVariable(value = "id") String id);
}
