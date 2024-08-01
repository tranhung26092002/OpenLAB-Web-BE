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
                    sh 'docker push tranvanhung26092002/openlab_be:${BUILD_NUMBER}'
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull mysql:8.0'
                sh 'docker network create dev || echo "this network exists"'
                sh 'docker container stop openlab-mysql || echo "this container does not exist" '
                sh 'echo y | docker container prune '
                sh 'docker volume rm openlab-mysql-data || echo "no volume"'

                sh '''
                docker run --name openlab-mysql --rm --network dev -v openlab-mysql-data:/var/lib/mysql \
                -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_LOGIN_PSW} \
                -e MYSQL_DATABASE=openlab \
                -p 3306:3306 \
                -d mysql:8.0
                '''
                sh 'sleep 20'
                sh '''
                docker exec -i openlab-mysql mysql --user=root --password=${MYSQL_ROOT_LOGIN_PSW} < script
                '''
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning Spring Boot container'
                sh 'docker image pull tranvanhung26092002/openlab_be:${BUILD_NUMBER}'
                sh 'docker container stop openlab-springboot || echo "Container does not exist"'
                sh 'docker container rm openlab-springboot || echo "Container does not exist"'
                sh 'docker network create dev || echo "Network already exists"'
                sh 'echo y | docker container prune'

                sh '''
                docker run -itd -p 8082:8082 tranvanhung26092002/openlab_be:${BUILD_NUMBER}
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
