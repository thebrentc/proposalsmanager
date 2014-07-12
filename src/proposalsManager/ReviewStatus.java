package proposalsManager;

/**
 *
 * a typedef for the status of a proposal's applicable review
 */
public class ReviewStatus {
    
    private String reviewStatus;    
    
    public ReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
    
    public String toString()
    {
        return this.reviewStatus;
    }    
    
}
