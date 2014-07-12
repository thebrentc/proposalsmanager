package proposalsManager;

/* Checking for ProposalReview */
class ProposalReviewChecker {
    
    public boolean check(ProposalApplicableReview proposalApplicableReview, ProposalReviewData proposalReviewData) {        
                
        // pass to review to run checks, which will check components match up, review contacts are ok, 
        // and will also call each review component to run checks
        // review subtypes add additional checks
        Review review = proposalApplicableReview.getReview();
        return review.check(proposalReviewData);
        
        // add additional checks if needed 
        
    }        
}
