package manager.user;

/**
 * This file is for AbstractUser class that implements User interface
 * contains all the functions we need for AbstractUser class
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
