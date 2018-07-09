#!/usr/bin/env groovy

def call(Map args = [:], Closure body) {
    def label = args.get("nodeLabel", "default")
    def dindImage = args.get("dindImage", "docker:18.05-dind")
    def slaveImage = args.get("slaveImage", "omisegoimages/jenkins-slave:3.19-alpine")

    def podYaml = args.get("podYaml", """
        spec:
          nodeSelector:
            cloud.google.com/gke-preemptible: "true"
          tolerations:
            - key: dedicated
              operator: Equal
              value: worker
              effect: NoSchedule
    """.stripIndent())

    podTemplate(
        label: label,
        yaml: podYaml,
        containers: [
            containerTemplate(
                name: "jnlp",
                image: slaveImage,
                args: "\${computer.jnlpmac} \${computer.name}",
                resourceRequestCpu: "200m",
                resourceLimitCpu: "500m",
                resourceRequestMemory: "256Mi",
                resourceLimitMemory: "1024Mi",
                envVars: [
                    containerEnvVar(key: "DOCKER_HOST", value: "tcp://localhost:2375")
                ]
            ),
            containerTemplate(
                name: "dind",
                image: dindImage,
                privileged: true,
                resourceRequestCpu: "700m",
                resourceLimitCpu: "1500m",
                resourceRequestMemory: "1024Mi",
                resourceLimitMemory: "2048Mi",
            ),
        ],
    ) {
        body.call()
    }
}
