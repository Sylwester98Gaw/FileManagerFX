/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GetBookmarksList {
    public static javafx.scene.control.ListView<Label> bookmark;

    public static ListView<Label> getBookmark() {
        return bookmark;
    }

    public static void setBookmark(javafx.scene.control.ListView<Label> getBookmark) {
        GetBookmarksList.bookmark = getBookmark;
    }
}
