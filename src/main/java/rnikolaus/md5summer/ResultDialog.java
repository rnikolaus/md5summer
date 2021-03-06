package rnikolaus.md5summer;

/**
 *
 * @author rapnik
 */
public class ResultDialog extends javax.swing.JDialog {

    /**
     * Creates new form ResultDialog
     * @param title
     * @param parent
     * @param modal
     */
    public ResultDialog(String title,java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle(title);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        streamTextArea1 = new rnikolaus.md5summer.StreamTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        streamTextArea1.setColumns(20);
        streamTextArea1.setRows(5);
        streamTextArea1.setFont(new java.awt.Font("Consolas", 0, 10)); // NOI18N
        jScrollPane1.setViewportView(streamTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public StreamTextArea getStreamTextArea(){
        return streamTextArea1;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private rnikolaus.md5summer.StreamTextArea streamTextArea1;
    // End of variables declaration//GEN-END:variables
}
