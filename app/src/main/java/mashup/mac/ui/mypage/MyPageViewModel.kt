package mashup.mac.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import mashup.mac.base.BaseViewModel
import mashup.mac.model.AnimalBadgeItem
import mashup.mac.model.Category
import mashup.mac.model.CounselingItem
import mashup.mac.ui.mypage.adapter.AnimalBadgeAdapter

class MyPageViewModel(
    handle: SavedStateHandle
) : BaseViewModel() {

    private val viewType: MyPageFragment.ViewType = handle[MyPageFragment.PARAM_VIEW_TYPE]
        ?: MyPageFragment.ViewType.MyCounseling

    private val _badgeItems = MutableLiveData<List<AnimalBadgeItem>>()
    val badgeItems: LiveData<List<AnimalBadgeItem>> get() = _badgeItems

    private val _counselingItems = MutableLiveData<List<CounselingItem>>()
    val counselingItems: LiveData<List<CounselingItem>> get() = _counselingItems

    fun loadData() {
        when (viewType) {
            MyPageFragment.ViewType.MyCounseling -> {
                loadSample()
            }
            MyPageFragment.ViewType.MyAnswer -> {
                loadSample()
            }
        }
    }

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