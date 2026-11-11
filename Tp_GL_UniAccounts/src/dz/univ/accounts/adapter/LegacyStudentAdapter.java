package dz.univ.accounts.adapter;

import dz.univ.accounts.legacy.LegacyStudentSystem;
import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AuditLogger;
import dz.univ.accounts.state.ActiveState;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Adapter (نمط المحول - بنائي Structural)
 * Role: تحويل واجهة النظام القديم LegacyStudentSystem إلى الواجهة المتوافقة
 * مع نظام UniAccounts الحديث.
 * 
 * المشكلة التي يحلها:
 * للجامعة نظام قديم يحمل حسابات الطلبة القدامى بصيغة نصية غير متوافقة:
 * "ID;LASTNAME;FIRSTNAME;EMAIL;LEVEL". ولا يمكن تعديل النظام القديم لأنه مكتبة
 * خارجية مجمدة.
 * 
 * ماذا يحدث لو لم نستعمله؟
 * سنعجز عن استيراد آلاف حسابات الطلبة القدامى، أو سنضطر إلى تلويث وتشويه نموذج
 * بيانات نظامنا الحديث لاستيعاب الصيغة النصية القديمة.
 * 
 * الربط مع العمل الأول:
 * المحول يستخدم نمط Builder المبني في العمل الأول لتركيب كائنات StudentAccount
 * بطريقة نظيفة وصحيحة 100%.
 * ============================================================================
 */
public class LegacyStudentAdapter implements StudentImporter {

    private final LegacyStudentSystem legacySystem;

    public LegacyStudentAdapter(LegacyStudentSystem legacySystem) {
        if (legacySystem == null) {
            throw new IllegalArgumentException("Legacy system instance cannot be null.");
        }
        this.legacySystem = legacySystem;
    }

    @Override
    public List<StudentAccount> importStudents() {
        List<String> rawRecords = legacySystem.fetchRecords();
        List<StudentAccount> convertedAccounts = new ArrayList<>();

        AuditLogger.getInstance().log("ADAPTER_START", "Starting import from LegacyStudentSystem (" + rawRecords.size() + " records found).");

        for (String record : rawRecords) {
            try {
                // الصيغة: ID;LASTNAME;FIRSTNAME;EMAIL;LEVEL
                String[] parts = record.split(";");
                if (parts.length >= 5) {
                    String id = parts[0].trim();
                    String lastName = parts[1].trim();
                    String firstName = parts[2].trim();
                    String email = parts[3].trim().replaceAll("\\s+", ""); // تنظيف المسافات في الإيميل
                    String level = parts[4].trim();

                    String fullName = firstName + " " + lastName;

                    // استعمال Builder من العمل الأول لبناء الحساب
                    StudentAccount account = new StudentAccount.Builder(id, fullName, email)
                            .level(level)
                            .major("Legacy Import")
                            .initialState(new ActiveState()) // الطلبة القدامى مفعلون افتراضياً
                            .build();

                    convertedAccounts.add(account);
                } else {
                    System.err.println("[ADAPTER ERROR] Malformed legacy record: " + record);
                }
            } catch (Exception e) {
                System.err.println("[ADAPTER ERROR] Failed to parse legacy record [" + record + "]: " + e.getMessage());
            }
        }

        AuditLogger.getInstance().log("ADAPTER_DONE", "Successfully adapted and imported " + convertedAccounts.size() + " accounts.");
        return convertedAccounts;
    }
}
