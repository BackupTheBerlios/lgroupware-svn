/*
 * test.java
 *
 * Created on 8. Juni 2005, 21:59
 */

package lgwclient.plugins;

import lgwclient.Preference;

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
        
    }
    
    public org.jdom.Document sendEvent()
    {
        return new org.jdom.Document();
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
        jButton1 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, "jButton1");
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
    
}
