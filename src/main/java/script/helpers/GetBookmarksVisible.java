/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;

public class GetBookmarksVisible {
    public static Boolean visibleBookmarks;

    public static Boolean getVisibleBookmarks() {
        return visibleBookmarks;
    }

    public static void setVisibleBookmarks(Boolean visibleBookmarks) {
        GetBookmarksVisible.visibleBookmarks = visibleBookmarks;
    }
}
