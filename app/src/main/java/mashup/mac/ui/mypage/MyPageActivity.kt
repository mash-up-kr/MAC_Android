package mashup.mac.ui.mypage

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityMyPageBinding
import mashup.mac.ui.setting.SettingActivity
import mashup.mac.util.log.Dlog

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    override var logTag = "MyPageActivity"

    private var viewType = MyPageFragment.ViewType.MyCounseling

    private val focusedTabBackground by lazy {
        ResourcesCompat.getDrawable(resources, R.drawable.bg_my_page_focused, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initButton()
        replaceMyCounseling()
    }

    private fun initButton() {
        with(binding) {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            ivSetting.setOnClickListener {
                startActivity(
                    Intent(this@MyPageActivity, SettingActivity::class.java)
                )
            }

            btnMyCounseling.setOnClickListener {
                if (viewType == MyPageFragment.ViewType.MyCounseling) {
                    goToFragmentTopScroll()
                    return@setOnClickListener
                }
                replaceMyCounseling()
            }

            btnMyAnswer.setOnClickListener {
                if (viewType == MyPageFragment.ViewType.MyAnswer) {
                    goToFragmentTopScroll()
                    return@setOnClickListener
                }
                replaceMyAnswer()
            }
        }
    }

    private fun goToFragmentTopScroll() {
        supportFragmentManager.findFragmentByTag(viewType.name)?.let {
            if (it is MyPageFragment) {
                it.goToTopScroll()
            }
        }
    }

    private fun replaceMyCounseling() {
        viewType = MyPageFragment.ViewType.MyCounseling
        showMyCounselingTab()
        hideAllFragment()
        addFragment()
    }

    private fun replaceMyAnswer() {
        viewType = MyPageFragment.ViewType.MyAnswer
        showMyAnswerTab()
        hideAllFragment()
        addFragment()
    }

    private fun addFragment() {
        val tagName = viewType.name
        val findFragment = supportFragmentManager.findFragmentByTag(tagName)
        Dlog.d("MyPageActivity findFragment : $findFragment , hashcode : ${findFragment.hashCode()}")

        if (findFragment == null) {
            val newFragment = createNewFragmentByViewType()
            addFragment(newFragment, tagName)
        } else {
            showFragment(findFragment)
        }
    }

    private fun createNewFragmentByViewType() = when (viewType) {
        MyPageFragment.ViewType.MyCounseling -> {
            MyPageFragment.newInstanceCounseling()
        }
        MyPageFragment.ViewType.MyAnswer -> {
            MyPageFragment.newInstanceAnswer()
        }
    }

    private fun addFragment(fragment: BaseFragment<*>, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, fragment, tag)
            .commitAllowingStateLoss()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .show(fragment)
            .commitAllowingStateLoss()
    }

    private fun hideAllFragment() {
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it)
                .commitAllowingStateLoss()
        }
    }

    private fun showMyCounselingTab() {
        hideAllTab()
        setFocusTabTitle(binding.tvMyCounseling)
    }

    private fun showMyAnswerTab() {
        hideAllTab()
        setFocusTabTitle(binding.tvMyAnswer)
    }

    private fun setFocusTabTitle(textView: TextView) {
        with(textView) {
            background = focusedTabBackground
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun hideAllTab() {
        setUnFocusTabTitle(binding.tvMyCounseling)
        setUnFocusTabTitle(binding.tvMyAnswer)
    }

    private fun setUnFocusTabTitle(textView: TextView) {
        with(textView) {
            background = null
            setTypeface(null, Typeface.NORMAL)
            setTextColor(ContextCompat.getColor(context, R.color.gray6))
        }
    }
}