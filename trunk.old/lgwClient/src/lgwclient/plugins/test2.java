/*
 * test2.java
 *
 * Created on 8. Juni 2005, 22:23
 */

package lgwclient.plugins;

import lgwclient.Preference;

/**
 *
 * @author  jfried
 */
public class test2 extends lgwclient.LgwPlugin 
{
    public Preference getPreference()
    {
        return new Preference();
    }
    
    public void recieveEvent(org.jdom.Document d)
    {
        
    }
    
    public javax.swing.JPanel getWorkspace()
    {
        return this;
    }

    /** Creates new form test2 */
    public test2() 
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
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, -1, -1));

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
    
}
