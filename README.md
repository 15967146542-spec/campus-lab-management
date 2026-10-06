# 校园实验室综合管理平台

个人开发项目：为学院实验室开发的信息管理系统，管理实验室、指导教师（含教育经历）、科研课题组及成员，提供登录认证、操作日志、数据统计等能力，替代原有 Excel 台账。

- 后端技术栈：Spring Boot 4 / MyBatis / MySQL 8 / JWT (jjwt) / Spring AOP / Bean Validation
- 接口文档：[接口说明.md](接口说明.md)（统一响应 `code/msg/data`，除 `POST /login` 外均需 `token` 请求头）
- 建库脚本：[sql/](sql/)（先执行建库建表，再修改 `application.yml` 中的数据库密码）
- 测试账号：`shinaian / 123456`

## 核心实现

- **条件分页与动态 SQL**：PageHelper 物理分页，`<if>/<where>/<set>/<foreach>` 动态拼接，`resultMap + collection` 一对多封装教育经历
- **JWT 无状态认证**：登录签发 12h HS256 令牌，`HandlerInterceptor` 统一校验，未登录/令牌非法返回 401
- **AOP 操作日志**：`@Around` 环绕全部增删改接口，自动记录操作人（从令牌解析）、入参、返回值、耗时并落库，业务代码零侵入
- **参数校验 + 全局异常**：`@Validated` 字段级校验，`@RestControllerAdvice` 统一处理业务异常/校验异常/唯一键冲突
- **事务**：教师新增/修改跨教师表与教育经历表，`@Transactional(rollbackFor = Exception.class)`

## 运行

```bash
# 1. 执行 sql/ 下脚本建库
# 2. 修改 application.yml 数据库密码
# 3. 启动
mvn spring-boot:run
# 默认 http://localhost:8080
```
