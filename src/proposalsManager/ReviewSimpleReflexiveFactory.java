package proposalsManager;

/* A simple factory to return a review template of requested type */
public class ReviewSimpleReflexiveFactory {
    
    public Review create(Class reviewType) {        
        try {
            return (Review) reviewType.newInstance();
        } catch (Exception e) { return null; }                        
    }
}
