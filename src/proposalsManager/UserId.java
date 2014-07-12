package proposalsManager;

/**
 *
 * a typedef for a userid
 */
class UserId {
    
    private String userid;    
    
    public UserId(String userid) {
        this.userid = userid;
    }
    
    public boolean isDelegateFor(UserId userid){
        return UserManager.user1isDelegateForUser2(this, userid);
    }
    
    // thanks http://en.wikibooks.org/wiki/Java_Programming/Comparing_Objects
    public boolean equals(Object obj) {    
        if (this == obj) { // if the two objects are equal in reference, they are equal
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof UserId) {
            if (obj.toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }
    
    public String toString()
    {
        return this.userid;
    }    
    
}
