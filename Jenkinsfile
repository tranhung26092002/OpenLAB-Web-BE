pipeline {
    agent any
    tools { 
        maven 'my-maven' 
    }
    environment {
        MYSQL_ROOT_LOGIN_PSW = credentials('mysql-root-login')  // Đảm bảo rằng credentials ID đúng
    }
    stages {
        stage('Build with Maven') {
            steps {
                sh 'mvn --version'
                sh 'java -version'
                sh 'mvn clean -Dmaven.test.failure.ignore=true'
                sh 'mvn package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Packaging/Pushing image') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker build -t tranvanhung26092002/openlab_be:${BUILD_NUMBER} .'
                    sh 'docker push tranvanhung26092002/openlab_be:${BUILD_NUMBER}' || error "Docker push failed"
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning MySQL'
                sh 'docker image pull mysql:8.0 || echo "Failed to pull MySQL image"'
                sh 'docker network create dev || echo "Network dev already exists"'
                sh 'docker container stop openlab-mysql || echo "Container openlab-mysql does not exist" '
                sh 'docker container prune -f'
                sh 'docker volume rm openlab-mysql-data || echo "Volume openlab-mysql-data does not exist"'

                sh "docker run --name openlab-mysql --rm --network dev -v openlab-mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} -e MYSQL_DATABASE=openlab -p 3307:3306 -d mysql:8.0"
                sh 'sleep 20'

                sh "docker exec -i openlab-mysql mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} openlab < /path/to/your/script.sql" || error "Failed to execute SQL script"
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning Spring Boot container'
                sh 'docker image pull tranvanhung26092002/openlab_be:${BUILD_NUMBER}' || error "Failed to pull Spring Boot image"
                sh 'docker container stop openlab-springboot || echo "Container openlab-springboot does not exist"'
                sh 'docker container rm openlab-springboot || echo "Container openlab-springboot does not exist"'
                sh 'docker network create dev || echo "Network dev already exists"'
                sh 'docker container prune -f'

                sh 'docker run -itd -p 8082:8082 --network dev tranvanhung26092002/openlab_be:${BUILD_NUMBER}' || error "Failed to run Spring Boot container"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
