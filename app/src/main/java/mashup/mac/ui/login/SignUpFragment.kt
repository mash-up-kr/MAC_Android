package mashup.mac.ui.login


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import mashup.data.ApiProvider
import mashup.data.api.UserApi
import mashup.mac.R
import mashup.mac.base.BaseFragment
import mashup.mac.databinding.FragmentSignUpBinding
import mashup.mac.model.Category
import mashup.mac.ui.main.MainActivity
import mashup.mac.ui.sample.adapter.GithubUserAdapter
import java.util.*


class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {


    override var logTag = "SampleFragment"

    private val snsToken by lazy { requireArguments().getString(SNS_TOKEN) }

    companion object {
        const val SNS_TOKEN = "snsToken"
        fun newInstance(snsToken: String) = SignUpFragment().apply {
            arguments = Bundle().apply {
                putString(SNS_TOKEN, snsToken)
            }
        }
    }

    private val loginViewModel by lazy {
        ViewModelProvider(
            viewModelStore, LoginViewModelFactory(
                ApiProvider.provideApiWithoutHeader(UserApi::class.java)
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
            if (step == 1 && loginViewModel.nickNameAble.value!!) {
                step++
                setImageStep(step)
            } else {
                step++
                setImageStep(step)
            }
        }

        loginViewModel.isMan.observe(viewLifecycleOwner) {
            if (it){
                binding.tvMen.setTextColor(resources.getColor(R.color.white))
                binding.tvMen.background = resources.getDrawable(R.drawable.stroke_gender_bg)
                binding.tvWoman.setTextColor(resources.getColor(R.color.gray5))
                binding.tvWoman.background = resources.getDrawable(R.drawable.non_stroke_gender_bg)
            }else{
                binding.tvWoman.setTextColor(resources.getColor(R.color.white))
                binding.tvWoman.background = resources.getDrawable(R.drawable.stroke_gender_bg)
                binding.tvMen.setTextColor(resources.getColor(R.color.gray5))
                binding.tvMen.background = resources.getDrawable(R.drawable.non_stroke_gender_bg)
            }
        }
        loginViewModel.nickname.observe(viewLifecycleOwner) {
            loginViewModel.checkNickName()
        }
        loginViewModel.nickNameAble.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnNext.setTextColor(Color.WHITE)
                binding.btnNext.setBackgroundColor(resources.getColor(R.color.point))
                binding.tvNicknameAble.setTextColor(resources.getColor(R.color.green))
            } else {
                binding.btnNext.setBackgroundColor(resources.getColor(R.color.gray4))
                binding.btnNext.setTextColor(resources.getColor(R.color.gray2))
                binding.tvNicknameAble.setTextColor(resources.getColor(R.color.red))
            }
        }

        loginViewModel.mainActivity.observe(viewLifecycleOwner) {
            loginViewModel.checkNickName()
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
            requireActivity().finish()
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
            4 -> {
                loginViewModel.postSignUp(snsToken!!,binding.pickerYear.value)
            }
            else->{}
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
        val photos = arrayOf("관계", "음식", "직업", "학업", "기타", "연애")
        val ran = Random()
        val i: Int = ran.nextInt(photos.size)
        binding.ivSignUpProfile.setImageResource(
            Category.findCircleImage(photos[i]) ?: Category.학업.circleRes
        )
        binding.tvSignUpProfile.text = "안녕 내 이름은 "+Category.findAnimalName(photos[i]) ?: ""

        binding.ivSignUpProfile.setOnClickListener {
            val k = ran.nextInt(photos.size)
            binding.ivSignUpProfile.setImageResource(
                Category.findCircleImage(photos[k]) ?: Category.학업.circleRes
            )
            binding.tvSignUpProfile.text = "안녕 내 이름은 "+ Category.findAnimalName(photos[k]) ?: ""
        }
    }

}