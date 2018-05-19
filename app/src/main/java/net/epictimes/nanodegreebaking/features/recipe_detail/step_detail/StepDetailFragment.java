package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
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
import net.epictimes.nanodegreebaking.di.qualifier.IsLandscape;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.features.BaseFragment;
import net.epictimes.nanodegreebaking.util.Preconditions;

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
    private static final String ARG_VIDEO_POSITION = "video_position";
    private static final String ARG_IS_VIDEO_PLAYING = "is_video_paying";

    private static final boolean INITIAL_IS_VIDEO_PLAYING = true;

    @Nullable
    private TextView textViewStepDescription;
    private PlayerView playerView;

    private long videoPosition;
    private boolean isVideoPlaying;
    private String stepId;
    private String videoUrl;

    private Listener fragmentListener;

    @Nullable
    private SimpleExoPlayer simpleExoPlayer;

    @Inject
    StepDetailContract.Presenter stepDetailPresenter;

    @IsTablet
    @Inject
    boolean isTablet;

    @IsLandscape
    @Inject
    boolean isLandscape;

    public interface Listener {

        void goFullScreen();

    }

    public static StepDetailFragment newInstance(@NonNull final String recipeId, @Nullable String stepId) {
        final StepDetailFragment stepDetailFragment = new StepDetailFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_RECIPE_ID, recipeId);
        args.putString(ARG_STEP_ID, stepId);
        stepDetailFragment.setArguments(args);
        return stepDetailFragment;
    }

    @NonNull
    @Override
    public StepDetailContract.Presenter createPresenter() {
        return stepDetailPresenter;
    }

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

        try {
            fragmentListener = (Listener) context;
        } catch (ClassCastException ignored) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
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

        final Bundle args = Preconditions.checkNotNull(getArguments(), "Arguments must not be null. ");
        final String recipeId = args.getString(ARG_RECIPE_ID);
        final String initialStepId = args.getString(ARG_STEP_ID);

        if (savedInstanceState != null) {
            stepId = savedInstanceState.getString(ARG_STEP_ID, initialStepId);
            videoPosition = savedInstanceState.getLong(ARG_VIDEO_POSITION, 0);
            isVideoPlaying = savedInstanceState.getBoolean(ARG_IS_VIDEO_PLAYING, INITIAL_IS_VIDEO_PLAYING);
        } else {
            stepId = initialStepId;
            isVideoPlaying = INITIAL_IS_VIDEO_PLAYING;
        }

        if (isLandscape && !isTablet) {
            fragmentListener.goFullScreen();

            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        presenter.getStep(recipeId, stepId);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (simpleExoPlayer != null) {
            outState.putString(ARG_STEP_ID, stepId);
            outState.putLong(ARG_VIDEO_POSITION, simpleExoPlayer.getCurrentPosition());
            outState.putBoolean(ARG_IS_VIDEO_PLAYING, simpleExoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            configurePlayerView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            configurePlayerView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            releaseVideoPlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            releaseVideoPlayer();
        }
    }

    @Override
    public void displayStepDetail(Step step) {
        this.stepId = step.getId();
        this.videoUrl = step.getVideoURL();

        if (textViewStepDescription != null) {
            textViewStepDescription.setText(step.getDescription());
        }

        configurePlayerView();
    }

    @Override
    public void displayStepError() {
        Toast.makeText(getContext(), R.string.error_step, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resetVideoState() {
        releaseVideoPlayer();
        videoPosition = 0;
        isVideoPlaying = INITIAL_IS_VIDEO_PLAYING;
    }

    private void configurePlayerView() {
        if (StringUtils.isBlank(videoUrl)) {
            playerView.setVisibility(View.GONE);
        } else {
            playerView.setVisibility(View.VISIBLE);
            initializeVideoPlayer(videoUrl);
        }
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
        simpleExoPlayer.setPlayWhenReady(isVideoPlaying);
        simpleExoPlayer.seekTo(videoPosition);
    }

    private void releaseVideoPlayer() {
        if (simpleExoPlayer != null) {
            videoPosition = simpleExoPlayer.getCurrentPosition();
            isVideoPlaying = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
