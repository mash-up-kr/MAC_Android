package mashup.mac.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import mashup.mac.BuildConfig
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivitySettingBinding
import mashup.mac.ui.webview.WebViewActivity

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override var logTag = "SettingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initButton()
    }

    private fun initView() {
        //알림 기능은 다음으로 미룸
        binding.flAlarm.visibility = View.INVISIBLE

        binding.tvVersion.text = ("${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})")
    }

    private fun initButton() {
        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            btnProducerInfo.setOnClickListener {
                WebViewActivity.startProducerActivity(this@SettingActivity)
            }

            btnAskEmail.setOnClickListener {
                //toast("이메일 문의")
                requestEmail()
            }

            btnVersion.setOnClickListener {
                //toast("버전 정보")
            }
        }
    }

    private fun requestEmail() {
        val email = Intent(Intent.ACTION_SEND).apply {
            type = "plain/Text"
            val address = arrayOf("choco@gmail.com")
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + ">")
            putExtra(
                Intent.EXTRA_TEXT,
                "AppVersion :${BuildConfig.VERSION_NAME}\nDevice : ${Build.MODEL}\nAndroid OS : ${Build.VERSION.SDK_INT}\n\n Content :\n"
            )
        }
        startActivity(email)
    }
}