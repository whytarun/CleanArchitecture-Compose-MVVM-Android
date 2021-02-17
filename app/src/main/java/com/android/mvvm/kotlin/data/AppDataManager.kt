package com.android.mvvm.kotlin.data

import android.content.Context
import com.android.mvvm.kotlin.R
import com.android.mvvm.kotlin.data.local.db.DbHelper
import com.android.mvvm.kotlin.data.local.prefs.PreferencesHelper
import com.android.mvvm.kotlin.data.model.api.BlogResponse
import com.android.mvvm.kotlin.data.model.api.LoginRequest
import com.android.mvvm.kotlin.data.model.api.LoginResponse
import com.android.mvvm.kotlin.data.model.api.LogoutResponse
import com.android.mvvm.kotlin.data.model.db.Option
import com.android.mvvm.kotlin.data.model.db.Question
import com.android.mvvm.kotlin.data.model.db.User
import com.android.mvvm.kotlin.data.model.others.QuestionCardData
import com.android.mvvm.kotlin.data.remote.ApiHeader
import com.android.mvvm.kotlin.data.remote.ApiHelper
import com.android.mvvm.kotlin.utils.AppConstants
import com.android.mvvm.kotlin.utils.CommonUtils
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
        private val mContext: Context,
        dbHelper: DbHelper,
        preferencesHelper: PreferencesHelper,
        apiHelper: ApiHelper,
        gson: Gson,
) :
    DataManager {
    private val mApiHelper: ApiHelper
    private val mDbHelper: DbHelper
    private val mGson: Gson
    private val mPreferencesHelper: PreferencesHelper
    override fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest?): Single<LoginResponse> {
        return mApiHelper.doFacebookLoginApiCall(request)
    }

    override fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest?): Single<LoginResponse> {
        return mApiHelper.doGoogleLoginApiCall(request)
    }

    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return mApiHelper.doLogoutApiCall()
    }

    override fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest?): Single<LoginResponse> {
        return mApiHelper.doServerLoginApiCall(request)
    }

    override fun getAccessToken(): String? {
        return mPreferencesHelper.getAccessToken()
    }

    override fun setAccessToken(accessToken: String?) {
        mPreferencesHelper.setAccessToken(accessToken)
        mApiHelper.getApiHeader().getProtectedApiHeader().accessToken
    }

    override fun getAllQuestions(): Observable<List<Question?>?>? {
        return mDbHelper.getAllQuestions()
    }

    override fun getAllUsers(): Observable<List<User>> {
        return mDbHelper.getAllUsers()
    }

    override fun getApiHeader(): ApiHeader {
        return mApiHelper.getApiHeader()
    }

    override fun getCurrentUserEmail(): String? {
        return mPreferencesHelper.getCurrentUserEmail()
    }

    override fun setCurrentUserEmail(email: String?) {
        mPreferencesHelper.setCurrentUserEmail(email)
    }

    override fun getCurrentUserId(): Long? {
        return mPreferencesHelper.getCurrentUserId()
    }

    override fun setCurrentUserId(userId: Long?) {
        mPreferencesHelper.setCurrentUserId(userId)
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.getCurrentUserLoggedInMode()
    }

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode?) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserName(): String? {
        return mPreferencesHelper.getCurrentUserName()
    }

    override fun setCurrentUserName(userName: String?) {
        mPreferencesHelper.setCurrentUserName(userName)
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return mPreferencesHelper.getCurrentUserProfilePicUrl()
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl)
    }

    override fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option?>?>? {
        return mDbHelper.getOptionsForQuestionId(questionId)
    }


    override fun getQuestionCardData(): Observable<List<QuestionCardData?>?>? {
        return mDbHelper.getAllQuestions()
                ?.flatMap { questions: List<Question?>? ->
                    Observable.fromIterable(questions)
                }
                ?.flatMap { question: Question? ->
                    Observable.zip(
                            mDbHelper.getOptionsForQuestionId(question!!.id),
                            Observable.just(question),
                            BiFunction { options: List<Option?>?, question1: Question? ->
                                QuestionCardData(
                                        question1!!,
                                        options as List<Option>
                                )
                            }
                    )
                }
                ?.toList()
                ?.toObservable()
    }


    override fun insertUser(user: User?): Observable<Boolean?>? {
        return mDbHelper.insertUser(user)
    }

    override fun isOptionEmpty(): Observable<Boolean?>? {
        return mDbHelper.isOptionEmpty()
    }

    override fun isQuestionEmpty(): Observable<Boolean?>? {
        return mDbHelper.isQuestionEmpty()
    }

    override fun saveOption(option: Option?): Observable<Boolean?>? {
        return mDbHelper.saveOption(option)
    }

    override fun saveOptionList(optionList: List<Option?>?): Observable<Boolean?>? {
        return mDbHelper.saveOptionList(optionList)
    }

    override fun saveQuestion(question: Question?): Observable<Boolean?>? {
        return mDbHelper.saveQuestion(question)
    }

    override fun saveQuestionList(questionList: List<Question?>?): Observable<Boolean?>? {
        return mDbHelper.saveQuestionList(questionList)
    }

    override fun seedDatabaseOptions(): Observable<Boolean> {
        return mDbHelper.isOptionEmpty()
            ?.concatMap { isEmpty ->
                if (isEmpty) {
                    val type = object :
                        TypeToken<List<Option?>?>() {}.type
                    val optionList: List<Option?> = mGson.fromJson(
                            CommonUtils.loadJSONFromAsset(
                                    mContext,
                                    AppConstants.SEED_DATABASE_OPTIONS
                            ), type
                    )
                    return@concatMap saveOptionList(optionList)
                }
                Observable.just(false)
            }!!
    }

    override fun seedDatabaseQuestions(): Observable<Boolean> {
        return mDbHelper.isQuestionEmpty()
            ?.concatMap { isEmpty ->
                if (isEmpty) {
                    val type: Type = `$Gson$Types`.newParameterizedTypeWithOwner(
                            null,
                            List::class.java,
                            Question::class.java
                    )
                    val questionList: List<Question?> = mGson
                        .fromJson(
                                CommonUtils.readJSONFromAsset(
                                        mContext,
                                        AppConstants.SEED_DATABASE_QUESTIONS
                                ), type
                        )
                    return@concatMap saveQuestionList(questionList)
                }
                Observable.just(false)
            }!!
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null
        )
    }

    override fun updateApiHeader(userId: Long?, accessToken: String?) {
        mApiHelper.getApiHeader().getProtectedApiHeader().userId
        mApiHelper.getApiHeader().getProtectedApiHeader().accessToken
    }

    override fun updateUserInfo(
            accessToken: String?,
            userId: Long?,
            loggedInMode: DataManager.LoggedInMode?,
            userName: String?,
            email: String?,
            profilePicPath: String?,
    ) {
        setAccessToken(accessToken)
        setCurrentUserId(userId)
        setCurrentUserLoggedInMode(loggedInMode)
        setCurrentUserName(userName)
        setCurrentUserEmail(email)
        setCurrentUserProfilePicUrl(profilePicPath)
        updateApiHeader(userId, accessToken)
    }

    override fun getBlogs(): Single<BlogResponse> {
        return mApiHelper.getBlogs()
    }

    companion object {
        private const val TAG = "AppDataManager"
    }

    init {
        mDbHelper = dbHelper
        mPreferencesHelper = preferencesHelper
        mApiHelper = apiHelper
        mGson = gson
    }
}