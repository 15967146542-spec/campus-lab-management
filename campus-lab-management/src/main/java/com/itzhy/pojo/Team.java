package com.itzhy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private Integer id; //ID
    private String name; //课题组名称
    private String location; //实验室房间
    private LocalDate beginDate; //立项日期
    private LocalDate endDate; //结题日期
    private Integer leaderId; //组长(指导教师ID)
    private Integer field; //研究方向, 1:人工智能, 2:网络安全, 3:物联网, 4:大数据, 5:集成电路, 6:智能驾驶
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
    private String leaderName; //组长姓名 (连表查询)
    private String status; //课题组状态 - 筹备中 , 运行中 , 已结题 (动态计算)
}
