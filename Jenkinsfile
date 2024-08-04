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
                    def imageId = sh(
                        script: "sudo docker images -q ${imageName}:${imageTag}",
                        returnStdout: true
                    ).trim()

                    if (imageId) {
                        sh "sudo docker rmi -f ${imageId}"
                    } else {
                        echo "Docker image ${imageName}:${imageTag} does not exist"
                    }
                }
            }
        }

        stage('Validate Docker Compose File') {
            steps {
                sh 'sudo docker-compose config'
            }
        }

        stage('Clean Up') {
            steps {
                sh 'sudo docker-compose down -v'
            }
        }

        stage('Creating and Deploy Container') {
            steps {
                script {
                    try {
                        sh 'sudo docker-compose up --force-recreate -d'
                    } catch (Exception e) {
                        echo 'Docker Compose failed with error: ' + e.getMessage()
                        sh 'docker-compose logs' // Optional: View logs for debugging
                        error 'Docker Compose failed'
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
