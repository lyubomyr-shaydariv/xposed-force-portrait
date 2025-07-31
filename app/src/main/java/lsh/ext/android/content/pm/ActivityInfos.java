package lsh.ext.android.content.pm;

import android.content.pm.ActivityInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ActivityInfos {

	public static boolean isScreenOrientationLandscape(final int requestedOrientation) {
		return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				|| requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
				|| requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
				|| requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE;
	}

}
