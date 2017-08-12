package roguelike.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

/**
 * Class for implementing the utility screen on the left 1/5
 * of the terminal screen. This screen contains information
 * such as status of the player, relevant keys to press, etc.
 */
public class UtilityScreen implements Screen {

    private int width;
    private int height;
    private String[] lines;
    private int curLine;

    public UtilityScreen(int terminalWidth, int terminalHeight) {
        this.height = terminalHeight;
        this.width = terminalWidth / 5;
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

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int y = 0; y < this.height; y++) {
            if (this.lines[y] == null) {
                terminal.write(' ', 0, y);
            } else {
                terminal.write(this.lines[y], 0, y, AsciiPanel.brightBlue);
            }
            terminal.write('|', this.width - 1, y, AsciiPanel.red);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}
