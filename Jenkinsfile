pipeline {
  agent any
  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub-pass')
    IMAGE = "hanishrishen/myapp:${BUILD_NUMBER}"
    COLOR = "green"
  }
  stages {
    stage('Clone Repo') {
      steps {
        git branch: 'main', url: 'https://github.com/hanish-rishen/blue-green-spring-app.git'
      }
    }
    stage('Maven Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t ${IMAGE} .'
      }
    }
    stage('Push Image to Docker Hub') {
      steps {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
        sh 'docker push ${IMAGE}'
      }
    }
    stage('Deploy to Green Environment') {
      steps {
        sh 'kubectl apply -f deployment-green.yaml || true'  // Ensures it exists
        sh 'kubectl set image deployment/myapp-green myapp=${IMAGE} --record'
        sh 'kubectl rollout status deployment/myapp-green'
      }
    }
    stage('Validate New Version (Manual Approval)') {
      steps {
        input message: "Test green pods (curl EXTERNAL-IP/hello). Switch traffic?"
      }
    }
    stage('Switch Service Traffic') {
      steps {
        sh 'kubectl patch service myapp-service -p \'{"spec":{"selector":{"app":"myapp","color":"${COLOR}"}}}\'\''
      }
    }
  }
  post {
    always {
      sh 'docker logout'
    }
  }
}
