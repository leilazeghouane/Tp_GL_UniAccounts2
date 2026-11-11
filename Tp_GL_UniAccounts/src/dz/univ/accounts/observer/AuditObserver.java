package dz.univ.accounts.observer;

import dz.univ.accounts.repository.AuditLogger;

/**
 * ============================================================================
 * Design Pattern: Observer Concrete Implementation
 * Observer 1: AuditObserver
 * Role: يقوم بتسجيل كل حدث يطرأ على أي حساب في مسجل التدقيق المركزي AuditLogger.
 * ============================================================================
 */
public class AuditObserver implements AccountObserver {

    private final AuditLogger auditLogger;

    public AuditObserver() {
        this.auditLogger = AuditLogger.getInstance();
    }

    @Override
    public void onAccountEvent(AccountEvent event) {
        String logMessage = String.format("Event [%s] on Account '%s' (%s): %s",
                event.getType(),
                event.getAccount().getId(),
                event.getAccount().getFullName(),
                event.getDescription()
        );
        auditLogger.log("OBSERVER_AUDIT", logMessage);
    }
}
