pipeline {
    agent any

    stages {
        stage('Checkout SCM') {
            steps {
                // Check out code from Git repository
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        // Build Docker image for openlab_be
                        sh 'docker-compose build'
                    } catch (Exception e) {
                        echo "Error building Docker image: ${e}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Deploy Docker Container') {
            steps {
                script {
                    try {
                        // Run Docker container for openlab_be
                        sh 'docker-compose up -d'
                    } catch (Exception e) {
                        echo "Error deploying Docker container: ${e}"
                        currentBuild.result = 'FAILURE'
                        throw e
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
