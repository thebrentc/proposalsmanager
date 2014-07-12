package proposalsManager;

/* An ApplicableReview record */
public class ApplicableReview {

    private ProposalType proposalType;
    private Class reviewType;
    private double weight;

    public ProposalType getProposalType() {
        return proposalType;
    }

    public Class getReviewType() {
        return reviewType;
    }

    public double getWeight() {
        return weight;
    }    

    public ApplicableReview(ProposalType proposalType, Class reviewType, double weight) {        
        this.proposalType = proposalType;
        this.reviewType = reviewType;
        this.weight = weight;
    }
    
    public String toString() {
        return proposalType.toString() + " : " + reviewType + "(" + weight + ")";    
    }
}
