package cn.itcast.test.swagger.controller;

import cn.itcast.test.swagger.bean.Demo;
import cn.itcast.test.swagger.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="用户管理API",description = "用户管理接口，提供用户的增、删、改、查")
public interface UserControllerApi {

    @ApiOperation("分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="Long"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="query",dataType="Long")
    })
    User<Demo> findList(Long page, Long size, User<Demo> user);
}
