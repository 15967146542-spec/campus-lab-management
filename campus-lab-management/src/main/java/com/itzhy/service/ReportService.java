package com.itzhy.service;

import com.itzhy.pojo.NameValueOption;
import com.itzhy.pojo.TeamCountOption;

import java.util.List;

public interface ReportService {
    //课题组人数统计
    TeamCountOption getTeamCountData();

    //成员学历统计
    List<NameValueOption> getMemberDegreeData();
}
