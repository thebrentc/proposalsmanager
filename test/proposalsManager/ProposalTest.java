package proposalsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/* Test Proposal class and core system operations */
public class ProposalTest {
      
    Proposal proposalInstance; // a default Proposal instance fixture from setUp()
    
    public ProposalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("setUp");
        // a Proposal
        ProposalsManager.createProposal(new ProposalType("NewModule"), new ProspectusCode("CODE"), new UserId("manager"));
        proposalInstance = ProposalsManager.getProposals().get(0);
        // tests will select from shortcut operations below
    }
    
    // some reusable fixture setUp operations to submit different reviews
    private void setUpGeneralReview() {
        ProposalReviewData proposalReviewData = new ProposalReviewData();
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Generic review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null)); 
        proposalReviewData.add(reviewGeneralInfo);        
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(0), proposalReviewData);        
    }
        
    private void setUpBusinessReview() { 
        ProposalReviewData proposalReviewData = new ProposalReviewData();   
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);        
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test business review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",new UserId("user"))); // todo
        proposalReviewData.add(reviewGeneralInfo);
        ProposalReviewData reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query",""));        
        proposalReviewData.add(reviewQuery);
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(1), proposalReviewData);
    }
    
    private void setUpBusinessReviewWithQuery() { 
        ProposalReviewData proposalReviewData = new ProposalReviewData();   
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);        
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test business review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        ProposalReviewData reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query","I query..."));        
        proposalReviewData.add(reviewQuery);
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(1), proposalReviewData);
    }    
        
    private void setUpQAReviewWithApproval() {    
        ProposalReviewData proposalReviewData = new ProposalReviewData();   
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);        
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test QA review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        ProposalReviewData reviewQuery = new ProposalReviewData(ReviewQuery.class);        
        reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query",""));
        proposalReviewData.add(reviewQuery);
        ProposalReviewData reviewDecision = new ProposalReviewData(ReviewDecision.class);
        reviewDecision.add(new ProposalReviewComponentItem("decision",new Decision(Decision.Literal.Approved)));
        proposalReviewData.add(reviewDecision);
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(2), proposalReviewData);                
    }    
    
    private void setUpInvalidFormalReviewWithoutDecisionComponent() {    
        ProposalReviewData proposalReviewData = new ProposalReviewData();   
        ProposalReviewData reviewGeneralInfo = new ProposalReviewData(ReviewGeneralInfo.class);        
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test Invalid QA review"));
        reviewGeneralInfo.add(new ProposalReviewComponentItem("delegator",null));
        proposalReviewData.add(reviewGeneralInfo);
        ProposalReviewData reviewQuery = new ProposalReviewData(ReviewQuery.class);        
        reviewQuery = new ProposalReviewData(ReviewQuery.class);
        reviewQuery.add(new ProposalReviewComponentItem("query",""));
        proposalReviewData.add(reviewQuery);
        //ProposalReviewData reviewDecision = new ProposalReviewData(ReviewDecision.class);
        //reviewDecision.add(new ProposalReviewComponentItem("decision",new Decision(Decision.Literal.Approved)));
        //proposalReviewData.add(reviewDecision);
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(2), proposalReviewData);                
    }        
    
    private void setUpCommitteeReview() {    
        // ...
    }    
    
    @After
    public void tearDown() {
        System.out.println("tearDown");
        // reset, removing Proposal and any submitted reviews after each test
        proposalInstance = null;
        ProposalsManager.getProposals().clear();
        Proposal.resetCounter();           
    }        

    /**
     * Test of submitReview method of class Proposal, base data and linkage creation
     */
    @Test
    public void testSubmitReview() throws ComponentNotFoundException {
        System.out.println("submitReview");
        setUpBusinessReview();        
        // check data
        ProposalReview instance = proposalInstance.getLastProposalReview();
        ProposalReviewData proposalReviewData = instance.getProposalReviewData();        
        try {                            
            assertEquals(proposalReviewData.find("reviewer"),UserManager.getCurrentUserId());
            assert(proposalReviewData.find("reviewed") instanceof Date);
            assertEquals(proposalReviewData.find("comments"),"Test business review");
            assertEquals(proposalReviewData.find("delegator").toString(),"user");
        } catch (ComponentNotFoundException e) { throw e; }
        // check linkage (in Proposal proposalApplicationReviewSubmissions)
        ProposalApplicableReview linkedInstance =  proposalInstance.getProposalApplicableReviewFor(instance);
        assert(proposalInstance.getProposalApplicationReviewSubmissions().containsKey(linkedInstance));
        assert(proposalInstance.getProposalApplicationReviewSubmissions().get(linkedInstance).contains(instance));
    }
    
    /**
     * Test submitReview, user check conditions
     */
    public void testSubmitReviewUserChecks() throws ComponentNotFoundException {
        System.out.println("testSubmitReviewUserChecks");
        setUpBusinessReview(); 
        ProposalReview instance = proposalInstance.getLastProposalReview();        
        ProposalReviewData proposalReviewData = instance.getProposalReviewData();
        // recheck with Review userCheck() function 
        ProposalApplicableReview linkedInstance = proposalInstance.getProposalApplicableReviewFor(instance);        
        Review review = linkedInstance.getReview();
        assert(!review.reviewContacts.isEmpty()); // (this review type should specific ReviewContacts for testing)
        assertEquals(review.userChecks(proposalReviewData),true);
    }        
    
    /**
     * Test of submitReview method of class Proposal, linked ProposalApplicableReview status update
     */
    @Test
    public void testSubmitReviewStatusUpdate() {
        System.out.println("testSubmitReviewStatusUpdate");
        setUpBusinessReview();        
        ProposalApplicableReview linkedInstance = proposalInstance.getProposalApplicableReviewFor(proposalInstance.getLastProposalReview());
        assertEquals(linkedInstance.getReviewStatus().toString(),"Completed");
    }    
        
    /**
     * Test submitReview with some generally invalid review submissions
     */
    @Test
    public void testSubmitInvalidReview() {
        System.out.println("testSubmitInvalidReview");                
        proposalInstance.submitReview(null, null);
        assert(proposalInstance.getProposalReviewsCount() == 0);
        proposalInstance.submitReview(proposalInstance.getProposalApplicableReviews().get(0), new ProposalReviewData(/* empty */));        
        assert(proposalInstance.getProposalReviewsCount() == 0);                
    }        
    
    /**
     * Test submitReview with invalid user details
     * 
     */
    @Test
    public void testSubmitReviewWithInvalidUsers() {
        UserManager.overrideCurrentUserId(new UserId("user3")); // not in review contacts 
        setUpBusinessReviewWithQuery(); // should not complete
        assert(proposalInstance.getProposalReviewsCount() == 0);                
        UserManager.cancelOverrideCurrentUserId();
    }            
    
    /**
     * Test of StandardReview, with query
     */
    @Test
    public void testSubmitStandardReview() throws ComponentNotFoundException {
        System.out.println("testSubmitStandardReview");
        setUpBusinessReviewWithQuery(); 
        ProposalReview instance = proposalInstance.getLastProposalReview();        
        ProposalReviewData proposalReviewData = instance.getProposalReviewData();                
        try {
            assert(proposalReviewData.has(ReviewQuery.class));
            assertEquals(proposalReviewData.find("query").toString(),"I query...");
        } catch (ComponentNotFoundException e) { throw e; }   
        // include status update check
        ProposalApplicableReview linkedInstance = proposalInstance.getProposalApplicableReviewFor(instance);
        assertEquals(linkedInstance.getReviewStatus().toString(),"Queried");                
    }   
    
        
    /**
     * Test of FormalReview, with approval
     */
    @Test
    public void testSubmitFormalReview() throws ComponentNotFoundException {
        System.out.println("testSubmitFormalReview");
        setUpBusinessReview(); // XXX need to first complete prior required review(s)
        setUpQAReviewWithApproval();    
        ProposalReview instance = proposalInstance.getLastProposalReview(); 
        ProposalReviewData proposalReviewData = instance.getProposalReviewData();                
        try {
            assert(proposalReviewData.has(ReviewDecision.class));
            assertEquals(proposalReviewData.find("decision").toString(),"Approved");
        } catch (ComponentNotFoundException e) { throw e; }   
        // include status update check
        ProposalApplicableReview linkedInstance = proposalInstance.getProposalApplicableReviewFor(instance);
        assertEquals(linkedInstance.getReviewStatus().toString(),"Approved");                
    }            
    
    /**
     * Test invalid formal review 
     */
    @Test
    public void testSubmitInvalidFormalReview() throws ComponentNotFoundException {
        System.out.println("testSubmitInvalidFormalReview");        
        setUpBusinessReview(); 
        Integer reviewCount = proposalInstance.getProposalReviewsCount();
        setUpInvalidFormalReviewWithoutDecisionComponent(); // shouldn't create a ProposalReview
        assert(proposalInstance.getProposalReviewsCount() == reviewCount);
    }        
    
    /**
     * Helper - check current proposalApplicableReviewDependencies are all ok 
     * (business rule dependent -ref Proposal.UpdateProposalApplicableReviewDependencies)
     */    
    private boolean checkUpdateProposalApplicableReviewDependencies(Proposal proposal) {

        // get proposalApplicableReviews sorted by weight
        List<ProposalApplicableReview> applicableReviews = new ArrayList<>(proposal.getProposalApplicableReviews().values());
        Collections.sort(applicableReviews);

        for (int i = applicableReviews.size()-1; i > 0; i--) {
            Boolean expected = applicableReviews.get(i).isOutstanding()?true:false;
            for (int j = i-1; j > 0; j--) {
                if (applicableReviews.get(j).isRequired()
                    && applicableReviews.get(j).isOutstanding()
                    && applicableReviews.get(j).getWeight() < applicableReviews.get(i).getWeight()
                    ) {
                    expected = false; break;
                }
            }
            if (applicableReviews.get(i).isAllowed() != expected) return false;
        }          
        // else
        return true;
    }
    
    
    /**
     * Workout of updateProposalApplicableReviewDependencies, checking proposalApplicableReviews allowed statuses at each step
     */
    @Test
    public void testUpdateProposalApplicableReviewDependencies() {
        System.out.println("testUpdateProposalApplicableReviewDependencies");
        setUpGeneralReview(); 
        assertEquals(checkUpdateProposalApplicableReviewDependencies(proposalInstance),true);
        setUpBusinessReview();
        assertEquals(checkUpdateProposalApplicableReviewDependencies(proposalInstance),true);
        setUpQAReviewWithApproval();
        assertEquals(checkUpdateProposalApplicableReviewDependencies(proposalInstance),true);
        // ...
    }        
    
}
