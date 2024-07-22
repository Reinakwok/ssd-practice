pipeline {
    agent any
    environment {
        NVD_API_KEY = credentials('NVD-API-KEY')
        CHROME_DRIVER_PATH = "./chromedriver"
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
                script {
                    sh 'chmod +x scripts/deploy.sh'
                    sh 'chmod +x scripts/kill.sh'
                    sh './scripts/deploy.sh'
                    input message: 'Finished using the web site? (Click "Proceed" to continue)'
                    sh './scripts/kill.sh'
                }
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                script {
                    // Run OWASP Dependency Check
                    dependencyCheck additionalArguments: '--format HTML --format XML', odcInstallation: 'owasp'
                }
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
}
