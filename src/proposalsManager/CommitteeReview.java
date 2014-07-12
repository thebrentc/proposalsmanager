package proposalsManager;

public class CommitteeReview extends FormalReview {
    public CommitteeReview() {
        super();
        this.reviewContacts.put(reviewContacts.size(), new UserId("user"));
    }
}
