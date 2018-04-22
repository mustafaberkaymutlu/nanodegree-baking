package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
public abstract class RepositoryModule {

    @Repository
    @Singleton
    @Binds
    abstract RecipeDataSource provideRecipeRepository(RecipeRepository repository);

}
