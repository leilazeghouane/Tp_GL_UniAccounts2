package dz.univ.accounts.adapter;

import dz.univ.accounts.model.StudentAccount;
import java.util.List;

/**
 * ============================================================================
 * Target Interface for Student Importing
 * ============================================================================
 */
public interface StudentImporter {
    List<StudentAccount> importStudents();
}
