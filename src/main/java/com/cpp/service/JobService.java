package com.cpp.service;

import com.cpp.pojo.Job;
import com.cpp.pojo.JobQueryParam;
import com.cpp.pojo.PageResult;

import java.util.List;

public interface JobService {
    // 添加岗位
    void insertJob(Job job);

    PageResult<Job> selectPageJob(JobQueryParam jqb);
    // 查询岗位通过岗位ID（数据回显）
    Job getByJobId(Integer id);

    void updateJob(Job job);

    void deleteJob(Integer id);

    List<Job> getByHrId(Integer userId);
}
