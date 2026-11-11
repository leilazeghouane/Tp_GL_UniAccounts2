package dz.univ.accounts.decorator;

import dz.univ.accounts.model.StudentAccount;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator (نمط المزخرف - بنائي Structural)
 * Role: واجهة المكون الأساسي (Component) للخدمات الطلابية الإضافية.
 * 
 * المشكلة التي يحلها:
 * يستفيد كل طالب من خدمات إضافية بتوليفات مختلفة (مكتبة فقط، مكتبة ومطعم،
 * مكتبة ومطعم وإنترنت، إلخ).
 * لو استخدمنا الوراثة التقليدية (Inheritance) لاحتجنا إنشاء أصناف لكل التوليفات الممكنة:
 * StudentWithLibrary, StudentWithLibraryAndRestaurant, StudentWithAll... (انفجار الأصناف Class Explosion).
 * 
 * ماذا يحدث لو لم نستعمله؟
 * صعوبة بالغة في إضافة خدمات جديدة، واستحالة تغيير أو إضافة خدمات للطالب
 * بشكل ديناميكي أثناء التشغيل (Runtime).
 * ============================================================================
 */
public interface StudentService {

    /**
     * الحساب الجامعي المرتبط بهذه الخدمات
     */
    StudentAccount getAccount();

    /**
     * وصف الحزمة والخدمات المفعلة
     */
    String getDescription();

    /**
     * التكلفة الشهرية الإجمالية للخدمات (بالدينار الجزائري DZD)
     */
    double getMonthlyCost();

    /**
     * قائمة الميزات والامتيازات الممنوحة
     */
    List<String> getPrivileges();
}
