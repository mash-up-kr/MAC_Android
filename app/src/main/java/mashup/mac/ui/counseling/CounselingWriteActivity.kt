package mashup.mac.ui.counseling

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import mashup.data.Injection
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivityCounselingWriteBinding
import mashup.mac.ext.observeEvent
import mashup.mac.ext.toast
import mashup.mac.model.Category
import mashup.mac.ui.counseling.adapter.AnimalCategoryAdapter
import mashup.mac.ui.counseling.model.CategoryItem


class CounselingWriteActivity :
    BaseActivity<ActivityCounselingWriteBinding>(R.layout.activity_counseling_write) {

    override var logTag = "CounselingWriteActivity"

    private val categoryAdapter by lazy { AnimalCategoryAdapter() }

    private val counselingWriteViewModel by viewModels<CounselingWriteViewModel> {
        CounselingWriteViewModelFactory(
            Injection.provideCounselingRepository(),
            categoryAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.counselingViewModel = counselingWriteViewModel
        initCategoryRecyclerView()
        loadCategory()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    override fun onViewModelSetup() {
        counselingWriteViewModel.eventToast.observeEvent(this) {
            toast(it)
        }
        counselingWriteViewModel.eventFinish.observeEvent(this) {
            setResult(Activity.RESULT_OK)
            finish()
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