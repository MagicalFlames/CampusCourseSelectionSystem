-- 注意：由于使用了 JPA 自动生成表结构，schema.sql 不是必需的

-- 插入示例学生数据
-- INSERT INTO students (id, student_id, name, major, grade, email, created_at)
-- VALUES 
--   ('student-1', '20210001', '张三', '计算机科学与技术', 2021, 'zhangsan@example.com', NOW()),
--   ('student-2', '20210002', '李四', '软件工程', 2021, 'lisi@example.com', NOW()),
--   ('student-3', '20210003', '王五', '计算机科学与技术', 2021, 'wangwu@example.com', NOW());

-- 插入示例课程数据
-- INSERT INTO courses (id, code, title, capacity, enrolled_count, 
--                      instructor_id, instructor_name, instructor_email,
--                      scheduleslot_dayofweek, scheduleslot_starttime, scheduleslot_endtime, scheduleslot_expectedAttendance,
--                      created_at)
-- VALUES 
--   ('course-1', 'CS101', '数据结构', 60, 0, 'T001', '李老师', 'liteacher@example.com', 
--    'MONDAY', '08:00:00', '09:40:00', 60, NOW()),
--   ('course-2', 'CS102', '算法设计', 50, 0, 'T002', '王老师', 'wangteacher@example.com',
--    'TUESDAY', '10:00:00', '11:40:00', 50, NOW()),
--   ('course-3', 'CS103', '数据库原理', 70, 0, 'T003', '赵老师', 'zhaoteacher@example.com',
--    'WEDNESDAY', '14:00:00', '15:40:00', 70, NOW());

