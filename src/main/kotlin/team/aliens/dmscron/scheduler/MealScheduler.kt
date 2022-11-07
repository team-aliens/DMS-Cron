package team.aliens.dmscron.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import team.aliens.dmscron.meal.entity.MealJpaEntity
import team.aliens.dmscron.meal.entity.MealJpaEntityId
import team.aliens.dmscron.meal.repository.MealJpaRepository
import team.aliens.dmscron.school.entity.SchoolJpaEntity
import team.aliens.dmscron.meal.service.ProcessingNeisMealsService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Component
class MealScheduler(
    private val mealRepository: MealJpaRepository,
    private val processingNeisMealsService: ProcessingNeisMealsService
) {

    /**
     * 매달 1일 00시에 실행합니다
     **/
    @Scheduled(cron = "0 0 0 1 * *")
    fun saveMeal() {
        // test school uuid
        val schoolId = UUID.fromString("30373832-6566-3438-2d61-3433392d3131")

        val passer = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val contractStartedAtToDate = LocalDate.parse("2020-04-11", passer)

        val mealJpaEntities = processingNeisMealsService.execute().map {
            MealJpaEntity(
                id = MealJpaEntityId(
                    mealDate = it.mealDate,
                    schoolId = schoolId
                ),
                school = SchoolJpaEntity(
                    id = schoolId,
                    name = "대덕소프트웨어마이스터고등학교",
                    code = "12345678",
                    question = "질문입니다",
                    answer = "답변입니다",
                    address = "주소는 ~~",
                    contractStartedAt = contractStartedAtToDate,
                    contractEndedAt = null
                ),
                breakfast = it.breakfast,
                lunch = it.lunch,
                dinner = it.dinner
            )
        }

        mealRepository.saveAll(mealJpaEntities)
    }
}