package proposalsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @inv user authentication information available
 * Provides a bridge to the organisation's user authentication system
 * Provides a static list of delegations used by ProposalsManager (currently only for ReviewContact's)
 */
public class UserManager // TODO
{
    
    private static final UserId currentUser = new UserId("user"); 
    
    public static final Map<String,ArrayList> delegations = new HashMap<String, ArrayList>(); 
    
    public UserManager()
    {
        UserManager.initDelegations();
    }
    
    static void initDelegations() {
        UserManager.delegations.put("user",new ArrayList());
        UserManager.delegations.get("user").add("user1");
        UserManager.delegations.get("user").add("user2");
    }
    
    static void showDelegations()
    {
        for (Map.Entry<String, ArrayList> entry : UserManager.delegations.entrySet()) 
        {
            String delegator = entry.getKey();
            System.out.print(delegator+": ");        
            List delegatees = entry.getValue();
            for (int c = 0; c < delegatees.size(); c++) {
                System.out.print(delegatees.get(c)+" ");        
            }
            System.out.println();
        }
    }
    
    static boolean user1isDelegateForUser2(UserId user1, UserId user2) {
        for (Map.Entry<String, ArrayList> entry : UserManager.delegations.entrySet()) 
        {
            String delegator = entry.getKey();
            List delegatees = entry.getValue();
            if (delegator == user2.toString()){
                if (delegatees.contains((String) user1.toString())){
                    return true;
                }
            }            
        }    
        // else
        return false;
    }
    
    static UserId getCurrentUserId() 
    {
        return currentUser;
    }
    
    
    static boolean isStaff(UserId userid) 
    {
        //TODO
        return true;         
    }
    
    static boolean isFaculty(UserId userid)
    {
        //TODO
        return true; 
    }
    
    static boolean isAcademic(UserId userid)
    {
        //TODO
        return true; 
    }    
    
    static boolean isFacultyAcademicStaff(UserId userid)
    {
        return isStaff(userid) && isFaculty(userid) && isAcademic(userid);
    }        
    
    static boolean isValidUser(UserId userid)    
    {
        //TODO
        return true;
    }    
}
