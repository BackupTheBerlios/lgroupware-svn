/*
 * CookieContainer.java
 *
 * Created on 11. Juni 2005, 14:37
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwclient;

import java.util.*;

/**
 *
 * @author jfried
 */
public class CookieContainer 
{
    protected Map<Object, Object> container;
    
    /** Creates a new instance of CookieContainer */
    public CookieContainer() 
    {
        container = new HashMap<Object, Object>();
    }
    
    /** Einen Cookie setzten
     * @param String key Name des cookies
     * @param String value Wert des Cookies
     */
    public void add(String key, String value)
    {
        container.put(key, value);
    }
    
    /** Einen Cookie auslesen
     * gibt dann den Wert des cookies als String zurück.
     * @param String key Name des Cookies
     */
    public String get(String key)
    {
        return (String)container.get(key);
    }
    
    public Map<Object, Object> getAllCookies()
    {
        return container;
    }
}
