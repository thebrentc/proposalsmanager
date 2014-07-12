package proposalsManager;

import java.util.ArrayList;
import java.util.Iterator;

/* Composite pattern composite for review components */
public class ReviewComponents extends ReviewComponent {
    
    protected ArrayList reviewComponents = new ArrayList();
    
    public ReviewComponents() {
        ref = this.getClass();
    }
        
    public void add(ReviewComponent reviewComponent) {
        reviewComponents.add(reviewComponent);
    }
    public void remove(ReviewComponent reviewComponent) {
        reviewComponents.remove(reviewComponent);
    }
    public ReviewComponent getChild(int i) {
        return (ReviewComponent)reviewComponents.get(i);
    }
    
    public Iterator createIterator() {
        return reviewComponents.iterator();
    }
        
    public Object find(Object ref) throws ComponentNotFoundException {
        
        if (this.ref.toString().equals(ref.toString())) return this;
                        
        Iterator iterator = reviewComponents.iterator();
        while (iterator.hasNext()) {
            ReviewComponent reviewComponent = (ReviewComponent) iterator.next();
            try {
                Object result = reviewComponent.find(ref);
                return result;
            } catch (ComponentNotFoundException e) { }
        }
        throw new ComponentNotFoundException();
    }
    
    public boolean has(Object ref) {        
        try {
            find(ref);            
        } catch (ComponentNotFoundException e) { 
            return false;        
        }        
        return true;
    }    
    
    public void print() {
        System.out.println(this.ref);
        Iterator iterator = reviewComponents.iterator();
        while (iterator.hasNext()) {
            ReviewComponent reviewComponent = (ReviewComponent)iterator.next();
            reviewComponent.print();
        }
    }    
    
    public boolean isRequired() {
        return required;
    }
 
    // check types of attributes submitted
    public boolean check(ProposalReviewData proposalReviewData) {
       
        Iterator iterator = this.reviewComponents.iterator();        
        while (iterator.hasNext()) {
            ReviewComponent reviewComponent = (ReviewComponent)iterator.next();
            try {
                if (proposalReviewData.has(reviewComponent.ref)) {
                    Object submission = proposalReviewData.find(reviewComponent.ref);                                       
                    if (submission != null) { // allow nulls                        
                        Class submissionType = submission.getClass();
                        Class expectedType = (Class) reviewComponent.find((String) reviewComponent.ref );
                        if (submissionType != expectedType) {
                            throw new Exception();
                        }
                    }
                }
            } catch (Exception exception) { 
                String message = reviewComponent.ref + " is invalid type.";
                System.out.println(message);    
                try { ProposalsManager.flash.add(message); } catch (Exception e) { } 
                return false;
            }
            
        }
        // else
        return true;
    }    
        
}
