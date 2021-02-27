package mashup.mac.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag = "MainActivity"

    //TODO:: ktx 공부하고 적용하기
    private val mainViewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainVm = mainViewModel
    }
}