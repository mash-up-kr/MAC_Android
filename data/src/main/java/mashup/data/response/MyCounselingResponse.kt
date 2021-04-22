package mashup.data.response

import mashup.data.model.Counseling

data class MyCounselingResponse(
   val 학업: List<Counseling>,
   val 연애: List<Counseling>,
   val 직업: List<Counseling>,
   val 관계: List<Counseling>,
   val 음식: List<Counseling>,
   val 기타: List<Counseling>
)