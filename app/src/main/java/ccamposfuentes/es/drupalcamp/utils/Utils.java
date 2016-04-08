package ccamposfuentes.es.drupalcamp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.ImageButton;

import ccamposfuentes.es.drupalcamp.R;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 08/04/16
 * Project: DrupalCamp
 */

public class Utils {
    /**
     * Calc dp to pixels
     * @param dp to calc pixels
     * @return dp in pixels
     */
    public static float dpToPx(int dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Calc pixels to dp
     * @param px to calc pixels
     * @return px in dp
     */
    public static int pxToDp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Tint Drawable with one color
     * @param drawable Drawable to tint
     * @param color Color to set drawable
     */
    public static void tintDrawable (Drawable drawable, int color) {

        if (Build.VERSION.SDK_INT >= 21) {
            drawable.setTint(color);
        } else {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

    }

    /**
     * Tint Drawable with one color
     * @param imageButton ImageButton to tint
     * @param color Color to set drawable
     */
    public static void tintImageButton (Context context, ImageButton imageButton, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageButton.getDrawable().setTint(ContextCompat.getColor(context, color));
        }
        else {
            imageButton.getDrawable().mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }

    }
}
