#!/usr/bin/env groovy

def call(Map args = [:], Closure body) {
    dockerNode(args) {
        try {
            currentBuild.result = 'STARTED'
            notifySlack(args << [slackStatus: "STARTED"])
            body.call()
            notifySlack(args << [slackStatus: "SUCCESS"])
            currentBuild.result = 'SUCCESSFUL'
        } catch(e) {
            if (currentBuild.result == 'STARTED') {
                currentBuild.result = 'FAILURE'
                notifySlack(args << [slackStatus: "FAILURE"])
            }
            throw e
        }
    }
}
