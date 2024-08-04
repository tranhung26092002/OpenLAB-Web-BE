pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'bash ./mvnw clean install -Dmaven.test.skip=true -Dspring-boot.repackage.main-class=edu.ptit.openlab'
            }
        }

        stage('Verify Artifact') {
            steps {
                script {
                    def artifactPath = sh(
                        script: 'ls target/*.jar',
                        returnStdout: true
                    ).trim()
                    if (artifactPath.empty) {
                        error 'Artifact not found'
                    }
                    echo "Artifact found at ${artifactPath}"
                }
            }
        }

        stage('Stop and Remove Previous Container') {
            steps {
                script {
                    def containerName = "openlab_be"
                    sh "sudo docker stop ${containerName} || true"
                    sh "sudo docker rm ${containerName} || true"
                }
            }
        }

        stage('Remove Previous Docker Image'){
            steps{
                script{
                    def imageName = 'openlab_be'
                    def imageTag = 'latest'

                    def dockerImageId = sh(
                        script: "sudo docker images -q $imageName:$imageTag",
                        returnStdout: true
                    ).trim()

                    if (dockerImageId) {
                        sh "sudo docker rmi -f $dockerImageId"
                    } else {
                        echo "Docker image $imageName:$imageTag does not exist"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'sudo docker-compose build'
            }
        }

        stage('Creating and Deploy Container') {
            steps {
                sh 'sudo docker-compose up --force-recreate -d'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
