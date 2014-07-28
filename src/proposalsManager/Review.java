package proposalsManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* Super-type review with defaults */
abstract class Review {
    
    protected ReviewComponents reviewComponents = new ReviewComponents(); 
    protected Map<Integer, UserId> reviewContacts = new HashMap<Integer, UserId>();
    
    public Review() {        
        reviewComponents.add(new ReviewMetaData());
        reviewComponents.add(new ReviewGeneralInfo());
    }
    
    // @pre user and delegator checks
    // @post validated information in proposalReviewData
    public boolean check(ProposalReviewData proposalReviewData)
    {           
        return (
                validateRequiredReviewData(proposalReviewData)
                && 
                validateProposalReviewData(proposalReviewData)
                && 
                userChecks(proposalReviewData)
                );
        
        // add any checks specific to review type in sub classes 
    }
    
    protected boolean validateRequiredReviewData(ProposalReviewData proposalReviewData)
    {
        Iterator iterator = reviewComponents.createIterator();
        while (iterator.hasNext()) {
            ReviewComponent reviewComponent = (ReviewComponent) iterator.next();
            
            if (reviewComponent.isRequired()){
                // check reviewComponent has corresponding submission in proposalReviewData
                if (!proposalReviewData.has(reviewComponent.ref)) {
                    String message = this + " should have " + reviewComponent.ref;
                    System.out.println(message);    
                    try { ProposalsManager.getFlash().add(message); } catch (Exception e) { }
                    return false;
                }
            }
        }
        // else
        return true;
    }
    
    
    protected boolean validateProposalReviewData(ProposalReviewData proposalReviewData)
    {
        Iterator iterator = reviewComponents.createIterator();
        while (iterator.hasNext()) {
            ReviewComponent reviewComponent = (ReviewComponent)iterator.next();
            try {
                // call on component to do basic component checks (data types)
                if (!reviewComponent.check((ProposalReviewData) proposalReviewData.find(reviewComponent.ref))) {
                    return false;
                }
            }
            catch (ComponentNotFoundException e) {
               return false;
            }
        }           
        // else
        return true;
    }
    
    protected boolean userChecks(ProposalReviewData proposalReviewData)
    {
        // @pre user or delegator (in proposalReviewData) is a specified ReviewContact user, 
        // and user is a delegate of delegator if relevant 
        // if ReviewContacts specified
        if (!reviewContacts.isEmpty()) {
            try {
                boolean usercheck = false;
                UserId reviewer = (UserId) proposalReviewData.find("reviewer");
                UserId delegator = (UserId) proposalReviewData.find("delegator");
                for (Map.Entry entry : reviewContacts.entrySet()) 
                {
                    UserId reviewContact = (UserId) entry.getValue();
                    if (reviewer.equals(reviewContact)
                        || 
                        (delegator != null && delegator.equals(reviewContact) && reviewer.isDelegateFor(delegator))
                        ) {
                        usercheck = true; break;
                    }                
                }
                if (!usercheck){
                    String message = "Reviewer or delegate don't correspond or aren't designated review contacts."; 
                    System.out.println(message);    
                    try { ProposalsManager.getFlash().add(message); } catch (Exception e) { }                
                    return false;
                }
            } catch (ComponentNotFoundException e) { 
                System.out.println(e); 
                return false;
            }
        }
        return true;
    }
        
    public ReviewStatus recommendReviewStatus(ProposalReviewData proposalReviewData) {
        return new ReviewStatus("Completed");
    }
    
    private String prettify(String string) {
        return string.substring(string.indexOf('.')+1);
    }        
    
    public String toString() {        
        return prettify(this.getClass().toString());        
    }
    
}
