pipeline {
    agent any

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        sh 'docker-compose build'
                    } catch (Exception e) {
                        echo "Error building Docker image: ${e}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Create and Deploy Container') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                script {
                    try {
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
