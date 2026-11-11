package dz.univ.accounts.observer;

import dz.univ.accounts.model.StudentAccount;
import java.time.LocalDateTime;

/**
 * كائن يحمل تفاصيل الحدث الذي طرأ على الحساب
 */
public class AccountEvent {
    private final AccountEventType type;
    private final StudentAccount account;
    private final String description;
    private final LocalDateTime timestamp;

    public AccountEvent(AccountEventType type, StudentAccount account, String description) {
        this.type = type;
        this.account = account;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public AccountEventType getType() {
        return type;
    }

    public StudentAccount getAccount() {
        return account;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
