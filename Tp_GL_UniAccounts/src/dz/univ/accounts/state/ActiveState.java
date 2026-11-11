package dz.univ.accounts.state;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AuditLogger;

public class ActiveState implements AccountState {

    @Override
    public String getStateName() {
        return "ACTIVE";
    }

    @Override
    public boolean login(StudentAccount account) {
        System.out.println("[ACCESS GRANTED] Welcome " + account.getFullName() + "! Login successful.");
        AuditLogger.getInstance().log("LOGIN_SUCCESS", "Student '" + account.getId() + "' logged in successfully.");
        return true;
    }

    @Override
    public void activate(StudentAccount account) {
        System.out.println("[INFO] Account '" + account.getId() + "' is already ACTIVE.");
    }

    @Override
    public void suspend(StudentAccount account) {
        account.setCurrentState(new SuspendedState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' state changed from ACTIVE to SUSPENDED.");
        System.out.println("[STATE] Account '" + account.getId() + "' has been SUSPENDED.");
    }

    @Override
    public void close(StudentAccount account) {
        account.setCurrentState(new ClosedState());
        AuditLogger.getInstance().log("STATE_CHANGE", "Account '" + account.getId() + "' closed upon graduation.");
        System.out.println("[STATE] Account '" + account.getId() + "' has been CLOSED (Graduated).");
    }
}
