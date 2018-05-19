package net.epictimes.nanodegreebaking.di;

import net.epictimes.nanodegreebaking.di.scope.ActivityScoped;
import net.epictimes.nanodegreebaking.features.widget.BakingRemoteViewsService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
abstract class ServiceBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract BakingRemoteViewsService contributeBakingRemoteViewsServiceInjector();

}
