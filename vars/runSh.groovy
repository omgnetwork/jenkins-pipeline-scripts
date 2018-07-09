#!/usr/bin/env groovy

def call(String cmd) {
    return sh(script: cmd, returnStdout: true).trim()
}
