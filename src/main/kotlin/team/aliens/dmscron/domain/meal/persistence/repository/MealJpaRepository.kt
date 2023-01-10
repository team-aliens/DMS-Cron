package team.aliens.dmscron.domain.meal.persistence.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import team.aliens.dmscron.meal.entity.MealJpaEntity
import team.aliens.dmscron.meal.entity.MealJpaEntityId

@Repository
interface MealJpaRepository : CrudRepository<MealJpaEntity, MealJpaEntityId> {
}