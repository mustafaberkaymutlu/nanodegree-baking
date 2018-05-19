package net.epictimes.nanodegreebaking.di;

import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;
import net.epictimes.nanodegreebaking.features.widget.BakingWidgetProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
abstract class BroadcastReceiverBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract BakingWidgetProvider contributeBakingWidgetProviderInjector();

}
