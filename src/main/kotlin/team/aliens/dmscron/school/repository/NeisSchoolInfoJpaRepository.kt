package team.aliens.dmscron.school.repository

import java.util.UUID
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import team.aliens.dmscron.school.entity.NeisSchoolInfoJpaEntity

@Repository
interface NeisSchoolInfoJpaRepository : CrudRepository<NeisSchoolInfoJpaEntity, UUID> {
}