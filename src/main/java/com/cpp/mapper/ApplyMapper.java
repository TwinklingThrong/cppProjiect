package com.cpp.mapper;

import com.cpp.pojo.Apply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplyMapper {
    @Insert("insert into applies(job_id, user_id, pdf_url,apply_status) values(#{jobId},#{userId},#{pdfUrl},#{applyStatus}) ")
    void insertJobApply(Apply apply);
    @Update("update jobs j set j.job_submit = (select count(*) from applies a where a.job_id = #{jobId} ) where j.id = #{jobId}  ")
    void updateJobSubmit(Apply apply);
    @Select("select count(*) from applies a where a.job_id = #{jobId}")
    int selectJobSubmit(Integer jobId);
    @Select("select j.job_total from jobs j where j.id= #{jobId}")
    int selectTotal(Integer jobId);
    @Select("select count(*) from applies where user_id =#{userId} and job_id =#{jobId}")
    int selectByUserId(Integer userId,Integer jobId);
    @Select("select * from applies where job_id =#{jobId}")
    List<Apply> listByJobId(Integer jobId);
    @Update("UPDATE applies a set a.apply_status = #{applyStatus} where a.id =#{userId}")
    void updateApply(Apply apply);
    @Select("select * from applies where user_id =#{userId}")
    List<Apply> listByUserId(Integer userId);
    @Delete("delete from applies where id = #{applyId}")
    void deleteApplyById(Integer applyId);
}
