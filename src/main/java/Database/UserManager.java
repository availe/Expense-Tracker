package Database;

public final class UserManager {
    // setup for singleton class
    private static UserManager firstInstance = null;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (firstInstance == null) firstInstance = new UserManager();
        return firstInstance;
    }
}
