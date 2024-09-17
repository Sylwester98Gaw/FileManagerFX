package script.helpers;

public enum FileSys {
    SYSTEM("/"),
    HOME(System.getProperty("user.home")),
    TRASH("/.local/share/Trash/files");
    private final String path;

    FileSys(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
