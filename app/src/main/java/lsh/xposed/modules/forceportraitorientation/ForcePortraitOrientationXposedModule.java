package lsh.xposed.modules.forceportraitorientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lsh.ext.android.content.pm.ActivityInfos;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class ForcePortraitOrientationXposedModule
		implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
		if ( loadPackageParam.packageName.startsWith("android") ) {
			return;
		}
		XposedHelpers.findAndHookMethod(
				Activity.class,
				"setRequestedOrientation",
				int.class,
				new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(final MethodHookParam methodHookParam) {
						final int requestedOrientation = (int) methodHookParam.args[0];
						if ( !ActivityInfos.isScreenOrientationLandscape(requestedOrientation) ) {
							return;
						}
						methodHookParam.args[0] = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
						XposedBridge.log("Blocked landscape orientation for " + loadPackageParam.packageName);
					}
				}
		);
	}

}
