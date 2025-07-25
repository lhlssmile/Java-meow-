pipeline {
    agent any
    stages {
        stage("Checkout") {
            steps {
                git url: "git@github.com:lhlssmile/Java-meow-.git", branch: "main", credentialsId: "github-ssh"
            }
        }
        stage("Build") {
            steps {
                echo "Building..."
                sh "mvn clean package -DskipTests"
            }
        }
        stage("Docker Build") {
            steps {
                withCredentials([file(credentialsId: 'meyaml', variable: 'YAML_FILE')]) {
                    // 调试信息
                    sh "pwd"
                    sh "echo 'YAML_FILE: '\"\$YAML_FILE\""
                    sh "ls -la \"\$YAML_FILE\""
                    
                    // 确保目录存在
                    sh "mkdir -p src/main/resources"
                    
                    // 使用cat重定向，更可靠，添加引号处理空格和特殊字符
                    sh "cat \"\$YAML_FILE\" > src/main/resources/application-dev.yaml"
                    
                    // 验证文件复制成功
                    sh "ls -la src/main/resources/"
                    sh "cat src/main/resources/application-dev.yaml"
                    
                    sh "docker build -t review_multi:latest ."
                }
            }
}
        stage("Deploy") {
            steps {
                sh "docker stop review_multi_container || true"
                sh "docker rm review_multi_container || true"
                sh "docker run -d -p 8080:8080 --name review_multi_container review_multi:latest"
            }
        }
    }
}