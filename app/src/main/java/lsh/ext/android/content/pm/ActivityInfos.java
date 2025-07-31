package lsh.ext.android.content.pm;

import android.content.pm.ActivityInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ActivityInfos {

	public static boolean isScreenOrientationLandscape(final int orientation) {
		return orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				|| orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
				|| orientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
				|| orientation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE;
	}

}

