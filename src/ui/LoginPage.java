import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements IPages {

    JFrame frame;
    int catagory;
    // Login Page received a integer to identify customer or manager.catagory 0:customer,1:manager
    public LoginPage(int catagory){
        this.catagory=catagory;
        String title;
        if(catagory==0)
            title="Customer Login";
        else
            title="Manager Login";
        frame = new JFrame(title);
        // Setting the width and height of frame
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        placePanelComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void placePanelComponents(JPanel panel) {


        panel.setLayout(null);
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        //add login button
        JButton loginButton = new JButton("login");
        loginButton.setBounds(40, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String password = String.valueOf(passwordText.getPassword());
                String username = String.valueOf(userText.getText());
                System.out.println(username);
                System.out.println(password);
                Boolean login;

                if(password==null||username==null||password.length()==0||username.length()==0) {
                    JOptionPane.showMessageDialog(null, "Empty username or password!", "Error ", JOptionPane.ERROR_MESSAGE);
                    userText.setText("");
                }
                //Here Add Login function
                if(catagory==0)//customer login
                    login=true;
                else
                    login=true;

                //Login successed. Jump to Customer or Manager Pages.
                if(login) {
                    frame.dispose();
                    if(catagory==0)
                        new CustomerPage();
                    else
                        new ManagerPage();
                }
                // Login failed. Clear username and password field.
                else {
                    JOptionPane.showMessageDialog(null,"Wrong username or password! Please reenter.","Error ",JOptionPane.ERROR_MESSAGE);
                    userText.setText("");
                }



            }
        });
        panel.add(loginButton);


        JButton backButton = new JButton("Back To Home Page");
        backButton.setBounds(150, 80, 160, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Customer Login
                frame.dispose();
                new WelcomePage();
            }
        });
        panel.add(backButton);



    }

}
