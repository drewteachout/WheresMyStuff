package com.example.drew.wheresmystuff;

import com.example.drew.wheresmystuff.model.User;
import com.example.drew.wheresmystuff.model.UserManager;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UserManagerTest {

    /**
     * Jordan Leahey - jleahey3
     * JUnit test to confirm that adding users to UserManager does not impact the user's information.
     */
    @Test
    public void test_userManager() {
        //using this array to store the users outside of the userManager class
        ArrayList<User> uArray = new ArrayList<>(3);
        //UserManager class used to store users and their emails
        UserManager test = new UserManager();
        //instantiating the users for testing
        User test_user = new User("test", "test@test.com", "test", false);
        User test_user2 = new User("test2", "test2@test.com", "test2", false);
        User test_user3 = new User("test3", "test2@test.com", "test2", false);
        //adding users to the array for comparison to UserManager
        uArray.add(test_user);
        uArray.add(test_user2);
        uArray.add(test_user3);
        //adding users to the Hashmap stored in UseManager
        test.putUser("test@test.com", test_user);
        test.putUser("test2@test.com", test_user2);
        test.putUser("test3@test.com", test_user3);
        //Comparing the original users to the ones in UserManger (test) to see if adding them
        //to the test had any effect on the account information
        assertEquals(uArray.get(0), test.getUser("test@test.com"));
        assertEquals(uArray.get(1), test.getUser("test2@test.com"));
        assertEquals(uArray.get(2), test.getUser("test3@test.com"));
    }
}
