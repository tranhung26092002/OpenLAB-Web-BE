pipeline {
    agent any 
    tools {
        // Sử dụng Maven version 3.8.5
        maven "3.8.5"
    }
    stages {
        stage('Compile and Clean') { 
            steps {
                // Chạy lệnh Maven clean và compile trên Unix agent
                sh "mvn clean compile"
            }
        }
        stage('Deploy') { 
            steps {
                // Chạy lệnh Maven package để tạo file JAR
                sh "mvn package"
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Building Docker Image for Java Express"
                sh 'ls' // Liệt kê các file trong thư mục hiện tại
                // Build Docker image và gán tag với số build của Jenkins
                sh 'docker build -t tranvanhung26092002/openlab_be:${BUILD_NUMBER} .'
            }
        }
        stage('Docker Login') {
            steps {
                // Đăng nhập vào Docker Hub với thông tin đăng nhập đã lưu trong Jenkins Credentials
                withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u tranvanhung26092002 -p ${Dockerpwd}"
                }
            }
        }
        stage('Docker Push') {
            steps {
                // Đẩy Docker image lên Docker Hub
                sh 'docker push tranvanhung26092002/openlab_be:${BUILD_NUMBER}'
            }
        }
        stage('Docker Deploy') {
            steps {
                // Chạy Docker container từ image đã đẩy lên Docker Hub
                sh 'docker run -itd -p 8082:8082 tranvanhung26092002/openlab_be:${BUILD_NUMBER}'
            }
        }
        stage('Archiving') { 
            steps {
                // Lưu trữ file JAR đã build được
                archiveArtifacts '**/target/*.jar'
            }
        }
    }
}
