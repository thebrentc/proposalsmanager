package proposalsManager;

import java.util.Iterator;

/* Composite pattern component item for review components */
public class ReviewComponentItem extends ReviewComponent {
    
    Object object;
    
    public ReviewComponentItem(Object ref, Object object) {
        this.ref = ref;
        this.object = object;
    }
    
    public Iterator createIterator() {
        return null;
    }
    
    public Object find(Object ref) throws ComponentNotFoundException {
        if (this.ref.toString().equals(ref.toString()))             
        {
            return object; 
        }
        else {
            throw new ComponentNotFoundException();
        }
    }
    
    public void print() {
        System.out.println(ref + ": " + object);
    }
    
}
