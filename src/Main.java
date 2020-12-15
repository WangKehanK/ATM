import manager.entity.Result;
import manager.user.Consumer;
import manager.user.Manager;
import manager.user.UserManager;
import ui.WelcomePage;

public class Main {

    public static void main(String[] args) {
//        UserManager userManager = UserManager.getInstance();
//        Result<Consumer> consumer = userManager.getConsumer("test1", "test1");
//        System.out.println(consumer);
//        Result<Manager> manager = userManager.getManager("test", "test");
//        System.out.println(manager);
//        Result<Consumer> consumerResult = userManager.registerConsumer("123", "123");
//        System.out.println(consumerResult);
//        consumerResult = userManager.registerConsumer("test1", "123");
//        System.out.println(consumerResult);
        new WelcomePage();
    }
}
