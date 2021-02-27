package mashup.mac.ui.counseling

import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityCounselingWriteBinding
import mashup.mac.model.Category
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class CounselingWriteActivity :
    BaseActivity<ActivityCounselingWriteBinding>(R.layout.activity_counseling_write) {

    override var logTag = "CounselingWriteActivity"

    private val categoryAdapter by lazy { AnimalCategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAnimalCategory()
    }

    private fun initAnimalCategory() {
        binding.rvAnimalCategory.adapter = categoryAdapter

        val categories = Category.getAllCategories()
        categoryAdapter.replaceAll(categories)
    }
}