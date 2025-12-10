/*
 * Copyright (c) 2025. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;

public class GetTheme {
    public static boolean light = true;

    public static boolean isLight() {
        return light;
    }

    public static void setLight(boolean light) {
        GetTheme.light = light;
    }
}
