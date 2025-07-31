package lsh.xposed.modules.forceportraitorientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
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
		hookActivitySetRequestedOrientation(loadPackageParam);
		hookDisplayGetOrientation(loadPackageParam);
		hookDisplayGetRotation(loadPackageParam);
		hookResourcesGetConfiguration(loadPackageParam);
	}

	private static void hookActivitySetRequestedOrientation(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
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

	private static void hookDisplayGetOrientation(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
		XposedHelpers.findAndHookMethod(
				"android.view.Display",
				loadPackageParam.classLoader,
				"getOrientation",
				new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(final MethodHookParam methodHookParam) {
						methodHookParam.setResult(Configuration.ORIENTATION_PORTRAIT);
					}
				}
		);
	}

	private static void hookDisplayGetRotation(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
		XposedHelpers.findAndHookMethod(
				"android.view.Display",
				loadPackageParam.classLoader,
				"getRotation",
				new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(final MethodHookParam methodHookParam) {
						methodHookParam.setResult(Surface.ROTATION_0);
					}
				}
		);
	}

	private static void hookResourcesGetConfiguration(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
		XposedHelpers.findAndHookMethod(
				"android.content.res.Resources",
				loadPackageParam.classLoader,
				"getConfiguration",
				new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(final MethodHookParam methodHookParam) {
						final Configuration config = (Configuration) methodHookParam.getResult();
						if ( config.orientation == Configuration.ORIENTATION_PORTRAIT ) {
							return;
						}
						config.orientation = Configuration.ORIENTATION_PORTRAIT;
						methodHookParam.setResult(config);
					}
				}
		);
	}

}
