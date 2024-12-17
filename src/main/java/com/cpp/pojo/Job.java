package com.cpp.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Job {
private Integer id;
private String jobTitle;
private String jobDesc;//岗位描述
private String jobSalary;
private Integer jobTotal;//岗位预计接受简历的人数
private Integer jobSubmit;//岗位已提交的人数
private LocalDate end;//岗位截止提交日期
private String jobStatus;//岗位状态，0表示未开发，1表示已开放 之前是Integer
private Integer hrId;
private List<JobTag> jobTags;//岗位标签，感觉要分表
////////////////////////下面是用来渲染的数据
//private String jobStatus
private String jobSubmitjobTotal;
private String jobTagsString;
}
