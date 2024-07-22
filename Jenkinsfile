pipeline {
    agent any
    environment {
        NVD_API_KEY = credentials('NVD-API-KEY')
        // APP_URL = "http://localhost:5000"
        // CHROME_DRIVER_PATH = "/chromedriver"
    }
    stages {
        stage('Checkout SCM') {
            steps {
                git url: 'https://github.com/Reinakwok/ssd-practice.git', branch: 'master'
            }
        }

        //  stage('Setup Python Environment') {
        //     agent any
        //     steps {
        //         sh 'python -m venv venv'
        //         sh './venv/bin/pip install -r requirements.txt'
        //     }
        // }

        // stage('Run Flask App') {
        //     agent any
        //     steps {
        //         sh 'nohup ./venv/bin/python app.py &'
        //     }
        // }

        // stage('OWASP Dependency Check') {
        //     agent any
        //     steps {
        //         dependencyCheck additionalArguments: '--format HTML --format XML', odcInstallation: 'owasp'
        //     }
        //     post {
        //         success {
        //             dependencyCheckPublisher pattern: 'dependency-check-report.xml'
        //         }
        //     }
        // }
        // stage('Run UI Tests') {
        //     agent {
        //         docker {
        //             image 'maven:3.8.1-jdk-11'
        //             args '-v /root/.m2:/root/.m2'
        //         }
        //     }
        //     steps {
        //         sh 'mvn test -Dwebdriver.chrome.driver=$CHROME_DRIVER_PATH'
        //     }
        //     post {
        //         always {
        //             junit 'target/surefire-reports/*.xml'
        //         }
        //     }
        // }
    }
}
