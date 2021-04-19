package mashup.mac.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import mashup.data.ApiProvider
import mashup.data.api.CounselingApi
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentMyPageBinding
import mashup.mac.ext.observeEvent
import mashup.mac.ext.toast
import mashup.mac.ui.mypage.adapter.AnimalBadgeAdapter
import mashup.mac.ui.mypage.adapter.CounselingAdapter
import mashup.mac.util.log.Dlog

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

    private val myPageViewModel by viewModels<MyPageViewModel> {
        Dlog.d("PARAM_VIEW_TYPE : ${arguments?.getSerializable(PARAM_VIEW_TYPE)}")
        MyPageViewModelFactory(
            this,
            ApiProvider.provideApi(CounselingApi::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dlog.d("onCreate MyPageFragment : ${this.hashCode()}, myPageViewModel : ${myPageViewModel.hashCode()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        Dlog.d("onViewCreated MyPageFragment : ${this.hashCode()}, myPageViewModel : ${myPageViewModel.hashCode()}")
        initRecyclerView()
        myPageViewModel.loadData()
    }

    override fun onDestroyView() {
        Dlog.d("onDestroyView MyPageFragment : ${this.hashCode()}, myPageViewModel : ${myPageViewModel.hashCode()}")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Dlog.d("onDestroy MyPageFragment : ${this.hashCode()},  myPageViewModel : ${myPageViewModel.hashCode()}")
        super.onDestroy()
    }


    override fun onViewModelSetup() {
        with(myPageViewModel) {
            badgeItems.observe(viewLifecycleOwner) {
                animalBadgeAdapter.replaceAll(it)
            }

            counselingItems.observe(viewLifecycleOwner) {
                counselingAdapter.replaceAll(it)
            }

            eventShowToast.observeEvent(viewLifecycleOwner) {
                toast(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAnimalBadge.adapter = animalBadgeAdapter
        binding.rvCounseling.adapter = counselingAdapter
    }

    fun goToTopScroll() {
        binding.ablMyPage.setExpanded(true)
        binding.rvCounseling.scrollToPosition(0)
    }
}