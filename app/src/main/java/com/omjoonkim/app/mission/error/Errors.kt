package com.omjoonkim.app.mission.error

abstract class Errors : Throwable(){
    abstract val errorText: String
}

class NotFoundUserError() : Errors() {
    override val errorText: String = "해당 사용자를 찾을 수 없습니다."
}