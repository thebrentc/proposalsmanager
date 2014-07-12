package proposalsManager;

public class NewProgrammeProposalDetailFactory extends ProposalDetailFactory {
    
    ProposalDetail create() {
        return new NewProgrammeDetail();
    }    
    
}
