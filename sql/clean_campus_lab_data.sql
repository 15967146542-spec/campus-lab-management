-- ============================================================
-- campus_lab 数据语义清洗（配合改名迁移，把旧业务值换成校园实验室语境）
-- 可重复执行（幂等）
-- ============================================================
USE campus_lab;

-- 1.实验室名称（原 dept 名）
UPDATE lab SET name = CASE id
    WHEN 1 THEN '智能感知实验室'
    WHEN 2 THEN '网络空间安全实验室'
    WHEN 3 THEN '嵌入式系统实验室'
    WHEN 4 THEN '大数据实验室'
    WHEN 5 THEN '集成电路实验室'
    WHEN 8 THEN '智能驾驶实验室'
    ELSE name
END;

-- 2.课题组名称（原班级名）
UPDATE team SET name = CASE id
    WHEN 1 THEN '多模态感知课题组'
    WHEN 2 THEN 'Web安全课题组'
    WHEN 3 THEN '边缘计算课题组'
    WHEN 4 THEN '时序数据挖掘课题组'
    WHEN 5 THEN '车路协同课题组'
    WHEN 6 THEN '芯片验证课题组'
    ELSE name
END;

-- 3.教师头像URL清洗（去除旧图床特征）
UPDATE teacher SET image = NULL;

-- 4.验证
SELECT id, name FROM lab ORDER BY id;
SELECT id, name, location, field FROM team ORDER BY id;
