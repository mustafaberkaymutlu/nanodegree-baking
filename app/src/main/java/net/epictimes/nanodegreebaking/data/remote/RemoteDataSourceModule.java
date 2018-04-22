package net.epictimes.nanodegreebaking.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import net.epictimes.nanodegreebaking.BuildConfig;
import net.epictimes.nanodegreebaking.data.RecipeDataSource;
import net.epictimes.nanodegreebaking.di.qualifier.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
public abstract class RemoteDataSourceModule {

    @RemoteDataSource
    @Singleton
    @Binds
    abstract RecipeDataSource provideRecipesRemoteDataSource(RecipesRemoteDataSource recipesRemoteDataSource);

    @Singleton
    @Provides
    static Services provideServices(@NonNull Retrofit retrofit) {
        return retrofit.create(Services.class);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(Endpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(@Nullable HttpLoggingInterceptor httpLoggingInterceptor) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        if (httpLoggingInterceptor != null) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        return okHttpClientBuilder.build();
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Nullable
    @Provides
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor();
        }

        return null;
    }

}
