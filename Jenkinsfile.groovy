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
                bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run the tests
                bat 'mvn test'
            }
        }

    }
}