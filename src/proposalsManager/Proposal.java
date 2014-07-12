package proposalsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* System class handling a proposal, and linked objects and processing */
public class Proposal  
{    
    static private Integer counter = 0; // used to compute id's
    
    private Integer id;
    private ProposalType proposalType;
    private ProspectusCode prospectusCode;
    private UserId delegator;
    private UserId proposer;
    private Date proposed;    
    private AdminInformation adminInformation;
    private Map<Integer, ProposalDetail> proposalDetails = new HashMap<Integer, ProposalDetail>(); // TODO make Composite?
    private Map<Integer, ProposalApplicableReview> proposalApplicableReviews = new HashMap<Integer, ProposalApplicableReview>();
    private Map<Integer, ProposalReview> proposalReviews = new HashMap<Integer, ProposalReview>();  
    // associate ProposalApplicableReview & ProposalReview
    private Map<ProposalApplicableReview, Set<ProposalReview>> proposalApplicationReviewSubmissions = new HashMap<>(); 
    private Map<Integer, ProposalRequiredAdmin> proposalRequiredAdmins = new HashMap<Integer, ProposalRequiredAdmin>();  
    private Map<Integer, ProposalAdmin> proposalAdmins = new HashMap<Integer, ProposalAdmin>(); 
    private ProposalStatus proposalStatus;

    public Integer getId() { return id; }

    public Map<Integer, ProposalApplicableReview> getProposalApplicableReviews() {
        return proposalApplicableReviews;
    }    
        
    public Proposal(ProposalType proposalType, ProspectusCode prospectusCode, UserId delegator) //throws Exception
    {                
        // compute metadata
        UserId proposer = UserManager.getCurrentUserId();  // -> "user"
        Date proposed = new Date();

        // validations and checks
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("proposer", proposer);
        data.put("proposed", proposed);
        data.put("proposalType", proposalType);
        data.put("prospectusCode", prospectusCode);
        data.put("delegator", delegator);       
        ProposalChecker proposalChecker = new ProposalChecker();
        if (!proposalChecker.check(data)) { // updates 'Flash' with any problem details
            System.out.println("ProposalCheckException");
            //throw new ProposalCheckException();
        }
        
        // create with generated id
        this.proposalType = proposalType;
        this.prospectusCode = prospectusCode;
        this.delegator= delegator;
        this.proposer = proposer;
        this.proposed = proposed;
        this.proposalStatus = ProposalStatus.CREATED;
        this.id = counter;        
        counter++; // for next id
        
        // create and initialise linked records
        createProposalApplicableReviews();        
        initProposalApplicableReviews(); // sets allowed statuses
        createAdminInformationRecord();
        //createProposalRequiredAdminTasks(); // can wait until final approval
    }        
    
    private void createProposalApplicableReviews() 
    {       
        // get list of applicable reviews for this proposal type
        ArrayList<ApplicableReview> applicableReviews = ApplicableReviewsList.getApplicableReviews(proposalType);
        
        // use list to create applicable reviews specific to this proposal
        ReviewSimpleFactory reviewSimpleFactory = new ReviewSimpleFactory();        
        for (ApplicableReview applicableReview : applicableReviews) {
                                                           
            ProposalApplicableReview proposalApplicableReview = new ProposalApplicableReview(reviewSimpleFactory.create(applicableReview.getReviewType()), applicableReview.getWeight());
            // ProposalApplicableReview() has default initialisation of reviewStatus                        
            
            // add to this proposal's applicable reviews list
            Integer proposalApplicableReviewId = proposalApplicableReviews.size(); 
            proposalApplicableReviews.put(proposalApplicableReviewId, proposalApplicableReview);
            
        } 
    }
    
    private void initProposalApplicableReviews() 
    {
        // set first level of required review(s) to allowed 
        // builds on default initialisation of allowed statuses in ProposalApplicableReview()
        
        // get a list of proposalApplicableReviews sorted by weight        
        List<ProposalApplicableReview> applicableReviewsList = new ArrayList<>(proposalApplicableReviews.values());        
        Collections.sort(applicableReviewsList);
        // traverse and set first required review(s) to allowed 
        ProposalApplicableReview last = null; 
        for (ProposalApplicableReview applicableReview : applicableReviewsList) {                        
            if (last != null && applicableReview.getWeight() > last.getWeight()) { break; }
            if (applicableReview.isRequired()) { 
                applicableReview.setAllowed(true);
                last = applicableReview;
            }            
        }    
    }
           
    private void createAdminInformationRecord() 
    {
        this.adminInformation = new AdminInformation();
    }    
    
