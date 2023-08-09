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

        stage('Publish Extent Report') {
            steps {
                publishHTML([allowMissing         : false,
                             alwaysLinkToLastBuild: false,
                             keepAll              : true,
                             reportDir            : 'build',
                             reportFiles          : 'TestExecutionReport.html',
                             reportName           : 'HTML Extent Report',
                             reportTitles         : ''])
            }

        }
    }
}