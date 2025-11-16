# 多阶段构建 - Stage 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# 设置工作目录
WORKDIR /build

# 先复制 pom.xml，利用 Docker 缓存机制
# 如果 pom.xml 没有变化，这一层会被缓存，加快构建速度
COPY pom.xml .

# 下载依赖（这一步会被缓存，除非 pom.xml 改变）
RUN mvn dependency:go-offline -B

# 复制源代码
COPY src ./src

# 构建应用，跳过测试
RUN mvn clean package -DskipTests -B

# 验证 JAR 文件是否生成
RUN ls -lh /build/target/*.jar

# 多阶段构建 - Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# 设置工作目录
WORKDIR /app

# 创建非 root 用户和组（Alpine 使用 addgroup 和 adduser）
RUN addgroup -S appuser && adduser -S appuser -G appuser

# 从构建阶段复制 JAR 文件
COPY --from=builder /build/target/*.jar app.jar

# 创建数据目录并设置权限
RUN mkdir -p /app/data && chown -R appuser:appuser /app

# 切换到非 root 用户
USER appuser

# 暴露应用端口
EXPOSE 8080

# 设置 JVM 参数优化内存使用
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# 使用 ENTRYPOINT 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
