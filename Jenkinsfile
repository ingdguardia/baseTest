pipeline{
    agent any
    tools{
        maven '3.9.2'
    }
    stages{
        stage('Example'){
            steps{
                sh 'mvn --version'
                sh 'mvn clean compile test'
            }
        }
    }
}










