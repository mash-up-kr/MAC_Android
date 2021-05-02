package mashup.mac.ui.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityWebViewBinding
import mashup.mac.ui.main.WebViewFragment
import mashup.mac.util.log.Dlog

class WebViewActivity : BaseActivity<ActivityWebViewBinding>(R.layout.activity_web_view) {

    override var logTag = "WebViewActivity"

    companion object {

        //https://www.cowcat.live/concerns?token={token}
        private const val counselingList = "https://www.cowcat.live/concerns"

        //https://cowcat.live/concern/{questionId}?token={token}
        private const val counselingDetail = "https://www.cowcat.live/concern"

        private const val PARAM_LINK = "link"

        private const val PARAM_QUESTION_ID = "question_id"

        fun startCounselingsActivity(context: Context) {
            context.startActivity(
                Intent(context, WebViewActivity::class.java).apply {
                    putExtra(PARAM_LINK, counselingList)
                }
            )
        }

        fun startCounselingDetailActivity(context: Context, questionId: Int) {
            context.startActivity(
                Intent(context, WebViewActivity::class.java).apply {
                    putExtra(PARAM_LINK, counselingDetail)
                    putExtra(PARAM_QUESTION_ID, questionId)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebViewFragment()
    }

    private fun initWebViewFragment() {
        val link = intent.getStringExtra(PARAM_LINK) ?: ""
        val questionId = intent.getIntExtra(PARAM_QUESTION_ID, -1)

        supportFragmentManager.beginTransaction()
            .add(R.id.webview_container, WebViewFragment.newInstance(link, questionId))
            .commit()
    }

    override fun onBackPressed() {
        val findFragment = supportFragmentManager.findFragmentById(R.id.webview_container)
        Dlog.d("findFragment : $findFragment")

        if (findFragment == null) {
            super.onBackPressed()
        } else {
            if (findFragment is WebViewFragment) {
                findFragment.onBackWebVew()
            } else {
                super.onBackPressed()
            }
        }
    }
}