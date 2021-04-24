package mashup.mac.ui.main


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import mashup.data.pref.PrefUtil
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentWebViewBinding


class WebViewFragment : BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {


    override var logTag = "WebViewFragment"


    companion object {
        const val KEY = "webViewType"
        fun newInstance(data: String) = WebViewFragment().apply {
            arguments = Bundle().apply {
                putString(KEY, data)
            }
        }
    }

    val webViewLink by lazy { requireArguments().getString(KEY) }

    private lateinit var mWebView: WebView
    private lateinit var mWebSettings: WebSettings //웹뷰세팅

    private val mainViewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(MainViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainVm = mainViewModel
        webView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webView() {
        binding.webViewMain.visibility = View.VISIBLE
        mWebView = binding.webViewMain
        mWebView.webViewClient = WebViewClient(); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.settings; //세부 세팅 등록
        mWebSettings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings.javaScriptCanOpenWindowsAutomatically = false; // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.loadWithOverviewMode = true // 메타태그 허용 여부
        mWebSettings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings.builtInZoomControls = false; // 화면 확대 축소 허용 여부
        mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT // 브라우저 캐시 허용 여부
        mWebSettings.domStorageEnabled = true // 로컬저장소 허용 여부
        webViewLink?.let {
            mWebView.loadUrl(it)
        }

        mWebView.addJavascriptInterface(object : CustomJavaScriptCallback {
            //웹에서 네이티브 메소드 호출
            @JavascriptInterface
            override fun userAccessToken(): String? {
                return PrefUtil.get(PrefUtil.PREF_ACCESS_TOKEN, "")
            }
        }, "WebViewCallbackInterface")
    }
}

/**
 *     <meta name="viewport" content="width=device-width, user-scalable=no">
 *      <script language="JavaScript">
 *      function userAccessToken
 *     () {
 *     alert(WebViewCallbackInterface.userAccessToken
 *    (1, 2));
 *     }
 */
interface CustomJavaScriptCallback {
    fun userAccessToken(): String?
}
