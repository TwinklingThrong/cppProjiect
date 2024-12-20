package com.cpp.mapper;

import com.cpp.pojo.Apply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplyMapper {
    //插入申请
    @Insert("insert into applies(job_id, user_id, pdf_url,apply_status) values(#{jobId},#{userId},#{pdfUrl},#{applyStatus}) ")
    void insertJobApply(Apply apply);
    //刷新总的申请数
    @Update("update jobs j set j.job_submit = (select count(*) from applies a where a.job_id = #{jobId} ) where j.id = #{jobId}  ")
    void updateJobSubmit(Integer jobId);

    //检查申请数
    @Select("select count(*) from applies a where a.job_id = #{jobId}")
    int selectJobSubmit(Integer jobId);

    //检查申请数
    @Select("select j.job_total from jobs j where j.id= #{jobId}")
    int selectTotal(Integer jobId);

    //防止重复申请
    @Select("select count(*) from applies where user_id =#{userId} and job_id =#{jobId}")
    int selectByUserId(Integer userId,Integer jobId);

    //通过工作id找对应申请
    @Select("select * from applies where job_id =#{jobId}")
    List<Apply> listByJobId(Integer jobId);

    //改变申请状态
    @Update("UPDATE applies a set a.apply_status = #{applyStatus} where a.id =#{userId}")
    void updateApply(Apply apply);

    //通过用户id找所有他申请过的工作
    @Select("select * from applies where user_id =#{userId}")
    List<Apply> listByUserId(Integer userId);

    //取消申请
    @Delete("delete from applies where id = #{applyId}")
    void deleteApplyById(Integer applyId);

    //判断岗位状态
    @Select("select job_status from jobs where id =#{jobId}")
    int selectJobStatus(Integer jobId);
    @Select("select t.job_id from tags t where id = #{applyId}")
    Integer selectJobByApplyId(Integer applyId);
}
