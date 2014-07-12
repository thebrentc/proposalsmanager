package proposalsManager;

import java.util.Iterator;

/* Composite pattern interface for review components */
public abstract class ReviewComponent {
    
    protected Object ref;
    protected boolean required = true;    
    
    // pattern functions    
    public void add(ReviewComponent reviewComponent) {
        throw new UnsupportedOperationException();
    }
    public void remove(ReviewComponent reviewComponent) {
        throw new UnsupportedOperationException();
    }
    public ReviewComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean has(Object ref) {
       throw new UnsupportedOperationException();
    }    
    
    public Object find(Object ref) throws ComponentNotFoundException {
        throw new UnsupportedOperationException();
    }    
    
    public abstract Iterator createIterator();        
        
    public void print() {
        throw new UnsupportedOperationException();
    }    
        
    // specific functions
    public boolean isRequired() {
       throw new UnsupportedOperationException();
    }
    
    public boolean check(ProposalReviewData proposalReviewData) {
       throw new UnsupportedOperationException();
    }    
        
}
