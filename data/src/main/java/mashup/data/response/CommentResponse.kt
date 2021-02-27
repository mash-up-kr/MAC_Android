package mashup.data.response

import mashup.data.model.CommentInfo

data class CommentResponse(
    val counselingQuestionId: Int,
    val counselingComments: List<CommentInfo>
)