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

        val cue =  arrayListOf<CounselingMapModel>()
        //x : 210
        cue.add(CounselingMapModel(500,200,"MapMdoMapMo","연애 | 5km",200))
        cue.add(CounselingMapModel(210,1200,"제 남친이 좀 이상...","연애 | 5km",200))
        cue.add(CounselingMapModel(720,600,"제 남친이 좀 이상...","연애 | 5km",200))
        binding.customCounselingMap.setCueList(cue)
    }
}