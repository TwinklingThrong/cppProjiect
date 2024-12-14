package com.cpp.pojo;

import lombok.Data;

@Data
public class Apply {
    private Integer id;
    private Integer jobId;
    private Integer userId;
    private Integer applyStatus;//1筛选中2待面试3已通过4未通过
    private String pdfUrl;
    /// /////////////////////下面是用来渲染的数据
    private Job job;
    private User user;
    private String applyStatusRender;

}
