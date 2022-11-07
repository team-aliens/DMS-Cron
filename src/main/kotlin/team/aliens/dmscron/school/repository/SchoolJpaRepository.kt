package team.aliens.dmscron.school.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import team.aliens.dmscron.school.entity.SchoolJpaEntity
import java.util.UUID

@Repository
interface SchoolJpaRepository : CrudRepository<SchoolJpaEntity, UUID> {
}