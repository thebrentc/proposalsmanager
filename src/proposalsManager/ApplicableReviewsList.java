package proposalsManager;

import java.util.ArrayList;

/* Provides lookup list of applicable reviews for Proposal types */
public class ApplicableReviewsList {
           
    public static ApplicableReview[] applicableReviewsList = {                    
            new ApplicableReview(new ProposalType("NewProgramme"), GeneralReview.class, 0.0),
            new ApplicableReview(new ProposalType("ModifiedProgramme"), GeneralReview.class, 0.0),
            new ApplicableReview(new ProposalType("NewModule"), GeneralReview.class, 0.0),
            new ApplicableReview(new ProposalType("ModifiedModule"), GeneralReview.class, 0.0),            
            new ApplicableReview(new ProposalType("NewProgramme"), BusinessPlanReview.class, 1.0),
            new ApplicableReview(new ProposalType("ModifiedProgramme"), BusinessPlanReview.class, 1.0),
            new ApplicableReview(new ProposalType("NewModule"), BusinessPlanReview.class, 1.0),
            new ApplicableReview(new ProposalType("ModifiedModule"), BusinessPlanReview.class, 1.0),
            new ApplicableReview(new ProposalType("NewModule"), QAReview.class, 2.0),
            new ApplicableReview(new ProposalType("ModifiedModule"), QAReview.class, 2.0),        
            new ApplicableReview(new ProposalType("NewProgramme"), CommitteeReview.class, 3.0),
            new ApplicableReview(new ProposalType("ModifiedProgramme"), CommitteeReview.class, 3.0),
            new ApplicableReview(new ProposalType("NewModule"), CommitteeReview.class, 3.0),
            new ApplicableReview(new ProposalType("ModifiedModule"), CommitteeReview.class, 3.0)
    };
        
    // Creates the list of applicable reviews for a specific Proposal
    public static ArrayList<ApplicableReview> getApplicableReviews(ProposalType proposalType) 
    {
        ArrayList<ApplicableReview> applicableReviews = new ArrayList<ApplicableReview>(1);
        for (ApplicableReview r : applicableReviewsList) {
            if (r.getProposalType().toString().equals(proposalType.toString())) {
                applicableReviews.add(r);
            }
        }        
        return applicableReviews;
    }
    
    public static void showApplicableReviewList() 
    {
        for (int c = 0; c < applicableReviewsList.length; c++) {
            System.out.println(applicableReviewsList[c].toString());
        }        
    }
    
}
