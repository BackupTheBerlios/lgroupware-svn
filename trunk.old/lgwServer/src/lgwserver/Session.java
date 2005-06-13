/*
 * Session.java
 *
 * Created on 11. Juni 2005, 11:58
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
import java.security.MessageDigest;

/**
 *
 * @author jfried
 */
public class Session 
{
    protected Map<Object, Object> info;
    
    /** Creates a new instance of Session */
    public Session()
    {
        info = new HashMap<Object, Object>();
        set("session_id", createMagic());
    }
    
    /** Einen wert aus er Session auslesen
     * @param String key Schluessel des Werts
     */
    public Object get(String key)
    {
        return info.get(key);
    }
    
    /** Einen wert in die Session schreiben
     * @param String key Schluessel
     * @param Object value Der wert als objekt
     */
    public void set(String key, Object value)
    {
        info.put(key, value);
    }
    
    protected String createMagic()
    {
        Random r = new Random();
        Calendar c = Calendar.getInstance();
        long l = c.getTimeInMillis();
        String alles;
        StringBuffer sb = new StringBuffer();
        
        alles = r.nextDouble() + ":" + r.nextDouble() + ":" + l;
        
        try
        {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte digest[] = md.digest(alles.getBytes());
            for(int i=0; i < digest.length; i++)
                sb.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
        } catch(NoSuchAlgorithmException ealg)
        {
            System.out.println("Fehler in session: " + ealg);
        }
        
        return sb.toString();
    }
}
