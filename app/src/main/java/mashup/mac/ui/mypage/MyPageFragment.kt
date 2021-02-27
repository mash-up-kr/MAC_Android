package mashup.mac.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentMyPageBinding
import mashup.mac.model.AnimalBadgeItem
import mashup.mac.model.CounselingItem
import mashup.mac.ui.mypage.adapter.AnimalBadgeAdapter
import mashup.mac.ui.mypage.adapter.CounselingAdapter
import mashup.mac.util.log.Dlog

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    override var logTag = "MyPageFragment"

    companion object {

        const val PARAM_VIEW_TYPE = "view_type"

        fun newInstanceCounseling() = MyPageFragment().apply {
            arguments = bundleOf(PARAM_VIEW_TYPE to ViewType.MyCounseling)
        }

        fun newInstanceAnswer() = MyPageFragment().apply {
            arguments = bundleOf(PARAM_VIEW_TYPE to ViewType.MyAnswer)
        }
    }

    enum class ViewType {
        MyCounseling, MyAnswer
    }

    private val animalBadgeAdapter by lazy { AnimalBadgeAdapter() }

    private val counselingAdapter by lazy { CounselingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewType = arguments?.getSerializable(PARAM_VIEW_TYPE) as? ViewType
        Dlog.d("viewType : $viewType")

        if (viewType == null) {
            Dlog.e("viewType is null")
            return
        }

        val sample1 = mutableListOf<AnimalBadgeItem>()
        (0..5).forEach { index ->
            sample1.add(
                AnimalBadgeItem(
                    badgeCount = index
                )
            )
        }

        val sample2 = when (viewType) {
            ViewType.MyCounseling -> getCounselingData()
            ViewType.MyAnswer -> getAnswerData()
        }

        initAnimalBadge(sample1)
        initCounseling(sample2)
    }

    private fun initAnimalBadge(sample: List<AnimalBadgeItem>) {
        binding.rvAnimalBadge.adapter = animalBadgeAdapter
        animalBadgeAdapter.replaceAll(sample)
    }

    private fun initCounseling(sample: List<CounselingItem>) {
        binding.rvCounseling.adapter = counselingAdapter
        counselingAdapter.replaceAll(sample)
    }

    private fun getCounselingData(): List<CounselingItem> {
        val sample = mutableListOf<CounselingItem>()
        (0..100).forEach { _ ->
            sample.add(
                CounselingItem(
                    title = "[내가 올린 고민들] 소양이팀 너무 좋아요!",
                    description = "뭔가 긴 설명이 필요한데 뭐라고 적을까요? 그냥 우리팀 너무너무 멋지다! 홍대 너무너무 신난다! 날씨 너무너무 좋다! 으아 다 좋다! 라이언 귀엽다!",
                    answer = 100
                )
            )
        }
        return sample
    }

    private fun getAnswerData(): List<CounselingItem> {
        val sample = mutableListOf<CounselingItem>()
        (0..100).forEach { _ ->
            sample.add(
                CounselingItem(
                    title = "[내가 남긴 답변들] 소양이팀 너무 좋아요!",
                    description = "뭔가 긴 설명이 필요한데 뭐라고 적을까요? 그냥 우리팀 너무너무 멋지다! 홍대 너무너무 신난다! 날씨 너무너무 좋다! 으아 다 좋다! 라이언 귀엽다!",
                    answer = 100
                )
            )
        }
        return sample
    }

    override fun onDestroyView() {
        Dlog.d("onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Dlog.d("onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Dlog.d("onDetach")
        super.onDetach()
    }
}