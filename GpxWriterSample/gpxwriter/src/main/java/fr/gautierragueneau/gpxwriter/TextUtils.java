package fr.gautierragueneau.gpxwriter;

/**
 * Created by gautierragueneau on 14/09/2016.
 */

public class TextUtils {

    public static boolean isValidText(String text) {
        if (text != null && !android.text.TextUtils.isEmpty(text)) {
            return true;
        } else {
            return false;
        }
    }

}
