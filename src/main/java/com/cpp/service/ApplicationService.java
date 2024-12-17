package com.cpp.service;

import com.cpp.pojo.Apply;

import java.util.List;

public interface ApplicationService {
    //新增职位申请
    void insertJobApply(Apply apply);

    //校验岗位申请是否已满，直接前端提醒拦截
    boolean checkJob(Integer jobId, Integer userId);

    //通过岗位id找所有申请
    List<Apply> getApplyByJobId(Integer jobId);

    //改变申请状态
    void updateApply(Apply apply);

    //通过用户id找所有他申请过的工作
    List<Apply> getApplyByUserId(Integer userId);

    void deleteApply(Integer applyId);
}
