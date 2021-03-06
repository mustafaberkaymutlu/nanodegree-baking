package net.epictimes.nanodegreebaking.di;

import android.content.Context;
import android.content.SharedPreferences;

import net.epictimes.nanodegreebaking.BakingApp;
import net.epictimes.nanodegreebaking.di.qualifier.IsLandscape;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.di.qualifier.WidgetSharedPrefs;
import net.epictimes.nanodegreebaking.util.DeviceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
@Module
class SingletonModule {

    @IsTablet
    @Singleton
    @Provides
    boolean provideIsTablet(BakingApp bakingApp) {
        return DeviceUtils.isTablet(bakingApp);
    }

    @IsLandscape
    @Provides
    boolean provideIsLandscape(BakingApp bakingApp) {
        return DeviceUtils.isLandscape(bakingApp);
    }

    @WidgetSharedPrefs
    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(BakingApp bakingApp) {
        return bakingApp.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE);
    }

}
