# 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY pom.xml /app/
COPY src /app/src/

# 安装特定版本的 Maven (3.8.6)
RUN apt-get update && apt-get install -y curl && \
    curl -O https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar -xzf apache-maven-3.8.6-bin.tar.gz && \
    mv apache-maven-3.8.6 /usr/local/maven && \
    rm apache-maven-3.8.6-bin.tar.gz && \
    ln -s /usr/local/maven/bin/mvn /usr/bin/mvn

# 构建项目，跳过测试加速
RUN mvn --version && mvn clean package -DskipTests

# 暴露应用端口
EXPOSE 8080

# 运行 JAR 文件
CMD ["java", "-jar", "target/review_multi-0.0.1-SNAPSHOT.jar"]