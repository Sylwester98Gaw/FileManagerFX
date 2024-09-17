package script.helpers;

import javafx.scene.paint.Color;

public enum Colors {
    DEFAULT(Color.BLACK),
    HIDDEN(Color.RED),
    FILE(Color.CHARTREUSE),
    APP(Color.CORAL),
    RED(Color.RED),
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    WHITE(Color.WHITE),
    YELLOW(Color.YELLOW),
    PURPLE(Color.PURPLE),
    SILVER(Color.SILVER);

    private final Color color;

    Colors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
