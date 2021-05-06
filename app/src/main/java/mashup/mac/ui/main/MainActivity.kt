package mashup.mac.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mashup.data.Injection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityMainBinding
import mashup.mac.ext.observeEvent
import mashup.mac.ext.toast
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem
import mashup.mac.ui.counseling.CounselingWriteActivity
import mashup.mac.ui.main.custom.CounselingMapCustom
import mashup.mac.ui.webview.WebViewActivity
import mashup.mac.util.log.Dlog


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag = "MainActivity"

    companion object {
        const val REQUEST_CODE_COUNSELING_WRITE = 1002
    }

    private val counselingAdapter by lazy {
        MainCounselingAdapter().apply {
            setOnItemClickListener(object :
                MainCounselingAdapter.OnItemClickListener {
                override fun onClick(questionId: Int) {
                    WebViewActivity.startCounselingDetailActivity(this@MainActivity, questionId)
                }

                override fun onScrollItem(id: Int) {
                    binding.customCounselingMap.selectItemId(id)
                }
            })
        }
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
            viewModelStore, MainViewModelFactory(
                Injection.provideCounselingRepository(),
                Injection.provideUserRepository()
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

        initView()
        initRecyclerView()
        mainViewModel.loadUserData()

        if (locationViewModel.isEvenGetLocation()) {
            //위치 정보 있는 경우에 화면을 로드 합니다.
            locationViewModel.showUserLocation()
            mainViewModel.loadData()
        } else {
            //위치 정보가 없는 경우에 위치 정보를 받아옵니다.
            locationViewModel.checkLocationPermission()
        }
    }

    override fun onViewModelSetup() {
        mainViewModel.distanceText.observe(this, {
            mainViewModel.loadData()
        })

        mainViewModel.mapItems.observe(this, { _counselingMapList ->
            val width = binding.svMainMap.width
            binding.customCounselingMap.setMapWidth(width)
            binding.svMainMap.scrollX = (width / 4.5).toInt()
            binding.customCounselingMap.setCueList(_counselingMapList, mainViewModel.getDistanceMin(), mainViewModel.getDistanceMax())
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
        })

        mainViewModel.mainListView.observe(this, {
            WebViewActivity.startCounselingsActivity(this@MainActivity)
        })

        mainViewModel.reset.observe(this, {
            mainViewModel.loadData()
        })

        mainViewModel.eventShowToast.observeEvent(this) {
            toast(it)
        }

        mainViewModel.eventGoToCounselingWrite.observeEvent(this) {
            startActivityForResult(
                Intent(this, CounselingWriteActivity::class.java),
                REQUEST_CODE_COUNSELING_WRITE
            )
        }

        locationViewModel.eventShowToast.observeEvent(this) {
            toast(it)
        }

        locationViewModel.eventLoadCounseling.observeEvent(this) {
            mainViewModel.loadData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        locationViewModel.onActivityResult(requestCode, resultCode, data)

        Dlog.d("requestCode : $requestCode, resultCode : $resultCode")
        if (requestCode == REQUEST_CODE_COUNSELING_WRITE) {
            if (resultCode == Activity.RESULT_OK) {
                mainViewModel.loadData()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initRecyclerView() {
        binding.rvMainCounseling.adapter = counselingAdapter
    }

    private fun initView() {
        binding.customCounselingMap.setOnMapItemClickListener(object :
            CounselingMapCustom.OnMapItemClickListener {
            override fun onClick(position: Int) {
                binding.rvMainCounseling.scrollToPosition(position)
            }

            override fun onClickDouble(questionId: Int) {
                WebViewActivity.startCounselingDetailActivity(this@MainActivity, questionId)
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
    }

    private fun replaceFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.layout_frame_main, fragment)
            addToBackStack(null)
        }.commit()
    }

}