package dz.univ.accounts.facade;

import dz.univ.accounts.adapter.LegacyStudentAdapter;
import dz.univ.accounts.composite.DepartmentComposite;
import dz.univ.accounts.composite.FacultyComposite;
import dz.univ.accounts.composite.GroupComposite;
import dz.univ.accounts.composite.UniversityComposite;
import dz.univ.accounts.decorator.BasicStudentService;
import dz.univ.accounts.decorator.InternetDecorator;
import dz.univ.accounts.decorator.LibraryDecorator;
import dz.univ.accounts.decorator.RestaurantDecorator;
import dz.univ.accounts.decorator.StudentService;
import dz.univ.accounts.legacy.LegacyStudentSystem;
import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.observer.AdminDashboard;
import dz.univ.accounts.observer.AuditObserver;
import dz.univ.accounts.observer.EmailNotifier;
import dz.univ.accounts.repository.AccountRepository;
import dz.univ.accounts.repository.AuditLogger;
import dz.univ.accounts.strategy.CsvExport;
import dz.univ.accounts.strategy.ExportStrategy;
import dz.univ.accounts.strategy.JsonExport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UniAccountsFacade {

    private final AccountRepository repository;
    private final AuditLogger logger;
    private final AdminDashboard adminDashboard;
    private final Map<String, StudentAccount> templates;
    private final Map<String, StudentService> studentServices;

    public UniAccountsFacade() {
        this.repository = AccountRepository.getInstance();
        this.logger = AuditLogger.getInstance();
        this.adminDashboard = new AdminDashboard();
        this.templates = new HashMap<>();
        this.studentServices = new HashMap<>();

        repository.registerObserver(new AuditObserver());
        repository.registerObserver(new EmailNotifier());
        repository.registerObserver(this.adminDashboard);

        logger.log("FACADE_INIT", "UniAccountsFacade initialized.");
    }

    public StudentAccount registerNewStudent(String id, String fullName, String email, String level, String major, String phone, boolean scholarship) {
        StudentAccount.Builder builder = new StudentAccount.Builder(id, fullName, email)
                .level(level)
                .major(major)
                .phone(phone)
                .scholarship(scholarship);

        StudentAccount account = builder.build();
        repository.addAccount(account);
        return account;
    }

    public void activateStudent(String id) {
        repository.activateAccount(id);
    }

    public void suspendStudent(String id) {
        repository.suspendAccount(id);
    }

    public void graduateAndCloseStudent(String id) {
        repository.closeAccount(id);
    }

    public boolean studentLogin(String id) {
        Optional<StudentAccount> acc = repository.findById(id);
        if (acc.isPresent()) {
            return acc.get().login();
        } else {
            System.out.println("[ERROR] Account not found: " + id);
            return false;
        }
    }

    public void registerBatchTemplate(String templateKey, StudentAccount template) {
        templates.put(templateKey, template);
        logger.log("TEMPLATE_REGISTERED", "Template key: " + templateKey);
    }

    public StudentAccount createStudentFromTemplate(String templateKey, String newId, String newFullName, String newEmail) {
        StudentAccount template = templates.get(templateKey);
        if (template == null) {
            throw new IllegalArgumentException("Template not found: " + templateKey);
        }
        StudentAccount clonedStudent = template.cloneWithNewIdentity(newId, newFullName, newEmail);
        repository.addAccount(clonedStudent);
        return clonedStudent;
    }

    public List<StudentAccount> importLegacyStudents() {
        LegacyStudentSystem legacySystem = new LegacyStudentSystem();
        LegacyStudentAdapter adapter = new LegacyStudentAdapter(legacySystem);
        List<StudentAccount> imported = adapter.importStudents();

        for (StudentAccount acc : imported) {
            repository.importAccount(acc);
        }
        return imported;
    }

    public String exportAccountsCsv() {
        ExportStrategy strategy = new CsvExport();
        return strategy.export(repository.getAllAccounts());
    }

    public String exportAccountsJson() {
        ExportStrategy strategy = new JsonExport();
        return strategy.export(repository.getAllAccounts());
    }

    public StudentService getOrInitStudentService(String studentId) {
        StudentAccount account = repository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));

        return studentServices.computeIfAbsent(studentId, k -> new BasicStudentService(account));
    }

    public void addLibraryService(String studentId) {
        StudentService current = getOrInitStudentService(studentId);
        studentServices.put(studentId, new LibraryDecorator(current));
        logger.log("SERVICE_ADDED", "Student " + studentId + " subscribed to Library.");
    }

    public void addRestaurantService(String studentId) {
        StudentService current = getOrInitStudentService(studentId);
        studentServices.put(studentId, new RestaurantDecorator(current));
        logger.log("SERVICE_ADDED", "Student " + studentId + " subscribed to Restaurant.");
    }

    public void addInternetService(String studentId) {
        StudentService current = getOrInitStudentService(studentId);
        studentServices.put(studentId, new InternetDecorator(current));
        logger.log("SERVICE_ADDED", "Student " + studentId + " subscribed to Internet.");
    }

    public void displayStudentServices(String studentId) {
        StudentService service = studentServices.get(studentId);
        if (service == null) {
            System.out.println("No active extra services for student: " + studentId);
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.println("STUDENT SUBSCRIPTIONS: " + service.getAccount().getFullName());
        System.out.println("Package     : " + service.getDescription());
        System.out.printf("Monthly Fee : %.2f DZD%n", service.getMonthlyCost());
        System.out.println("Privileges  :");
        for (String p : service.getPrivileges()) {
            System.out.println(" - " + p);
        }
        System.out.println("--------------------------------------------------");
    }

    public UniversityComposite buildCleanHierarchyForStudents(List<StudentAccount> targetedStudents) {
        UniversityComposite university = new UniversityComposite("Universite Hamma Lakhdar - El Oued");

        FacultyComposite scienceFaculty = new FacultyComposite("Faculte des Sciences Exactes");
        DepartmentComposite csDepartment = new DepartmentComposite("Departement d'Informatique");
        GroupComposite glGroup = new GroupComposite("Groupe 1 - Master Genie Logiciel (GL)");
        GroupComposite isGroup = new GroupComposite("Groupe 2 - Master Systemes d'Information (SI)");

        for (int i = 0; i < targetedStudents.size(); i++) {
            if (i < 4) {
                glGroup.addStudent(targetedStudents.get(i));
            } else {
                isGroup.addStudent(targetedStudents.get(i));
            }
        }

        csDepartment.add(glGroup);
        csDepartment.add(isGroup);
        scienceFaculty.add(csDepartment);
        university.add(scienceFaculty);

        return university;
    }

    public UniversityComposite buildDemoUniversityHierarchy() {
        return buildCleanHierarchyForStudents(repository.getAllAccounts());
    }

    public void printDashboard() {
        adminDashboard.printDashboardSummary();
    }

    public void printAuditLogs() {
        System.out.println("\n--- AUDIT LOG ENTRIES ---");
        logger.getLogs().forEach(System.out::println);
    }

    public AccountRepository getRepository() {
        return repository;
    }
}
