package dz.univ.accounts.state;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AuditLogger;

public class SuspendedState implements AccountState {

    @Override
    public String getStateName() {
        return "SUSPENDED";
    }

    @Override
    public boolean login(StudentAccount account) {
        System.out.println("[ACCESS BLOCKED] Account '" + account.getId() + "' is SUSPENDED. Please contact administration.");
        AuditLogger.getInstance().log("LOGIN_BLOCKED", "Suspended student '" + account.getId() + "' attempted login.");
        return false;
    }

    @Override
    public void activate(StudentAccount account) {
        account.setCurrentState(new ActiveState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' reactivated to ACTIVE.");
        System.out.println("[STATE] Account '" + account.getId() + "' is reactivated (ACTIVE).");
    }

    @Override
    public void suspend(StudentAccount account) {
        System.out.println("[INFO] Account '" + account.getId() + "' is already SUSPENDED.");
    }

    @Override
    public void close(StudentAccount account) {
        account.setCurrentState(new ClosedState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' closed from SUSPENDED.");
        System.out.println("[STATE] Account '" + account.getId() + "' has been CLOSED.");
    }
}
