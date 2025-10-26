# 校园选课系统 (Campus Course Selection System)

## 项目说明

校园选课系统是一个基于 Spring Boot 的 Web 应用程序，为学生提供课程选课功能。系统实现了学生管理、课程管理和选课管理三大核心功能模块。

### 技术栈

- **后端框架**: Spring Boot 3.5.6
- **开发语言**: Java 17
- **数据持久化**: Spring Data JPA + Hibernate
- **数据库**: H2 Database (嵌入式)
- **构建工具**: Maven 3
- **API 风格**: RESTful

### 核心功能

- **学生管理**: 创建、查询、更新和删除学生信息
- **课程管理**: 添加、查询、更新和删除课程信息
- **选课管理**: 学生选课、退课、查询选课记录

### 数据模型

- **Student (学生)**: 学号、姓名、专业、年级、邮箱
- **Course (课程)**: 课程代码、课程名称、容量、教师信息、课程时间
- **Enrollment (选课记录)**: 学生与课程的多对多关联关系

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

## 项目结构

```
CampusCourseSelectionSystem/
├── src/
│   ├── main/
│   │   ├── java/com/zjgsu/wzy/campuscourseselectionsystem/
│   │   │   ├── controller/          # REST API 控制器
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   └── EnrollmentController.java
│   │   │   ├── model/               # JPA 实体模型
│   │   │   │   ├── Student.java
│   │   │   │   ├── Course.java
│   │   │   │   ├── Enrollment.java
│   │   │   │   ├── Instructor.java
│   │   │   │   ├── ScheduleSlot.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── service/             # 业务逻辑层
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── CourseService.java
│   │   │   │   └── EnrollmentService.java
│   │   │   ├── repository/          # 数据访问层
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── EnrollmentRepository.java
│   │   │   └── CampusCourseSelectionSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── data/                             # H2 数据库文件
├── pom.xml
└── README.md
```

## 数据库说明

项目使用 H2 嵌入式数据库，数据文件存储在 `./data/campuscourseselectionsystem.mv.db`。

应用启动时，Spring Data JPA 会自动根据实体类创建数据库表结构。

### 主要数据表

- **students**: 学生信息表
- **courses**: 课程信息表
- **enrollments**: 选课记录表
