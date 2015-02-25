/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rnikolaus.md5summer;

import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author rapnik
 */
public class StreamTextArea extends JTextArea{
    final private TextAreaOutputStream outputStream ;

    public StreamTextArea() {
        outputStream = new TextAreaOutputStream(this);
        this.setEditable(false);
    }
    public OutputStream getOutputStream(){
        return outputStream;
    }
    
}
