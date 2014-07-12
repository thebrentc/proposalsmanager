package proposalsManager;

import java.util.ArrayList;

/* Provides a static list of required admin tasks, required when a proposal is finally approved */
public class RequiredAdminsList {
    
    // fixed list of admin tasks
    private static Object[][] requiredAdminsList = {
        { "NewModule", "Publish", new UserId("user") }
        // etc
    };
               
    public static ArrayList getRequiredAdmins(ProposalType proposalType) 
    {
        ArrayList requiredAdmins = new ArrayList(1);
        for (int c = 0; c < requiredAdminsList.length; c++) {
            if (requiredAdminsList[c][0].equals(proposalType.toString())) {
                requiredAdmins.add(requiredAdminsList[c]);
            }
        }        
        return requiredAdmins;
    }

}
