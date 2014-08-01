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

    // accessors, some more for testing
    public Integer getId() { return id; }
    public ProposalType getProposalType() { return proposalType; }
    public ProspectusCode getProspectusCode() { return prospectusCode; }
    public UserId getDelegator() { return delegator; }
    public UserId getProposer() { return proposer; }
    public Date getProposed() { return proposed; }    
    protected static void resetCounter() { counter = 0; } // XXX
    public Map<Integer, ProposalApplicableReview> getProposalApplicableReviews() { return proposalApplicableReviews; }    
    public AdminInformation getAdminInformation() { return adminInformation; }
    public Map<Integer, ProposalDetail> getProposalDetails() { return proposalDetails; }
    public Map<Integer, ProposalReview> getProposalReviews() { return proposalReviews; }
    public Integer getProposalReviewsCount() { return proposalReviews.size(); }
    public ProposalReview getLastProposalReview() { return proposalReviews.get(proposalReviews.size()-1); }    
    public Map<ProposalApplicableReview, Set<ProposalReview>> getProposalApplicationReviewSubmissions() { return proposalApplicationReviewSubmissions; }
    public ProposalApplicableReview getProposalApplicableReviewFor(ProposalReview proposalReview ) { 
        for (Iterator i = proposalApplicationReviewSubmissions.keySet().iterator(); i.hasNext();) { 
            ProposalApplicableReview proposalApplicableReview = (ProposalApplicableReview) i.next();
            if (proposalApplicationReviewSubmissions.get(proposalApplicableReview).contains(proposalReview)) return proposalApplicableReview;            
        }
        // else
        return null;
    }              
    public Map<Integer, ProposalRequiredAdmin> getProposalRequiredAdmins() { return proposalRequiredAdmins; }
    public Map<Integer, ProposalAdmin> getProposalAdmins() { return proposalAdmins; }
    public ProposalStatus getProposalStatus() { return proposalStatus; }    
        
    // @post a new Proposal instance is created, and returned
    public Proposal(ProposalType proposalType, ProspectusCode prospectusCode, UserId delegator) throws ProposalCheckException
    {                
        // compute metadata
        UserId proposer = UserManager.getCurrentUserId();  // -> "user"
        Date proposed = new Date();

        // @post validate (and check) information and @pre user validation
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("proposer", proposer);
        data.put("proposed", proposed);
        data.put("proposalType", proposalType);
        data.put("prospectusCode", prospectusCode);
        data.put("delegator", delegator);       
        ProposalChecker proposalChecker = new ProposalChecker();
        if (!proposalChecker.check(data)) { // updates 'Flash' with any problem details
            System.out.println("ProposalCheckException");
            // @post or an exception is raised
            throw new ProposalCheckException();
        }
        
        // @post a new instance of a Proposal is created
        this.proposalType = proposalType;
        this.prospectusCode = prospectusCode;
        this.delegator= delegator;
        this.proposer = proposer;
        this.proposed = proposed;
        // @post with a unique generated Proposal instance identifier id.
        this.id = counter;        
        counter++; // for next id
        this.proposalStatus = ProposalStatus.CREATED;        
        
        // create and initialise linked records
        // @post ProposalApplicableReview instances are created (based on ApplicableReviewsList)
        // @pre (createProposalApplicableReviews) a Proposal instance is available (in construction)
        createProposalApplicableReviews();        
        // @post allowed status of the linked ProposalApplicableReview instances are appropriately initialised.
        // @pre (updateProposalApplicableReviewDependencies) allowed flags initialised ..
        initProposalApplicableReviews(); // sets allowed statuses
        // @post an AdminInformation instance is created, linked to the Proposal.
        createAdminInformationRecord();
        //createProposalRequiredAdminTasks(); // can wait until final approval
    }        
    
    // @post ProposalApplicableReview instances are created (based on ApplicableReviewsList)    
    private void createProposalApplicableReviews() 
    {       
        // @pre the ApplicableReviewsList lookup class exists
        assert(ApplicableReviewsList.class.toString().contains("ApplicableReviewsList"));
        
        // get list of applicable reviews for this proposal type
        ArrayList<ApplicableReview> applicableReviews = ApplicableReviewsList.getApplicableReviews(proposalType);
        
        // use list to create applicable reviews specific to this proposal
        ReviewSimpleReflexiveFactory reviewFactory = new ReviewSimpleReflexiveFactory();        
        for (ApplicableReview applicableReview : applicableReviews) {
            // @post created ProposalApplicableReviews linked to a Review of specified type
            ProposalApplicableReview proposalApplicableReview = new ProposalApplicableReview(reviewFactory.create(applicableReview.getReviewType()), applicableReview.getWeight());
            // ProposalApplicableReview() has default initialisation of reviewStatus                        
                        
            // @post ProposalApplicableReviews linked to the Proposal instance 
            Integer proposalApplicableReviewId = proposalApplicableReviews.size(); 
            proposalApplicableReviews.put(proposalApplicableReviewId, proposalApplicableReview);            
        } 
    }
    
    // @post createProposal allowed status of the linked ProposalApplicableReview instances are appropriately initialised.
    private void initProposalApplicableReviews() 
    {
        // @ pre (updateProposalApplicableReviewDependencies) the allowed flags of this Proposal instance’s proposalApplicableReviews have been initialised 
        // (allowed if proposalApplicableReview is optional or in the first ‘weighting’ level of these proposalApplicableReviews)        

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
        
    // @post createProposal: an AdminInformation instance is created, linked to the Proposal.    
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
        try
        {
            // initial checks
            // @pre proposalApplicableReview exists and is linked to self
            if (!(proposalApplicableReview instanceof ProposalApplicableReview) || proposalApplicableReview == null
                || !this.proposalApplicableReviews.containsValue(proposalApplicableReview)
               ) { 
                throw new ProposalReviewCheckException(); 
            }
            
            // @post create a proposal review submission 
            // @post with validated information in proposalReviewData
           try
            {          
                proposalReview = new ProposalReview(
                    proposalApplicableReview,
                    proposalReviewData
                );
           
            }
            //@post or an exception is raised            
            catch (ProposalReviewCheckException e) {
                throw e; 
            } 
        } catch (ProposalReviewCheckException exception) { 
            String message = "Failed to create new proposal review";
            System.out.println(message);
            try { ProposalsManager.getProposalsManagerGUI().refreshFlash(); } catch (Exception e) { } // flash will contain check fail messages
            return;
        } 
        
        // add to proposal reviews list
        Integer proposalReviewId = proposalReviews.size();  
        proposalReviews.put(proposalReviewId, proposalReview);                
        
        //@post with linkage identifiers (to the relevant proposalApplicableReview)
        // add to proposal's ProposalApplicableReview-ProposalReview association collection
        proposalApplicationReviewSubmissions.putIfAbsent(proposalApplicableReview, new HashSet());
        proposalApplicationReviewSubmissions.get(proposalApplicableReview).add(proposalReview);
        
        // @post and status of the linked ProposalApplicableReview instance is updated according to the submitted proposalReviewData
        // update linked proposalRequiredReview status, using linked review template's recommendation given the submitted review data
        proposalApplicableReview.setReviewStatus(proposalApplicableReview.getReview().recommendReviewStatus(proposalReviewData));        
        // update linked proposalRequiredReview allowed flag (disallows if a required review is done)
        if (proposalApplicableReview.isRequired() && proposalApplicableReview.isDone()) {
            proposalApplicableReview.setAllowed(false); 
        }
        
        // @post and ‘allowed’ status of all this Proposal instance’s ProposalApplicableReview instances are updated ..        
        updateProposalApplicableReviewDependencies();  
        
        // update Proposal status
        if (proposalApplicableReview.getReview().getClass() == CommitteeReview.class && proposalApplicableReview.isDone()) {
            if (proposalApplicableReview.getReviewStatus().toString() == "Approved") this.proposalStatus = ProposalStatus.APPROVED;
            else if (proposalApplicableReview.getReviewStatus().toString() == "Rejected") this.proposalStatus = ProposalStatus.REJECTED;            
        } 
        else {
            this.proposalStatus = ProposalStatus.REVIEWING;
        }
        
        String message = proposalApplicableReview.getReview()+" submitted.";
        System.out.println(message);
        try {
            ProposalsManager.getFlash().set(message);            
            ProposalsManager.getProposalsManagerGUI().refreshFlash(); 
        } catch (Exception e) { }
    }    
        
    // @post (submitReview) allowed status of all this Proposal instance’s ProposalApplicableReview instances are updated 
    // according to the their weightings as affected by the status change of the proposalApplicableReview
    // @pre proposalApplicableReviews collection exists for this proposal is assumed
    private void updateProposalApplicableReviewDependencies() {
        
        // get proposalApplicableReviews sorted by weight
        List<ProposalApplicableReview> applicableReviews = new ArrayList<>(proposalApplicableReviews.values());        
        Collections.sort(applicableReviews);
                
        // @post updates ‘allowed’ flags of all this Proposal instance’s proposalApplicableReviews 
        // according to completion status of other proposalApplicableReviews and rank in the weightings.
        // traverse and set allowed statuses according to lower weighted review statuses        
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
