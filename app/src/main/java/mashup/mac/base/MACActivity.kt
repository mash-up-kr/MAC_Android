package mashup.mac.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class MACActivity<B : ViewDataBinding>(layoutId: Int) : BaseActivity<B>(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewModelSetup()
    }

    open fun onViewModelSetup() {}
}