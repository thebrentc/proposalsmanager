package proposalsManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProposalsManagerTest {
    
    public ProposalsManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of createProposal method, of class ProposalsManager.
     */
    @Test
    public void testCreateProposal() {
        System.out.println("createProposal");
        ProposalType proposalType = null;
        ProspectusCode code = null;
        UserId delegator = null;
        ProposalsManager.createProposal(proposalType, code, delegator);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of populateSampleProposals method, of class ProposalsManager.
     */
    @Test
    public void testPopulateSampleProposals() {
        System.out.println("populateSampleProposals");
        ProposalsManager.populateSampleProposals();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}