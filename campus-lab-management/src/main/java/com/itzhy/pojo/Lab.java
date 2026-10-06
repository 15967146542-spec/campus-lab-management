package com.itzhy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lab {
    private Integer id;
    private String name; //实验室名称
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
