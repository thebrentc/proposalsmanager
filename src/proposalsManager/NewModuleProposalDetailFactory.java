package proposalsManager;

public class NewModuleProposalDetailFactory extends ProposalDetailFactory {
    
    ProposalDetail create() {
        return new NewModuleDetail();
    }
    
}
