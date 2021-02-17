package com.android.mvvm.kotlin.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.android.mvvm.kotlin.BuildConfig;
import com.android.mvvm.kotlin.data.AppDataManager;
import com.android.mvvm.kotlin.data.DataManager;
import com.android.mvvm.kotlin.data.local.db.AppDatabase;
import com.android.mvvm.kotlin.data.local.db.AppDbHelper;
import com.android.mvvm.kotlin.data.local.db.DbHelper;
import com.android.mvvm.kotlin.data.local.prefs.AppPreferencesHelper;
import com.android.mvvm.kotlin.data.local.prefs.PreferencesHelper;
import com.android.mvvm.kotlin.data.remote.ApiHeader;
import com.android.mvvm.kotlin.data.remote.ApiHelper;
import com.android.mvvm.kotlin.data.remote.AppApiHelper;
import com.android.mvvm.kotlin.di.ApiInfo;
import com.android.mvvm.kotlin.di.DatabaseInfo;
import com.android.mvvm.kotlin.di.PreferenceInfo;
import com.android.mvvm.kotlin.utils.AppConstants;
import com.android.mvvm.kotlin.utils.rx.AppSchedulerProvider;
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.mvvm.kotlin.BuildConfig.BASE_URL;

/**
 * Created by hemanth on 17,January,2021
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(PreferencesHelper preferencesHelper) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("access_token", preferencesHelper.getAccessToken())
                    .addHeader("api_key", BuildConfig.API_KEY)
                    .addHeader("user_id", preferencesHelper.getCurrentUserId().toString())
                    .build();
            return chain.proceed(newRequest);
        })
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES);
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(logging);
        }


        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
