/*
 * LgwPlugin.java
 *
 * Created on 7. Juni 2005, 21:04
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
public abstract class LgwPlugin extends javax.swing.JPanel
{
    /** Liefert ein Eigenschaften Objekt zur�ck mit allem m�glichen
     * drin wie Text f�r den Button, Bildname usw.
     */
    abstract public Preference getPreference();
    
    /** Eventbehandlung
     * Das Uebertragenen XML Dokument wird weitergegeben
     *
     * @param Document d XML document der JDOM Librarie
     */
    abstract public void recieveEvent(org.jdom.Document d);
    
    /** Sendet ein Event an den Server
     */
    abstract public org.jdom.Document sendEvent();
    
    /** Liefert den Workspace zur�ck
     */
    abstract public javax.swing.JPanel getWorkspace();
}
