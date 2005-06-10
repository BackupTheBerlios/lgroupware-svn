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

import org.jdom.*;
import org.jdom.output.*;

/**
 *
 * @author jfried
 */
public abstract class LgwPlugin extends javax.swing.JPanel
{
    protected org.jdom.Document doc;
    protected TcpClientSocket sock;
    
    /** Liefert ein Eigenschaften Objekt zurück mit allem möglichen
     * drin wie Text für den Button, Bildname usw.
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
    public void sendEvent()
    {
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat(Format.getPrettyFormat());
        System.out.println(outp.outputString(doc));
        sock.send(outp.outputString(doc));
    }
    
    /** Liefert den Workspace zurück
     */
    abstract public javax.swing.JPanel getWorkspace();
    
    /** den Netzwerksocket bekannt geben
     */
    public void setNetworkSocket(TcpClientSocket o)
    {
        sock = o;
    }
}
