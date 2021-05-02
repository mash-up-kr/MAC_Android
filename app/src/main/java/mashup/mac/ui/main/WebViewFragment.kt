package mashup.mac.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import mashup.data.pref.PrefUtil
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentWebViewBinding
import mashup.mac.util.log.Dlog


class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {

    override var logTag = "WebViewFragment"

    companion object {

        private const val LINK = "webViewType"

        private const val QuestionId = "counselingQuestionId"

        fun newInstance(link: String, questionId: Int) = WebViewFragment().apply {
            arguments = Bundle().apply {
                putString(LINK, link)
                putInt(QuestionId, questionId)
            }
        }
    }

    private val webViewLink by lazy { requireArguments().getString(LINK) }
    private val counselingQuestionId by lazy { requireArguments().getInt(QuestionId) }

    private lateinit var mWebView: WebView
    private lateinit var mWebSettings: WebSettings //웹뷰세팅

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webViewMain.visibility = View.VISIBLE
        mWebView = binding.webViewMain
        mWebView.webViewClient = WebViewClient(); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.settings; //세부 세팅 등록
        mWebSettings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.loadWithOverviewMode = true // 메타태그 허용 여부
        mWebSettings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings.builtInZoomControls = false; // 화면 확대 축소 허용 여부
        mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT // 브라우저 캐시 허용 여부
        mWebSettings.domStorageEnabled = true // 로컬저장소 허용 여부
        webViewLink?.let {
            val token = (PrefUtil.get(PrefUtil.PREF_ACCESS_TOKEN, "").replace("Bearer ", ""))

            val url = if (counselingQuestionId > 0) {
                "$it/$counselingQuestionId?token=$token"
            } else {
                "$it?token=$token"
            }
            Dlog.d(url)
            mWebView.loadUrl(url)
        }
        mWebView.addJavascriptInterface(WebAppInterface((object :
            WebAppInterface.WebViewListener {
            override fun webViewClose() {
                Log.e("webview","webViewClose")
                requireActivity().finish()
            }
        })), "mac")

    }

    fun onBackWebVew() {
        if (binding.webViewMain.canGoBack()) {
            binding.webViewMain.goBack()
        } else {
            requireActivity().finish()
        }
    }

    class WebAppInterface(private val webViewListener: WebViewListener) {
        @JavascriptInterface
        fun webview_close() {
            Log.e("webview","webviewclose")
            webViewListener.webViewClose()
        }

        interface WebViewListener {
            fun webViewClose()
        }
    }
}
