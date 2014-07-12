package proposalsManager;

/* Provide a singleton handler for semi-persistent user messages */
public class Flash {
    
    private static Flash instance = new Flash();
    private static String flash = ""; 
    
    private Flash() {        
    }
    
    public static Flash getInstance() { return instance; }
    
    public static void set(String message) {
        flash = message;
    }
    
    public static void add(String message) {
        set(flash + "\n" + message);
    }    
    
    public static String get() {
        return flash;
    }    
    
    public static void clear() {
        set("");
    }
    
}
