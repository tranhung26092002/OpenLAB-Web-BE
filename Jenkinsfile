pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean install -Dmaven.test.skip=true -Dspring-boot.repackage.main-class=edu.ptit.openlab'
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
                    def containerName = "openlab_fe"
                    sh "sudo docker stop ${containerName} || true"
                    sh "sudo docker rm ${containerName} || true"
                }
            }
        }

        stage('Remove Previous Docker Image') {
            steps {
                script {
                    def imageName = 'my-docker-image'
                    def imageTag = 'latest'
                    sh "sudo docker rmi -f ${imageName}:${imageTag} || true"
                }
            }
        }

        stage('Create and Deploy Container') {
            steps {
                sh 'sudo docker-compose up --force-recreate -d --remove-orphans'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
