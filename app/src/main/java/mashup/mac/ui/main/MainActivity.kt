package mashup.mac.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityMainBinding
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag = "MainActivity"
    private val counselingList = "https://www.cowcat.live/concerns"
    private val counselingDetail = "https://www.cowcat.live/concern/edit"

    private val counselingAdapter by lazy { MainCounselingAdapter() }

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
        initRecyclerView()

        val cue = arrayListOf<CounselingMapModel>()
        cue.add(CounselingMapModel(1, 1, 1, false, "MapMdoMapMo", Category.관계.title))
        cue.add(CounselingMapModel(2, 4, 1, false, "제 남친이 좀 이상...", Category.음식.title))
        cue.add(CounselingMapModel(3, 2, 1, false, "제 남친이 좀 이상...", Category.연애.title))
        cue.add(CounselingMapModel(4, 5, 1, false, "제 남친이 좀 이상...", Category.학업.title))
        cue.add(CounselingMapModel(5, 3, 1, false, "제 남친이 좀 이상...", Category.학업.title))
        binding.customCounselingMap.setCueList(cue)
        counselingAdapter.replaceAll(cue.map {
            CounselingItem(
                category = Category.getFromTitle(it.category)!!,
                title = it.title,
                description = it.description,
                date = it.date,
                answer = it.answer
            )
        })

        counselingAdapter.setOnItemClickListener(object :
            MainCounselingAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                replaceFragment(WebViewFragment.newInstance(counselingDetail, position))
            }
        })

        mainViewModel.mainListView.observe(this, {
            val link = when (mainViewModel.mainListView.value) {
                MainViewModel.CounselingWebView.LIST -> {
                    counselingList
                }
                MainViewModel.CounselingWebView.DETAIL -> {
                    counselingDetail
                }
                else -> counselingDetail
            }
            replaceFragment(WebViewFragment.newInstance(link, 0))
        })

        //TODO: 지우기.. 공전코드입니다,ㅎ,,
        mainViewModel.reset.observe(this, {
            binding.customCounselingMap.setCueList(cue)
            lifecycleScope.launch {
                binding.customCounselingMap.cycle()
            }
        })
    }


    private fun initRecyclerView() {
        binding.rvMainCounseling.adapter = counselingAdapter
    }

    private fun replaceFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.layout_frame_main, fragment)
            addToBackStack(null)
        }.commit()
    }

}