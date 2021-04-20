package mashup.mac.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.api.CounselingApi
import mashup.data.request.CounselingGetRequest
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.postEvent
import mashup.mac.model.AnimalBadgeItem
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem
import mashup.mac.ui.mypage.adapter.AnimalBadgeAdapter
import mashup.mac.util.log.Dlog

class MyPageViewModel(
    handle: SavedStateHandle,
    private val counselingApi: CounselingApi
) : BaseViewModel() {

    private val viewType: MyPageFragment.ViewType? = handle[MyPageFragment.PARAM_VIEW_TYPE]

    private val _badgeItems = MutableLiveData<List<AnimalBadgeItem>>()
    val badgeItems: LiveData<List<AnimalBadgeItem>> get() = _badgeItems

    private val _counselingItems = MutableLiveData<List<CounselingItem>>()
    val counselingItems: LiveData<List<CounselingItem>> get() = _counselingItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val eventShowToast = EventMutableLiveData<String>()

    fun loadData() {
        when (viewType) {
            MyPageFragment.ViewType.MyCounseling -> {
                loadMyCounseling()
            }
            MyPageFragment.ViewType.MyAnswer -> {
                //TODO API 나오면 작업
                loadSample()
            }
        }
    }

    private fun loadCounselingSample() {
        counselingApi.getCounselings(
            CounselingGetRequest(
                minKilometer = 0,
                maxKilometer = 9999
            )
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLoading()
            }
            .doOnTerminate {
                hideLoading()
            }
            .subscribe({
                if (it.isSuccess()) {
                    val items = it.data
                    Dlog.d("items : $items")
                } else {
                    showToast(it.error)
                }
            }) {
                Dlog.e(it.message)
            }.also {
                compositeDisposable.add(it)
            }
    }

    private fun loadMyCounseling() {
        counselingApi.getMyCounselings()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLoading()
            }
            .doOnTerminate {
                hideLoading()
            }
            .subscribe({
                if (it.isSuccess()) {
                    val items = it.data
                    Dlog.d("items : $items")
                } else {
                    showToast(it.error)
                }
            }) {
                Dlog.e(it.message)
            }.also {
                compositeDisposable.add(it)
            }
    }

    private fun showLoading() {
        _isLoading.postValue(true)
    }

    private fun hideLoading() {
        _isLoading.postValue(false)
    }

    private fun showToast(msg: String?) {
        msg?.let {
            if (it.isNotEmpty()) {
                eventShowToast.postEvent(it)
            }
        }
    }

    /**
     * SampleData
     */
    private fun loadSample() {
        _badgeItems.postValue(getBadgeSampleData())
        _counselingItems.postValue(getCounselingItemsSampleData())
    }

    private fun getBadgeSampleData(): List<AnimalBadgeItem> {
        return Category.getAllCategories().mapIndexed { index, category ->
            AnimalBadgeItem(
                category = category,
                badgeCount = index * 50,
                isCheck = index == AnimalBadgeAdapter.DEFAULT_CHECK_ITEM_POSITION
            )
        }
    }

    private fun getCounselingItemsSampleData(): List<CounselingItem> {
        val sample = mutableListOf<CounselingItem>()
        (0..100).forEach { _ ->
            sample.add(
                CounselingItem(
                    category = Category.getRandomCategory(),
                    title = "소양이팀 너무 좋아요!",
                    description = "뭔가 긴 설명이 필요한데 뭐라고 적을까요? 그냥 우리팀 너무너무 멋지다! 홍대 너무너무 신난다! 날씨 너무너무 좋다! 으아 다 좋다! 라이언 귀엽다!",
                    answer = 100
                )
            )
        }
        return sample
    }
}