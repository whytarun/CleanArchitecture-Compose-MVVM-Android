package com.android.mvvm.kotlin.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.data.model.api.BlogResponse
import com.android.mvvm.kotlin.data.model.others.QuestionCardData
import com.android.mvvm.kotlin.ui.base.BaseViewModel
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by hemanth on 17,January,2021
 */
class MainViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) :
    BaseViewModel<MainNavigator?>(dataManager!!, schedulerProvider!!) {
     private val appVersion = ObservableField<String>()
     val questionCardData: MutableLiveData<List<QuestionCardData>>
     val repos: MutableLiveData<Any>
     val repoLoadError: MutableLiveData<Boolean>
     private val questionDataList: ObservableList<QuestionCardData> =
        ObservableArrayList<QuestionCardData>()
    private val userEmail = ObservableField<String>()
    private val userName = ObservableField<String>()
    private val userProfilePicUrl = ObservableField<String>()
    private var action = NO_ACTION
    fun getAction(): Int {
        return action
    }

    fun getAppVersion(): ObservableField<String> {
        return appVersion
    }

    fun getQuestionCardData(): LiveData<List<QuestionCardData>> {
        return questionCardData
    }

    fun getQuestionDataList(): ObservableList<QuestionCardData> {
        return questionDataList
    }

    fun setQuestionDataList(questionCardDatas: List<QuestionCardData?>?) {
        action = ACTION_ADD_ALL
        questionDataList.clear()
        questionDataList.addAll(questionCardDatas!!)
    }

    fun getUserEmail(): ObservableField<String> {
        return userEmail
    }

    fun getUserName(): ObservableField<String> {
        return userName
    }

    fun getUserProfilePicUrl(): ObservableField<String> {
        return userProfilePicUrl
    }

    fun setUserProfilePicUrl(url: String) {
        userProfilePicUrl.set(url)

    }

    fun loadQuestionCards() {
        getDataManager()
                .getQuestionCardData()
                ?.doOnNext { list -> Log.d(TAG, "loadQuestionCards: " + list!!.size) }
                ?.subscribeOn(getSchedulerProvider().io())
                ?.observeOn(getSchedulerProvider().ui())?.let {
                    getCompositeDisposable().add(it
                            .subscribe({ questionList ->
                                if (questionList != null) {
                                    Log.d(TAG, "loadQuestionCards: " + questionList!!.size)
                                    action = ACTION_ADD_ALL

                                    Log.e("questionlistValues",questionList.toString());
                                    questionCardData.setValue(questionList as List<QuestionCardData>?)
                                }
                            }) { throwable -> Log.d(TAG, "loadQuestionCards: $throwable") })
                }
    }


    fun fetchBlogs() {
        setIsLoading(true)
        getCompositeDisposable().add(
                getDataManager().getBlogs().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<BlogResponse?>() {
                            override fun onSuccess(value: BlogResponse) {
                                Log.d("blog res", value.getMessage().toString())
                                repoLoadError.value = false
                                repos.value = value
                                setIsLoading(false)
                            }

                            override fun onError(e: Throwable) {
                                e.printStackTrace()
                                repoLoadError.value = true
                                setIsLoading(false)
                            }
                        })
        )
    }

    fun logout() {
        setIsLoading(true)
        getCompositeDisposable().add(
                getDataManager().doLogoutApiCall()
                        .doOnSuccess({ response -> getDataManager().setUserAsLoggedOut() })
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe({ response ->
                            setIsLoading(false)
                            getNavigator()?.openLoginActivity()
                        }, { throwable ->
                            setIsLoading(false)
                            getNavigator()?.handleError(throwable)
                        })
        )
    }

    fun onNavMenuCreated() {
        val currentUserName: String = getDataManager().getCurrentUserName().toString()
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName)
        }
        val currentUserEmail: String = getDataManager().getCurrentUserEmail().toString()
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail)
        }
        val profilePicUrl: String = getDataManager().getCurrentUserProfilePicUrl().toString()
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl)
        }
    }

    fun removeQuestionCard() {
        action = ACTION_DELETE_SINGLE
        //questionCardData.value.remove(0)
    }

    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }

    companion object {
        private const val TAG = "MainViewModel"
        const val NO_ACTION = -1
        const val ACTION_ADD_ALL = -1
        const val ACTION_DELETE_SINGLE = 1
    }

    init {
        questionCardData = MutableLiveData<List<QuestionCardData>>()
        repos = MutableLiveData()
        repoLoadError = MutableLiveData()
        loadQuestionCards()
        fetchBlogs()
    }
}
