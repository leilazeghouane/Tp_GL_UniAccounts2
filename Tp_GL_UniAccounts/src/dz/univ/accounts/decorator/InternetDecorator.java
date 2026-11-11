package dz.univ.accounts.decorator;

import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator Concrete Implementation
 * Decorator 3: InternetDecorator
 * Role: يضيف خدمة الإنترنت عالي السرعة وشبكة الواي فاي والـ VPN الجامعي.
 * ============================================================================
 */
public class InternetDecorator extends ServiceDecorator {

    private static final double INTERNET_FEE = 800.0; // 800 DZD

    public InternetDecorator(StudentService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + [High-Speed Internet]";
    }

    @Override
    public double getMonthlyCost() {
        return super.getMonthlyCost() + INTERNET_FEE;
    }

    @Override
    public List<String> getPrivileges() {
        List<String> privileges = super.getPrivileges();
        privileges.add("Unlimited Campus High-Speed Wi-Fi");
        privileges.add("Secure Remote University VPN Access");
        return privileges;
    }
}
