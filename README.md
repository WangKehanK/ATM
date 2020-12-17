# ATM

# 1. Introduction
- We assume:
  - Every single user can only have one saving account,one checking account,one loan account and one stock account.
  - We have different interest rates for day saving, month saving, year saving.
  - Every 60s in real life equals one hour in our bank system, where interest rates will be calculated at daliy basis.
  - Our exchange rate is steady, which USDTOEURO=2 , USDTOCNY = 10, EUROTOUSD = 0.5, EUROTOCNY = 5, CNYTOUSD = 0.1, CNYTOEURO = 0.2
  - We have three type of interest rate(day, month, year), they are all steady. User can choose what kind of account they want while creating account
  - We set up a simple stock system, where manager can add stocks to the market, and customers can buy or sell them.
- Bonus: Input is getting parsed from the input files and is not harcoded: Details in **config.properties**; we provide exchange rate for three currencies we have (USD, CNY, EURO), and how many second equails to one day.(defualt: 60s second = one day in our bank system)
- Bonus: We build our stock market; in order to buy stock, you have to create a stock account first in "create account" page. THen you can sell/buy in "Stock" Page.

```
Customer account #1
username: 123
password: 123

Customer account #1
username: test1
password: test1

manager account
username: test
password: test
```
# 2. Structure

All files in ***src*** folder
- config.properties - A config file that allow you to modify the different exchange rates ***It can be located either in ./ATM directory or ./ATM/src directory***.If the file is broken/disappeared for some reason, I have also set a default value for each of variables.
- Main.java -Note that all below files/classes are in a **dao** and **manager**, **ui**, **utils** directory; Main is located at the same level of these folders, which is a main game entrance; To start the our bank system you have to go to the directory where **Main.java** located first, then follow the instruction to run.
- dao - The Data Access Object(DAO) patterns we will use in our implementation
  - AccountDao.java - The account Dao pattern we use to read the database, operating account-related data operations
  - LoanDao.java - The loan Dao pattern we use to read the database
  - LogDao.java - The log Dao pattern we use to read the database, store log data operations about user operations
  - StockDao.java -The stock Dao pattern we use to read the database, operating stock-related data operations
  - UserDao.java - The user Dao pattern we use to read the database, Operate user-related data operations
- manager
  - account
    - Account.java - The account interface, can be extended to any type of account e.g., Checking Account, Saving Account, Loan Account, Security Account (Stock)..., including functions like get balance, pick a currency, convert, etc..
    - AccountType.java - Enum class for all different account type
    - CheckingAccount.java - This file is for Checking account class, implement the account interface contains all functions for Checking account
    - LoanAccount.java - This file is for loan account class, implement the account interface and time observer contains all functions for loan account
    - SavingAccount.java - This file is for saving account class, implement the account interface and timer observer contains all functions for saving account
    - SecurityAccount.java - This file is for securities accounts(stock) class, implement the account interface; Save the security amount at the time of purchase; Calculate how much money you made
  - entity
    - Collateral.java - This file is for Collateral class contains all the functions we need for collateral class, e.g. check the price for collateral, check the type, get the name, etc
    - Log.java - This file is for Log class contains all the functions we need for Log class, used to write down log for manager
    - Result.java - This file is for result class contains all the functions we need for result class, easier for us to implement interfaces
    - Stock.java - This file is for Stock class contains all the functions we need for Stock, e.g. get the stock name, get the stock ID, get the stock price
  - timer
    - Timer.java - Time class implements the runnable interface used to track time
    - TimeObserver.java - A time observer for ui and account needs interest. In our bank system, we set 60s as one day, you can modify this in our config.properties file
  - user
    - User.java - A basic User interface, can be extended to any types of user, e.g. Customers, Managers..
    - AbstractUser.java - A abstract user class, implements User interface, contains functions fot login purpose (username, password)
    - Consumer.java - The customer user class, extends abstract user, use Dao to read the database
    - Manager.java - The customer user class, extends abstract user, use Dao to read the database
    - UserManager.java - A user manager class, that use to manage all users, the result class from entity is used
  - BankIncomeLedger.java - The bank income ledger class, used to track the bank's income
  - SystemManager.java - The system manager class, the most important class that contains all the main logic for front end; the front end can import these functions in very easy and convinent way.
