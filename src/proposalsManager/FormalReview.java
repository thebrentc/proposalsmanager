package proposalsManager;

/* A review super-type also providing decision functionality */
public abstract class FormalReview extends StandardReview {
    public FormalReview() {
        super();
        reviewComponents.add(new ReviewDecision());
    }
    
    @Override    
    public ReviewStatus recommendReviewStatus(ProposalReviewData proposalReviewData) {
        
        ReviewStatus recommendation = new ReviewStatus("Reviewing"); // default?
                       
        try {
            ProposalReviewComponent reviewQuery = (ProposalReviewComponent) proposalReviewData.find(ReviewQuery.class);            
            
            if (!reviewQuery.find("query").equals("")) {
                recommendation = new ReviewStatus("Queried");
            }
        }
        catch (ComponentNotFoundException e) { System.out.println(e); }         

        if (!recommendation.toString().equals("Queried")) {            
            try {
                ProposalReviewComponent reviewDecision = (ProposalReviewComponent) proposalReviewData.find(ReviewDecision.class);  

                if (reviewDecision.find("decision").toString().equals("Approved")) {
                    recommendation = new ReviewStatus("Approved");
                }
                else if (reviewDecision.find("decision").toString().equals("Rejected")) {
                    recommendation = new ReviewStatus("Rejected");
                }
            }
            catch (ComponentNotFoundException e) { System.out.println(e); }
        }
        
        return recommendation;    
    }
}
