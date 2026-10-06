-- ============================================================
-- campus_lab 列注释修复：与新业务语义对齐（幂等，可重复执行）
-- ============================================================
USE campus_lab;

-- 实验室
ALTER TABLE lab
    MODIFY COLUMN name VARCHAR(10) NOT NULL COMMENT '实验室名称';

-- 课题组
ALTER TABLE team
    MODIFY COLUMN name VARCHAR(30) NOT NULL COMMENT '课题组名称',
    MODIFY COLUMN begin_date DATE NOT NULL COMMENT '立项日期',
    MODIFY COLUMN end_date DATE NOT NULL COMMENT '结题日期';

-- 成员：学历枚举 + 违纪→实验室安全违规
ALTER TABLE member
    MODIFY COLUMN degree TINYINT UNSIGNED NULL COMMENT '最高学历, 1:专科, 2:本科, 3:硕士, 4:博士',
    MODIFY COLUMN violation_count TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '实验室安全违规次数',
    MODIFY COLUMN violation_score TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '实验室安全违规扣分';

-- 验证
SELECT table_name, column_name, column_comment
FROM information_schema.columns
WHERE table_schema = 'campus_lab'
  AND ((table_name = 'lab' AND column_name = 'name')
    OR (table_name = 'team' AND column_name IN ('name','begin_date','end_date'))
    OR (table_name = 'member' AND column_name IN ('degree','violation_count','violation_score')))
ORDER BY table_name, ordinal_position;
