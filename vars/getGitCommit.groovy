#!/usr/bin/env groovy

def call() {
    return runSh("git rev-parse --short HEAD")
}
