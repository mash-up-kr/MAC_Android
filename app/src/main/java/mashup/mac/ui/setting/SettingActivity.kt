package mashup.mac.ui.setting

import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivitySettingBinding
import mashup.mac.ext.toast

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override var logTag = "SettingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initButton()
    }

    private fun initButton() {
        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnProducerInfo.setOnClickListener {
                toast("제작자 정보")
            }

            btnAskEmail.setOnClickListener {
                toast("이메일 문의")
            }

            btnVersion.setOnClickListener {
                toast("버전 정보")
            }
        }
    }
}