/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proposalsManager;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author brent
 */
public class ProposalsManagerTest1 {
    
    public ProposalsManagerTest1() {
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
     * Test of main method, of class ProposalsManager
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ProposalsManager.main(args);
    }


    /**
     * Test of createProposal method, of class ProposalsManager.
     */
    @Test
    public void testCreateProposal1() throws Exception {
        System.out.println("createProposal1");
        
        //Proposal expected = new Proposal(proposalType, code, delegator);
        //assert(expected == ProposalsManager.proposals[0]); // -> fails
        
        /*
        ProposalType proposalType = new ProposalType("NewModule");
        Code code = new Code("CODE");
        UserId delegator = new UserId("Mgr1");
        ProposalsManager.createProposal(proposalType, code, delegator);        
        */
        ProposalsManager.populateSampleProposals();
        
        assertEquals(ProposalsManager.proposals.get(0).proposalType.toString(), "NewModule");
        assertEquals(ProposalsManager.proposals.get(0).code.toString(), "CODE");
        assertEquals(ProposalsManager.proposals.get(0).delegator.toString(), "Mgr1");
        assertEquals(ProposalsManager.proposals.get(0).proposer.toString(), "User");
        //assertEquals(ProposalsManager.proposals[0].proposed, "???");
        
    }
    
    /**
     * Test of createProposal method, of class ProposalsManager.
     */
    /*
    @Test
    public void testCreateProposal2() throws Exception {
        System.out.println("createProposal2");
        
        ProposalsManager.populateSampleProposals();
        
        Proposal instance = ProposalsManager.proposals[0];
        
        assertEquals(instance.proposalType.toString(), "NewModule");
        assertEquals(instance.code.toString(), "CODE");
        assertEquals(instance.delegator.toString(), "Mgr1");
        assertEquals(instance.proposer.toString(), "User");
        //assertEquals(instance.proposed, "???");
        
    }
    */
        
    @Test(expected = ProposalCheckException.class)
    public void testCreateProposalException() throws Exception {
        System.out.println("createProposalException");        
        Proposal instance = new Proposal(null, null, null);        
    }    
    
    /**
     * Test of refreshProposalsManagerGUI method, of class ProposalsManager.
     */
    /* triggered by createProposal() 
    @Test
    public void testRefreshProposalsManagerGUI() {
        System.out.println("refreshProposalsManagerGUI");
        ProposalsManager.refreshProposalsManagerGUI();
    }  
    */
    
}
