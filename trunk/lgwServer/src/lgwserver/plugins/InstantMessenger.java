/*
 * test.java
 *
 * Created on 9. Juni 2005, 20:29
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver.plugins;

import lgwserver.*;

import org.jdom.*;
import org.jdom.output.*;

/**
 *
 * @author jfried
 */
public class InstantMessenger extends LgwPlugin
{
    /** Creates a new instance of test */
    public InstantMessenger() 
    {
        super();
    }
    
    /** Methode um Plugin zu installieren
     * also datenbanken erzeugen und was halt so anfaellt
     */
    public void installPlugin()
    {
        
    }
    
    /** Plugin initialisieren
     */
    public void initialize()
    {
        System.out.println("InstantMessenger geladen.");
    }
    
    /** Eventbehandlung
     * Das Uebertragenen XML Dokument wird weitergegeben
     *
     * @param Document d XML document der JDOM Librarie
     */
    public void recieveEvent(org.jdom.Document d)
    {
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat(Format.getPrettyFormat());
        System.out.println("IM:");
        System.out.println(outp.outputString(d));
        System.out.println("-----------------------------------------------");
        
        // doc leeren
        doc = new Document();
        
        Element root = new Element("lgw");
        Element plugin = new Element("plugin");
        plugin.setAttribute("name", "InstantMessenger");
        Element answer = new Element("return");
        answer.setText("true");
        
        plugin.addContent(answer);
        root.addContent(plugin);
        doc.addContent(root);
    }
    
    /** Sendet ein Event an den Server
     */
    public org.jdom.Document sendEvent()
    {
        return doc;
    }
}
