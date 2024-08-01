pipeline {
    agent any
    tools { 
        maven 'my-maven' 
    }
    environment {
        MYSQL_ROOT_LOGIN_PSW = credentials('mysql-root-login')
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
                    sh 'docker build -t tranvanhung26092002/openlab_be .'
                    sh 'docker push tranvanhung26092002/openlab_be'
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning MySQL container'
                sh 'docker image pull mysql:8.0'
                sh 'docker network create dev || echo "Network already exists"'
                sh 'docker container stop openlab-mysql || echo "Container does not exist"'
                sh 'echo y | docker container prune'
                sh 'docker volume rm openlab-mysql-data || echo "Volume does not exist"'

                sh '''
                docker run --name openlab-mysql --rm --network dev -v openlab-mysql-data:/var/lib/mysql \
                -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} \
                -e MYSQL_DATABASE=openlab \
                -d mysql:8.0
                '''
                sh 'sleep 20'
                sh '''
                docker exec -i openlab-mysql mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} <<-EOSQL
                CREATE DATABASE IF NOT EXISTS openlab;
                USE openlab;
                -- Add your SQL commands here, e.g., create tables
                EOSQL
                '''
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning Spring Boot application'
                sh 'docker image pull tranvanhung26092002/openlab_be'
                sh 'docker container stop openlab-springboot || echo "Container does not exist"'
                sh 'echo y | docker container prune'

                sh '''
                docker run -d --rm --name openlab-springboot -p 8082:8082 --network dev \
                tranvanhung26092002/openlab_be
                '''
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
