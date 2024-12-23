package com.cpp.service.impl;

import com.cpp.mapper.ApplyMapper;
import com.cpp.mapper.JobMapper;
import com.cpp.mapper.JobTagMapper;
import com.cpp.pojo.*;
import com.cpp.service.JobService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private JobTagMapper jobTagMapper;
    @Autowired
    private ApplyMapper applyMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    // 添加岗位
    public void insertJob(Job job) {
        //插入职位信息
        jobMapper.insertJob(job);
        //插入tag信息
        List<JobTag> tagList = job.getJobTags();
        int id = job.getId();
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tag -> {
                tag.setJobId(id);
            });
           // System.out.println(job.getId());
            //插入tag
            jobTagMapper.insertJobTag(tagList);
        }
    }
    //分页查询
    @Override
    public PageResult<Job> selectPageJob(JobQueryParam jqb) {
        PageHelper.startPage(jqb.getPage(), jqb.getPageSize());
        //更新申请的人数
        //jobMapper.updateJob();
        List<Job> jobsList = jobMapper.list(jqb);
        //遍历集合拿对象
        //for (Job job : jobsList) {
//            Apply apply  = new Apply();
//            apply.setJobId(job.getId());
            //applyMapper.updateJobSubmit(apply);不知道有什么用，忘记以前怎么想的了，好像是之前有bug用来检查的
            //List<JobTag> jobTags = job.getJobTags();
            //job.setJobSubmitjobTotal(render.render(job.getJobSubmit(),job.getJobTotal()));
            //job.setJobTagsString(job.getJobTags().get(0).getTagString());
            //job.setJobStatus(render.render(job.getJobStatus()));
     //   }
        Page<Job> p = (Page<Job>) jobsList;
        return new PageResult<Job>(p.getTotal(),p.getResult());
    }

    // 查询岗位通过岗位ID（数据回显）
    @Override
    public Job getByJobId(Integer id) {
        Job job = jobMapper.selectById(id);
        return job;
    }
    //修改招聘信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job) {
        //更新job
        jobMapper.updateJob(job);
        //先删除
        jobMapper.deleteJobTag(job.getId());
        //后插入
        List<JobTag> tagList = job.getJobTags();
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tag -> {
                tag.setJobId(job.getId());
            });
            jobTagMapper.insertJobTag(tagList);
        }

    }
    //删除招聘
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Integer id) {
        //删除job所以申请
        jobMapper.deleteApply(id);
        //删除job所有标签
        jobMapper.deleteJobTag(id);
        //删除job
        jobMapper.deleteJob(id);
    }

    //Hr查看招聘
    @Override
    public List<Job> getByHrId(Integer hrId) {
        List<Job> jobsList = jobMapper.listByHrId(hrId);
//        for (Job job :jobsList){
//            //渲染数据
//            job.setJobSubmitjobTotal(render.render(job.getJobSubmit(),job.getJobTotal()));
//            job.setJobStatus(render.render(job.getJobStatus()));
//            job.setJobTagsString(render.render(jobMapper.taglist(job)));
//        }
        return jobsList;
    }

}
   