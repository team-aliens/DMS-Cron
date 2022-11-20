package team.aliens.dmscron.school.entity

import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_school")
class SchoolJpaEntity(

    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID,

    @Column(columnDefinition = "VARCHAR(10)", nullable = false, unique = true)
    val name: String,

    @Column(columnDefinition = "CHAR(8)", nullable = false, unique = true)
    val code: String,

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    val question: String,

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    val answer: String,

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    val address: String,

    @Column(columnDefinition = "DATE", nullable = false)
    val contractStartedAt: LocalDate,

    @Column(columnDefinition = "DATE")
    val contractEndedAt: LocalDate?

)