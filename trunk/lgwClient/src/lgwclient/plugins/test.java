/*
 * test.java
 *
 * Created on 8. Juni 2005, 21:59
 */

package lgwclient.plugins;

import lgwclient.Preference;

import org.jdom.*;
import org.jdom.output.*;

/**
 *
 * @author  jfried
 */
public class test extends lgwclient.LgwPlugin
{
    public Preference getPreference()
    {
        return new Preference();
    }
    
    public void recieveEvent(org.jdom.Document d)
    {
        System.out.println("Hab WAS!!!!");
    }
    
    public javax.swing.JPanel getWorkspace()
    {
        return this;
    }
    
    /** Creates new form test */
    public test() 
    {
        super();
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        btnTest = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.openide.awt.Mnemonics.setLocalizedText(btnTest, "Plugin 1 Test Button");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        add(btnTest, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

    }
    // </editor-fold>//GEN-END:initComponents

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        doc = new Document();
        plugin_root = new Element("plugin");
        Element plugin_message = new Element("message");
        
        plugin_message.setText("Test Test Test");
        plugin_root.setAttribute("name", "test");
        
        plugin_root.addContent(plugin_message);
        
        sendEvent();
    }//GEN-LAST:event_btnTestActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTest;
    // End of variables declaration//GEN-END:variables
    
}
