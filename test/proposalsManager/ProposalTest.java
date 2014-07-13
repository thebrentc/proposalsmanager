package proposalsManager;

import java.util.Date;
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
        reviewGeneralInfo.add(new ProposalReviewComponentItem("comments","Test QA review"));
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
     * Test some invalid review submissions
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
     * Specifically test submitReviewwith failed user check conditions
     */
    // TODO
    
    
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
     * Workout of updateProposalApplicableReviewDependencies, checking proposalApplicableReviews allowed statuses
     */
    @Test
    public void testUpdateProposalApplicableReviewDependencies() {
        System.out.println("testSubmitReviewAllStatusesUpdate");
        setUpBusinessReview();
    }        
    
}
