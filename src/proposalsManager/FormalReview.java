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
        
    /*
    public boolean check(ProposalReviewData proposalReviewData)
    {
        boolean checks = true;
        if (!super.check(proposalReviewData)) { checks = false; }                
        
        // additional checks for formal review 
        // should have either query or decision, not both        
        System.out.println("FormalReview::check()");        
        
        // get relevant submission items
        Map<String, Object> reviewGeneralInfo = (Map) proposalReviewData.get(ReviewGeneralInfo.class);
        Map<String, Object> reviewDecision = (Map) proposalReviewData.get(ReviewDecision.class);
        Decision decision = null;
        String query = "";
        if (reviewGeneralInfo.containsKey("query")) query = (String) reviewGeneralInfo.get("query");
        if (reviewDecision.containsKey("decision")) decision = (Decision) reviewGeneralInfo.get("decision");
        // and check
        if (decision != null && !query.equals("")) {
            String message = "A formal review can have a query or a decision, but not both.";
            System.out.println(message);    
            try { ProposalsManager.flash.add(message); } catch (Exception e) { }
            checks = false;
        }
        return checks;
    }    
*/
}
