package com.cpp.mapper;

import com.cpp.pojo.JobTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface JobTagMapper {
    void insertJobTag(List<JobTag> jobTags);
}
