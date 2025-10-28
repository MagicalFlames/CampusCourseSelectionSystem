# 校园选课系统 (Campus Course Selection System)

## 项目说明

校园选课系统是一个基于 Spring Boot 的 Web 应用程序，为学生提供课程选课功能。系统实现了学生管理、课程管理和选课管理三大核心功能模块，采用 JPA 进行数据持久化，数据在重启后仍然存在。

### 版本信息

- **版本号**: v1.1.0
- **项目阶段**: 单体架构（数据库持久化）

### 技术栈

- **后端框架**: Spring Boot 3.5.6
- **开发语言**: Java 17
- **数据持久化**: Spring Data JPA + Hibernate
- **数据库**: H2 Database (文件模式，支持数据持久化)
- **构建工具**: Maven 3
- **API 风格**: RESTful
- **事务管理**: Spring Transaction Management

### 核心功能

- **学生管理**: 创建、查询、更新和删除学生信息
  - 学号和邮箱唯一性校验
  - 删除前的选课记录检查
- **课程管理**: 添加、查询、更新和删除课程信息
  - 课程代码唯一性校验
  - 容量管理和已选人数统计
  - 删除前的选课记录检查
- **选课管理**: 学生选课、退课、查询选课记录
  - 容量限制校验
  - 重复选课检验
  - 选课状态管理（活跃、已退课、已完成）
  - 自动更新课程已选人数

### 数据模型

- **Student (学生)**:
  - 主键 ID (UUID)
  - 学号（唯一）、姓名、专业、年级、邮箱（唯一）
  - 创建时间（自动维护）

- **Course (课程)**:
  - 主键 ID (UUID)
  - 课程代码（唯一）、课程名称、容量、已选人数
  - 教师信息（嵌入式对象）
  - 课程时间（嵌入式对象）
  - 创建时间（自动维护）

- **Enrollment (选课记录)**:
  - 主键 ID (UUID)
  - 学生 ID、课程 ID（双重唯一约束）
  - 选课状态（ACTIVE, DROPPED, COMPLETED）
  - 选课时间（自动维护）
  - 支持查询索引

## 如何运行项目

### 环境要求

- JDK 17 或更高版本
- Maven 3.6+

### 运行步骤

#### 方式一：使用 Maven 直接运行

```bash
mvn spring-boot:run
```

#### 方式二：打包后运行

```bash
# 1. 清理并打包项目
mvn clean package

# 2. 运行生成的 JAR 文件
java -jar target/CampusCourseSelectionSystem-0.0.1-SNAPSHOT.jar
```

### 访问应用

