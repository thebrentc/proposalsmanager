
package proposalsManager;

import java.util.Map;

/* Provides checking for Proposal */
class ProposalChecker {
    
    public boolean check(Map data) {        
        
        // check submitted data
        String message = "";
        if (!data.containsKey("proposer") || !UserManager.isValidUser( (UserId) data.get("proposer"))) { message = "Bad proposer"; }
        if (!data.containsKey("proposed") || data.get("proposed") == null || data.get("proposed").toString() == "") { message = "Bad proposed date"; }
        if (!data.containsKey("proposalType") || data.get("proposalType") == null || data.get("proposalType").toString() == "") { message = "Bad proposal type"; }
        if (!data.containsKey("prospectusCode") || data.get("prospectusCode") == null || data.get("prospectusCode").toString() == "") { message = "Bad prospectus code"; }        
        if (!data.containsKey("delegator") || ( data.get("delegator") != null && !UserManager.isValidUser((UserId) data.get("delegator")))) { message = "Bad delegator"; }
        
        // @pre user or delegator is Faculty academic staff
        if (!UserManager.isFacultyAcademicStaff((UserId) data.get("proposer")) && !UserManager.isFacultyAcademicStaff((UserId) data.get("delegator"))) {
            message = "Proposer or delegator must be Faculty academic staff."; 
        }
        
        // handle check failures
        if (!message.equals("")) {
            System.out.println(message);
            try { ProposalsManager.flash.add(message); } catch (Exception e) { };
            return false;
        }
        //else
        return true;
    }
}
