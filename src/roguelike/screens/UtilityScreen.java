package roguelike.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

/**
 * Class for implementing the utility screen on the left 1/5
 * of the terminal screen. This screen contains information
 * such as status of the player, relevant keys to press, etc.
 */
public class UtilityScreen {
    private String[] lines;
    private int curLine;
    private int width;
    private int height;

    public UtilityScreen(int w, int h) {
        this.width = w;
        this.height = h;
        this.lines = new String[this.height];
        this.curLine = 0;
    }

    public void addLine(String text) {
        if (text.length() <= this.width) {
            this.lines[this.curLine] = text;
            this.curLine++;
        } else {
            throw new IndexOutOfBoundsException("String too long");
        }
    }

    public void modifyLine(int line, String newText) {
        if (newText.length() <= this.width) {
            this.lines[line] = newText;
        } else {
            throw new IndexOutOfBoundsException("String too long");
        }
    }

    public void setCurLine(int curLine) {
        this.curLine = curLine;
    }

    public void addLineBreaks(int num) {
        this.curLine += num;
    }

    public void displayScreen(AsciiPanel terminal) {
        for (int y = 0; y < this.height; y++) {
            if (this.lines[y] == null) {
                terminal.write(' ', 0, y);
            } else {
                terminal.write(this.lines[y], 0, y, AsciiPanel.brightCyan);
            }
            terminal.write('|', this.width - 1, y, AsciiPanel.red);
        }
    }
}
