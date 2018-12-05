node ('nimble-jenkins-slave') {
    stage('Download Latest') {
        git(url: 'https://github.com/nimble-platform/dcf-service.git', branch: env.BRANCH_NAME)
    }

    stage('Build Java') {
        sh 'mvn clean install -DskipTests'
    }

    if (env.BRANCH_NAME == 'staging') {
        stage('Build Docker') {
            sh 'docker build -t nimbleplatform/dcf-service:staging .'
        }

        stage('Push Docker') {
            sh 'docker push nimbleplatform/dcf-service:staging'
        }

        stage('Deploy') {
            sh 'ssh staging "cd /srv/nimble-staging/ && ./run-staging.sh restart-single identity-service"' // ToDo: implement???
        }
    }

    if (env.BRANCH_NAME == 'master') {
        stage('Build Docker') {
            sh 'docker build -t nimbleplatform/dcf-service:latest .'
        }

        stage('Push Docker') {
            sh 'docker push nimbleplatform/dcf-service:latest'
        }

        stage('Deploy') {
            sh 'ssh nimble "cd /data/deployment_setup/prod/ && sudo ./run-prod.sh restart-single dcf-service"'
        }
    }
}