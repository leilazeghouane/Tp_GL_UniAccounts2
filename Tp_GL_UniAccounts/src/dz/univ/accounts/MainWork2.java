package dz.univ.accounts;

import dz.univ.accounts.composite.UniversityComposite;
import dz.univ.accounts.facade.UniAccountsFacade;
import dz.univ.accounts.model.StudentAccount;

import java.util.Arrays;

/**
 * TP 2 - Structural & Behavioral Design Patterns
 * Universite Hamma Lakhdar - El Oued
 * Departement d'Informatique
 */
public class MainWork2 {

    public static void main(String[] args) {

        UniAccountsFacade facade = new UniAccountsFacade();

        // 1. State & Observer Pattern
        System.out.println("\n[1] State & Observer Demo:");
        StudentAccount student = facade.registerNewStudent(
                "20240010",
                "Leila Zeghouane",
                "leila.zeghoune@univ-eloued.dz",
                "L3",
                "Informatique",
                "0555998877",
                true
        );

        System.out.println("\n-> Attempt login while PENDING:");
        facade.studentLogin("20240010");

        System.out.println("\n-> Activate account:");
        facade.activateStudent("20240010");

        System.out.println("\n-> Attempt login while ACTIVE:");
        facade.studentLogin("20240010");

        System.out.println("\n-> Suspend account:");
        facade.suspendStudent("20240010");

        System.out.println("\n-> Attempt login while SUSPENDED:");
        facade.studentLogin("20240010");

        System.out.println("\n-> Reactivate account:");
        facade.activateStudent("20240010");

        System.out.println("\n-> Close account on graduation:");
        facade.graduateAndCloseStudent("20240010");

        System.out.println("\n-> Attempt login while CLOSED:");
        facade.studentLogin("20240010");

        // 2. Prototype Pattern via Facade (Registering the rest of the 8 students)
        System.out.println("\n[2] Prototype Pattern Demo:");
        StudentAccount masterGlTemplate = new StudentAccount.Builder("TEMPLATE_M2_GL", "Master GL Batch", "m2gl@univ-eloued.dz")
                .level("M2")
                .major("Informatique (Genie Logiciel)")
                .courses(Arrays.asList("Software Architecture", "Formal Verification", "DevOps & Cloud"))
                .build();

        facade.registerBatchTemplate("M2_GL", masterGlTemplate);

        StudentAccount s1 = facade.createStudentFromTemplate("M2_GL", "20240001", "Med Adel Kouidi", "medadel.kouidi@univ-eloued.dz");
        StudentAccount s2 = facade.createStudentFromTemplate("M2_GL", "20240002", "Oualid Laroum", "oualid.laroum@univ-eloued.dz");
        StudentAccount s3 = facade.createStudentFromTemplate("M2_GL", "20240003", "Nori Heni", "nori.heni@univ-eloued.dz");
        StudentAccount s4 = facade.createStudentFromTemplate("M2_GL", "20240004", "Mohammed Khadem", "mohammed.khadem@univ-eloued.dz");
        StudentAccount s5 = facade.createStudentFromTemplate("M2_GL", "20240005", "Farah Kouidi", "farah.kouidi@univ-eloued.dz");
        StudentAccount s6 = facade.createStudentFromTemplate("M2_GL", "20240006", "Abdo Kacca", "abdo.kacca@univ-eloued.dz");
        StudentAccount s7 = facade.createStudentFromTemplate("M2_GL", "20240007", "Abdo Louthri", "abdo.louthri@univ-eloued.dz");

        facade.activateStudent(s1.getId());
        facade.activateStudent(s2.getId());
        facade.activateStudent(s3.getId());
        facade.activateStudent(s4.getId());
        facade.activateStudent(s5.getId());
        facade.activateStudent(s6.getId());
        facade.activateStudent(s7.getId());

        // 3. Adapter Pattern Demo (Demonstrating external system integration)
        System.out.println("\n[3] Adapter Pattern Demo (Legacy Import Test):");
        facade.importLegacyStudents();

        // 4. Decorator Pattern Demo (Student Services)
        System.out.println("\n[4] Decorator Pattern Demo (Student Services):");
        System.out.println("Nori Heni: Library only");
        facade.addLibraryService(s3.getId());
        facade.displayStudentServices(s3.getId());

        System.out.println("\nMohammed Khadem: Library + Restaurant + Internet combo");
        facade.addLibraryService(s4.getId());
        facade.addRestaurantService(s4.getId());
        facade.addInternetService(s4.getId());
        facade.displayStudentServices(s4.getId());

        // 5. Strategy Pattern Demo
        System.out.println("\n[5] Strategy Pattern Demo (Export):");
        System.out.println("CSV Export (first 6 lines):");
        String csvOutput = facade.exportAccountsCsv();
        csvOutput.lines().limit(6).forEach(System.out::println);

        System.out.println("\nJSON Export preview:");
        String jsonOutput = facade.exportAccountsJson();
        jsonOutput.lines().limit(18).forEach(System.out::println);

        // 6. Composite Pattern Demo (Configured specifically for the 8 students)
        System.out.println("\n[6] Composite Pattern Demo (Hierarchy):");
        UniversityComposite univ = facade.buildCleanHierarchyForStudents(Arrays.asList(
                student, s1, s2, s3, s4, s5, s6, s7
        ));
        univ.printHierarchy("  ");

        System.out.println("\nTotal accounts in student tree: " + univ.countAccounts());
        univ.getChildren().forEach(faculty -> {
            System.out.printf(" - %s : %d accounts%n", faculty.getName(), faculty.countAccounts());
        });

        // 7. Admin Dashboard Summary
        System.out.println("\n[7] Admin Dashboard:");
        facade.printDashboard();
    }
}
