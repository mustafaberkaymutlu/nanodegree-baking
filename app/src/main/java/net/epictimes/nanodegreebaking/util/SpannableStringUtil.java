package net.epictimes.nanodegreebaking.util;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;

/**
 Created by Mustafa Berkay Mutlu on 03.05.18.
 */
public final class SpannableStringUtil {

    private SpannableStringUtil() {
        throw new AssertionError("No instances for you!");
    }

    /**
     * Searches a string inside an other and applies given color and typeface.
     *
     * @param text       the full text
     * @param textToSpan the area which needs to be spanned
     * @param spans      the spans to be applied
     * @return SpannedString
     */
    @NonNull
    public static SpannableString spanInternal(@NonNull final String text,
                                               @NonNull final String textToSpan,
                                               @NonNull final Object... spans) {
        return spanInternal(new SpannableString(text), textToSpan, spans);
    }

    /**
     * Searches a string inside an other and applies given color and typeface.
     *
     * @param text       the full text
     * @param textToSpan the area which needs to be spanned
     * @param spans      the spans to be applied
     * @return SpannedString
     */
    @NonNull
    public static SpannableString spanInternal(@NonNull final SpannableString text,
                                               @NonNull final String textToSpan,
                                               @NonNull final Object... spans) {
        String textString = text.toString();

        if (textString.contains(textToSpan)) {
            final int startIndex = textString.indexOf(textToSpan);
            final int endIndex = startIndex + textToSpan.length();

            for (Object span : spans) {
                text.setSpan(span, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return text;
    }

}
