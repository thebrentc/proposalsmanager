package proposalsManager;

/* A contact for a template review */
class ReviewContact {
    
    private UserId user;
    
    public ReviewContact(UserId user) {
        this.user = user;    
    }
    
    public String toString() {
        return this.user.toString();
    }
}
