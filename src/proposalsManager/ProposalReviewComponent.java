package proposalsManager;

import java.util.Iterator;

/* Composite pattern interface for ProposalReviewData */
public abstract class ProposalReviewComponent  {      
    
    protected Object ref; // allow identifiers, class titles often used
        
    public void add(ProposalReviewComponent proposalReviewComponent) {
        throw new UnsupportedOperationException();
    }
    public void remove(ProposalReviewComponent proposalReviewComponent) {
        throw new UnsupportedOperationException();
    }
    public ProposalReviewComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean has(Object ref) {
       throw new UnsupportedOperationException();
    }    
    
    public Object find(Object ref) throws ComponentNotFoundException 
    {
        throw new UnsupportedOperationException();
    }    
    
    public abstract Iterator createIterator();        
        
    public void print() {
        throw new UnsupportedOperationException();
    }    
        
}
