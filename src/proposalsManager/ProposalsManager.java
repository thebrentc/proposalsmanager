package proposalsManager;

import java.util.Map;
import java.util.HashMap;

/* System class managing all proposals */
public class ProposalsManager {
    
    public static Map<Integer, Proposal> proposals = new HashMap<Integer, Proposal>();
    public static ProposalsManagerGUI proposalsManagerGUI;
    public static Flash flash = Flash.getInstance(); // transient messages for user e.g. confirmations or submission check failures   

    public static void main(String[] args) {
        
        UserManager.initDelegations();
        //UserManager.showDelegations();
        proposalsManagerGUI = new ProposalsManagerGUI();
        proposalsManagerGUI.setVisible(true);        
    }
    
    public static void createProposal(ProposalType proposalType, ProspectusCode code, UserId delegator) {
                        
        Proposal proposal = null;
        //try
        {
            // @post a new instance of a Proposal is created
            proposal = new Proposal(proposalType, code, delegator);        
        } 
        // @post with validated initial information 
        // @post or an exception is raised        
        /*catch (Exception e) {             
            System.out.println ("Failed to create new proposal"); 
            proposalsManagerGUI.refreshFlash();  // contains check fail messages
            return;
        }*/
        
        // @post linked to self
        // add to list using proposal's id as key
        Integer proposalId = proposal.getId();
        proposals.put(proposalId, proposal);  
        
        // basic GUI functionality (TODO)
        flash.set("Proposal created"); 
        proposalsManagerGUI.refreshFlash();
        proposalsManagerGUI.refreshProposalsList();
    }    
    
    public static void populateSampleProposals()
    {
        ProposalType proposalType = new ProposalType("NewModule");
        ProspectusCode code = new ProspectusCode("CODE");
        UserId delegator = null; // new UserId("Mgr1");
        ProposalsManager.createProposal(proposalType, code, delegator);    
    }    

}
