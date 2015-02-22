package rnikolaus.md5summer;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author rapnik
 */
public class TextAreaOutputStream extends OutputStream {

    private final JTextArea textArea;
    private final StringBuffer buffer = new StringBuffer();

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        synchronized (buffer) {
            buffer.append(Character.toChars((b + 256) % 256));
            if ((char) b == '\n') {
                final String toString = buffer.toString();
                buffer.delete(0, buffer.length());
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        textArea.append(toString);
                        textArea.setCaretPosition(textArea.getDocument().getLength());
                    }
                });
            }
        }
    }

}
