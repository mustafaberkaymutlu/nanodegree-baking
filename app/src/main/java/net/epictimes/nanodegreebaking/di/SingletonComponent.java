package net.epictimes.nanodegreebaking.di;

import android.support.annotation.VisibleForTesting;

import net.epictimes.nanodegreebaking.BakingApp;
import net.epictimes.nanodegreebaking.data.RepositoryModule;
import net.epictimes.nanodegreebaking.data.remote.RemoteDataSourceModule;
import net.epictimes.nanodegreebaking.util.SimpleIdlingResource;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Singleton
@Component(modules = {
        SingletonModule.class,
        RemoteDataSourceModule.class,
        RepositoryModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        ServiceBuilderModule.class,
        BroadcastReceiverBuilderModule.class})
public interface SingletonComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(BakingApp application);

        SingletonComponent build();
    }

    void inject(BakingApp application);

    @VisibleForTesting
    SimpleIdlingResource simpleIdlingResource();

}
