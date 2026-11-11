package dz.univ.accounts.composite;

import dz.univ.accounts.model.StudentAccount;

public class StudentLeaf implements UniversityComponent {

    private final StudentAccount account;

    public StudentLeaf(StudentAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("StudentAccount cannot be null.");
        }
        this.account = account;
    }

    public StudentAccount getAccount() {
        return account;
    }

    @Override
    public String getName() {
        return account.getFullName() + " (" + account.getId() + ")";
    }

    @Override
    public int countAccounts() {
        return 1;
    }

    @Override
    public void printHierarchy(String indent) {
        System.out.println(indent + "- [Student] " + account.getId() + " : " + account.getFullName() +
                " [" + account.getLevel() + ", " + account.getStatusName() + "]");
    }
}
