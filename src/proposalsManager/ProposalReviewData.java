package proposalsManager;

import java.util.ArrayList;
import java.util.Iterator;

/* ProposalReviewData composite pattern composite */
public class ProposalReviewData extends ProposalReviewComponent { 
    
    private ArrayList proposalReviewComponents = new ArrayList();    
    
    public ProposalReviewData() {
        ref = this.getClass(); 
    }
    
    public ProposalReviewData(Object ref) {
        this.ref = ref;
    }    
        
    public void add(ProposalReviewComponent proposalReviewComponent) {
        proposalReviewComponents.add(proposalReviewComponent);
    }
       
    public void remove(ProposalReviewComponent proposalReviewComponent) {
        proposalReviewComponents.remove(proposalReviewComponent);
    }
    public ProposalReviewComponent getChild(int i) {
        return (ProposalReviewComponent)proposalReviewComponents.get(i);
    }
    
    public Iterator createIterator() {
        return proposalReviewComponents.iterator();
    }
        
    public Object find(Object ref) throws ComponentNotFoundException 
    {
        if (this.ref.toString().equals(ref.toString())) { return this; }
                        
        Iterator iterator = proposalReviewComponents.iterator();
        while (iterator.hasNext()) {
            ProposalReviewComponent proposalReviewComponent = (ProposalReviewComponent)iterator.next();
            try {
                Object result = proposalReviewComponent.find(ref);
                return result;
            } catch (ComponentNotFoundException e) { }
        }
        // else
        throw new ComponentNotFoundException(); // as return false
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
        Iterator iterator = proposalReviewComponents.iterator();
        while (iterator.hasNext()) {
            ProposalReviewComponent proposalReviewComponent = (ProposalReviewComponent)iterator.next();
            proposalReviewComponent.print();
        }
    }    

}
