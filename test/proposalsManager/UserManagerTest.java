package proposalsManager;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * General tests for UserManager (TODO)
 * @uses UserId
 */
public class UserManagerTest {
    
    public UserManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("UserManagerTest");        
        UserManager.initDelegations();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Basic test of user authentication 
     */    
    @Test
    public void testUserAuthentication() {
        System.out.println("testUserAuthentication");
        assertEquals(UserManager.getCurrentUserId().toString(),"user");        
    }    
    
    /**
     * Test of isValidUser method with known valid user id.
     */
    @Test
    public void testIsValidUser() {
        System.out.println("testIsValidUser");        
        assertEquals(UserManager.isValidUser(new UserId("user")), true);        
    }        
    
    /**
     * Test of isValidUser method with invalid user id.
     */
    @Ignore("UserManager TODO")     
    @Test
    public void testIsNotValidUser() { 
        System.out.println("testIsNotValidUser");                
        //assertEquals(UserManager.isValidUser(new UserId(null)), false);        
    }            

    /**
     * Test known delegation
     */
    @Test
    public void testKnownDelegation() {
        System.out.println("testKnownDelegation");                
        assertEquals(new UserId("user1").isDelegateFor(new UserId("user")),true);
    }
    
    /**
     * Test nonexistent delegation accurately reported
     */
    @Test
    public void testNonexistentDelegation() {
        System.out.println("testNonexistentDelegation");
        assertEquals(new UserId("other").isDelegateFor(new UserId("user")),false);
    }    

    /**
     * Test of isFacultyAcademicStaff method, of class UserManager, using known user that should evaluate true
     * Shortcut also testing isStaff(), isFaculty() and isAcademic()
     */
    @Test
    public void testIsFacultyAcademicStaff() {
        System.out.println("testIsFacultyAcademicStaff");
        assertEquals(UserManager.isFacultyAcademicStaff(new UserId("user")), true);        
    }
    
    /**
     * Test isFacultyAcademicStaff method with known user that should evaluate false
     */
    @Ignore("UserManager TODO") 
    @Test    
    public void testIsNotFacultyAcademicStaff() { 
        System.out.println("testIsNotFacultyAcademicStaff");        
        // assertEquals(UserManager.isFacultyAcademicStaff(new UserId("other")), false);        
    }    
    
}
