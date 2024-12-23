package com.cpp.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UriHandle {
    private String uri;
    private String fid;
    private String sid;

    public void chuLi(String uri){
        int last = uri.lastIndexOf('/');
        int lastSecond = uri.lastIndexOf('/', last - 1);
         fid = uri.substring(lastSecond + 1, last);
         sid = uri.substring(last + 1);
    }
}
