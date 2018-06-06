package sharecourse.others;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class SysUtils {

	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity) {
		int width = 0, height = 0;
		WindowManager windowManager = activity.getWindowManager();
		DisplayMetrics outMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(outMetrics);
		width = outMetrics.widthPixels;
		height = outMetrics.heightPixels;

		/*
		 * Display display = windowManager.getDefaultDisplay();
		 * width=display.getWidth();
		 */
		return width;
	}

}
