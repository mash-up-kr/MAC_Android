package mashup.mac.ui.mypage

import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.ActivityMyPageBinding

class MyPageActivity : BaseActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    override var logTag = "MyPageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButton()
        replaceFragment(
            MyPageFragment.newInstanceCounseling()
        )
    }

    private fun initButton() {
        with(binding) {
            btnMyCounseling.setOnClickListener {
                replaceFragment(
                    MyPageFragment.newInstanceCounseling()
                )
            }

            btnMyAnswer.setOnClickListener {
                replaceFragment(
                    MyPageFragment.newInstanceAnswer()
                )
            }
        }
    }

    private fun replaceFragment(fragment: BaseFragment<*>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commitAllowingStateLoss()
    }
}