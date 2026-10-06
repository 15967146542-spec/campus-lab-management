package com.itzhy.service.impl;

import com.itzhy.mapper.ReportMapper;
import com.itzhy.pojo.NameValueOption;
import com.itzhy.pojo.TeamCountOption;
import com.itzhy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public TeamCountOption getTeamCountData() {
        //1.统计各课题组人数
        List<NameValueOption> optionList = reportMapper.teamCountData();

        //2.封装结果：teamList=课题组名称列表，dataList=人数列表
        List<String> teamList = optionList.stream().map(NameValueOption::getName).collect(Collectors.toList());
        List<Integer> dataList = optionList.stream().map(NameValueOption::getValue).collect(Collectors.toList());
        return new TeamCountOption(teamList, dataList);
    }

    @Override
    public List<NameValueOption> getMemberDegreeData() {
        //1.统计各学历成员人数
        List<NameValueOption> optionList = reportMapper.memberDegreeData();

        //2.将学历编码转换为名称: 1:专科 2:本科 3:硕士 4:博士
        optionList.forEach(option -> option.setName(degreeName(Integer.valueOf(option.getName()))));
        return optionList;
    }

    //学历编码转名称
    private String degreeName(Integer degree) {
        return switch (degree) {
            case 1 -> "专科";
            case 2 -> "本科";
            case 3 -> "硕士";
            case 4 -> "博士";
            default -> "其他";
        };
    }
}
