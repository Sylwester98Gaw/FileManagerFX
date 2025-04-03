/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.helpers;

import javafx.scene.control.CheckBox;

public class GetMultipleSelect {
    private static CheckBox checkBox;

    public static CheckBox getCheckBox() {
        return checkBox;
    }

    public static void setCheckBox(CheckBox checkBox) {
        GetMultipleSelect.checkBox = checkBox;
    }
}
