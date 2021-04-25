package mashup.mac.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.api.CounselingApi
import mashup.data.model.Counseling
import mashup.mac.base.BaseViewModel
import mashup.mac.ext.EventMutableLiveData
import mashup.mac.ext.postEvent
import mashup.mac.model.AnimalBadgeItem
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem
import mashup.mac.util.log.Dlog

class MyPageViewModel(
    handle: SavedStateHandle,
    private val counselingApi: CounselingApi
) : BaseViewModel() {

    private val viewType: MyPageFragment.ViewType? = handle[MyPageFragment.PARAM_VIEW_TYPE]

    val eventShowToast = EventMutableLiveData<String>()

    private val _badgeItems = MutableLiveData<List<AnimalBadgeItem>>()
    val badgeItems: LiveData<List<AnimalBadgeItem>> get() = _badgeItems

    val badgeTotalCount = MediatorLiveData<Int>().apply {
        addSource(badgeItems) { badges ->
            var totalCount = 0

            badges.forEach { badge ->
                totalCount += badge.badgeCount
            }

            postValue(totalCount)
        }
    }

    private val _counselingItems = MutableLiveData<List<CounselingItem>>()
    val counselingItems: LiveData<List<CounselingItem>> get() = _counselingItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun loadData() {
        when (viewType) {
            MyPageFragment.ViewType.MyCounseling -> {
                loadMyCounseling()
            }
            MyPageFragment.ViewType.MyAnswer -> {
                //TODO API 아직 안나옴
                loadSample()
            }
        }
    }

    fun onClickBadge(items: List<CounselingItem>) {
        _counselingItems.postValue(items)
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
                    val data = it.data
                    Dlog.d("data : $data")
                    val badges = mutableListOf<AnimalBadgeItem>()

                    setAnimalBadgeItem(Category.연애.title, data.연애)?.let { badge ->
                        badges.add(badge)
                        onClickBadge(badge.counselingItems)
                    }
                    setAnimalBadgeItem(Category.학업.title, data.학업)?.let { badge ->
                        badges.add(badge)
                    }
                    setAnimalBadgeItem(Category.직업.title, data.직업)?.let { badge ->
                        badges.add(badge)
                    }
                    setAnimalBadgeItem(Category.관계.title, data.관계)?.let { badge ->
                        badges.add(badge)
                    }
                    setAnimalBadgeItem(Category.음식.title, data.음식)?.let { badge ->
                        badges.add(badge)
                    }
                    setAnimalBadgeItem(Category.기타.title, data.기타)?.let { badge ->
                        badges.add(badge)
                    }

                    _badgeItems.postValue(badges)
                } else {
                    showToast(it.error)
                }
            }) {
                Dlog.e(it.message)
            }.also {
                compositeDisposable.add(it)
            }
    }

    private fun setAnimalBadgeItem(categoryTitle: String, counselings: List<Counseling>): AnimalBadgeItem? {
        val category = Category.getFromTitle(categoryTitle) ?: return null
        val tempCounselings = mutableListOf<CounselingItem>()

        counselings.forEach { counseling ->
            getCounselingItem(counseling)?.let { item ->
                tempCounselings.add(item)
            }
        }

        return AnimalBadgeItem(
            category = category,
            badgeCount = counselings.size,
            counselingItems = tempCounselings,
            isCheck = categoryTitle == Category.연애.title
        )
    }

    private fun getCounselingItem(counseling: Counseling): CounselingItem? {
        val category = Category.getFromTitle(counseling.category?.title) ?: return null
        return CounselingItem(
            id = counseling.id?:0,
            category = category,
            title = counseling.title ?: "",
            description = counseling.content ?: "",
            date = counseling.createdAt ?: "",
            answer = counseling.commentCount ?: 0
        )
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
    }

    private fun getBadgeSampleData(): List<AnimalBadgeItem> {
        return Category.getAllCategories().mapIndexed { index, category ->
            AnimalBadgeItem(
                category = category,
                badgeCount = index * 50,
                counselingItems = getCounselingItemsSampleData(),
                isCheck = index == 0
            )
        }
    }

    private fun getCounselingItemsSampleData(): List<CounselingItem> {
        val sample = mutableListOf<CounselingItem>()
        (0..100).forEach { _ ->
            sample.add(
                CounselingItem(
                    id = 0,
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