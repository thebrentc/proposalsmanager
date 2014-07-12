package proposalsManager;

/* A review component composite implementing general review information */
public class ReviewGeneralInfo extends ReviewComponents {
    public ReviewGeneralInfo() {
        reviewComponents.add(new ReviewComponentItem("comments",String.class)); 
        reviewComponents.add(new ReviewComponentItem("delegator",UserId.class)); 
    }
}
