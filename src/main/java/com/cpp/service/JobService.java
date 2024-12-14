package com.cpp.service;

import com.cpp.pojo.Job;
import com.cpp.pojo.JobQueryParam;
import com.cpp.pojo.PageResult;

import java.util.List;

public interface JobService {
    void insertJob(Job job);

    PageResult<Job> selectPageJob(JobQueryParam jqb);

    Job getByJobId(Integer id);

    void updateJob(Job job);

    void deleteJob(Integer id);

    List<Job> getByHrId(Integer userId);
}
