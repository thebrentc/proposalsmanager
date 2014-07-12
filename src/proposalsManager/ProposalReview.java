package proposalsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* A proposal review submission */
public class ProposalReview  {
    
    static private Integer counter = 0; // used to compute id's    
    private Integer id;    
    //private Map <Integer, ProposalApplicableReview> proposalApplicableReview =  new HashMap<>(); // optional
    private ProposalReviewData proposalReviewData = new ProposalReviewData();
    
    // @post creates and returns an instance of a ProposalReview submission     
    public ProposalReview(ProposalApplicableReview proposalApplicableReview, ProposalReviewData proposalReviewData) //throws PropProposalReviewData1eption 
    {        
        // compute and add metadata        
        UserId reviewer = UserManager.getCurrentUserId();  // -> "User"
        Date reviewed = new Date();                
        ProposalReviewData reviewMetaData = new ProposalReviewData(ReviewMetaData.class);
        reviewMetaData.add(new ProposalReviewComponentItem("reviewer",reviewer));
        reviewMetaData.add(new ProposalReviewComponentItem("reviewed",reviewed));        
        proposalReviewData.add(reviewMetaData);                
        
        //@post with validated proposalReviewData information ..
        ProposalReviewChecker proposalReviewChecker = new ProposalReviewChecker();
        if (!proposalReviewChecker.check(proposalApplicableReview, proposalReviewData)) { 
            System.out.println("ProposalReviewCheckException");
            // throw new ProposalReviewCheckException(); // @post or an exception is raised       
        }                
        
        this.proposalReviewData = proposalReviewData;        
        // generate and add id
        this.id = counter;
        counter++;        
    }            
}
