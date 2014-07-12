package proposalsManager;

/* A formal decision typedef for a formal review submission */
class Decision {
    
    enum Literal 
    {
        Approved,
        Rejected,
    };
    
    String decision;
    
    public Decision(String decision) {
        this.decision = decision;
    }
    
    public Decision(Decision.Literal decision) {
        this.decision = decision.toString();
    }    
    
    public String toString()
    {
        return this.decision;
    }
    
}
