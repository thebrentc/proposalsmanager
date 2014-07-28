package proposalsManager;

public class BusinessPlanReview extends StandardReview {
    public BusinessPlanReview() {
        super();
        this.reviewContacts.put(reviewContacts.size(), new UserId("user")); // test
    }
    
    public boolean check(ProposalReviewData proposalReviewData)
    {
        // no additional checks needed for business review (allows blank comments, tbc)        
        return super.check(proposalReviewData);
    }
    
}
