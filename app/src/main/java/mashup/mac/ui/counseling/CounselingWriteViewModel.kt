package mashup.mac.ui.counseling

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.repository.CounselingRepository
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.postEvent
import mashup.mac.ext.safeLet
import mashup.mac.model.Emotion
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter
import mashup.mac.util.log.Dlog

class CounselingWriteViewModel(
    private val counselingRepository: CounselingRepository,
    private val animalCategoryAdapter: AnimalCategoryAdapter
) : BaseViewModel() {

    val eventToast = EventMutableLiveData<String>()
    val eventFinish = EventMutableLiveData<Unit>()

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val feelingCheckingMap = hashMapOf<Emotion, Boolean>()

    private val _feeling = MutableLiveData<HashMap<Emotion, Boolean>>()
    val emotion: LiveData<HashMap<Emotion, Boolean>> get() = _feeling

    init {
        Emotion.getAllFeeling().forEachIndexed { index, feeling ->
            feelingCheckingMap[feeling] = index == 0
        }
        showFeelingCheckingMap()
    }

    fun onClickFeeling(emotion: Emotion) {
        initAllFellingUnCheck()
        setFeelingCheck(emotion)
        showFeelingCheckingMap()
    }

    private fun setFeelingCheck(emotion: Emotion) {
        feelingCheckingMap[emotion] = true
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
            counselingRepository.addCounseling(
                title = title!!,
                content = description!!,
                category = category.category.title,
                emotion = feeling.title,
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Dlog.d(it.toString())
                    if (it.isSuccess()) {
                        showToast("작성 완료")
                        finish()
                    } else if (it.isRefreshToken()) {
                        onRefreshToken { submitCounseling() }
                    } else {
                        showToast(it.error)
                    }
                }) {
                    Dlog.e(it.message)
                }.also {
                    compositeDisposable.add(it)
                }
        } ?: run {
            error("category and feeling must not be null")
        }
    }

    private fun getCheckedFeeling(): Emotion? {
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

    private fun finish() {
        eventFinish.postEvent(Unit)
    }
}