package com.omjoonkim.app.mission.error

abstract class Errors : Throwable() {
    abstract val errorText: String
}

class NotFoundUserError : Errors() {
    override val errorText: String = "해당 사용자를 찾을 수 없습니다."
}

class NotConnectedNetworkError : Errors() {
    override val errorText: String = "인터넷이 연결되어 있지 않습니다."
}