- ui - TimerObserver from entity is implements in all UI pages, so we can observe the system time 
  - Ipage.java - A interface page, can be extends to any kinds of pages we want
  - WelcomePage - This file is for WelcomePage class that implements IPages interface and timer observer, contains all the functions we need for WelcomePage class, provides the UI for WelcomePage; User can choose redirect to manager login page or customer login page here
  - LoginPage - This file is for LoginPage class that implements IPages interface and timer observer provides the UI for Login Page for both customer side and manager side
  - CustomerPage - This page provides options to customers what to do next, create account, withdraw, save...
  - ManagerPage - This page provides options to manager what to do next, check daily report...
  - CreateAccountPage - This page provides customers an option to choose saving account or checking account to interact with
  - NewAccountPage - This page provides options to user to choose the what kind of interest rate they want (day, month, year), and what type of account they want create
  - LoanPage - This page provide UI and buttons for users to loan
  - ExchangePage - This page provides options to exchange between three different currencies
  - PickAccountPage - This page provides options to choose which account the customers want to use while they save, withdraw, transfer
  - swPage - The swPage page class, that requires two paramters, which is option(save, withdraw, or transfer), and account type(checking or saving). We implement in such way because these pages are very similar, so we just need to read the input and generate different functionality for them
  - StockAccountPage - The page provides options for user to buy and sell stock
  - StockAddPage - This page provides options for manager to add stocks to the market
  - SecurityPickAccountPage - This page provides user options to transfer money from saving/checking account into their security account(stock)
- utils
  - ConfigUtils.java - A config util class, read config.properties
  - FileUtils.java - A file util class, read our database (csv files)
- account.csv - Account database, used to store account type, the content(money) an account has, corresponding user
- collateral.csv - Collateral database, used to store the type & name of collateral, and its value 
- consumers.csv - Consumers database, used to store the username and password of a customer, and their userID (unique identifier)
- managers.csv - Managers database, used to store the username and password of a manager, and their user ID (unique identifier)
- stock.csv - Stock database, used to store the stock name, id, and its price

```
└─src
    │  Main.java
    ├─dao
    │      AccountDao.java
    │      LoanDao.java
    │      LogDao.java
    │      StockDao.java
    │      UserDao.java
    │
    ├─manager
    │  │  BankIncomeLedger.java
    │  │  SystemManager.java
    │  │
    │  ├─account
    │  │      Account.java
    │  │      AccountType.java
    │  │      CheckingAccount.java
    │  │      LoanAccount.java
    │  │      SavingAccount.java
    │  │      SecurityAccount.java
    │  │
    │  ├─entity
    │  │      Collateral.java
    │  │      Log.java
    │  │      Result.java
    │  │      Stock.java
    │  │
    │  ├─timer
    │  │      Timer.java
    │  │      TimerObserver.java
    │  │
    │  └─user
    │          AbstractUser.java
    │          Consumer.java
    │          Manager.java
    │          User.java
    │          UserManager.java
    │
    ├─ui
    │      CreateAccountPage.java
    │      CustomerPage.java
    │      IPages.java
    │      LoanPage.java
    │      LoginPage.java
    │      ManagerPage.java
    │      NewAccountPage.java
    │      PickAccountPage.java
    │      swPage.java
    │      TestMain.java
    │      TransferPage.java
    │      WelcomePage.java
    │
    └─utils
            ConfigUtils.java
            FileUtils.java

```
# 3. Get Start


First you need to compile the program
```
cd ./src
//using UTF-8 can avoid many errors in different systems
javac -encoding UTF-8 Main.java
```
You can avoid warning “ -Xlint:unchecked ”

Then run
```
java Main
```

Enter the username and password we provide at the beginning of the file. (You can choose customers or managers to play with)


