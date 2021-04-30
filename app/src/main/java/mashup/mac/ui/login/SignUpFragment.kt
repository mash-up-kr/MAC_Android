package mashup.mac.ui.login


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import mashup.data.sample.SampleInjection
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentSignUpBinding
import mashup.mac.model.Category
import mashup.mac.ui.main.MainActivity
import mashup.mac.ui.sample.adapter.GithubUserAdapter
import java.util.*


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {


    override var logTag = "SampleFragment"

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val loginViewModel by lazy {
        ViewModelProvider(
            viewModelStore, LoginViewModelFactory(
                SampleInjection.provideRepository()
            )
        ).get(LoginViewModel::class.java)

    }

    private val userAdapter by lazy { GithubUserAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginVm = loginViewModel

        animalProfile()

        binding.pickerYear.maxValue = 2021
        binding.pickerYear.minValue = 1920
        binding.pickerYear.wrapSelectorWheel = false
        binding.pickerYear.value = 2000

        binding.btnNext.setOnClickListener {
            step++
            setImageStep(step)
        }
    }

    private var step = 1

    // 닉네임 유효성검사 -> true : step 증가, false : step 유지
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,}\$")
        return !trimmedNickname.isEmpty() && exp.matches(trimmedNickname)
    }
    private fun setImageStep(step: Int) {
        when (step) {
            1 -> {
                binding.ivStep.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_step1
                    )
                )
                showNickSet()
            }
            2 -> {
                binding.ivStep.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_step2
                    )
                )
                showNickAccess()
            }
            3 -> {
                binding.ivStep.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_step3
                    )
                )
                showNickGender()
            }
            else -> {
                startActivity(
                    Intent(requireContext(), MainActivity::class.java)
                )
                requireActivity().finish()
            }
        }
    }

    private fun showNickSet() {
        hideAll()
        binding.nickSet.visibility = View.VISIBLE
    }

    private fun showNickAccess() {
        hideAll()
        binding.nickAccess.visibility = View.VISIBLE
    }

    private fun showNickGender() {
        hideAll()
        binding.nickGender.visibility = View.VISIBLE
    }

    private fun hideAll() {
        binding.nickSet.visibility = View.GONE
        binding.nickAccess.visibility = View.GONE
        binding.nickGender.visibility = View.GONE
    }

    private fun animalProfile() {
        val photos =
            intArrayOf(Category.관계.circleRes, Category.음식.circleRes, Category.직업.circleRes, Category.학업.circleRes, Category.기타.circleRes, Category.연애.circleRes)

        val ran = Random()
        val i: Int = ran.nextInt(photos.size)



        binding.ivSignUpProfile.setImageResource(photos[i])

        binding.ivSignUpProfile.setOnClickListener()
        {

                val k: Int = ran.nextInt(photos.size)
            binding.ivSignUpProfile.setImageResource(photos[k])
        }




    }

}