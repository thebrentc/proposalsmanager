package proposalsManager;

import java.util.Iterator;

/* Composite pattern component item for ProposalReviewData */
public class ProposalReviewComponentItem extends ProposalReviewComponent {
    
    private Object object; // the value
    
    public ProposalReviewComponentItem(Object ref, Object object) {
        this.ref = ref;
        this.object = object;
    }
    
    public Iterator createIterator() {
        return null;
    }
    
    public Object find(Object ref) throws ComponentNotFoundException 
    {
        if (this.ref.toString().equals(ref.toString()))             
        {
            return object; // for a leaf item, return the value
        }
        else {
            throw new ComponentNotFoundException();
        }
    }
    
    public void print() {
        System.out.println(ref + ": " + object);
    }
    
}
