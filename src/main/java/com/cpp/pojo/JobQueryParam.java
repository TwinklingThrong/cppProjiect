package com.cpp.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class JobQueryParam {
    private Integer page =1;
    private Integer pageSize=10;
    private String jobTitle;
    private String jobDesc;
    private Integer salaryMax;
    private Integer salaryMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;//范围时间开始
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;//范围时间结束
}
