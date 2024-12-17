package com.cpp.mapper;

import com.cpp.pojo.Job;
import com.cpp.pojo.JobQueryParam;
import com.cpp.pojo.JobTag;
import org.apache.ibatis.annotations.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface JobMapper {
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into jobs(job_title, job_desc, job_salary, job_total, job_submit, end, hr_id,job_status)" +
            "        values (#{jobTitle},#{jobDesc},#{jobSalary}, #{jobTotal}, #{jobSubmit}, #{end}, #{hrId},#{jobStatus}  )")
    void insertJob(Job job);

    List<Job> list(JobQueryParam jqb);

    List<JobTag> taglist(Job job);

    @Select("select j.end from jobs j where id = #{jobId}")
    LocalDate selectEndById(Integer jobId);
    @Select("select * from jobs j where id = #{id}")
    Job selectById(Integer id);
    @Update("update jobs set job_title = #{jobTitle}, job_desc = #{jobDesc}, job_salary = #{jobSalary}, job_total = #{jobTotal}, job_submit = #{jobSubmit}, end = #{end}, hr_id = #{hrId}, job_status = #{jobStatus} where id = #{id}")
    void updateJob(Job job);
    @Delete("delete from tags where job_id = #{id}")
    void deleteJobTag(Integer id);
    @Delete("delete from jobs where id = #{id}")
    void deleteJob(Integer id);
    @Delete("delete from applies where job_id = #{id}")
    void deleteApply(Integer id);
    @Select("select * from jobs j where j.hr_id = #{hrId}")
    List<Job> listByHrId(Integer hrId);
}
