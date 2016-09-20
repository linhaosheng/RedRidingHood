package linhao.redridinghood.util;

/**
 * Created by coreVK on 2016/4/2.
 */
public class ExitClickUtil {

    private static long lastClickTime;

    public static boolean isClickAgain() {
        long time = System.currentTimeMillis();
        long timeAgain = time - lastClickTime;
        if (0 < timeAgain && timeAgain < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
