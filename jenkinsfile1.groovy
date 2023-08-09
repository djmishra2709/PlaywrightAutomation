pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/djmishra2709/PlaywrightAutomation.git'
            }
        }
        
        stage('Build and Test') {
            steps {
                // Build and execute your automation tests
                 bat 'mvn clean install'
            }
        }
    }
    
    post {
        always {
            // Archive the test report files
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        
        success {
            script {
                // Get the latest test report file
                def reportFile = findFiles(glob: '**/target/surefire-reports/*.xml').first()
                
                if (reportFile != null) {
                    // Define email parameters
                    def subject = "Test Automation Report - ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}"
                    def body = "Attached is the test report for the ${env.JOB_NAME} build #${env.BUILD_NUMBER}."
                    
                    // Send email with attachment
                    emailext subject: subject,
                             body: body,
                             to: 'djmishra2709@gmail.com',
                             attachLog: true,
                             attachmentsPattern: reportFile.path
                } else {
                    echo "No test report file found."
                }
            }
        }
    }
}
