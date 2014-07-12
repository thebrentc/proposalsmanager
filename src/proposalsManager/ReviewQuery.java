package proposalsManager;

/* A review component composite implementing a query functionality */
public class ReviewQuery extends ReviewComponents {

    public ReviewQuery() {
        reviewComponents.add(new ReviewComponentItem("query",String.class)); 
    }

}
