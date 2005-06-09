/*
 * Preference.java
 *
 * Created on 5. Juni 2005, 19:08
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
import org.jdom.input.SAXBuilder;

/**
 *
 * @author jfried
 */
public class Preference 
{
    private String fileName;
    protected Reader is;
    protected SAXBuilder builder;
    protected Document doc;

    
    public List getList(String path)
    {
        try
        {
            XPath serverConfigPath = XPath.newInstance(path);
            
            List serverConfig = serverConfigPath.selectNodes(doc);
            return serverConfig;
        } catch(JDOMException jex)
        {
            System.out.println("FEHLER: JDOM: " + jex);
        } catch(Exception ex)
        {
            System.out.println("FEHLER: " + ex);
        }
        
        return null;        
    }
    /**
     * get a config entry
     * @param String path XPath Path
     * @return String
     */
    public Element getElement(String path)
    {
        try
        {
            List serverConfig = getList(path);
            Iterator i = serverConfig.iterator();
            Element configEntry = (Element) i.next();
            return configEntry;
        } catch(Exception ex)
        {
            System.out.println("FEHLER: " + ex);
        }
        
        return null;
    }
    
    /**
     * @desc reads a xml file
     */
    private boolean load()
    {
        try
        {
            builder = new SAXBuilder();
            doc = builder.build(fileName);
            //doRecursive(doc);
        } catch(JDOMException jex)
        {
            System.out.println("FEHLER: JDOM: " + jex);
        } catch(IOException ex)
        {
            System.out.println("FEHLER: " + ex);
        }
        return true;
    }
    
    // @todo noch ne methode fuer loadFile(File)
    public Preference()
    {
    }
    
    /**
     * Creates a new instance of Preference 
     */
    public Preference(String f) 
    {
        fileName = "file:" + new File(f).getAbsolutePath();
        load();
    }
    
}
