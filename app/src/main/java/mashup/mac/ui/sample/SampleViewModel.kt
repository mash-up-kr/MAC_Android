package mashup.mac.ui.sample

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import mashup.data.sample.model.GithubUserModel
import mashup.data.sample.repository.SampleRepository
import mashup.mac.base.BaseViewModel
import mashup.mac.util.log.Dlog

class SampleViewModel(
    private val sampleRepository: SampleRepository
) : BaseViewModel() {

    val loadingData = MutableLiveData<Boolean>(false)
    val emptyUserMessageData = MutableLiveData<Boolean>(false)
    val showToastData = MutableLiveData<String>()

    val userItems = MutableLiveData<List<GithubUserModel>>()

    fun loadRandomUsers() {
        sampleRepository.getRandomUsers()
            // 이 이후에 수행되는 코드는 모두 메인 스레드에서 실행합니다.
            .observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현합니다.
            .doOnSubscribe {
                showProgress()
            }
            // 옵서버블을 구독합니다.
            .subscribe({ data ->
                // data 를 받아 처리합니다.
                // 작업 중 오류가 발생하면 이 블록은 호출되지 않습니다.
                hideProgress()

                val users = data.results
                if (users.isEmpty()) {
                    showEmptyMessage()
                    return@subscribe
                }

                showUser(users)

            }) {
                // 에러블록
                Dlog.d(it.message)
                showToast(it.message)
            }.also {
                // disposable 일괄 관리합니다.
                compositeDisposable.add(it)
            }
    }

    private fun showProgress() {
        loadingData.postValue(true)
    }

    private fun hideProgress() {
        loadingData.postValue(false)
    }

    private fun showToast(message: String?) {
        message?.let {
            showToastData.postValue(it)
        }
    }

    private fun showEmptyMessage() {
        emptyUserMessageData.postValue(true)
    }

    private fun showUser(users: List<GithubUserModel>) {
        userItems.postValue(users)
    }
}