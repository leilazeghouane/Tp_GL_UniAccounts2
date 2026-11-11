package dz.univ.accounts.composite;

import dz.univ.accounts.model.StudentAccount;

/**
 * ============================================================================
 * Design Pattern: Composite - Group (الفوج)
 * يحتوي مباشرة على حسابات الطلبة
 * ============================================================================
 */
public class GroupComposite extends BaseCompositeUnit {

    public GroupComposite(String name) {
        super("Group", name);
    }

    /**
     * دالة مساعدة لإضافة حساب طالب مباشرة كـ Leaf
     */
    public void addStudent(StudentAccount account) {
        add(new StudentLeaf(account));
    }
}
