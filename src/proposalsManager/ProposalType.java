package proposalsManager;

/* A proposal type typedef for proposals */
public class ProposalType {
    
    public String proposalType;    
    
    public ProposalType(String proposalType) {
        this.proposalType = proposalType;
    }
       
    public String toString()
    {
        return this.proposalType;
    }    
    
    public String getValue()
    {
        return this.proposalType;
    }    
        
}
