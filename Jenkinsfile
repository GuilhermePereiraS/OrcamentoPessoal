pipeline {
    agent any

    stages {
        stage('Validando commit') {
            steps {
                script {
                    def msg = sh(
                        script: 'git log -1 --pretty=%s',
                        returnStdout: true
                    ).trim()

                    echo "Commit: ${msg}"

                    if (!(msg ==~ /^\((0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\)\s+.+$/)) {
                        error('Commit com padrão inválido')
                    }
                }
            }
        }
    }
}
