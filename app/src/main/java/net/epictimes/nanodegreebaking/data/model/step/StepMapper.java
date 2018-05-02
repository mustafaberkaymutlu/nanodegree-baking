package net.epictimes.nanodegreebaking.data.model.step;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class StepMapper implements Function<StepRaw, Step> {

    @Inject
    public StepMapper() {
    }

    @Override
    public Step apply(final StepRaw stepRaw) {
        validateFields(stepRaw);

        return Step.StepBuilder.aStep()
                               .withId(stepRaw.getId())
                               .withDescription(stepRaw.getDescription())
                               .withShortDescription(stepRaw.getShortDescription())
                               .withThumbnailURL(stepRaw.getThumbnailURL())
                               .withVideoURL(stepRaw.getVideoURL())
                               .build();
    }

    private void validateFields(final StepRaw stepRaw) {
        // TODO validate fields
    }
}
