package com.itzhy.mapper;

import com.itzhy.pojo.NameValueOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//数据统计
@Mapper
public interface ReportMapper {

    //统计各课题组人数
    @Select("select t.name name, count(m.id) value from team t left join member m on m.team_id = t.id group by t.id, t.name order by t.id")
    List<NameValueOption> teamCountData();

    //统计各学历成员人数
    @Select("select degree name, count(*) value from member group by degree order by degree")
    List<NameValueOption> memberDegreeData();
}
