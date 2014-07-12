package proposalsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* A review applicable to a proposal */
public class ProposalApplicableReview implements Comparable<ProposalApplicableReview> {

    static private Integer counter = 0; // used to compute id's    
    private Integer id;
    //private Proposal proposal; //optional
    private Review review;
    private double weight;
    private boolean allowed; 
    private ReviewStatus reviewStatus;
    
    public Integer getId() { return id; }
    public boolean isAllowed() { return allowed; }    
    public ReviewStatus getReviewStatus() { return reviewStatus; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }
    public void setReviewStatus(ReviewStatus reviewStatus) { this.reviewStatus = reviewStatus; }    
    public Review getReview() { return review; }
    public double getWeight() { return weight; }
    public boolean isRequired() { return weight > 0.0; }        
    
    public ProposalApplicableReview(Review review, double weight) 
    {        
        // @post ProposalApplicableReviews linked to a Review of specified type        
        this.review = review; 
        // @post the weightings and allowed status of the linked ProposalApplicableReview instances are appropriately initialised.
        // @post (createProposalApplicableReviews) with the specified weighting
        this.weight = weight;        
        this.allowed = (!isRequired())?true:false;        
        this.reviewStatus = (this.weight>0)?new ReviewStatus("Pending"):new ReviewStatus("");
        this.id = counter;
        counter++;
    }    
    
    public int compareTo(ProposalApplicableReview proposalApplicableReview) {
        return (this.weight < proposalApplicableReview.weight)?-1:(this.weight > proposalApplicableReview.weight)?+1:0;
    }
        
    public boolean isDone() 
    {
        return (
                reviewStatus.toString() == "Completed" 
                ||
                reviewStatus.toString() == "Approved" 
                ||
                reviewStatus.toString() == "Rejected" 
                );
    }
    
    public boolean isSuccessful() 
    {
        return (
                reviewStatus.toString() == "Completed" 
                ||
                reviewStatus.toString() == "Approved" 
                );
    }    
    
    public boolean isOutstanding() 
    {       
        return isRequired() && !isDone();
    }
    
    public String toString() {
        return /*id + ". " +*/ weight + " " + review +  (!this.reviewStatus.toString().equals("")?(" [" + this.reviewStatus.toString() + "] "):"") + (this.isAllowed()?"..":"x");    
    }    
}
