/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proposalsManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brent
 */
public class ProposalsManagerIT {
    
    public ProposalsManagerIT() {
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
     * Test of main method, of class ProposalsManager.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ProposalsManager.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
