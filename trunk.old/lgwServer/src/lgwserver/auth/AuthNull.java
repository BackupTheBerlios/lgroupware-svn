/*
 * AuthNull.java
 *
 * Created on 7. Juni 2005, 18:02
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver.auth;

/**
 *
 * @author jfried
 */
public class AuthNull extends Auth
{
    
    /** Creates a new instance of AuthNull */
    public AuthNull() 
    {
    }
    
    public void initialize()
    {
        System.out.println("AuthNull!!");
        System.out.println("Jeder hat alle Rechte!!!");
    }
    
    
    public boolean authenticate(String username, String password)
    {
        return true;
    }
}
