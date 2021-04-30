package mashup.mac.ui.login

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.api.UserApi
import mashup.data.request.NicknameCheckRequest
import mashup.data.request.SignupRequest
import mashup.mac.base.BaseViewModel
import mashup.mac.util.log.Dlog

class LoginViewModel(
    private val userApi: UserApi
) : BaseViewModel() {

    val onClickLogin = MutableLiveData<Unit>()

    val nickname = MutableLiveData<String>()
    val ableNickname = MutableLiveData<String>("10글자 내로 설정 가능해요")
    val nickNameAble = MutableLiveData<Boolean>()
    val isMan = MutableLiveData<Boolean>()

    fun onClickLogin() {
        onClickLogin.postValue(Unit)
    }

    fun onClickMan(boolean: Boolean) {
        isMan.postValue(boolean)
    }

    fun postSignUp(birth: Int) {
        userApi.postSignin(SignupRequest(snsType ="kakao"
            ,nickname=nickname.value
            ,birthdayYear=birth,
            gender= if(isMan.value!!)"M" else "F" ))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Dlog.d(it.data.toString())
            }) {
                Dlog.e(it.message)
            }
    }
    fun checkNickName(){
        if (nickname.value?.length!! > 10 ){
            nickNameAble.postValue(false)
            ableNickname.postValue("10자 이하로 설정해주세요!")
        } else {
            userApi.postNickNameCheck(NicknameCheckRequest(nickname.value))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    nickNameAble.postValue(it.data ?: false)
                    ableNickname.postValue(if (it.data) "사용 가능한 닉네임에요" else "사용 불가능한 닉네임이에요!")
                }) {
                    Dlog.e(it.message)
                }
        }
    }
}