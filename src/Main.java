import manager.entity.Result;
import manager.user.Consumer;
import manager.user.Manager;
import manager.user.UserManager;
import ui.WelcomePage;

/**
 * Note that all below files/classes are in a **dao** and **manager**, **ui**, **utils** directory;
 * Main is located at the same level of these folders, which is a main game entrance;
 * To start the our bank system you have to go to the directory where **Main.java** located first, then follow the instruction to run.
 */
public class Main {

    public static void main(String[] args) {
        //UserManager userManager = UserManager.getInstance();
        //Result<Consumer> consumer = userManager.getConsumer("test1", "test1");
        //System.out.println(consumer);
        //Result<Manager> manager = userManager.getManager("test", "test");
        //System.out.println(manager);
        //Result<Consumer> consumerResult = userManager.registerConsumer("123", "123");
        //System.out.println(consumerResult);
        //consumerResult = userManager.registerConsumer("test1", "123");
        //System.out.println(consumerResult);
        new WelcomePage();
    }
}
