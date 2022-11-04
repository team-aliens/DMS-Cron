package team.aliens.dmscron

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DmsCronApplication

fun main(args: Array<String>) {
    runApplication<DmsCronApplication>(*args)
}
