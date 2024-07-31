pipeline {
    agent any  // Sử dụng bất kỳ agent nào

    tools {
        maven "3.8.5"  // Sử dụng Maven version 3.8.5
    }

    environment {
        // Định nghĩa các biến môi trường cần thiết
        DOCKER_IMAGE_NAME = 'tranvanhung26092002/openlab_be'
        DOCKER_REGISTRY = 'docker.io'
    }

    stages {
        stage('Checkout') {
            steps {
                // Kiểm tra mã nguồn từ repository Git
                git url: 'https://github.com/tranhung26092002/OpenLAB-Web-BE.gitt'
            }
        }

        stage('Compile and Test') {
            steps {
                // Chạy Maven để clean và build dự án, đồng thời kiểm thử
                sh "mvn clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                // Xây dựng Docker image từ Dockerfile
                script {
                    def buildNumber = "${env.BUILD_NUMBER}"
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${buildNumber} ."
                }
            }
        }

        stage('Docker Login') {
            steps {
                // Đăng nhập vào Docker registry
                withCredentials([string(credentialsId: 'DockerId', variable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u ${DOCKER_REGISTRY_USER} -p ${DOCKER_PASSWORD}"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                // Đẩy Docker image lên Docker registry
                script {
                    def buildNumber = "${env.BUILD_NUMBER}"
                    sh "docker push ${DOCKER_IMAGE_NAME}:${buildNumber}"
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                // Triển khai Docker container
                script {
                    def buildNumber = "${env.BUILD_NUMBER}"
                    sh "docker run -d -p 8082:8082 --name openlab_be_${buildNumber} ${DOCKER_IMAGE_NAME}:${buildNumber}"
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Lưu trữ các file JAR đã được build
                archiveArtifacts '**/target/*.jar'
            }
        }
    }

    post {
        always {
            // Làm sạch Docker containers và images cũ (tuỳ chọn)
            sh "docker container prune -f"
            sh "docker image prune -f"
        }
    }
}
