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
                script {
                    // 使用Docker Maven镜像来构建项目，避免在Jenkins容器中安装Maven
                    sh "docker run --rm -v \$PWD:/app -w /app maven:3.8.6-openjdk-17 mvn clean package -DskipTests"
                }
            }
        }
        stage("Docker Build") {
            steps {
                withCredentials([file(credentialsId: 'meyaml', variable: 'YAML_FILE')]) {
                    sh "cp \$YAML_FILE src/main/resources/application-dev.yaml"
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