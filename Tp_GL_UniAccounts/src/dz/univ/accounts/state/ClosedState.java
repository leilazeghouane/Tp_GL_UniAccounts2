package dz.univ.accounts.state;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AuditLogger;

public class ClosedState implements AccountState {

    @Override
    public String getStateName() {
        return "CLOSED";
    }

    @Override
    public boolean login(StudentAccount account) {
        System.out.println("[ACCESS DENIED] Account '" + account.getId() + "' is CLOSED (Graduated student). Login is forbidden.");
        AuditLogger.getInstance().log("LOGIN_DENIED", "Attempted login on closed account: '" + account.getId() + "'.");
        return false;
    }

    @Override
    public void activate(StudentAccount account) {
        System.out.println("[ERROR] Cannot reactivate a CLOSED account ('" + account.getId() + "'). Re-enrollment required.");
    }

    @Override
    public void suspend(StudentAccount account) {
        System.out.println("[ERROR] Cannot suspend an already CLOSED account.");
    }

    @Override
    public void close(StudentAccount account) {
        System.out.println("[INFO] Account '" + account.getId() + "' is already CLOSED.");
    }
}
