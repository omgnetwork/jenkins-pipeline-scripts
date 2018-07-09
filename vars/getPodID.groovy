#!/usr/bin/env groovy

def call(String opts) {
    def pods = runSh("kubectl get pods ${opts} -o name")
    def matched = (pods.split()[0] =~ /pods\/(.+)/)
    return "" + matched[0].getAt(1)
}
