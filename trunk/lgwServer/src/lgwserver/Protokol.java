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
    public Map<Object, Object> pluginHash;
    
    /**
     * Creates a new instance of Protokol 
     */
    public Protokol(StringReader is, Map<Object, Object> p, Session s)
        throws IOException, JDOMException
    {
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
                
                if(auth.authenticate(e.getChildText("username"), e.getChildText("password")))
                {
                    System.out.println("Login: " + e.getChildText("username"));
                    
                    plugin.setAttribute("name", "auth");
                    
                    Element returnCode = new Element("return");
                    returnCode.setText("true");
                    
                    plugin.addContent(returnCode);
                    root.addContent(plugin);
                    
                    Element set = new Element("set");
                    Element cookie = new Element("cookie");
                    cookie.setAttribute("name", "session");
                    cookie.setAttribute("value", (String)session.get("session_id")); // die session fehlt noch
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
            } else
            {
                System.out.println("plugin: " + e.getAttributeValue("name"));
                ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).recieveEvent(doc);
                answerDoc = ((LgwPlugin)pluginHash.get(e.getAttributeValue("name"))).sendEvent();
            }
        }
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
