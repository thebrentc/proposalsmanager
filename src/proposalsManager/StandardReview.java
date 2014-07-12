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
 * A review super-type providing for general commenting or querying but no decision
 */
public abstract class StandardReview extends Review {
    public StandardReview() {
        super();
        reviewComponents.add(new ReviewQuery());
    }
    
    @Override
    public ReviewStatus recommendReviewStatus(ProposalReviewData proposalReviewData) {
        ReviewStatus recommendation = null;
                
        try {
            ProposalReviewComponent reviewQuery = (ProposalReviewComponent) proposalReviewData.find(ReviewQuery.class);            
            
            if (!reviewQuery.find("query").equals("")) {
                recommendation = new ReviewStatus("Queried");
            }
            else {
                recommendation = new ReviewStatus("Completed");  
            }
        }
        catch (ComponentNotFoundException e) { } 
        return recommendation;
    }            
    
}
