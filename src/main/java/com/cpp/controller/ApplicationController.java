package com.cpp.controller;

import com.cpp.pojo.Apply;
import com.cpp.pojo.Result;
import com.cpp.service.ApplicationService;
import com.cpp.service.UserService;
import com.cpp.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/apply")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @PostMapping
    public Result insertJobApply(@RequestBody Apply apply){
        applicationService.insertJobApply(apply);
        return Result.success();
    }
    //上传简历
    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file) throws Exception {
       String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
       return Result.success(url);
    }
    //校验岗位申请是否已满
    @GetMapping("/check/{jobId}/{userId}")
    public Result checkJob(@PathVariable Integer jobId ,@PathVariable Integer userId) {
        // 假设这里需要调用 service 方法进行校验
        if (applicationService.checkJob(jobId,userId)){
        return Result.success();
        } else {
            return Result.error( "该岗位已满额");
        }
    }
    //展示一个职位已投递的申请表
    @GetMapping("/{jobId}")
    public Result getApply(@PathVariable Integer jobId) {
        return Result.success(applicationService.getApplyByJobId(jobId));
    }
    //改变申请状态
    @PutMapping
    public Result updateApply(@RequestBody Apply apply){
        log.info("-------------apply:{}",apply);
        applicationService.updateApply(apply);
        return Result.success();
    }
    //查找User申请的所有Apply
    @GetMapping("/user/{userId}")
    public Result getApplyByUserId(@PathVariable Integer userId){
        return Result.success(applicationService.getApplyByUserId(userId));
    }
    @DeleteMapping("/{applyId}")
    public Result deleteApply(@PathVariable Integer applyId){
        applicationService.deleteApply(applyId);
        return Result.success();
    }

    
}
