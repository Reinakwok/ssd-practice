pipeline {
    agent any
    environment {
        NVD_API_KEY = credentials('NVD-API-KEY')
        CHROME_DRIVER_PATH = "./chromedriver"  // Specify your ChromeDriver path here
    }
    stages {
        stage('Checkout SCM') {
            steps {
                git url: 'https://github.com/Reinakwok/ssd-practice.git', branch: 'master'
            }
        }

        stage('Setup Python Environment') {
            steps {
                sh 'python -m venv venv'
                sh './venv/bin/pip install -r requirements.txt'
            }
        }

        stage('Deploy Flask App') {
            steps {
                sh 'chmod +x deploy.sh'
                sh './scripts/deploy.sh'
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                sh 'dependency-check --project MyProject --scan . --format HTML --format XML'
            }
            post {
                success {
                    dependencyCheckPublisher pattern: 'dependency-check-report.xml'
                }
            }
        }

        stage('Run UI Tests') {
            agent {
                docker {
                    image 'maven:3.8.1-jdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn test -Dwebdriver.chrome.driver=$CHROME_DRIVER_PATH'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
    post {
        always {
            sh 'chmod +x kill.sh'
            sh './scripts/kill.sh || true'  // Allow the script to continue even if pkill fails
            cleanWs()
        }
    }
}
