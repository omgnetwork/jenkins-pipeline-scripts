#!/usr/bin/env groovy

def call(Map args = [:], Closure body) {
    def label = args.get("nodeLabel", UUID.randomUUID().toString())

    dockerTemplate(args) {
        node(label) {
            body.call()
        }
    }
}
