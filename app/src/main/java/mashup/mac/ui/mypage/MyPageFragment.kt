package mashup.mac.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentMyPageBinding
import mashup.mac.ui.mypage.adapter.AnimalBadgeAdapter
import mashup.mac.ui.mypage.adapter.CounselingAdapter

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

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

    override var logTag = "MyPageFragment"

    private val animalBadgeAdapter by lazy { AnimalBadgeAdapter() }

    private val counselingAdapter by lazy { CounselingAdapter() }

    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        initRecyclerView()
        myPageViewModel.loadData()
    }

    override fun onViewModelSetup() {
        myPageViewModel.badgeItems.observe(this) {
            animalBadgeAdapter.replaceAll(it)
        }

        myPageViewModel.counselingItems.observe(this) {
            counselingAdapter.replaceAll(it)
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimalBadge.adapter = animalBadgeAdapter
        binding.rvCounseling.adapter = counselingAdapter
    }
}