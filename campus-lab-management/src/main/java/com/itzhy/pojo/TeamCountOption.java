package com.itzhy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 课题组人数统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCountOption {
    private List<String> teamList; //课题组名称列表
    private List<Integer> dataList; //课题组人数列表
}
