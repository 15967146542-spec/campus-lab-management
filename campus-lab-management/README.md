# 校园实验室综合管理平台（campus-lab-management）

面向高校学院实验室的信息管理系统：管理实验室、指导教师、科研课题组与课题组成员，提供 JWT 登录认证、AOP 操作日志、参数校验、数据统计等能力，替代传统 Excel 台账管理。

## 技术栈

- Spring Boot 4.0 / MyBatis / MySQL 8+ / PageHelper
- JWT（jjwt 0.12）+ 拦截器登录认证
- Spring AOP 操作日志切面
- Logback 日志（控制台 + 滚动文件）

## 功能模块

| 模块 | 接口前缀 | 说明 |
|---|---|---|
| 实验室管理 | `/labs` | 增删改查，删除前校验实验室下是否有教师 |
| 教师管理 | `/teachers` | 条件分页（姓名/性别/入职时间）、教育经历维护、登录账号 |
| 课题组管理 | `/teams` | 条件分页、状态动态计算（筹备中/运行中/已结题）、删除校验 |
| 成员管理 | `/members` | 条件分页、批量删除、实验室安全违规处理 |
| 数据统计 | `/report` | 课题组人数统计（柱状图）、成员学历统计（饼状图） |
| 登录认证 | `/login` | JWT 令牌签发，其余接口统一经拦截器校验 |

## 快速开始

1. 执行 `sql/migrate_tilas_to_campus_lab.sql`（或项目 `sql/` 目录下脚本）初始化 `campus_lab` 库
2. 修改 `src/main/resources/application.yml` 中的数据库账号密码
3. 启动 `CampusLabManagementApplication`
4. 登录获取 token（种子账号 `shinaian / 123456`），后续请求携带请求头 `token: <jwt>`

```bash
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" \
  -d '{"username":"shinaian","password":"123456"}'
```

## 项目结构

```
src/main/java/com/itzhy/
├── controller/    # 接口层：Lab/Teacher/Team/Member/Report/Login
├── service/       # 业务层（接口 + impl 实现）
├── mapper/        # MyBatis Mapper（注解 + XML 动态 SQL）
├── pojo/          # 实体类与查询参数封装
├── interceptor/   # TokenInterceptor JWT 校验拦截器
├── aop/           # OperateLogAspect 操作日志切面
├── anno/          # 自定义注解
├── config/        # WebMvc 拦截器配置
├── exception/     # BusinessException + 全局异常处理器
└── utils/         # JwtUtils 令牌工具
```
