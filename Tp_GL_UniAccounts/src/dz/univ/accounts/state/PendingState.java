package dz.univ.accounts.state;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AuditLogger;

public class PendingState implements AccountState {

    @Override
    public String getStateName() {
        return "PENDING";
    }

    @Override
    public boolean login(StudentAccount account) {
        System.out.println("[ACCESS DENIED] Account '" + account.getId() + "' is PENDING activation. Login is forbidden.");
        return false;
    }

    @Override
    public void activate(StudentAccount account) {
        account.setCurrentState(new ActiveState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' state changed from PENDING to ACTIVE.");
        System.out.println("[STATE] Account '" + account.getId() + "' is now ACTIVE.");
    }

    @Override
    public void suspend(StudentAccount account) {
        System.out.println("[INVALID ACTION] Cannot suspend an account that is still PENDING.");
    }

    @Override
    public void close(StudentAccount account) {
        account.setCurrentState(new ClosedState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' closed from PENDING.");
        System.out.println("[STATE] Account '" + account.getId() + "' has been CLOSED.");
    }
}
