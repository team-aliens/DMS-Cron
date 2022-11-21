package team.aliens.dmscron.scheduler

import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.aliens.dmscron.meal.entity.MealJpaEntity
import team.aliens.dmscron.meal.entity.MealJpaEntityId
import team.aliens.dmscron.meal.repository.MealJpaRepository
import team.aliens.dmscron.meal.service.NeisMealsService
import team.aliens.dmscron.school.repository.SchoolJpaRepository
import team.aliens.dmscron.school.service.NeisSchoolInfoService

@Component
class MealScheduler(
    private val mealRepository: MealJpaRepository,
    private val schoolRepository: SchoolJpaRepository,
    private val neisMealsService: NeisMealsService,
    private val neisSchoolInfoService: NeisSchoolInfoService
) {

    /**
     * 매달 20일 00시에 실행합니다
     **/
    @Transactional
    @Scheduled(cron = "0 0 0 20 * *")
    fun saveMeals() {
        val meals = schoolRepository.findAll().flatMap { school ->
            neisMealsService.execute(
                sdSchoolCode = neisSchoolInfoService.execute(schoolName = school.name, schoolAddress = school.address).sdSchoolCode,
                regionCode = neisSchoolInfoService.execute(schoolName = school.name, schoolAddress = school.address).regionCode
            ).map { meal ->
                MealJpaEntity(
                    id = MealJpaEntityId(
                        mealDate = meal.mealDate,
                        schoolId = school.id,
                    ),
                    school = schoolRepository.findByIdOrNull(school.id)!!,
                    breakfast = meal.breakfast,
                    lunch = meal.lunch,
                    dinner= meal.dinner
                )
            }
        }

        mealRepository.saveAll(meals)
    }
}