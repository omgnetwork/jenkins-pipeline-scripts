#!/usr/bin/env groovy

def call(Map args = [:]) {
    def color
    def name
    def msg

    def status = args.get("slackStatus", "STARTED")
    def channel = args.get("slackChannel", null)

    status = status ?: "SUCCESSFUL"

    switch (status) {
        case "STARTED":
            color = "#3377aa"
            name = "🚀 Started:"
            break
        case "SUCCESSFUL":
            color = "#77aa33"
            name = "🎉 Success:"
            break
        default:
            color = "#dd4455"
            name = "🔥 Failure:"
            break
    }

    msg = "${name} <${env.RUN_DISPLAY_URL}|${env.JOB_NAME} #${env.BUILD_NUMBER}>\n"
    if (args.containsKey("stageName") && args.stageName) {
        msg = "${msg}\nFailed in ${args.stageName} stage"
    }

    slackSend(
        channel: channel,
        color: color,
        message: msg,
    )
}
