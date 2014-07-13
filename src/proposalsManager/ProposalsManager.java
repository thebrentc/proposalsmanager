package proposalsManager;

import java.util.Map;
import java.util.HashMap;

/* System class managing all proposals */
public class ProposalsManager {
    
    private static Map<Integer, Proposal> proposals = new HashMap<Integer, Proposal>();
    private static ProposalsManagerGUI proposalsManagerGUI;
    private static Flash flash = Flash.getInstance(); // transient messages for user e.g. confirmations or submission check failures   
    
    public static Map<Integer, Proposal> getProposals() { return proposals; }
    public static ProposalsManagerGUI getProposalsManagerGUI() { return proposalsManagerGUI; }
    public static Flash getFlash() { return flash; }
    
    public static void main(String[] args) {
        
        UserManager.initDelegations();
        //UserManager.showDelegations();
        proposalsManagerGUI = new ProposalsManagerGUI();
        proposalsManagerGUI.setVisible(true);        
    }
    
    public static void createProposal(ProposalType proposalType, ProspectusCode code, UserId delegator) {                        
        Proposal proposal = null;
        try
        {
            // @post a new instance of a Proposal is created
            proposal = new Proposal(proposalType, code, delegator);        
        } 
        // @post with validated initial information 
        // @post or an exception is raised        
        catch (Exception e) {
            String message = "Failed to create new proposal";
            System.out.println (message); 
            try {
                proposalsManagerGUI.refreshFlash();  // Flash contains check/validation messages
            } catch (Exception _) { }
            return;
        }
        
        // @post linked to self
        // add to list using proposal's id as key
        Integer proposalId = proposal.getId();
        proposals.put(proposalId, proposal);  
        
        String message = "Proposal created";
        System.out.println(message);
        // basic GUI functionality (TODO)
        try {
            flash.set(message); 
            proposalsManagerGUI.refreshFlash();
            proposalsManagerGUI.refreshProposalsList();
        } catch (Exception e) { };
    }    
    
    public static void populateSampleProposals()
    {
        ProposalType proposalType = new ProposalType("NewModule");
        ProspectusCode code = new ProspectusCode("CODE");
        UserId delegator = null; // new UserId("Mgr1");
        ProposalsManager.createProposal(proposalType, code, delegator);    
    }    

}
