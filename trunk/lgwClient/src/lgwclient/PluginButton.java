/*
 * PluginButton.java
 *
 * Created on 8. Juni 2005, 19:47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwclient;

/**
 *
 * @author jfried
 */
public class PluginButton extends javax.swing.JButton 
{
    protected int index;
    public String pluginName="";
    
    /** Creates a new instance of PluginButton */
    public PluginButton() 
    {
        super();
    }
    
    public void setIndex(int i)
    {
        index = i;
    }
    
    public int getIndex()
    {
        return index; 
    }
}
