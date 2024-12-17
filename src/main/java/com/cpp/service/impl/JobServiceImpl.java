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
    private RenderImpl render;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private JobTagMapper jobTagMapper;
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    @Transactional
    public void insertJob(Job job) {
        jobMapper.insertJob(job);

        List<JobTag> tagList = job.getJobTags();
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tag -> {
                tag.setJobId(job.getId());
            });
            System.out.println(job.getId());
            jobTagMapper.insertJobTag(tagList);
        }
    }

    @Override
    public PageResult<Job> selectPageJob(JobQueryParam jqb) {
        PageHelper.startPage(jqb.getPage(), jqb.getPageSize());
        //更新申请的人数
        //jobMapper.updateJob();
        List<Job> jobsList = jobMapper.list(jqb);
        //遍历集合拿对象
        for (int i = 0; i < jobsList.size(); i++) {
            Apply apply  = new Apply();
            apply.setJobId(jobsList.get(i).getId());
            applyMapper.updateJobSubmit(apply);
            //把标签拿出来
            List<JobTag> TagsList = jobMapper.taglist(jobsList.get(i));
            //标签放入上传的对象里
            jobsList.get(i).setJobTags(TagsList);
            //拼接申请人数和招收人数比并放入上传对象
            jobsList.get(i).setJobSubmitjobTotal(render.render(jobsList.get(i).getJobSubmit(),jobsList.get(i).getJobTotal()));
            jobsList.get(i).setJobTagsString(render.render(TagsList));
            jobsList.get(i).setJobStatus(render.render(jobsList.get(i).getJobStatus()));
        }
        Page<Job> p = (Page<Job>) jobsList;
        return new PageResult<Job>(p.getTotal(),p.getResult());
    }

    @Override
    public Job getByJobId(Integer id) {
        Job job = jobMapper.selectById(id);
        job.setJobTags(jobMapper.taglist(job));
        return job;
    }

    @Override
    public void updateJob(Job job) {
        jobMapper.updateJob(job);
        jobMapper.deleteJobTag(job.getId());
        List<JobTag> tagList = job.getJobTags();
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(tag -> {
                tag.setJobId(job.getId());
            });
            System.out.println(job.getId());
            jobTagMapper.insertJobTag(tagList);
        }

    }

    @Override
    public void deleteJob(Integer id) {
        System.out.println("-------------deleteApply----------------");
        jobMapper.deleteApply(id);
        System.out.println("-------------deleteJobTag----------------");
        jobMapper.deleteJobTag(id);
        System.out.println("-------------deleteJob----------------");
        jobMapper.deleteJob(id);
    }

    @Override
    public List<Job> getByHrId(Integer hrId) {
        List<Job> jobsList = jobMapper.listByHrId(hrId);
        for (int i = 0; i < jobsList.size(); i++){
            jobsList.get(i).setJobSubmitjobTotal(render.render(jobsList.get(i).getJobSubmit(),jobsList.get(i).getJobTotal()));
            jobsList.get(i).setJobStatus(render.render(jobsList.get(i).getJobStatus()));
            jobsList.get(i).setJobTagsString(render.render(jobMapper.taglist(jobsList.get(i))));
        }
        return jobsList;
    }

}
   