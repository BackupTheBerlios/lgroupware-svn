/*
 * ClientPool.java
 *
 * Created on 5. Juni 2005, 13:14
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lgwserver;

import java.util.*;

/**
 *
 * @author jfried
 */
public class ClientPool implements Iterator
{
    protected ArrayList clients;
    protected int index = 0;
    
    /** Creates a new instance of clientPool */
    public ClientPool()
    {
        clients = new ArrayList();
    }
    
    public void add(TcpServerSocket s)
    {
            clients.add(s);
    }
    
    public Object remove(int index)
    {
        return clients.remove(index);
    }
    
    public void remove(Object o)
    {
        clients.remove(o);
    }
    
    public int size()
    {
        return clients.size();
    }
    
    public int length()
    {
        return clients.size();
    }
    
    public void setData(ArrayList a)
    {
        clients = a;
        index = 0;
    }
    
    public boolean hasNext()
    {
        return (index < clients.size());
    }
    
    public Object next()
    {
        if(hasNext())
        {
            return clients.get(index++);
        }
        throw new IndexOutOfBoundsException("max: " + clients.size());
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
    
    public void rewind()
    {
        index = 0;
    }
}

