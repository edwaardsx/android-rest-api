package com.magdaraog.engagia.ojtapp

class Logs  (val logsID: Int? = null, val log: String? = null, val logsDate: String? = null, val logsTime: String? = null) {
    override fun toString(): String {
        return "Logs(ID=$logsID, log=$log, date=$logsDate, time=$logsTime)"
    }
}