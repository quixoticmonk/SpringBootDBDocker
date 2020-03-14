'''
################# USER DEFINED VARIABLES###############################
'''
def CF_ORG = "columbus-apps"
def CF_SPACE = "jenkins-demo"
def JOB_NAME = "${env.JOB_NAME}".tokenize('/')[0]
def BRANCH = "${env.JOB_NAME}".tokenize('/')[1]
def CF_APPNAME ="jenkins-container-demo"
def KARATE_URL = 'localhost:8080'
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
                script{
                    env.CF_ENDPOINT=""
                }

            }
        }
        stage('Build') {
            steps {
                sh "mvn clean package -DskipTests=true"
            }
        }
        stage('Dockerize the app') {
            steps {
                sh "docker build . -t springboot-docker"
            }
        }
        stage("Quality Gates") {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh"mvn test -Dtest=!TestRunner jacoco:prepare-agent"
                        junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                    }
                }
                stage('Running mutation Tests') {
                    steps {
                        sh"mvn org.pitest:pitest-maven:mutationCoverage"
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target/pit-reports/**/', reportFiles: 'index.html', reportName: 'Mutation testing report', reportTitles: ''])
                    }
                }
                stage('container scan') {
                    steps {
                        sh"trivy -f json -o trivy-results.json springboot-docker:latest"
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
                    sh "cf push ${CF_APPNAME} -p target/*.jar "
                    script{
                        env.CF_ENDPOINT=sh label: '', returnStdout: true, script: "cf app ${CF_APPNAME} |grep \"routes\" |cut -d \":\" -f 2|xargs"
                        env.CF_ENDPOINT=env.CF_ENDPOINT.trim()
                        sh label: '', script:  "sed -e 's@${KARATE_URL}@${CF_ENDPOINT}@g' **/**/karate-config.js"
                    }
                }
            }
        }

        stage("Post deploy Quality Gates") {
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
