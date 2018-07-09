#!/usr/bin/env groovy

def call(String fileName) {
    return runSh("cat ${fileName} |grep -i version |tr -d '[:blank:]' |cut -d\"\\\"\" -f2")
}
