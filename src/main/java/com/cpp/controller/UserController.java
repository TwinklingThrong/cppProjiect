package com.cpp.controller;

import com.cpp.pojo.Apply;
import com.cpp.pojo.Result;
import com.cpp.pojo.User;
import com.cpp.service.UserService;
import com.cpp.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {
@Autowired
    private UserService userService;
@Autowired
    private AliyunOSSOperator aliyunOSSOperator;

//---------------------用户数据回显---------------------------
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
    User user = userService.getById(id);
    return Result.success(user);
}
//------------------修改数据--------------------------------
    @PutMapping
    public Result update(@RequestBody User user){
    userService.update(user);
    return Result.success();
}
//-------------------上传图片-------------------
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception{
        String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        return Result.success(url);
    }

}