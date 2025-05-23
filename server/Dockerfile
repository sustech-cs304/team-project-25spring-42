# 基础镜像，使用带有JDK的官方OpenJDK镜像（这里用Java 21）
FROM eclipse-temurin:21-jdk-jammy

# 设置工作目录
WORKDIR /app

# 复制构建好的 jar 包到容器中
COPY target/AIDE-1.0.0-SNAPSHOT.jar app.jar

# 对外暴露端口（Spring Boot默认8080）
EXPOSE 8080

# 运行 jar 包
ENTRYPOINT ["java","-jar","app.jar"]
