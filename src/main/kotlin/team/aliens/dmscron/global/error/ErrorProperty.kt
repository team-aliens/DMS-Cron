package team.aliens.dmscron.global.error

import org.springframework.http.HttpStatus

interface ErrorProperty {

    fun status(): HttpStatus

    fun message(): String

}