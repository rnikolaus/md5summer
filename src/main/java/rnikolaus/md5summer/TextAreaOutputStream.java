/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.md5summer;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author rapnik
 */
public class TextAreaOutputStream extends OutputStream {

    private final JTextArea textArea;
    private StringBuffer buffer = new StringBuffer();

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        buffer.append(Character.toChars((b + 256) % 256));
        if ((char) b == '\n') {
            textArea.append(buffer.toString());
            textArea.setCaretPosition(textArea.getDocument().getLength());
            buffer.delete(0, buffer.length());
        }
    }

}
