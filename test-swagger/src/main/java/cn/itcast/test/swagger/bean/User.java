package cn.itcast.test.swagger.bean;

import io.swagger.annotations.ApiModelProperty;

public class User<T> {
    @ApiModelProperty("这是一个用户的ID")
    private Long id;
    private String username;
    private String password;
    private T demo;

    public T getDemo() {
        return demo;
    }

    public void setDemo(T demo) {
        this.demo = demo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
