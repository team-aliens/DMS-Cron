package team.aliens.dmscron.domain.meal.exception

import team.aliens.dmscron.global.exception.DmsCronException

sealed class MealInfoExceptions(
    override val status: Int,
    override val message: String
) : DmsCronException(status, message) {

    class NotFound(message: String = NOT_FOUND): MealInfoExceptions(404, message)

    companion object {
        private const val NOT_FOUND = "Meal Info Not Found"
    }
}
