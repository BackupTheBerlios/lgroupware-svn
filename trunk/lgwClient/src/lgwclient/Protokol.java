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
import java.util.zip.*;

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
    protected CookieContainer cookies;
    protected boolean hasanswer = false;
    
    protected PrintWriter out;
    protected Object gui;
    protected Preference prefs;
    public Map<Object, Object> pluginHash;
    
    /** Creates a new instance of Protokol */
    public Protokol(StringReader is, Object g, Preference p, Map<Object, Object> m, CookieContainer c, PrintWriter pw)
        throws IOException, JDOMException
    {
        gui = g;
        prefs = p;
        pluginHash = m;
        cookies = c;
        out = pw;
        
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false); // keine validierung weil keine gramatik
        builder.setIgnoringElementContentWhitespace(true);
        doc = builder.build(is);
        
        XPath pluginPath = XPath.newInstance("/lgw/plugin");
        List list = pluginPath.selectNodes(doc);
        
        // Cookies verarbeiten
        XPath cookiePath = XPath.newInstance("/lgw/set/*");
        List listSet = cookiePath.selectNodes(doc);
        Iterator ci = listSet.iterator();
        
        while(ci.hasNext())
        {
            Element e = (Element)ci.next();
            if(e.getName().equalsIgnoreCase("cookie"))
            {
                System.out.println("Cookie: " + e.getAttributeValue("name") + " || Value: " + e.getAttributeValue("value"));
                cookies.add(e.getAttributeValue("name"), e.getAttributeValue("value"));
            }
        }
        
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
                    ((ClientGUI)gui).lblStatusBarConnectionState.setText("Eingeloggt als: " + prefs.getElement("/lgw/auth/username").getTextTrim());
                } else
                { // authentifizierung fehlerhaft, verbindung beenden
                    
                }
            } else if(e.getAttributeValue("name").equalsIgnoreCase("update") == true)
            {
                List childlist = e.getChildren();
                Iterator itch = childlist.iterator();
                
                while(itch.hasNext())
                {
                    Element che = (Element)itch.next();
                    if(che.getName().equalsIgnoreCase("update") == true)
                    {
                        if(e.getChildTextTrim("update").equalsIgnoreCase("true") == true)
                        { // ein update ist verfuegbar, als fragen wir auch nach dem update
                            System.out.println("ein update ist verfügbar...");
                            doc = new Document();
                            Element plugin_message = new Element("update");
                            plugin_message.setText("update_me");
                            plugin.setAttribute("name", "update");

                            plugin.addContent(plugin_message);
                            // cookies hinzufügen
                    //        Map<Object, Object> allCookies = cookies.getAllCookies();
                            System.out.println("sendEvent()");
                            Map<Object, Object> allCookies = cookies.getAllCookies();
                            Iterator it = allCookies.keySet().iterator();
                            root.addContent(plugin);
                            while(it.hasNext())
                            {
                                String key = (String) it.next();
                                Element c2 = new Element("cookie");
                                c2.setAttribute("name", key);
                                c2.setAttribute("value", (String)allCookies.get(key));
                                root.addContent(c2);
                            }

                            doc.addContent(root);

                            XMLOutputter outp = new XMLOutputter();
                            outp.setFormat(Format.getPrettyFormat());
                            System.out.println(outp.outputString(doc));
                            out.println(outp.outputString(doc));
                            out.flush();
                        } else
                        { // kein update verfuegbar
                            System.out.println("kein update ist verfügbar...");
                        }
                    } else if(che.getName().equalsIgnoreCase("file") == true)
                    {
                        byte b[] = Base64.decode(che.getText().toCharArray());
                        FileOutputStream fo = new FileOutputStream(((Element)prefs.getElement("/lgw/tmp_dir")).getText() + "/client.zip");
                        fo.write(b);
                        fo.close();
                        
                        try
                        {
                            BufferedOutputStream destination = null;
                            FileInputStream finps = new FileInputStream(((Element)prefs.getElement("/lgw/tmp_dir")).getText() + "/client.zip");
                            ZipInputStream zipinp = new ZipInputStream(new BufferedInputStream(finps));
                            ZipEntry entry;
                            while((entry = zipinp.getNextEntry()) != null) 
                            {
                                System.out.println("unzip: " + entry);
                                if(entry.isDirectory() == true)
                                {
                                    new File(((Element)prefs.getElement("/lgw/install_dir")).getText() + "/" + entry.getName()).mkdirs();
                                } else
                                {
                                    int count;
                                    byte databuff[] = new byte[4096];
                                    FileOutputStream fos = new FileOutputStream(((Element)prefs.getElement("/lgw/install_dir")).getText() + "/" + entry.getName());
                                    destination = new BufferedOutputStream(fos, 4096);
                                    while ((count = zipinp.read(databuff, 0, 4096)) != -1) 
                                    {
                                        destination.write(databuff, 0, count);
                                    }
                                    destination.flush();
                                    destination.close();
                                }
                            }
                            zipinp.close();
                        } catch(Exception zipex)
                        {
                            System.out.println("Fehler beim entpacken des updates..." + zipex);
                        }
                    }
                }
                
                System.out.println("update request");
            } else
            { // ein anderes plugin
                System.out.println("Plugin: " + e.getAttributeValue("name"));
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).setCookies(cookies);
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).recieveEvent(doc);
            }
        }
    }
    
    /** gibt true zurueck wenn eine xml antwort zur verfuegung steht
     */
    public boolean hasAnswer()
    {
        return hasanswer;
    }
    
    public CookieContainer getCookies()
    {
        return cookies;
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
/*    public void setPrefs(Object o)
    {
        prefs = o;
    } */
}
