package dz.univ.accounts.decorator;

import dz.univ.accounts.model.StudentAccount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator Concrete Component
 * Role: الخدمة الجامعية الأساسية التي يحصل عليها أي طالب مجاناً.
 * ============================================================================
 */
public class BasicStudentService implements StudentService {

    private final StudentAccount account;

    public BasicStudentService(StudentAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("StudentAccount cannot be null.");
        }
        this.account = account;
    }

    @Override
    public StudentAccount getAccount() {
        return account;
    }

    @Override
    public String getDescription() {
        return "Basic University Package for " + account.getFullName();
    }

    @Override
    public double getMonthlyCost() {
        return 0.0; // الخدمة الأساسية مجانية
    }

    @Override
    public List<String> getPrivileges() {
        return new ArrayList<>(Arrays.asList(
                "Campus Ground Access",
                "Official Student Card",
                "Pedagogical Registration"
        ));
    }
}
