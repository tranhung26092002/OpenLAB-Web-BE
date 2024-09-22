pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('MYSQL_ROOT_PASSWORD')
        MYSQL_USER = credentials('MYSQL_USER')
        MYSQL_PASSWORD = credentials('MYSQL_PASSWORD')
        JWT_SECRET_KEY = credentials('JWT_SECRET_KEY')
        GOOGLE_CLIENT_ID = credentials('SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID')
        GOOGLE_CLIENT_SECRET = credentials('SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET')
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout source code from GitHub
                git branch: 'dev-v2', url: 'https://github.com/tranhung26092002/OpenLAB-Web-BE.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Build Docker images
                    sh 'docker-compose build'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    // Deploy services using Docker Compose
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker images and containers after build
            sh 'docker-compose down --remove-orphans'
        }
    }
}
