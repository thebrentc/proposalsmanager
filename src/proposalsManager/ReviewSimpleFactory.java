package proposalsManager;

/* A simple factory to return a review template of requested type */
public class ReviewSimpleFactory {
    
    public Review create(Class reviewType) {        
        // XXX 
        try {
            return (Review) reviewType.newInstance();
        } catch (Exception e) { return null; }                        
    }
}
