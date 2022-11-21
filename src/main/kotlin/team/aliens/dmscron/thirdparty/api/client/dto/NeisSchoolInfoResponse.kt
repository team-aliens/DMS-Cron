package team.aliens.dmscron.thirdparty.api.client.dto

data class NeisSchoolInfoResponse(
    val schoolInfo: List<SchoolInfo>
)

data class SchoolInfo(
    val head: List<NeisSchoolInfoHead>,
    val row: List<NeisSchoolInfoRow>
)

data class NeisSchoolInfoHead(
    val RESULT: NeisSchoolInfoRESULT,
    val list_total_count: Int
)

data class NeisSchoolInfoRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class NeisSchoolInfoRow(
    val ATPT_OFCDC_SC_CODE: String,
    val SD_SCHUL_CODE: String
)