package net.epictimes.nanodegreebaking.data;

import net.epictimes.nanodegreebaking.data.model.ingredient.IngredientMapper;
import net.epictimes.nanodegreebaking.data.model.recipe.RecipeMapper;
import net.epictimes.nanodegreebaking.data.model.step.StepMapper;
import net.epictimes.nanodegreebaking.di.qualifier.Repository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
@Module
public abstract class RepositoryModule {

    @Repository
    @Singleton
    @Binds
    abstract RecipeDataSource provideRecipeRepository(RecipeRepository repository);

    @Singleton
    @Provides
    static RecipeMapper provideRecipeMapper(StepMapper stepMapper, IngredientMapper ingredientMapper) {
        return new RecipeMapper(stepMapper, ingredientMapper);
    }

    @Singleton
    @Provides
    static StepMapper provideStepMapper() {
        return new StepMapper();
    }

    @Singleton
    @Provides
    static IngredientMapper provideIngredientMapper() {
        return new IngredientMapper();
    }

}
