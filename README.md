# ATM

# 1. Introduction
- We assume:
  - Every single user can only have one saving account,one checking account,one loan account and one stock account.
  - We have different interest rates for day saving, month saving, year saving.
  - Every 60s in real life equals a day in our bank system, where interest rates will be calculated at daliy basis.
- 
- Bonus: Input is getting parsed from the input files and is not harcoded: Details in **config.properties**; we provide exchange rate for three currencies we have (USD, CNY, EURO)

# 2. Structure

All files in ***src*** folder

- Main.java - 
- dao - The Data Access Object(DAO) patterns we will use in our implementation
  - AccountDao.java - The account Dao pattern we use to read the database
  - LoanDao.java - The loan Dao pattern we use to read the database
  - LogDao.java - The log Dao pattern we use to read the database
  - StockDao.java -The stock Dao pattern we use to read the database
  - UserDao.java - The user Dao pattern we use to read the database
- manager
  - account
    - Account.java
    - AccountType.java
    - CheckingAccount.java
    - LoanAccount.java
    - SavingAccount.java
    - SecurityAccount.java
  - entity
    - Collateral.java
    - Log.java
    - Result.java
    - Stock.java
  - timer
    - Timer.java
    - TimeObserver.java
  - user
    - User.java
    - AbstractUser.java
    - Consumer.java
    - Manager.java
    - UserManager.java
  - BankIncomeLedger.java - 
  - SystemManager.java - 
- ui
  - Ipage.java
  - ...
- utils
  - ConfigUtils.java
  - FileUtils.java
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
javac Main.java
```
If error ***UnicodeEncodeError: 'gbk' codec can't encode character: illegal multibyte sequence*** appears, I will suggest to compile use the following command
```
javac -encoding UTF-8 Main.java
```
Then run
```
java Main
```


