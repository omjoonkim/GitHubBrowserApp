package com.omjoonkim.app.githubBrowserApp.error

sealed class Error : Throwable() {
    abstract val errorText: String
}

object UnExpected : Error() {
    override val errorText: String = "알 수 없는 에러."
}

object NotFoundUser : Error() {
    override val errorText: String = "해당 사용자를 찾을 수 없습니다."
}

object NotConnectedNetwork : Error() {
    override val errorText: String = "인터넷이 연결되어 있지 않습니다."
}
