#!/usr/bin/env groovy

def call(Map args = [:], Closure body) {
    def stageName = args.get("stageName", null)

    stage(stageName) {
        try {
            body.call()
        } catch(e) {
            notifySlack(args << [slackStatus: "FAILURE"])
            throw e
        } finally {
            if (args.containsKey("stageJunit") && args.stageJunit) {
                junit(args.stageJunit)
            }
        }
    }
}
