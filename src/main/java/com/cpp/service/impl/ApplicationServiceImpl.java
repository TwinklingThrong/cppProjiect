package com.cpp.service.impl;

import com.cpp.mapper.ApplyMapper;
import com.cpp.mapper.JobMapper;
import com.cpp.pojo.Apply;
import com.cpp.pojo.JobQueryParam;
import com.cpp.pojo.User;
import com.cpp.service.ApplicationService;
import com.cpp.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private RenderImpl render;
    @Autowired
    private UserService userMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private JobMapper jobMapper;
    //新建岗位
    @Override
    public void insertJobApply(Apply apply) {
        int jobSubmit = applyMapper.selectJobSubmit(apply.getJobId());
        int total = applyMapper.selectTotal(apply.getJobId());
        if (jobSubmit < total) {
            applyMapper.insertJobApply(apply);
            applyMapper.updateJobSubmit(apply);
        }else {
            applyMapper.updateJobSubmit(apply);
            throw new RuntimeException("该岗位已满员");
        }
    }
    //检查是否可以申请这个职位

    @Override
    public boolean checkJob(Integer jobId, Integer userId) {
        int jobSubmit = applyMapper.selectJobSubmit(jobId);
        int total = applyMapper.selectTotal(jobId);
        int i = applyMapper.selectByUserId(userId,jobId);
        int s = applyMapper.selectJobStatus(jobId);
        LocalDate now = LocalDate.now();
        LocalDate end = jobMapper.selectEndById(jobId);
        if (jobSubmit < total&& i==0&&(now.isBefore(end) || now.isEqual(end))&&s==1){
            return true;
        }else {
            return false;
        }
}

    @Override
    public List<Apply> getApplyByJobId(Integer jobId) {
        List<Apply> applyList = applyMapper.listByJobId(jobId);
        for (int i = 0; i < applyList.size(); i++) {
            applyList.get(i).setUser(userMapper.getById(applyList.get(i).getUserId()));
            applyList.get(i).setApplyStatusRender(render.render(applyList.get(i).getApplyStatus()));
        }
        return applyList;
    }

    @Override
    public void updateApply(Apply apply) {
        applyMapper.updateApply(apply);
    }

    @Override
    public List<Apply> getApplyByUserId(Integer userId) {
        List<Apply> applyList = applyMapper.listByUserId(userId);
        for (int i = 0; i < applyList.size(); i++) {
            applyList.get(i).setJob(jobMapper.selectById(applyList.get(i).getJobId()));
            applyList.get(i).getJob().setJobTags(jobMapper.taglist(applyList.get(i).getJob()));
            applyList.get(i).setApplyStatusRender(render.render(applyList.get(i).getApplyStatus()));
            applyList.get(i).getJob().setJobTagsString(render.render(applyList.get(i).getJob().getJobTags()));
        }
        return applyList;
    }

    @Override
    public void deleteApply(Integer applyId) {
        applyMapper.deleteApplyById(applyId);
    }
}
