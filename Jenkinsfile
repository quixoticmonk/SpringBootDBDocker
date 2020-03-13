'''
################# USER DEFINED VARIABLES###############################
'''
def CF_ORG = "columbus-apps"
def CF_SPACE = "jenkins-demo-container"
def JOB_NAME = "${env.JOB_NAME}".tokenize('/')[0]
def BRANCH = "${env.JOB_NAME}".tokenize('/')[1]
'''
################# USER DEFINED VARIABLES###############################
'''

pipeline {
    agent any
    tools {
        maven "maven"
        jdk "jdk"
    }

    environment {
        scannerHome = tool 'sonar-scanner'
    }

    options {
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout(false)
        parallelsAlwaysFailFast()
        buildDiscarder(logRotator(numToKeepStr: '15'))
    }

    stages {
        stage("Initialize") {
            steps {
                sh "java -version"
                sh "mvn -version"
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean package -DskipTests=true"
            }
        }
        stage('Dockerize the app') {
            steps {
                sh "docker build ."
            }
        }
        stage("Quality Gates") {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh"mvn test -Dtest=!TestRunner"
                    }
                }
                stage('Running mutation Tests') {
                    steps {
                        sh"mvn org.pitest:pitest-maven:mutationCoverage"
                    }
                }
                stage('Karate Tests') {
                    steps {
                        sh "mvn surefire:test -Dtest=TestRunner"
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/cucumber-html-reports', reportFiles: 'overview-features.html', reportName: 'Karate test run report', reportTitles: ''])
                    }
                }
                stage('Gatling tests') {
                    steps {
                        sh "mvn gatling:test"
                        gatlingArchive()
                    }
                }

                stage('Dependency Check') {
                    steps {
                        dependencyCheck additionalArguments: '', odcInstallation: 'depCheck'
                        dependencyCheckPublisher pattern: ''
                    }
                }
            }
        }

        stage('Sonar') {
            steps{
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }

        stage('Deploy to staging') {
            steps {
                withCredentials([string(credentialsId: 'CF_API', variable: 'CF_API'), string(credentialsId: 'CF_USER', variable: 'CF_USER'), string(credentialsId: 'CF_PASS', variable: 'CF_PASS')]) {
                    sh "cf login -a ${CF_API} -u ${CF_USER} -p ${CF_PASS} -o ${CF_ORG} -s ${CF_SPACE} "
                    sh "cf push jenkins-demo -p target/*.jar "
                }
            }
        }
                stage("Post Deploy Gates") {
            parallel {
                stage('Karate Tests') {
                    steps {
                        sh "mvn surefire:test -Dtest=TestRunner"
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/cucumber-html-reports', reportFiles: 'overview-features.html', reportName: 'Karate test run report', reportTitles: ''])
                    }
                }
                stage('Gatling tests') {
                    steps {
                        sh "mvn gatling:test"
                        gatlingArchive()
                    }
                }

                stage('Health Check') {
                    steps {
                        echo"health check"
                    }
                }
            }
        }
        stage('Deploy to Pre-prod') {
            steps {
                echo"deploy to pre-prod"
            }
        }
        
    }
    post {
        success {
            echo "SUCCESSFUL BUILD"
        }
        failure {
            echo "BUILD FAILED"
        }
        always {
            deleteDir()
        }
    }
}
