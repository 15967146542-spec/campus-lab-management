package com.itzhy.mapper;

import com.itzhy.pojo.Member;
import com.itzhy.pojo.MemberQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//课题组成员信息
@Mapper
public interface MemberMapper {

    //条件分页查询成员列表(动态SQL，实现在 MemberMapper.xml)
    public List<Member> list(MemberQueryParam memberQueryParam);

    //新增成员
    @Options(useGeneratedKeys = true, keyProperty = "id")//获取到主键-主键返回
    @Insert("insert into member(name, no, gender, phone, id_card, is_graduate, address, degree, graduation_date, team_id, create_time, update_time)" +
            "    values(#{name}, #{no}, #{gender}, #{phone}, #{idCard}, #{isGraduate}, #{address}, #{degree}, #{graduationDate}, #{teamId}, #{createTime}, #{updateTime})")
    void insert(Member member);

    //根据ID批量删除成员(动态SQL，实现在 MemberMapper.xml)
    void deleteByIds(List<Integer> ids);

    //根据ID查询成员信息
    @Select("select * from member where id = #{id}")
    Member getById(Integer id);

    //修改成员信息(动态SQL，实现在 MemberMapper.xml)
    void update(Member member);

    //安全违规处理：违规次数+1，违规扣分累加
    @Update("update member set violation_count = violation_count + 1, violation_score = violation_score + #{score} where id = #{id}")
    void updateViolation(@Param("id") Integer id, @Param("score") Integer score);

    //统计指定课题组下的成员数量
    @Select("select count(*) from member where team_id = #{teamId}")
    Long countByTeamId(Integer teamId);
}
