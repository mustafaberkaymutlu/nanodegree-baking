package net.epictimes.nanodegreebaking.data.remote;

import net.epictimes.nanodegreebaking.data.model.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public interface Services {

    @GET("/android-baking-app-json")
    Flowable<List<Recipe>> getRecipes();

}
