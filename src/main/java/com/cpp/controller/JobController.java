package com.cpp.controller;

import com.cpp.pojo.Job;
import com.cpp.pojo.JobQueryParam;
import com.cpp.pojo.PageResult;
import com.cpp.pojo.Result;
import com.cpp.service.ApplicationService;
import com.cpp.service.JobService;
import com.cpp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/job")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    // 添加岗位
    @PostMapping
    public Result insertJob(@RequestBody Job job) {
        jobService.insertJob(job);
        return Result.success();
    }

    // 分页查询岗位
    @GetMapping
    public Result selectPageJob(JobQueryParam jqb) {
        PageResult<Job> pageResult = jobService.selectPageJob(jqb);
        return Result.success(pageResult);
    }

    // 查询岗位通过岗位ID（数据回显）
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Job job = jobService.getByJobId(id);
        return Result.success(job);
    }
    //修改职位信息
    @PutMapping
    public Result updateJob(@RequestBody Job job) {
        jobService.updateJob(job);
        return Result.success();
    }
    //删除招聘
    @DeleteMapping("/{id}")
    public Result deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
        return Result.success();
    }
    //HR查询自己发的申请
    @GetMapping("/hr/{userId}")
    public Result getJob(@PathVariable Integer userId) {
        return Result.success(jobService.getByHrId(userId));
    }

}
