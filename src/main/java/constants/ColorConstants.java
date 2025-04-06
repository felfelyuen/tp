package constants;

/**
 * Contains ANSI color codes and text formatting constants for console output.
 */
public class ColorConstants {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";

    // Text formatting
    public static final String BOLD = "\u001B[1m";
    public static final String BLINK = "\u001B[5m";

    public static final String[] RAINBOW = {
        "\u001B[38;5;196m",
        "\u001B[38;5;202m",
        "\u001B[38;5;208m",
        "\u001B[38;5;214m",
\       "\u001B[38;5;220m",
        "\u001B[38;5;226m",
        "\u001B[38;5;190m",
        "\u001B[38;5;154m",
        "\u001B[38;5;118m",
        "\u001B[38;5;82m",
        "\u001B[38;5;46m",
        "\u001B[38;5;47m",
        "\u001B[38;5;48m",
        "\u001B[38;5;49m",
        "\u001B[38;5;50m",
        "\u001B[38;5;51m",
        "\u001B[38;5;45m",
        "\u001B[38;5;39m",
        "\u001B[38;5;33m",
        "\u001B[38;5;27m",
        "\u001B[38;5;21m",
        "\u001B[38;5;57m",
        "\u001B[38;5;93m",
        "\u001B[38;5;129m",
        "\u001B[38;5;165m",
        "\u001B[38;5;201m",
        "\u001B[38;5;200m"
    };

    // Background colors
    public static final String BG_BLACK = "\u001B[48;5;16m";
    public static final String BG_GRADIENT = "\u001B[48;5;232m";
}
