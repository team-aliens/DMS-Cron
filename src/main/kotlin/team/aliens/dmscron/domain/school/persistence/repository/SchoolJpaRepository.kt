package team.aliens.dmscron.domain.school.persistence.repository

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import team.aliens.dmscron.domain.school.persistence.entity.SchoolJpaEntity

@Repository
interface SchoolJpaRepository : JpaRepository<SchoolJpaEntity, UUID> {
}