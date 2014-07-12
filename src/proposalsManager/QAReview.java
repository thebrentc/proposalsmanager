package proposalsManager;

/* Quality assurance formal review and decision */
public class QAReview extends FormalReview {
    public QAReview() {
        super();
        this.reviewContacts.put(reviewContacts.size(), new UserId("user"));        
    }
}
