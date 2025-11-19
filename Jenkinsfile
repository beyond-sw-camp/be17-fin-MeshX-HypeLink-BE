def isManual = false
def changedServices = []
def currentBranch = ""

pipeline {
    agent any

    environment {
        GIT_URL = "https://github.com/beyond-sw-camp/be17-fin-MeshX-HypeLink-BE"
        SERVICES = "api-auth api-item api-direct api-notice monolith"
    }

    stages {

        /* --------------------------------------------------
         🔥 1. Multibranch: BRANCH_NAME 자동 인식
        -------------------------------------------------- */
        stage('Check Branch') {
            steps {
                script {
                    currentBranch = env.BRANCH_NAME ?: ""

                    echo "Detected BRANCH_NAME = '${currentBranch}'"

                    // 🟡 수동 빌드 → 브랜치 없음 → 전체 빌드
                    if (!currentBranch?.trim()) {
                        isManual = true
                        echo "⚠ Manual build → build all services"
                        return
                    }

                    // 🟢 Swagger/MSA만 허용
                    if (!currentBranch.equalsIgnoreCase("Swagger/MSA")) {
                        echo "⛔ Not Swagger/MSA branch → Skip build"
                        currentBuild.result = "SUCCESS"
                        error("STOP")
                    }

                    echo "✅ Allowed branch detected: Swagger/MSA"
                }
            }
        }


        /* --------------------------------------------------
         🔥 2. Checkout (Multibranch는 자동으로 해당 브랜치 checkout함)
        -------------------------------------------------- */
        stage('Checkout') {
            steps {
                checkout scm
            }
        }


        /* --------------------------------------------------
         🔥 3. 변경된 서비스 감지 (Webhook Only)
        -------------------------------------------------- */
        stage('Detect Changed Modules') {
            when {
                expression { !isManual && currentBranch == "Swagger/MSA" }
            }
            steps {
                script {
                    echo "🔍 Checking diff: previous commit vs current commit"

                    def changes = sh(
                        script: "git diff --name-only HEAD~1 HEAD",
                        returnStdout: true
                    ).trim()

                    echo "Changed Files:\n${changes}"

                    changedServices = []

                    SERVICES.split(" ").each { svc ->
                        if (changes.contains("${svc}/")) {
                            changedServices.add(svc)
                        }
                    }

                    echo "Changed Services: ${changedServices}"
                }
            }
        }

        /* --------------------------------------------------
         🔥 4. Build Services (Max 1 parallel)
        -------------------------------------------------- */
        stage('Build Services (Concurrent 1)') {
            steps {
                script {
                    def targetServices = isManual ?
                        SERVICES.split(" ") :
                        changedServices

                    echo "🚀 Build target services: ${targetServices}"

                    // 동시 작업 제한
                    final int MAX_PARALLEL = 1
                    int index = 0

                    while (index < targetServices.size()) {

                        int end = Math.min(index + MAX_PARALLEL, targetServices.size())
                        def batch = targetServices[index..<end].toList()

                        echo "⚡ Running build batch: ${batch}"

                        def jobs = [:]

                        batch.each { svc ->
                            jobs[svc] = {
                                build job: "build-${svc}", wait: true
                            }
                        }

                        parallel jobs

                        index = end
                    }
                }
            }
        }
    }
}
