package manager.user;

/**
 * A abstract user class, implements User interface, contains functions fot login purpose (username, password)
 */

public abstract class AbstractUser implements User{
    private String userName;
    private String password;

    public AbstractUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
