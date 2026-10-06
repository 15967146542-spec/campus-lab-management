package com.itzhy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用的名称-数值封装类（用于统计数据封装）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValueOption {
    private String name;  //名称
    private Integer value; //数值
}
