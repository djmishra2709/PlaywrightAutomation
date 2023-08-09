pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from Git
                git branch: 'master', url: 'https://github.com/djmishra2709/PlaywrightAutomation.git'
            }
        }
        stage('Build') {
            steps {
                // Build the Maven project
                bat 'mvn clean'
            }
        }
        stage('Test') {
            steps {
                // Run the tests
                bat 'mvn install'
            }
        }

        stage ('Send Email') {

            mail to: "djmishra2709@gmail.com",
                    cc: 'manager@domainxxx.com', charset: 'UTF-8',
                    from: 'Jenkinsnoreply@gmail.com', mimeType: 'text/html', replyTo: '',
                    bcc: '',
                    subject: "CI: Project name -> ${env.JOB_NAME}",
                    body: "<b>Example</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> URL de build: ${env.BUILD_URL}"
        }

        }
    }
