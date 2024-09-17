/*
 * Copyright (c) 2024. This code is my creation, my passion, and my contribution to the community. It's open for all to use, learn, and grow from. I sign it with my name proudly, committed to supporting freedom and collaboration in the world of programming.
 */

package script.lang;

public enum LangENG {
    CONTEXTMENU_CHANGECOLOR("Change display color..."),
    CONTEXTMENU_COPY("Copy"),
    CONTEXTMENU_ADDBOOKMARKS("Add directory to bookmarks"),
    CONTEXTMENU_PASTE("Paste"),
    CONTEXTMENU_MOVE("Move"),
    CONTEXTMENU_RENAME("Rename..."),
    CONTEXTMENU_TERMINAL("Terminal"),
    CONTEXTMENU_CREATE("Create..."),
    CONTEXTMENU_CREATEDIR("Create directory"),
    CONTEXTMENU_CREATEFILE("Create file"),
    CONTEXTMENU_MOVETOTRASH("Move to trash"),
    CONTEXTMENU_PROPERTIES("Properties"),
    CONTEXTMENU_OPENWITHPROGRAM("Run with.."),
    CONTEXTMENU_ADDNEWPROGRAM("Run with a program from the list"),
    CONTEXTMENU_DELETE("Delete"),

    ALERT_COPING_COPINGFAILURE("Coping failed "),
    ALERT_COPING_COPINGPASSED("Coping succes "),
    ALERT_COPING_COPING("Coping "),
    ALERT_ERROR("Error "),
    ALERT_ERROR_SOMETHINGWENTWRONG("Something went wrong "),

    DIALOG_CREATEDIRORFILES_TITLE("New file/dir creator "),
    DIALOG_CREATEDIRORFILES_HEADERTEXT("Specify the name of the new directory/file that will be created in "),
    DIALOG_CREATEDIRORFILES_CONTENT("Name.,, "),
    ALERT_CREATEDIRORFILES_DIRISEXIST("The catalogue already exists "),
    ALERT_CREATEDIRORFILES_FILEISEXIST("The file already exists "),

    ALERT_BADNAME_WARNING("Warning ! "),
    ALERT_BADNAME_YOUCANNOT("You cannot use the character / in the name !"),

    ALERT_TRASH_MOVETOTRASH("Move to trash"),
    ALERT_TRASH_TRASH("Trash"),
    ALERT_TRASH_DELETE("Delete"),
    ALERT_TRASH_DELETINGFAILS("Removal not successful"),
    ALERT_TRASH_DELETINGDIR("Delete catalog"),
    ALERT_TRASH_DELETEFILE("Delete file"),

    ALERT_MOVE_TITLE("Moving.."),
    ALERT_MOVE_HEADER_FROM("Moving from "),
    ALERT_MOVE_HEADER_TO("To "),
    ALERT_MOVE_CONTENT("Everything is correct? "),
    ALERT_MOVE_CANCEL("Cancel "),
    ALERT_MOVE_MOVING("Moving  "),
    ALERT_MOVE_MOVINGFAIL("Moving failed "),

    ALERT_TERMINAL_PROBLEMWITHTERMINAL("There is a problem with the terminal "),

    ///////////////////////////////////////////////////////////////////////////////////
    // MAIN SCENE !!!!
    //////////////////////////////////////////////////////////////////////////////////

    LABEL_BOOKMARKDEFAULT_SYSTEM("System"),
    LABEL_BOOKMARKDEFAULT_HOME("Home"),
    LABEL_BOOKMARKDEFAULT_TRASH("TRASH"),

    DIRECTORYCHOOSER_TITLE("Moving"),

    FILECHOOSER_ADDOTHERPROGRAM("Add an optional program"),

    CONTEXTMENU_PROPERTIES_NAME_DIR("Directory"),
    CONTEXTMENU_PROPERTIES_NAME_FILE("File");

    //////////////////////////////////////////////////////////////////////////////////////////// TODO NIE WSZYSTKIE ZOSTAŁY WYMIENIONE
    private final String text;

    LangENG(String text) {
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}
