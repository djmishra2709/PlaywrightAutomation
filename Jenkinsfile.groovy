pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from Git
                git branch: 'main', url: 'https://github.com/djmishra2709/PlaywrightAutomation.git'
            }
        }
        stage('Build') {
            steps {
                // Build the Maven project
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run the tests
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Deploy the build artifacts
                sh 'mvn deploy'
            }
        }
    }
}