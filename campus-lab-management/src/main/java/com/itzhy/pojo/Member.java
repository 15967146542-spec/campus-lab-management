package com.itzhy.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Integer id; //ID
    @NotBlank(message = "姓名为必填项")
    private String name; //姓名
    @NotBlank(message = "学号为必填项")
    private String no; //学号
    private Integer gender; //性别, 1:男, 2:女
    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)", message = "手机号格式不正确")
    private String phone; //手机号
    //身份证号可选：15位纯数字 / 18位 / 17位数字+校验位xX
    @Pattern(regexp = "(^$)|(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}[xX]$)", message = "身份证号格式不正确")
    private String idCard; //身份证号
    private Integer isGraduate; //是否研究生, 1:是, 0:否
    private String address; //联系地址
    private Integer degree; //学历, 1:专科, 2:本科, 3:硕士, 4:博士
    private LocalDate graduationDate; //预计毕业时间
    private Integer teamId; //课题组ID
    private Short violationCount; //实验室安全违规次数
    private Short violationScore; //实验室安全违规扣分
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
    private String teamName; //课题组名称 (连表查询)
}
