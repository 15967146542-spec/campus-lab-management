-- ============================================================
-- 校园实验室综合管理平台 — 数据库迁移脚本
-- 作用：从旧库 tilas 复制数据到新库 campus_lab（原库保持不动，可随时回退）
-- 执行：mysql -uroot -p < migrate_tilas_to_campus_lab.sql
-- ============================================================

-- 1.创建新库
CREATE DATABASE IF NOT EXISTS campus_lab DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2.复制表结构与数据（新表名 = 项目新命名）
CREATE TABLE IF NOT EXISTS campus_lab.lab LIKE tilas.dept;
INSERT INTO campus_lab.lab SELECT * FROM tilas.dept;

CREATE TABLE IF NOT EXISTS campus_lab.teacher LIKE tilas.emp;
INSERT INTO campus_lab.teacher SELECT * FROM tilas.emp;

CREATE TABLE IF NOT EXISTS campus_lab.teacher_expr LIKE tilas.emp_expr;
INSERT INTO campus_lab.teacher_expr SELECT * FROM tilas.emp_expr;

CREATE TABLE IF NOT EXISTS campus_lab.team LIKE tilas.clazz;
INSERT INTO campus_lab.team SELECT * FROM tilas.clazz;

CREATE TABLE IF NOT EXISTS campus_lab.member LIKE tilas.student;
INSERT INTO campus_lab.member SELECT * FROM tilas.student;

CREATE TABLE IF NOT EXISTS campus_lab.operate_log LIKE tilas.operate_log;
INSERT INTO campus_lab.operate_log SELECT * FROM tilas.operate_log;

-- 3.字段重命名（语义从“培训机构”换为“校园实验室”）
-- 3.1 teacher 表：职位→职称、工资→科研经费、入职日期、所属实验室
ALTER TABLE campus_lab.teacher
    CHANGE COLUMN job title TINYINT UNSIGNED NULL COMMENT '职称, 1:教授, 2:副教授, 3:讲师, 4:助教, 5:研究员',
    CHANGE COLUMN salary research_fund INT UNSIGNED NULL COMMENT '科研经费(万元)',
    CHANGE COLUMN entry_date hire_date DATE NULL COMMENT '入职日期',
    CHANGE COLUMN dept_id lab_id INT UNSIGNED NULL COMMENT '所属实验室ID';

-- 3.2 teacher_expr 表：工作经历 → 教育经历
ALTER TABLE campus_lab.teacher_expr
    CHANGE COLUMN emp_id teacher_id INT UNSIGNED NULL COMMENT '教师ID',
    CHANGE COLUMN company school VARCHAR(50) NULL COMMENT '毕业院校',
    CHANGE COLUMN job degree VARCHAR(50) NULL COMMENT '学位';

-- 3.3 team 表：教室→房间、班主任→组长、学科→研究方向
ALTER TABLE campus_lab.team
    CHANGE COLUMN room location VARCHAR(20) NULL COMMENT '实验室房间',
    CHANGE COLUMN master_id leader_id INT UNSIGNED NULL COMMENT '组长(指导教师ID)',
    CHANGE COLUMN subject field TINYINT UNSIGNED NOT NULL COMMENT '研究方向, 1:人工智能, 2:网络安全, 3:物联网, 4:大数据, 5:集成电路, 6:智能驾驶';

-- 3.4 member 表：是否院校学生→是否研究生、班级ID→课题组ID
ALTER TABLE campus_lab.member
    CHANGE COLUMN is_college is_graduate TINYINT UNSIGNED NOT NULL COMMENT '是否研究生, 1:是, 0:否',
    CHANGE COLUMN clazz_id team_id INT UNSIGNED NOT NULL COMMENT '课题组ID';

-- 3.5 operate_log 表：操作人
ALTER TABLE campus_lab.operate_log
    CHANGE COLUMN operate_emp_id operate_teacher_id INT UNSIGNED NULL COMMENT '操作人ID';

-- 3.6 列注释对齐新语义
ALTER TABLE campus_lab.lab
    MODIFY COLUMN name VARCHAR(10) NOT NULL COMMENT '实验室名称';
ALTER TABLE campus_lab.team
    MODIFY COLUMN name VARCHAR(30) NOT NULL COMMENT '课题组名称',
    MODIFY COLUMN begin_date DATE NOT NULL COMMENT '立项日期',
    MODIFY COLUMN end_date DATE NOT NULL COMMENT '结题日期';
ALTER TABLE campus_lab.member
    MODIFY COLUMN degree TINYINT UNSIGNED NULL COMMENT '最高学历, 1:专科, 2:本科, 3:硕士, 4:博士',
    MODIFY COLUMN violation_count TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '实验室安全违规次数',
    MODIFY COLUMN violation_score TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '实验室安全违规扣分';

-- 4.数据迁移：学历枚举值重映射
--    旧: 1初中 2高中 3大专 4本科 5硕士 6博士 → 新: 1专科 2本科 3硕士 4博士
UPDATE campus_lab.member SET degree = CASE degree
    WHEN 3 THEN 1
    WHEN 4 THEN 2
    WHEN 5 THEN 3
    WHEN 6 THEN 4
    WHEN 1 THEN 2
    WHEN 2 THEN 2
    ELSE degree
END;

-- 5.依据新学历推导“是否研究生”（硕士/博士 = 1）
UPDATE campus_lab.member SET is_graduate = CASE WHEN degree IN (3, 4) THEN 1 ELSE 0 END;

-- 6.验证
SELECT 'lab' t, COUNT(*) c FROM campus_lab.lab
UNION ALL SELECT 'teacher', COUNT(*) FROM campus_lab.teacher
UNION ALL SELECT 'teacher_expr', COUNT(*) FROM campus_lab.teacher_expr
UNION ALL SELECT 'team', COUNT(*) FROM campus_lab.team
UNION ALL SELECT 'member', COUNT(*) FROM campus_lab.member
UNION ALL SELECT 'operate_log', COUNT(*) FROM campus_lab.operate_log;
