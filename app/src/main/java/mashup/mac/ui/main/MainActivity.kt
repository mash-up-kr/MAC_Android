package mashup.mac.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityMainBinding
import mashup.mac.model.Category

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
        cue.add(CounselingMapModel(500,400,"MapMdoMapMo", Category.관계.title,5))
        cue.add(CounselingMapModel(210,1300,"제 남친이 좀 이상...", Category.음식.title,1))
        cue.add(CounselingMapModel(620,1400,"제 남친이 좀 이상...", Category.연애.title,7))
        cue.add(CounselingMapModel(720,700,"제 남친이 좀 이상...", Category.직업.title,7))
        binding.customCounselingMap.setCueList(cue)
    }
}