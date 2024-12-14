package com.cpp.service;

import com.cpp.pojo.Apply;

import java.util.List;

public interface ApplicationService {
    void insertJobApply(Apply apply);

    boolean checkJob(Integer jobId, Integer userId);

    List<Apply> getApplyByJobId(Integer jobId);

    void updateApply(Apply apply);

    List<Apply> getApplyByUserId(Integer userId);

    void deleteApply(Integer applyId);
}
