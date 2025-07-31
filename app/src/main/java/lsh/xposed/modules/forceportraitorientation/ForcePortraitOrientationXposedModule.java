package lsh.xposed.modules.forceportraitorientation;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public final class ForcePortraitOrientationXposedModule
		implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
		if ( loadPackageParam.packageName.startsWith("android") ) {
			return;
		}
	}

}
