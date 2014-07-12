package proposalsManager;

/* Checking for ProposalReview */
class ProposalReviewChecker {
    
    public boolean check(ProposalApplicableReview proposalApplicableReview, ProposalReviewData proposalReviewData) {        
                
        // @post (Proposal.submitReview) with validated information in proposalReviewData, according to the linked Review template
        // pass to review to run checks, which will check components match up, review contacts are ok, 
        // and call each review component to run basic validation checks
        // review subtypes and components can add additional checks
        Review review = proposalApplicableReview.getReview();
        return review.check(proposalReviewData);
        
        // add additional checks if needed 
        
    }        
}
