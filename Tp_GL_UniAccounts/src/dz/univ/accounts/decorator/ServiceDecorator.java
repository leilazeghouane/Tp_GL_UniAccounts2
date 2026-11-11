package dz.univ.accounts.decorator;

import dz.univ.accounts.model.StudentAccount;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator Base Class
 * Role: صنف أساسي مجرد يغلف كائن StudentService ويفوض له العمليات.
 * ============================================================================
 */
public abstract class ServiceDecorator implements StudentService {

    protected final StudentService wrappedService;

    public ServiceDecorator(StudentService service) {
        if (service == null) {
            throw new IllegalArgumentException("Wrapped service cannot be null.");
        }
        this.wrappedService = service;
    }

    @Override
    public StudentAccount getAccount() {
        return wrappedService.getAccount();
    }

    @Override
    public String getDescription() {
        return wrappedService.getDescription();
    }

    @Override
    public double getMonthlyCost() {
        return wrappedService.getMonthlyCost();
    }

    @Override
    public List<String> getPrivileges() {
        return new ArrayList<>(wrappedService.getPrivileges());
    }
}
