/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proposalsManager;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author brent
 */
public class ProposalsManagerTestDrive extends ProposalsManager {
    
    public static void main(String[] args) {
        
        ProposalsManager.main(args);     
        
        ProposalsManager.populateSampleProposals();
        ProposalsManager.proposalsManagerGUI.refreshProposalsList();        

        ProposalReviewData proposalReviewData = new ProposalReviewData();
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Generic review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        ProposalsManager.proposals.get(0).submitReview(ProposalsManager.proposals.get(0).getProposalApplicableReviews().get(0), proposalReviewData);        
        
        proposalReviewData = new ProposalReviewData();
        reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test business review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        ProposalReviewData reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query",""));        
        proposalReviewData.add(reviewQuery);
        ProposalsManager.proposals.get(0).submitReview(ProposalsManager.proposals.get(0).getProposalApplicableReviews().get(1), proposalReviewData);
        
        proposalReviewData = new ProposalReviewData();
        reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test QA review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query",""));
        proposalReviewData.add(reviewQuery);
        ProposalReviewData reviewDecision = new ProposalReviewData(ReviewDecision.class);
        reviewDecision.add(new ProposalReviewComponentItem("decision",new Decision(Decision.Literal.Approved)));
        proposalReviewData.add(reviewDecision);
        ProposalsManager.proposals.get(0).submitReview(ProposalsManager.proposals.get(0).getProposalApplicableReviews().get(2), proposalReviewData);        
        
        /*
        proposalReviewData = new ProposalReviewData();
        reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test QA review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query","Query"));
        proposalReviewData.add(reviewQuery);
        reviewDecision = new ProposalReviewData(ReviewDecision.class);
        reviewDecision.add(new ProposalReviewComponentItem("decision",null));
        proposalReviewData.add(reviewDecision);
        ProposalsManager.proposals.get(0).submitReview(ProposalsManager.proposals.get(0).getProposalApplicableReviews().get(3), proposalReviewData); 
        */
        
        // test with nulls ...
        // test with applicable reviews with same weights
        // check proposers
        // check reviewers        
        
    }    
    
}
