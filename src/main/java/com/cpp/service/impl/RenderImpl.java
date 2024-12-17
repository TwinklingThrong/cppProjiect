package com.cpp.service.impl;

import com.cpp.pojo.JobTag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RenderImpl {
    //渲染招人比
    public String render(Integer getJobSubmit, Integer getJobTotal){
        String bi = getJobSubmit+"/"+getJobTotal;
        return bi;
    }
    //渲染标签
    public String render(List<JobTag> tags){
        String tag = "";
        for (JobTag s : tags) {
            tag += s.getTagString() + " ";
        }
        return tag;
    }
    //渲染岗位状态
    public String render(String jobStatus){
        if (jobStatus.equals("0")){
            return "未开始";
        }else {
            return "进行中";
        }
    }
    //渲染申请状态
    public String render(Integer apppyStatus){
        switch (apppyStatus){
            case 1:
                return "筛选中";
            case 2:
                return "待面试";
            case 3:
                return "已通过";
            case 4:
                return "未通过";
        }
        return "提交异常";
    }
    //渲染性别（由于数据类型废弃）改为前端渲染
//    public String renderG(Integer gender){
//        switch (gender){
//            case 1:
//                return "男";
//            case 2:
//                return "女";
//        }
//        return "未知";
//    }

}
