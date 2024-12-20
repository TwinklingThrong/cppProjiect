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

import java.util.List;

@Slf4j
@RequestMapping("/apply")
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    //新增职位申请，内含没有提醒的校验
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
    //校验岗位申请是否已满，直接前端提醒拦截
    @GetMapping("/check/{jobId}/{userId}")
    public Result checkJob(@PathVariable Integer jobId ,@PathVariable Integer userId) {
        if (applicationService.checkJob(jobId,userId)){
        return Result.success();
        } else {
            return Result.error( "该岗位已满额");
        }
    }
    //展示一个职位已投递的申请表
    @GetMapping("/{jobId}")
    public Result getApply(@PathVariable Integer jobId) {
        List<Apply> applies =applicationService.getApplyByJobId(jobId);
        return Result.success(applies);
    }
    //改变申请状态
    @PutMapping
    public Result updateApply(@RequestBody Apply apply){
        //log.info("-------------apply:{}",apply);
        applicationService.updateApply(apply);
        return Result.success();
    }
    //查找User申请的所有Apply
    @GetMapping("/user/{userId}")
    public Result getApplyByUserId(@PathVariable Integer userId){
        return Result.success(applicationService.getApplyByUserId(userId));
    }
    //用户取消申请
    @DeleteMapping("/{applyId}/{jobId}")
    public Result deleteApply(@PathVariable Integer applyId,@PathVariable Integer jobId){
        applicationService.deleteApply(applyId,jobId);
        return Result.success();
    }

    
}
