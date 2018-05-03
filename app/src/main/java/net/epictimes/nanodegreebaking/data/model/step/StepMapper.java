package net.epictimes.nanodegreebaking.data.model.step;

import org.apache.commons.lang3.StringUtils;

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
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(stepRaw.getId())) {
            stringBuilder.append("id cannot be empty, ");
        }

        if (StringUtils.isBlank(stepRaw.getShortDescription())) {
            stringBuilder.append("shortDescription cannot be empty, ");
        }

        if (StringUtils.isBlank(stepRaw.getDescription())) {
            stringBuilder.append("description cannot be empty, ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
