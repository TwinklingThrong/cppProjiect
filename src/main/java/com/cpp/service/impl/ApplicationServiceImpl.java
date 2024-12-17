package com.cpp.service.impl;

import com.cpp.mapper.ApplyMapper;
import com.cpp.mapper.JobMapper;
import com.cpp.pojo.*;
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
    //新增职位申请
    @Override
    public void insertJobApply(Apply apply) {
        //检查申请数
        int jobSubmit = applyMapper.selectJobSubmit(apply.getJobId());
        int total = applyMapper.selectTotal(apply.getJobId());
        //判断
        if (jobSubmit < total) {
            //插入申请
            applyMapper.insertJobApply(apply);
            //重新计算total
            applyMapper.updateJobSubmit(apply);
        }else {
            //再次计算total可以省略
            applyMapper.updateJobSubmit(apply);
            throw new RuntimeException("该岗位已满员");
        }
    }
    //检查是否可以申请这个职位
    //校验岗位申请是否已满，直接前端提醒拦截
    @Override
    public boolean checkJob(Integer jobId, Integer userId) {
        //检查申请数
        int jobSubmit = applyMapper.selectJobSubmit(jobId);
        int total = applyMapper.selectTotal(jobId);
        //防止重复申请
        int i = applyMapper.selectByUserId(userId,jobId);
        //判断岗位状态
        int s = applyMapper.selectJobStatus(jobId);
        //判断时间是否支持
        LocalDate now = LocalDate.now();
        LocalDate end = jobMapper.selectEndById(jobId);
        //一起判断
        if (jobSubmit < total&& i==0&&(now.isBefore(end) || now.isEqual(end))&&s==1){
            return true;
        }else {
            return false;
        }
}
    //查询这个岗位的所以申请信息
    @Override
    public List<Apply> getApplyByJobId(Integer jobId) {
        //通过工作id找对应申请
        List<Apply> applyList = applyMapper.listByJobId(jobId);

        for (Apply apply: applyList) {
            //往申请返回里插入用户信息
            apply.setUser(userMapper.getById(apply.getUserId()));
            //往申请里渲染申请状态信息
            apply.setApplyStatusRender(render.render(apply.getApplyStatus()));
        }
        return applyList;
    }

    //改变申请状态
    @Override
    public void updateApply(Apply apply) {
        applyMapper.updateApply(apply);
    }

    //通过用户id找所有他申请过的工作
    @Override
    public List<Apply> getApplyByUserId(Integer userId) {
        //通过用户id找所有他申请过的工作
        List<Apply> applyList = applyMapper.listByUserId(userId);
        for (Apply apply : applyList) {
            Job job = jobMapper.selectById(apply.getJobId());
            List<JobTag> jobTags = job.getJobTags();
            job.setJobTagsString(render.render(jobTags));
            apply.setApplyStatusRender(render.render(apply.getApplyStatus()));
            apply.setJob(job);

//            applyList.get(i).setJob(jobMapper.selectById(applyList.get(i).getJobId()));
//            applyList.get(i).getJob().setJobTags(jobMapper.taglist(applyList.get(i).getJob()));
//            applyList.get(i).setApplyStatusRender(render.render(applyList.get(i).getApplyStatus()));
//            applyList.get(i).getJob().setJobTagsString(render.render(applyList.get(i).getJob().getJobTags()));
        }
        return applyList;
    }
    //取消申请
    @Override
    public void deleteApply(Integer applyId) {
        applyMapper.deleteApplyById(applyId);
    }
}
