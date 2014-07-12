package proposalsManager;

/* An optional review type providing for general commenting without querying facility  */
public class GeneralReview extends Review {
    public GeneralReview() {
        super();
    }
    
    @Override
    public ReviewStatus recommendReviewStatus(ProposalReviewData proposalReviewData) {
        return new ReviewStatus("");  // N/A
    }    
}