    private void createProposalRequiredAdminTasks() 
    {
        ArrayList requiredAdmins = RequiredAdminsList.getRequiredAdmins(proposalType);
        for (Iterator it = requiredAdmins.iterator(); it.hasNext();) { 
            proposalRequiredAdmins.put(proposalRequiredAdmins.size(), new ProposalRequiredAdmin(it.next())); // simple array structure used in ProposalRequiredAdminsList 
        }         
    }        
          
    public void submitReview(ProposalApplicableReview proposalApplicableReview, ProposalReviewData proposalReviewData) 
    {        
        
        ProposalReview proposalReview;        
        //try
        {
            // basic checks of incoming submission (further checks in ProposalReview constructor)
            if (!(proposalApplicableReview instanceof ProposalApplicableReview) || proposalApplicableReview == null) { 
                /* throw new ProposalReviewCheckException(); */ return; 
            }
            
            // create a proposal review submission, catching any check exception            
           //try
            {          
                proposalReview = new ProposalReview(
                    proposalApplicableReview,
                    proposalReviewData
                );
           
            } /*catch (ProposalReviewCheckException e) {
                throw e;
            } */
        } /*catch (Exception exception) { 
 //            System.out.println("Failed to create new proposal review");
            ProposalsManager.flash.add("Failed to create new proposal review");
            try { ProposalsManager.proposalsManagerGUI.refreshFlash(); } catch (Exception e) { } // ProposalsManager flash will contain check fail messages
            return;
            // http://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html
        } */
        
        // add to proposal reviews list
        Integer proposalReviewId = proposalReviews.size();  
        proposalReviews.put(proposalReviewId, proposalReview);                
        
        // add to proposal's ProposalApplicableReview-ProposalReview association collection
        proposalApplicationReviewSubmissions.putIfAbsent(proposalApplicableReview, new HashSet());
        proposalApplicationReviewSubmissions.get(proposalApplicableReview).add(proposalReview);
        System.out.println(proposalApplicationReviewSubmissions);        
                        
        // update linked proposalRequiredReview status, using linked review template's recommendation given the submitted review data
        proposalApplicableReview.setReviewStatus(proposalApplicableReview.getReview().recommendReviewStatus(proposalReviewData));
        
        // update linked proposalRequiredReview allowed flag (disallows if a required review is done)
        if (proposalApplicableReview.isRequired() && proposalApplicableReview.isDone()) {
            proposalApplicableReview.setAllowed(false); 
        }
        
        // update 'allowed' status for other proposal reviews as necessary
        updateProposalApplicableReviewDependencies();  
        
        // update Proposal status
        this.proposalStatus = ProposalStatus.REVIEWING;
        
        String message = proposalApplicableReview.getReview()+" submitted.";
        try {
            ProposalsManager.flash.set(message);
            ProposalsManager.proposalsManagerGUI.refreshFlash(); 
        } catch (Exception e) { }
    }    
        
    // toggle allowed flags according to review weights and completions 
    private void updateProposalApplicableReviewDependencies() {
        System.out.println("updateProposalRequiredReviewDependencies()");
        
        // get a list of proposalApplicableReviews sorted by weight
        List<ProposalApplicableReview> applicableReviews = new ArrayList<>(proposalApplicableReviews.values());        
        Collections.sort(applicableReviews);
                
        for (int i = 0; i < applicableReviews.size(); i++) {
            
            for (int j = i-1; j > 0; j--) {                
                if (!applicableReviews.get(j).isOutstanding()) {
                    if (applicableReviews.get(i).isOutstanding()) { 
                        applicableReviews.get(i).setAllowed(true); 
                    }
                }
                else {
                    if (applicableReviews.get(j).getWeight() < applicableReviews.get(i).getWeight()) // handle reviews with same weight
                        applicableReviews.get(i).setAllowed(false); 
                        break;
                }
            }
            
        }        
    }    

    private ProposalDetail createDetail(Class proposalDetailType) { // TODO
        ProposalDetailFactory factory;
        
        if (proposalDetailType == NewModuleDetail.class) {
            factory = new NewModuleProposalDetailFactory();
        }
        else if (proposalDetailType == NewProgrammeDetail.class) {
            factory = new NewProgrammeProposalDetailFactory();
        }
        // etc
        else
        {
         return null;
        }        
        ProposalDetail proposalDetail = factory.create();
        return proposalDetail;
    }    
        
    public String toString()
    {
        return /*id + ": " + */ proposalType.toString()+" - "+prospectusCode.toString()+" - "+proposer+((delegator != null)?(" ("+delegator+")"):"")+ " - " + proposed + " [" + proposalStatus +"]";
    }
}
