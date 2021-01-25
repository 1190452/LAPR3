package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSessionTest {

    @Test
    void setUser() {
        UserSession userSessionmock = mock(UserSession.class);
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        when(userSessionmock.getUser()).thenReturn(user);

        userSessionmock.setUser(user);
        User result = userSessionmock.getUser();;
        assertEquals(user, result);

    }

    @Test
    void getUser() {
        User user = new User("admin@isep.ipp.pt","qwerty","Administrator");
        UserSession userSessionmock = mock(UserSession.class);
        when(userSessionmock.getUser()).thenReturn(user);

        User result = userSessionmock.getUser();
        assertEquals(user, result);

    }
}