- **应用地址**: http://localhost:8080
- **H2 数据库控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/campuscourseselectionsystem`
  - 用户名: `sa`
  - 密码: (留空)
- **数据库健康检查**: http://localhost:8080/health/db

## 数据持久化说明

### H2 文件模式

项目使用 H2 数据库的文件模式，数据文件存储在 `./data/campuscourseselectionsystem.mv.db`。

**重要特性**:
- ✅ 数据在应用重启后仍然存在
- ✅ 自动创建数据库表结构（基于 JPA 实体）
- ✅ 支持通过 H2 Console 直接查看和操作数据库
- ✅ 开发环境显示 SQL 日志，便于调试

### JPA 配置

- `spring.jpa.hibernate.ddl-auto=update`: 自动更新表结构，保留现有数据
- `spring.jpa.show-sql=true`: 在控制台显示 SQL 语句
- `spring.jpa.properties.hibernate.format_sql=true`: 格式化 SQL 输出

## API 接口列表

所有 API 返回统一的响应格式：

```json
{
  "success": true,
  "message": {
    "ok": "操作成功消息",
    "data": { }
  }
}
```

### 1. 学生管理接口

基础路径: `/api/students`

| 方法 | 接口路径 | 说明 | 请求体示例 |
|------|---------|------|-----------|
| POST | `/api/students` | 创建新学生 | 见下方 |
| GET | `/api/students` | 获取所有学生列表 | - |
| GET | `/api/students/{id}` | 根据 ID 获取学生信息 | - |
| PUT | `/api/students/{id}` | 更新学生信息 | 见下方 |
| DELETE | `/api/students/{id}` | 删除学生 | - |

**创建学生请求示例**:
```json
{
  "studentId": "20210001",
  "name": "张三",
  "major": "计算机科学与技术",
  "grade": "2021",
  "email": "zhangsan@example.com"
}
```

**更新学生请求示例**:
```json
{
  "studentId": "20210001",
  "name": "张三",
  "major": "软件工程",
  "grade": "2021",
  "email": "zhangsan@example.com"
}
```

### 2. 课程管理接口

基础路径: `/api/courses`

| 方法 | 接口路径 | 说明 | 请求体示例 |
|------|---------|------|-----------|
| POST | `/api/courses` | 添加新课程 | 见下方 |
| GET | `/api/courses` | 获取所有课程列表 | - |
| GET | `/api/courses/{courseId}` | 根据 ID 获取课程信息 | - |
| PUT | `/api/courses/{courseId}` | 更新课程信息 | 见下方 |
| DELETE | `/api/courses/{courseId}` | 删除课程 | - |

**添加课程请求示例**:
```json
{
  "code": "CS101",
  "title": "数据结构",
  "capacity": 60,
  "instructor": {
    "id": "T001",
    "name": "李老师",
    "email": "liteacher@example.com"
  },
  "scheduleSlot": {
    "dayOfWeek": "MONDAY",
    "startTime": "08:00",
    "endTime": "09:40",
    "expectedAttendance": 60
  }
}
```

**更新课程请求示例**:
```json
{
  "code": "CS101",
  "title": "数据结构与算法",
  "capacity": 80,
  "instructor": {
    "id": "T001",
    "name": "李老师",
    "email": "liteacher@example.com"
  },
  "scheduleSlot": {
    "dayOfWeek": "MONDAY",
    "startTime": "08:00",
    "endTime": "09:40",
    "expectedAttendance": 80
  }
}
```

### 3. 选课管理接口

基础路径: `/api/enrollments`

| 方法 | 接口路径 | 说明 | 请求体示例 |
|------|---------|------|-----------|
| POST | `/api/enrollments` | 学生选课 | 见下方 |
| GET | `/api/enrollments` | 获取所有选课记录 | - |
| GET | `/api/enrollments/student/{studentId}` | 获取某学生的所有选课记录 | - |
| GET | `/api/enrollments/course/{courseId}` | 获取某课程的所有选课记录 | - |
| DELETE | `/api/enrollments/{id}` | 退课（取消选课） | - |

**学生选课请求示例**:
```json
{
  "studentId": "学生ID (UUID)",
  "courseId": "课程ID (UUID)"
}
```

### 4. 健康检查接口

基础路径: `/health`

| 方法 | 接口路径 | 说明 |
|------|---------|------|
| GET | `/health/db` | 检查数据库连接状态 |

**成功响应示例**:
```json
{
  "success": true,
  "message": {
    "ok": "数据库连接正常",
    "data": {
      "status": "UP",
      "database": "H2",
      "version": "2.x.x",
      "url": "jdbc:h2:file:./data/campuscourseselectionsystem"
    }
  }
}
```

**失败响应示例**:
```json
{
  "success": false,
  "message": {
    "error": "数据库连接失败: ...",
    "data": {
      "status": "DOWN",
      "error": "错误信息"
    }
  }
}
```

## 项目结构

```
CampusCourseSelectionSystem/
├── src/
│   ├── main/
│   │   ├── java/com/zjgsu/wzy/campuscourseselectionsystem/
│   │   │   ├── controller/          # REST API 控制器
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   ├── EnrollmentController.java
│   │   │   │   └── HealthController.java       # 新增：健康检查
│   │   │   ├── model/               # JPA 实体模型
│   │   │   │   ├── Student.java
│   │   │   │   ├── Course.java
│   │   │   │   ├── Enrollment.java
│   │   │   │   ├── EnrollmentStatus.java       # 新增：选课状态枚举
│   │   │   │   ├── Instructor.java
│   │   │   │   ├── ScheduleSlot.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── service/             # 业务逻辑层（含事务管理）
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── CourseService.java
│   │   │   │   └── EnrollmentService.java
│   │   │   ├── repository/          # 数据访问层（含自定义查询）
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── EnrollmentRepository.java
│   │   │   └── CampusCourseSelectionSystemApplication.java
│   │   └── resources/
│   │       ├── application.properties           # 数据库配置
│   │       └── db/                              # 数据库脚本
│   │           └── data.sql                     # 示例数据（可选）
│   └── test/
├── data/                             # H2 数据库文件（自动生成）
├── pom.xml
└── README.md
```

## 数据库说明

项目使用 H2 嵌入式数据库文件模式，数据文件存储在 `./data/campuscourseselectionsystem.mv.db`。

**数据持久化特性**：
- 应用重启后数据自动加载
- Spring Data JPA 自动根据实体类创建/更新数据库表结构
- 支持事务管理，确保数据一致性

### 主要数据表

- **students**: 学生信息表（包含学号、邮箱唯一约束）
- **courses**: 课程信息表（包含课程代码唯一约束、容量管理）
- **enrollments**: 选课记录表（包含学生-课程双重唯一约束、状态索引）

### 业务规则

1. **学号和邮箱唯一性**: 创建或更新学生时，系统会自动检查学号和邮箱是否已存在
2. **课程代码唯一性**: 创建或更新课程时，系统会自动检查课程代码是否已存在
3. **选课容量限制**: 当课程已选人数达到容量上限时，无法继续选课
4. **重复选课检查**: 学生不能重复选择同一门课程（活跃状态）
5. **删除保护**:
   - 有活跃选课记录的学生无法被删除
   - 有活跃选课记录的课程无法被删除
6. **退课限制**: 只能退选处于活跃状态的课程
7. **事务一致性**: 所有涉及多表操作的业务（如选课）都使用事务保证数据一致性

## 版本更新记录

### v1.1.0 (当前版本)
- ✅ 添加 Spring Data JPA 持久化支持
- ✅ 实现数据库文件模式，支持数据持久化
- ✅ 添加事务管理（@Transactional）
- ✅ 完善业务逻辑校验（唯一性、容量、关联检查等）
- ✅ 新增选课状态管理（ACTIVE, DROPPED, COMPLETED）
- ✅ 新增数据库健康检查接口
- ✅ 优化 Repository 自定义查询方法
- ✅ 完善错误处理和异常响应

### v1.0.0
- 基础 REST API 实现
- 学生、课程、选课管理功能
