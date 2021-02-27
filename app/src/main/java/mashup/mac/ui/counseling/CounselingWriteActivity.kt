package mashup.mac.ui.counseling

import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityCounselingWriteBinding
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter

class CounselingWriteActivity :
    BaseActivity<ActivityCounselingWriteBinding>(R.layout.activity_counseling_write) {

    override var logTag = "CounselingWriteActivity"

    private val categoryAdapter by lazy { AnimalCategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvAnimalCategory.adapter = categoryAdapter

        val sample = listOf("초코", "민트", "오레오", "연애", "학업", "직업", "주변", "기타")
        categoryAdapter.replaceAll(sample)
    }
}