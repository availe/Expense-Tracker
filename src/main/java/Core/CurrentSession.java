package Core;
public class CurrentSession {
    // singleton class setup
    private static CurrentSession firstInstance = null;

    private CurrentSession() {
    }

    public static CurrentSession getInstance() {
        if (firstInstance == null) firstInstance = new CurrentSession();
        return firstInstance;
    }

    // fields
    private UserRecord currentUser;

    public UserRecord getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserRecord currentUser) {
        this.currentUser = currentUser;
    }
}
