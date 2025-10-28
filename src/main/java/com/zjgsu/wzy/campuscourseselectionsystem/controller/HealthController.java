package com.zjgsu.wzy.campuscourseselectionsystem.controller;

import com.zjgsu.wzy.campuscourseselectionsystem.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    /**
     * 数据库健康检查接口
     */
    @GetMapping("/db")
    public ApiResponse checkDatabaseHealth() {
        Map<String, Object> healthInfo = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            // 检查数据库连接是否有效
            boolean isValid = connection.isValid(2);

            if (isValid) {
                healthInfo.put("status", "UP");
                healthInfo.put("database", connection.getMetaData().getDatabaseProductName());
                healthInfo.put("version", connection.getMetaData().getDatabaseProductVersion());
                healthInfo.put("url", connection.getMetaData().getURL());

                return new ApiResponse(true, Map.of(
                        "ok", "数据库连接正常",
                        "data", healthInfo
                ));
            } else {
                healthInfo.put("status", "DOWN");
                healthInfo.put("error", "数据库连接无效");

                return new ApiResponse(false, Map.of(
                        "error", "数据库连接无效",
                        "data", healthInfo
                ));
            }
        } catch (Exception e) {
            healthInfo.put("status", "DOWN");
            healthInfo.put("error", e.getMessage());

            return new ApiResponse(false, Map.of(
                    "error", "数据库连接失败: " + e.getMessage(),
                    "data", healthInfo
            ));
        }
    }
}
