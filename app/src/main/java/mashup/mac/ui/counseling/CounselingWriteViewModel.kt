package mashup.mac.ui.counseling

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.postEvent
import mashup.mac.ext.safeLet
import mashup.mac.model.Feeling
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class CounselingWriteViewModel(
    private val animalCategoryAdapter: AnimalCategoryAdapter
) : BaseViewModel() {

    val eventToast = EventMutableLiveData<String>()

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val feelingCheckingMap = hashMapOf<Feeling, Boolean>()

    private val _feeling = MutableLiveData<HashMap<Feeling, Boolean>>()
    val feeling: LiveData<HashMap<Feeling, Boolean>> get() = _feeling

    init {
        Feeling.getAllFeeling().forEachIndexed { index, feeling ->
            feelingCheckingMap[feeling] = index == 0
        }
        showFeelingCheckingMap()
    }

    fun onClickFeeling(feeling: Feeling) {
        initAllFellingUnCheck()
        setFeelingCheck(feeling)
        showFeelingCheckingMap()
    }

    private fun setFeelingCheck(feeling: Feeling) {
        feelingCheckingMap[feeling] = true
    }

    private fun initAllFellingUnCheck() {
        feelingCheckingMap.keys.forEach { key ->
            feelingCheckingMap[key] = false
        }
    }

    private fun showFeelingCheckingMap() {
        _feeling.postValue(feelingCheckingMap)
    }

    fun submitCounseling() {
        val title = title.value
        if (TextUtils.isEmpty(title)) {
            showToast("제목을 작성해주세요")
            return
        }

        val description = description.value
        if (TextUtils.isEmpty(description)) {
            showToast("내용을 작성해주세요")
            return
        }

        val checkedCategory = animalCategoryAdapter.getCheckedCategory()
        val checkedFeeling = getCheckedFeeling()

        safeLet(checkedCategory, checkedFeeling) { category, feeling ->
            showToast(
                "title : $title , category : ${category.category.title} , feeling : ${feeling.title} , description : $description"
            )
        } ?: run {
            error("category and feeling check must not be null")
        }
    }

    private fun getCheckedFeeling(): Feeling? {
        for ((feeling, usCheck) in feelingCheckingMap) {
            if (usCheck) {
                return feeling
            }
        }
        return null
    }

    private fun showToast(message: String?) {
        message?.let {
            eventToast.postEvent(message)
        }
    }
}