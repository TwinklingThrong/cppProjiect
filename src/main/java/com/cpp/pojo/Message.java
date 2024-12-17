package com.cpp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer userIdF;
    private Integer userIdS;
    private String message;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate time;//范围时间结束
    private String userIdFString;
    private String userIdSString;
}
