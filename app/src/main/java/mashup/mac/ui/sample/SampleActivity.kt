package mashup.mac.ui.sample

import android.os.Bundle
import mashup.mac.R
import mashup.mac.base.BaseActivity
import mashup.mac.databinding.ActivitySampleBinding

class SampleActivity : BaseActivity<ActivitySampleBinding>(R.layout.activity_sample) {

    override var logTag = "SampleActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        val sampleFragment = SampleFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, sampleFragment)
            .commit()
    }
}