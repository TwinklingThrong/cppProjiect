package com.cpp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
public class User {
    private Integer id;//mysql自增加
    private String username;//前端上传
    private String password;//前端上传
    private Integer gender;//前端上传
    private String email;//前端上传
    private String phone;//前端上传
    private String image;//前端上传
    //role =1为hr，其他都为普通用户
    private Integer role;//前端上传
    private LocalDateTime createTime; //创建时间,后端系统给
    private LocalDateTime updateTime; //修改时间,后端系统给
}

