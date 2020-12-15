package manager.account;
/**
 * enum class for all different account type
 */
public enum AccountType {
    CHECKING(1, "Checking"),
    SAVING(2, "Saving"),
    LOAN(3, "Loan"),
    SECURITY(4,"Security");


    private final int accountType;
    private final String typeName;

    AccountType(int accountType, String typeName) {
        this.accountType = accountType;
        this.typeName = typeName;
    }

    public static AccountType valueofInt(int accountType) {
        for(AccountType type: values()){
            if(type.getAccountType() == accountType){
                return type;
            }
        }
        return null;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getTypeName() {
        return typeName;
    }
}
