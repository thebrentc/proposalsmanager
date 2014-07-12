package proposalsManager;

import java.util.Date;

/* A review component composite implementing computed meta data */
public class ReviewMetaData extends ReviewComponents {
    public ReviewMetaData() {
       reviewComponents.add(new ReviewComponentItem("reviewer",UserId.class)); 
       reviewComponents.add(new ReviewComponentItem("reviewed",Date.class)); 
    }
}
