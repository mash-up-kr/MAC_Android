package mashup.mac.ui.main

import android.content.Context
import android.content.Intent
import mashup.data.sample.repository.SampleRepository
import mashup.mac.base.BaseViewModel
import mashup.mac.ui.counseling.CounselingWriteActivity
import mashup.mac.ui.mypage.MyPageActivity

class MainViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel() {

    fun onClickCounselingWrite(context: Context) {
        val intent = Intent(context, CounselingWriteActivity::class.java)
        context.startActivity(intent)
    }

    fun onClickMyPage(context: Context) {
        val intent = Intent(context, MyPageActivity::class.java)
        context.startActivity(intent)
    }
    fun onClickList(){}
    fun onClickReset(){}
}