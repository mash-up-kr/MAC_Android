package mashup.mac.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mashup.mac.util.log.Dlog
import mashup.mac.util.log.Showlog

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B

    abstract var logTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setScreenOrientationPortrait()
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

    }

    private fun setScreenOrientationPortrait() {
        try {
            //https://gun0912.tistory.com/79
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } catch (ignore: IllegalStateException) {
            Dlog.e(ignore.message)
        }
    }

    override fun onResume() {
        super.onResume()
        Showlog.d(logTag)
    }
}
