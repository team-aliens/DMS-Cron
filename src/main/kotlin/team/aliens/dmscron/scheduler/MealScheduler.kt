package team.aliens.dmscron.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.aliens.dmscron.meal.entity.MealJpaEntity
import team.aliens.dmscron.meal.entity.MealJpaEntityId
import team.aliens.dmscron.domain.meal.persistence.repository.MealJpaRepository
import team.aliens.dmscron.domain.meal.ProcessedMealInfoAdapter
import team.aliens.dmscron.domain.school.persistence.entity.SchoolJpaEntity
import team.aliens.dmscron.domain.school.persistence.repository.SchoolJpaRepository
import team.aliens.dmscron.domain.school.ProcessedSchoolInfoAdapter

@Component
class MealScheduler(
    private val mealRepository: MealJpaRepository,
    private val schoolRepository: SchoolJpaRepository,
    private val processedMealInfoAdapter: ProcessedMealInfoAdapter,
    private val processedSchoolInfoAdapter: ProcessedSchoolInfoAdapter
) {

    /**
     * 매달 20일 00시에 실행합니다
     */
    @Transactional
    @Scheduled(cron = "0 0 0 20 * *")
    fun saveMeals() {
        val mealEntities = mutableListOf<MealJpaEntity>()

        /**
         * school table 에서 조회한 name, address 로 Neis 학교 정보를 가져오고, 가져온 학교 정보로 급식을 가져옵니다
         */
        schoolRepository.findAll().map { school ->
            val schoolInfo = getNeisSchoolInfo(school)

            processedMealInfoAdapter.execute(
                sdSchoolCode = schoolInfo.sdSchoolCode,
                regionCode = schoolInfo.regionCode
            ).mealInfoElements.map { meal ->
                val mealEntity = MealJpaEntity(
                    id = MealJpaEntityId(
                        mealDate = meal.mealDate,
                        schoolId = school.id,
                    ),
                    school = school,
                    breakfast = meal.breakfast,
                    lunch = meal.lunch,
                    dinner= meal.dinner
                )

                mealEntities.add(mealEntity)
            }
        }
        mealRepository.saveAll(mealEntities)
    }
    
    private fun getNeisSchoolInfo(school: SchoolJpaEntity) =
        processedSchoolInfoAdapter.execute(schoolName = school.name, schoolAddress = school.address)
}