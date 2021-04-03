package mashup.mac.ui.counseling

import android.os.Bundle
import androidx.activity.viewModels
import mashup.mac.R
import mashup.mac.base.MACActivity
import mashup.mac.databinding.ActivityCounselingWriteBinding
import mashup.mac.ext.observeEvent
import mashup.mac.ext.toast
import mashup.mac.model.Category
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter
import mashup.mac.ui.counseling.model.CategoryItem

class CounselingWriteActivity :
    MACActivity<ActivityCounselingWriteBinding>(R.layout.activity_counseling_write) {

    override var logTag = "CounselingWriteActivity"

    private val categoryAdapter by lazy { AnimalCategoryAdapter() }

    private val counselingWriteViewModel by viewModels<CounselingWriteViewModel> {
        CounselingWriteViewModelFactory(categoryAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.counselingViewModel = counselingWriteViewModel
        initCategoryRecyclerView()
        loadCategory()
    }

    override fun onViewModelSetup() {
        counselingWriteViewModel.eventToast.observeEvent(this) {
            toast(it)
        }
    }

    private fun initCategoryRecyclerView() {
        binding.rvAnimalCategory.adapter = categoryAdapter
    }

    private fun loadCategory() {
        val categories = Category.getAllCategories()
        val items = categories.mapIndexed { index, category ->
            CategoryItem(
                category = category,
                isCheck = index == AnimalCategoryAdapter.DEFAULT_CHECK_ITEM_POSITION
            )
        }
        categoryAdapter.replaceAll(items)
    }
}