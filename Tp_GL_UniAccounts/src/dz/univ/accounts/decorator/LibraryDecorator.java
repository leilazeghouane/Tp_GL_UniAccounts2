package dz.univ.accounts.decorator;

import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator Concrete Implementation
 * Decorator 1: LibraryDecorator
 * Role: يضيف خدمة المكتبة المركزية واستعارة الكتب والولوج للأرشيف الرقمي.
 * ============================================================================
 */
public class LibraryDecorator extends ServiceDecorator {

    private static final double LIBRARY_FEE = 500.0; // 500 DZD

    public LibraryDecorator(StudentService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + [Library Service]";
    }

    @Override
    public double getMonthlyCost() {
        return super.getMonthlyCost() + LIBRARY_FEE;
    }

    @Override
    public List<String> getPrivileges() {
        List<String> privileges = super.getPrivileges();
        privileges.add("Library Book Lending (Up to 5 books)");
        privileges.add("Access to Online Research Journals (IEEE / Springer)");
        return privileges;
    }
}
