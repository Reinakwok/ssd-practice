pipeline {
    agent none
    environment {
        NVD_API_KEY = credentials('NVD-API-KEY')
        // APP_URL = "http://localhost:8080"
    }
    stages {
        stage('Checkout SCM') {
            agent any
            steps {
                git url: 'https://github.com/Reinakwok/ssd-practice.git', branch: 'master'
            }
        }
        stage('OWASP Dependency Check') {
            agent any
            steps {
                dependencyCheck additionalArguments: '--format HTML --format XML', odcInstallation: 'owasp'
            }
            post {
                success {
                    dependencyCheckPublisher pattern: 'dependency-check-report.xml'
                }
            }
        }
        stage('Integration UI Test') {
            parallel {
                stage('Deploy') {
                    agent any
                    steps {
                        script {
                            // sh 'chmod +x jenkins/scripts/deploy.sh'
                            // sh 'chmod +x jenkins/scripts/kill.sh'
                            sh './jenkins/scripts/deploy.sh'
                            input message: 'Finished using the web site? (Click "Proceed" to continue)'
                            sh './jenkins/scripts/kill.sh'
                        }
                    }
                }
                stage('Headless Browser Test') {
                    agent {
                        docker {
                            image 'maven:3-alpine'
                            args '-v /root/.m2:/root/.m2'
                        }
                    }
                    steps {
                        script {
                            // Run Maven to clean and package the application
                            sh 'mvn -B -DskipTests clean package'
                            // Run UI tests with Selenium
                            sh 'mvn test'
                        }
                    }
                    post {
                        always {
                            script {
                                junit 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }
            }
        }
    }
}
