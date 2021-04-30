package mashup.mac.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.ApiProvider
import mashup.data.Injection
import mashup.data.api.UserApi
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityMainBinding
import mashup.mac.ext.observeEvent
import mashup.mac.ext.toast
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem
import mashup.mac.ui.login.LoginActivity
import mashup.mac.ui.main.custom.CounselingMapCustom
import mashup.mac.util.log.Dlog


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag = "MainActivity"
    private val counselingList = "https://www.cowcat.live/concerns"
    private val counselingDetail = "https://www.cowcat.live/concern/edit"

    private val counselingAdapter by lazy { MainCounselingAdapter() }

    private val mainViewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
                Injection.provideCounselingRepository()
            )
        ).get(MainViewModel::class.java)
    }

    private val locationViewModel by lazy {
        ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LocationViewModel(this@MainActivity, Injection.provideUserRepository()) as T
            }
        }).get(LocationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainVm = mainViewModel
        binding.locationVm = locationViewModel
        initRecyclerView()

        //TODO:: 죽어서 일단 유저정보 못가져 올경우 로그인 액티비티로 보내둠 로직 개선 필요
        val userApi = ApiProvider.provideApiWithoutHeader(UserApi::class.java)
        userApi.getUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (!it.isSuccess()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }) {
                Dlog.e(it.message)
            }

        mainViewModel.loadData()
        locationViewModel.checkLocationFirstTime()

        counselingAdapter.setOnItemClickListener(object :
            MainCounselingAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                replaceFragment(WebViewFragment.newInstance(counselingDetail, position))
            }

            override fun onScrollItem(id: Int) {
                binding.customCounselingMap.selectItemId(id)
            }
        })

        binding.customCounselingMap.setOnMapItemClickListener(object :
            CounselingMapCustom.OnMapItemClickListener {
            override fun onClick(id: Int) {
                binding.rvMainCounseling.scrollToPosition(id)
            }
        })

        binding.rvMainCounseling.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount =
                    (recyclerView.computeHorizontalScrollOffset() + recyclerView.width / 2) / recyclerView.width
                counselingAdapter.setScrollPositionItem(itemCount)
            }
        })
        mainViewModel.distanceText.observe(this) {
            mainViewModel.loadData()
        }

        mainViewModel.mapItems.observe(this) { _counselingMapList ->
            val width = binding.svMainMap.width
            binding.customCounselingMap.setMapWidth(width)
            binding.svMainMap.scrollX = (width / 4.5).toInt()
            binding.customCounselingMap.setCueList(_counselingMapList,mainViewModel.getDistanceMin(),mainViewModel.getDistanceMax())
            Dlog.d(_counselingMapList.toString())
            val counselingMapList = _counselingMapList.map {
                CounselingItem(
                    id = it.id,
                    category = Category.getFromTitle(it.category.name)!!,
                    title = it.title,
                    content = it.content,
                    date = it.date,
                    commentCount = it.commentCount
                )
            }
            counselingAdapter.replaceAll(counselingMapList)
        }

        mainViewModel.mainListView.observe(this) {
            val link = when (mainViewModel.mainListView.value) {
                MainViewModel.CounselingWebView.LIST -> {
                    counselingList
                }
                MainViewModel.CounselingWebView.DETAIL -> {
                    counselingDetail
                }
                else -> counselingDetail
            }
            replaceFragment(WebViewFragment.newInstance(link, -1))
        }

        mainViewModel.reset.observe(this) {
            mainViewModel.loadData()
            //TODO: 지우기.. 공전코드입니다,ㅎ,,
            //            lifecycleScope.launch {
            //                binding.customCounselingMap.cycle()
            //            }
        }

        locationViewModel.eventShowToast.observeEvent(this) {
            toast(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        locationViewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
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