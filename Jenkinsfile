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
                    sh "mkdir -p src/main/resources"
                    // 移除sudo命令，直接使用chmod
                    sh "chmod -R 755 src/main/resources"
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