package mashup.mac.ui.counseling

import androidx.lifecycle.MutableLiveData
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.postEvent

class CounselingWriteViewModel : BaseViewModel() {

    val eventToast = EventMutableLiveData<String>()

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    fun submitCounseling() {
        showToast("title : ${title.value} , description : ${description.value}")
    }

    private fun showToast(message: String?) {
        message?.let {
            eventToast.postEvent(message)
        }
    }
}