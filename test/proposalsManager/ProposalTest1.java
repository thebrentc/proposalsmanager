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
 * @author zhzz3282
 */
public class ProposalTest1 {
    
    public ProposalTest1() {
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
     * Test of createProposalRequiredReviews method, of class Proposal.
     */
    @Test
    public void testCreateProposalRequiredReviews() {
        System.out.println("createProposalRequiredReviews");
        Proposal instance = null;
        instance.createProposalRequiredReviews();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createAdminInformationRecord method, of class Proposal.
     */
    @Test
    public void testCreateAdminInformationRecord() {
        System.out.println("createAdminInformationRecord");
        Proposal instance = null;
        instance.createAdminInformationRecord();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of submitReview method, of class Proposal.
     */
    @Test
    public void testSubmitReview() {
        System.out.println("submitReview");
        ProposalApplicableReview proposalRequiredReview = null;
        String comments = "";
        String query = "";
        UserId delegator = null;
        Decision decision = null;
        Proposal instance = null;
        instance.submitReview(proposalRequiredReview, comments, query, delegator, decision);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Proposal.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Proposal instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
}
