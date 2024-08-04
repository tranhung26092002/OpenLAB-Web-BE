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
                        script: 'ls target/*.jar || true',
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
                    sh "docker stop ${containerName} || true"
                    sh "docker rm ${containerName} || true"
                }
            }
        }

        stage('Remove Previous Docker Image') {
            steps {
                script {
                    def imageName = 'openlab_be'
                    def imageTag = 'latest'

                    def dockerImageId = sh(
                        script: "docker images -q $imageName:$imageTag || true",
                        returnStdout: true
                    ).trim()

                    if (dockerImageId) {
                        sh "docker rmi -f $dockerImageId"
                    } else {
                        echo "Docker image $imageName:$imageTag does not exist"
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Create and Deploy Container') {
            steps {
                sh 'docker-compose up --force-recreate -d'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
