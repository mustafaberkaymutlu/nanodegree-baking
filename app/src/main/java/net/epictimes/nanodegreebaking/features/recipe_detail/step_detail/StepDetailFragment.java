package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.features.BaseFragment;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepDetailFragment extends BaseFragment<StepDetailContract.View, StepDetailContract.Presenter>
        implements StepDetailContract.View {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private TextView textViewStepDescription;
    private PlayerView playerView;

    @Nullable
    private SimpleExoPlayer simpleExoPlayer;

    public static StepDetailFragment newInstance(@Nullable final String recipeId, @Nullable String stepId) {
        final StepDetailFragment stepDetailFragment = new StepDetailFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_RECIPE_ID, recipeId);
        args.putString(ARG_STEP_ID, stepId);
        stepDetailFragment.setArguments(args);
        return stepDetailFragment;
    }

    @Inject
    StepDetailContract.Presenter stepDetailPresenter;

    @NonNull
    @Override
    public StepDetailContract.Presenter createPresenter() {
        return stepDetailPresenter;
    }

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_detail, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewStepDescription = view.findViewById(R.id.textViewStepDescription);
        playerView = view.findViewById(R.id.playerView);

        final Bundle args = getArguments();
        final String recipeId = args.getString(ARG_RECIPE_ID);
        final String stepId = args.getString(ARG_STEP_ID);

        presenter.displayStep(recipeId, stepId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        releaseVideoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void displayStepDetail(Step step) {
        textViewStepDescription.setText(step.getDescription());

        releaseVideoPlayer();

        final String videoUrl = step.getVideoURL();

        if (StringUtils.isBlank(videoUrl)) {
            playerView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            initializeVideoPlayer(videoUrl);
        }
    }

    @Override
    public void displayStepError() {
        Toast.makeText(getContext(), R.string.error_step, Toast.LENGTH_SHORT).show();
    }

    private void initializeVideoPlayer(@NonNull String videoUrl) {
        final Context context = getContext();

        final DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setControllerAutoShow(false);
        playerView.setPlayer(simpleExoPlayer);

        final Uri videoUri = Uri.parse(videoUrl);
        final String userAgent = Util.getUserAgent(context, "NanodegreeBaking");

        final DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
        final ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void releaseVideoPlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
