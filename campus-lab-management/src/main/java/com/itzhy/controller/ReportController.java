package com.itzhy.controller;

import com.itzhy.pojo.NameValueOption;
import com.itzhy.pojo.Result;
import com.itzhy.pojo.TeamCountOption;
import com.itzhy.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//数据统计Controller
@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    //课题组人数统计(柱状图)
    @GetMapping("/teamCountData")
    public Result getTeamCountData(){
        log.info("统计各课题组人数");
        TeamCountOption option = reportService.getTeamCountData();
        return Result.success(option);
    }

    //成员学历统计(饼状图)
    @GetMapping("/memberDegreeData")
    public Result getMemberDegreeData(){
        log.info("统计各学历成员人数");
        List<NameValueOption> optionList = reportService.getMemberDegreeData();
        return Result.success(optionList);
    }
}
