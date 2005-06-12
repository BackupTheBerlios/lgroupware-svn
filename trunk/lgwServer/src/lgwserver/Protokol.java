/*
 * Protokol.java
 *
 * SERVER!!!
 *
 * Created on 5. Juni 2005, 14:46
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver;

import java.io.*;
import java.util.*;
import org.apache.tools.ant.filters.StringInputStream;

import org.jdom.*;
import org.jdom.xpath.*;
import org.jdom.output.*;
import org.jdom.input.*;

import lgwserver.auth.*;

/**
 * Hier laeuft die ganze kommunikation ab
 * @author jfried
 */
public class Protokol 
{
    protected Document doc, answerDoc;
    protected String xmlDocument;
    protected Session session;
    protected Auth auth;
    protected Preference prefs;
    public Map<Object, Object> pluginHash;
    
    /**
     * Creates a new instance of Protokol 
     */
    public Protokol(StringReader is, Map<Object, Object> p, Session s, Preference pr)
        throws IOException, JDOMException
    {
        prefs = pr;
        pluginHash = p;
        session = s;
        
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(false); // keine validierung weil keine gramatik
        builder.setIgnoringElementContentWhitespace(true);
        
        doc = builder.build(is);

        XPath pluginPath = XPath.newInstance("/lgw/plugin");
        List list = pluginPath.selectNodes(doc);
        
        answerDoc = new Document();
        Element root = new Element("lgw");
        Element plugin = new Element("plugin");
        
        Iterator i = list.iterator();
        if(i.hasNext())
        {
            Element e = (Element)i.next();
            
            if(e.getAttributeValue("name").equalsIgnoreCase("auth") == true)
            {
                AuthFactory fact = new AuthFactory();
                auth = (Auth)fact.getAuthObject("Null");
                
                if(auth.authenticate(e.getChildTextTrim("username"), e.getChildTextTrim("password")))
                {
                    System.out.println("Login: " + e.getChildText("username"));
                    
                    session.set("username", e.getChildText("username"));
                    
                    plugin.setAttribute("name", "auth");
                    
                    Element returnCode = new Element("return");
                    returnCode.setText("true");
                    
                    plugin.addContent(returnCode);
                    root.addContent(plugin);
                    
                    Element set = new Element("set");
                    Element cookie = new Element("cookie");
                    cookie.setAttribute("name", "session");
                    cookie.setAttribute("value", (String)session.get("session_id"));
                    set.addContent(cookie);
                    root.addContent(set);
                    
                    answerDoc.addContent(root);
                } else
                {
                    plugin.setAttribute("plugin", "auth");
                    
                    Element returnCode = new Element("return");
                    returnCode.setText("false");
                    
                    plugin.addContent(returnCode);
                    root.addContent(plugin);
                    answerDoc.addContent(root);
                }
            } else if(e.getAttributeValue("name").equalsIgnoreCase("update") == true)
            {
                List childlist = e.getChildren();
                Iterator itch = childlist.iterator();
                
                while(itch.hasNext())
                {
                    Element che = (Element)itch.next();
                    if(che.getName().equalsIgnoreCase("version") == true)
                    { // erstmal versionsvergleich
                        plugin.setAttribute("name", "update");
                        Element update = new Element("update");
                        if(checkForUpdate(Float.valueOf(e.getChildTextTrim("version")).floatValue()) == true)
                        {
                            update.setText("true");
                        } else
                        {
                            update.setText("false");
                        }
                        plugin.addContent(update);
                        root.addContent(plugin);
                        answerDoc.addContent(root);
                    } else if(che.getName().equalsIgnoreCase("update") == true)
                    { // dann update
                        if(e.getChildTextTrim("update").equalsIgnoreCase("update_me") == true)
                        { // wirklich ein update durchfuehren
                            System.out.println("update durchfuehren!!");
                            FileInputStream ins = new FileInputStream(((Element)prefs.getElement("/lgw/download_path")).getText() + "/client.zip");
                            int r;
                            byte b[] = new byte[4096];
                            
                            String base64 = "";
                            
                            while((r = ins.read(b)) > -1)
                            {
                                base64 +=  String.copyValueOf(Base64.encode(b));
                            }
 
                            System.out.println(base64);
                            plugin.setAttribute("name", "update");
                            Element file = new Element("file");
                            file.setText(base64);
                            plugin.addContent(file);
                            root.addContent(plugin);
                            answerDoc.addContent(root);
                        } else
                        { // kein update durchfuehren
                        }
                    } else
                    {
                        System.out.println("Unknown update call...");
                    }
                }
            } else
            {
                System.out.println("plugin: " + e.getAttributeValue("name"));
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).recieveEvent(doc);
                answerDoc = ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).sendEvent();
            }
        }
    }

    protected boolean checkForUpdate(float v)
    {
        float tv = Float.valueOf(((Element)prefs.getElement("/lgw/client_version")).getText()).floatValue();
        if(v < tv)
            return true;
        return false;
    }
    
    public Session getSession()
    {
        return session;
    }    
    
    /** Abfragen des Anwort XML
     */
    public String getXML()
    {
        String xml;
        
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat(Format.getPrettyFormat());
        xml = outp.outputString(answerDoc);
        
        return xml;
    }
}
