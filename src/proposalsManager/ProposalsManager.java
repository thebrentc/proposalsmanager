package proposalsManager;

import java.util.Map;
import java.util.HashMap;

/* System class managing all proposals */
public class ProposalsManager {
    
    public static Map<Integer, Proposal> proposals = new HashMap<Integer, Proposal>();
    public static ProposalsManagerGUI proposalsManagerGUI;
    public static Flash flash = Flash.getInstance(); // transient messages for user e.g. confirmations or submission check failures   

    /**
     * @param args the command line arguments
     */
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
            proposal = new Proposal(proposalType, code, delegator);
        
        } /*catch (Exception e) { 
            System.out.println ("Failed to create new proposal"); 
            proposalsManagerGUI.refreshFlash();  // contains check fail messages
            return;
        }*/
        
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
