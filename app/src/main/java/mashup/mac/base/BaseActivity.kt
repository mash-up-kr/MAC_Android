package mashup.mac.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mashup.mac.util.log.Showlog

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B

    abstract var logTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        Showlog.d(logTag)
    }
}
