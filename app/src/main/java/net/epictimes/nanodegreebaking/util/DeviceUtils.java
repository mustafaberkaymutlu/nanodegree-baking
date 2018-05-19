package net.epictimes.nanodegreebaking.util;

import android.content.Context;
import android.support.annotation.NonNull;

import net.epictimes.nanodegreebaking.R;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public final class DeviceUtils {

    private DeviceUtils() {
        throw new AssertionError("No instances for you!");
    }

    public static boolean isTablet(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static boolean isLandscape(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.isLandscape);
    }
}
