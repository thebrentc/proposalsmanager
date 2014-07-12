package proposalsManager;

/* A review component composite implementing a formal decision */
public class ReviewDecision extends ReviewComponents {

    public ReviewDecision() {
        reviewComponents.add(new ReviewComponentItem("decision",Decision.class));                 
    }
}
