package net.epictimes.nanodegreebaking;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import net.epictimes.nanodegreebaking.di.DaggerSingletonComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class BakingApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        initSingletonComponent();

        initTimber();
    }

    private void initSingletonComponent() {
        DaggerSingletonComponent.builder()
                                .application(this)
                                .build()
                                .inject(this);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
