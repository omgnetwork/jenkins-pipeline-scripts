#!/usr/bin/env groovy

def call() {
    def rawNodeIP = runSh("ip -4 -o addr show scope global")
    def matched = (rawNodeIP =~ /inet (\d+\.\d+\.\d+\.\d+)/)
    return "" + matched[0].getAt(1)
}
