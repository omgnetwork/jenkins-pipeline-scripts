#!/usr/bin/env groovy

def call(String baseBranch = "", String gitCommit = "") {
    baseBranch = baseBranch ?: "remotes/origin/master"
    gitCommit = gitCommit ?: getGitCommit()
    return runSh("git merge-base ${baseBranch} ${gitCommit}")
}
