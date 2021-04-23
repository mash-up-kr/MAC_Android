package mashup.mac.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mashup.data.sample.repository.SampleRepository
import mashup.mac.base.BaseViewModel
import mashup.mac.ui.counseling.CounselingWriteActivity
import mashup.mac.ui.mypage.MyPageActivity

class MainViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel() {

    val mainListView = MutableLiveData<CounselingWebView>()
    val reset = MutableLiveData<Unit>()

    enum class CounselingWebView { DETAIL, LIST }

    fun onClickCounselingWrite(context: Context) {
        val intent = Intent(context, CounselingWriteActivity::class.java)
        context.startActivity(intent)
    }

    fun onClickMyPage(context: Context) {
        val intent = Intent(context, MyPageActivity::class.java)
        context.startActivity(intent)
    }

    fun onClickList() {
        mainListView.postValue(CounselingWebView.LIST)
    }

    fun onClickReset() {
        reset.postValue(Unit)
    }
}