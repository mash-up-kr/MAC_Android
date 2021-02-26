package mashup.mac.ui.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.SampleFragmentBinding
import mashup.mac.ui.sample.adapter.GithubUserAdapter

class SampleFragment : BaseFragment<SampleFragmentBinding>(R.layout.sample_fragment) {

    override var logTag = "SampleFragment"

    companion object {
        fun newInstance() = SampleFragment()
    }

    /**
     * Factory 필요 없는 경우
     */
    /*private val sampleViewModel by lazy {
        ViewModelProvider(this).get(SampleViewModel::class.java)
    }*/

    //ktx 사용를 사용해 초기화한 경우 (https://developer.android.com/kotlin/ktx#fragment)
    //private val sampleViewModel by viewModels<SampleViewModel>()

    /**
     * Factory 필요한 경우
     */
    /*private val sampleViewModel by lazy {
        ViewModelProvider(
            viewModelStore, SampleViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(SampleViewModel::class.java)
    }*/

    //ktx 사용를 사용해 초기화한 경우
    private val sampleViewModel by viewModels<SampleViewModel> {
        SampleViewModelFactory(
            SampleInjection.provideRepository()
        )
    }

    private val userAdapter by lazy { GithubUserAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = sampleViewModel
        initRecyclerView()
        loadData()
    }

    override fun onViewModelSetup() {
        sampleViewModel.userItems.observe(viewLifecycleOwner, { items ->
            userAdapter.replaceAll(items)
        })
    }

    private fun initRecyclerView() {
        binding.rvUser.adapter = userAdapter
    }

    private fun loadData() {
        sampleViewModel.loadRandomUsers()
    }
}