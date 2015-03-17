package rnikolaus.md5summer;

import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author rapnik
 */
public class StreamTextArea extends JTextArea {

    final private TextAreaOutputStream outputStream;

    public StreamTextArea() {
        outputStream = new TextAreaOutputStream(this);
        this.setEditable(false);
    }

    public void reset() {
        this.setText("");
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

}
