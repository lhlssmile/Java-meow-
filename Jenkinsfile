pipeline {
    agent any
    stages {
        stage("Checkout") {
            steps {
                git url: "git@github.com:lhlssmile/Java-meow-.git", branch: "melocal", credentialsId: "github-ssh"
            }
        }
        stage("Build") {
            steps {
                echo "Building..."
                sh "mvn clean package"
            }
        }
    }
}
