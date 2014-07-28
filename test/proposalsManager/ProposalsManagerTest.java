package proposalsManager;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProposalsManagerTest {
    
    public ProposalsManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // fixture for happy path tests
        ProposalType proposalType = new ProposalType("NewModule");
        ProspectusCode code = new ProspectusCode("CODE");
        UserId delegator = new UserId("manager");
        ProposalsManager.createProposal(proposalType, code, delegator);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Base test of createProposal method, of class ProposalsManager.
     */
    @Test
    public void testCreateProposal() {                
        System.out.println("testCreateProposal");        
        // setUpClass()
        Proposal instance = ProposalsManager.getProposals().get(0);
        assertEquals(instance.getProposer(),UserManager.getCurrentUserId()); 
        assert(instance.getProposed() instanceof Date);         
        assertEquals(instance.getProposalType().toString(), "NewModule");
        assertEquals(instance.getProspectusCode().toString(), "CODE");
        assertEquals(instance.getDelegator().toString(),"manager");
        // also test AdminInformation here
        assert(instance.getAdminInformation() instanceof AdminInformation);
        // and proposal status initiation
        assertEquals(instance.getProposalStatus(),ProposalStatus.CREATED);                
    }
    
    /**
     * TODO? test creation of different ProposalType's
     */                
    
    /**
     * precondition that user or delegator is Faculty academic staff 
     */        
    @Test
    public void testCreateProposalUserChecks() {
        System.out.println("testCreateProposalUserChecks");
        // setUpClass()     
        Proposal instance = ProposalsManager.getProposals().get(0);        
        assert(UserManager.isFacultyAcademicStaff(instance.getProposer()) | UserManager.isFacultyAcademicStaff(instance.getDelegator()));
    }        
    
    /**
     * TODO? test precondition of user or delegator is Faculty academic staff not satisfied?
     */            
    
    /**
     * test postcondition the linked ProposalApplicableReviews collection is created 
     */        
    @Test
    public void testCreateProposalApplicableReviewsCreated() {
        System.out.println("testCreateProposalApplicableReviewsCreated");
        // setUpClass()     
        Proposal instance = ProposalsManager.getProposals().get(0);                
        assert(instance.getProposalApplicableReviews().size() > 0);
    }            
    
    /**
     * test postcondition the linked ProposalApplicableReviews collection is initialised as expected (business rule dependent)
     * statuses should be <blank> or "Pending"
     * all optional ProposalApplicableReviews and the first level of required ProposalApplicableReviews should be allowed      
     */        
    @Test
    public void testCreateProposalApplicableReviewsInitialised() {
        System.out.println("testCreateProposalApplicableReviewsInitialised");
        // setUpClass()     
        Proposal instance = ProposalsManager.getProposals().get(0);  
        Collection<ProposalApplicableReview> instances = instance.getProposalApplicableReviews().values();
        
        // get first level of required ProposalApplicableReviews
        Double firstlevel = null;
        for (ProposalApplicableReview item : instances) { 
            if (firstlevel == null) if (item.isRequired()) { firstlevel = item.getWeight(); break; }
        }
        
        // traverse and check
        for (ProposalApplicableReview item : instances) { 
            // initial statuses
            if (item.isRequired()) assert(item.getReviewStatus().toString().equals("Pending"));
            else if (!item.isRequired()) assert(item.getReviewStatus().toString().equals(""));
            // allowed flags
            if (!item.isRequired() || (item.isRequired() && item.getWeight() == firstlevel))
                assertEquals(item.isAllowed(),true);
            else
                assertEquals(item.isAllowed(),false);            
        }
    }                
    
     /**
     * test validations/checks successfully reject invalid data
     * TODO? differentiated tests for each argument
     */        
    @Test
    public void testCreateProposalChecks() {    
        System.out.println("testCreateProposalChecks");        
       Integer nextId = ProposalsManager.getProposals().size();
       // the following should fail checking and not create a proposal
       ProposalsManager.createProposal(null, null, null);
       assert(!ProposalsManager.getProposals().containsKey(nextId));
    }
}
