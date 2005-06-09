/*
 * Protokol.java
 *
 * Created on 7. Juni 2005, 19:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwclient;

import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.output.*;
import org.jdom.input.*;

/**
 *
 * @author jfried
 */
public class Protokol 
{
    protected Document doc, answerDoc;
    protected String xmlDocument;
    
    protected boolean hasanswer = false;
    
    protected Object gui, prefs;
    
    /** Creates a new instance of Protokol */
    public Protokol(StringReader is, Object g, Object p)
        throws IOException, JDOMException
    {
        gui = g;
        prefs = p;
        
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false); // keine validierung weil keine gramatik
        builder.setIgnoringElementContentWhitespace(true);
        doc = builder.build(is);
        
        XPath pluginPath = XPath.newInstance("/lgw/plugin");
        List list = pluginPath.selectNodes(doc);
        
        answerDoc = new Document();
        Element root = new Element("lgw");
        Element plugin = new Element("plugin");
        
        System.out.println(getXML());
        
        Iterator i = list.iterator();
        if(i.hasNext())
        {
            Element e = (Element)i.next();
            if(e.getAttributeValue("name").equalsIgnoreCase("auth") == true)
            { // authentifizierung
                if(e.getChildText("return").equalsIgnoreCase("true") == true)
                { // authentifiziernug erfolgreich
                    ((ClientGUI)gui).lblStatusBarConnectionState.setText("Eingeloggt als: " + ((Preference)prefs).getElement("/lgw/auth/username").getTextTrim());
                } else
                { // authentifizierung fehlerhaft, verbindung beenden
                    
                }
            } else
            { // ein anderes plugin
                System.out.println("Plugin: " + e.getAttributeValue("name"));
            }
        }
    }
    
    /** gibt true zurueck wenn eine xml antwort zur verfuegung steht
     */
    public boolean hasAnswer()
    {
        return hasanswer;
    }
    
    /** Abfragen des Anwort XML
     */
    public String getXML()
    {
        String xml;
        
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat(Format.getPrettyFormat());
        //xml = outp.outputString(answerDoc);
        xml = outp.outputString(doc);
        
        return xml;
    }
    /** Client GUI setzten
     * das die Klasse auf die GUI zugreifen kann.
     */
    public void setGUI(Object o)
    {
        gui = o;
    }
    
    /** Preference klasse setzten
     */
    public void setPrefs(Object o)
    {
        prefs = o;
    }
}