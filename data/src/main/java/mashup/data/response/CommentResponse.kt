package mashup.data.response

import mashup.data.model.CommentDetail

data class CommentResponse(
    val counselingQuestionId: Int,
    val counselingComments: List<CommentDetail>
)