# نظام ادارة حسابات الطلبة - UniAccounts
## مشروع العمل التطبيقي لمقياس نماذج التصميم (Design Patterns)

---

### معلومات عامة
- المشروع: UniAccounts لإدارة حسابات الطلبة.
- اللغة: Java (JDK 17 أو أحدث).
- الهيكل: 10 أنماط تصميم من GoF مقسمة إلى مرحلتين متتاليتين (العمل الأول والعمل الثاني).

---

## هيكل الحزم (Package Architecture)

تم تقسيم المشروع إلى حزم منفصلة وواضحة:

```text
src/
└── dz/
    └── univ/
        └── accounts/
            ├── model/
            │   └── StudentAccount.java         // Builder, Prototype
            ├── state/
            │   ├── AccountState.java           // State Interface
            │   ├── PendingState.java
            │   ├── ActiveState.java
            │   ├── SuspendedState.java
            │   └── ClosedState.java
            ├── repository/
            │   ├── AccountRepository.java      // Singleton
            │   └── AuditLogger.java            // Singleton
            ├── observer/
            │   ├── AccountObserver.java        // Observer Interface
            │   ├── AccountEvent.java
            │   ├── AccountEventType.java
            │   ├── AuditObserver.java
            │   ├── EmailNotifier.java
            │   └── AdminDashboard.java
            ├── strategy/
            │   ├── ExportStrategy.java         // Strategy Interface
            │   ├── CsvExport.java
            │   └── JsonExport.java
            ├── composite/
            │   ├── UniversityComponent.java    // Composite Interface
            │   ├── BaseCompositeUnit.java
            │   ├── StudentLeaf.java
            │   ├── GroupComposite.java
            │   ├── DepartmentComposite.java
            │   ├── FacultyComposite.java
            │   └── UniversityComposite.java
            ├── legacy/
            │   └── LegacyStudentSystem.java    // الصنف المقدم (مكتبة خارجية)
            ├── adapter/
            │   ├── StudentImporter.java
            │   └── LegacyStudentAdapter.java   // Adapter
            ├── decorator/
            │   ├── StudentService.java         // Decorator Interface
            │   ├── BasicStudentService.java
            │   ├── ServiceDecorator.java
            │   ├── LibraryDecorator.java
            │   ├── RestaurantDecorator.java
            │   └── InternetDecorator.java
            ├── facade/
            │   └── UniAccountsFacade.java      // Facade
            ├── MainWork1.java                  // كلاس اختبار العمل الاول
            └── MainWork2.java                  // كلاس اختبار العمل الثاني
```

---

## طريقة الترجمة والتشغيل

### 1. الترجمة
```powershell
javac -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

### 2. تشغيل العمل الاول (Work 1)
```powershell
java -cp bin dz.univ.accounts.MainWork1
```

### 3. تشغيل العمل الثاني (Work 2)
```powershell
java -cp bin dz.univ.accounts.MainWork2
```

---

## جدول الانماط المستعملة في الشيفرة

| النمط | النوع | الاصناف المعنية | المشكلة التي يحلها في المشروع |
|---|---|---|---|
| Builder | Creational | StudentAccount.Builder | بناء حساب طالب بـ 8 حقول مع التحقق من صحتها وتفادي المنشئات متعددة الوسائط. |
| Singleton | Creational | AccountRepository, AuditLogger | ضمان وجود مستودع مركزي وحيد وسجل تدقيق وحيد لعدم تشتت البيانات. |
| Prototype | Creational | StudentAccount.clone() | نسخ حسابات الطلبة الجدد من قالب الدفعة لتوفير الوقت وتفادي اعادة ادخال المقررات. |
| State | Behavioral | AccountState, PendingState, ActiveState, SuspendedState, ClosedState | ادارة دورة حياة الحساب ومنع العمليات غير المسموح بها حسب الحالة دون شروط معقدة. |
| Observer | Behavioral | AccountObserver, AuditObserver, EmailNotifier, AdminDashboard | اشعار التدقيق والبريد ولوحة التحكم تلقائيا عند حدوث اي تغيير على الحساب. |
| Strategy | Behavioral | ExportStrategy, CsvExport, JsonExport | تصدير بيانات الحسابات بصيغ متعددة (CSV و JSON) مع سهولة اضافة صيغ اخرى. |
| Composite | Structural | UniversityComponent, StudentLeaf, BaseCompositeUnit, GroupComposite... | تمثيل هيكل الجامعة الشجري وحساب عدد الطلبة في اي عقدة بدالة تكرارية موحدة. |
| Adapter | Structural | LegacyStudentAdapter, StudentImporter | التكيف مع كلاس LegacyStudentSystem وتحويل نصوصه القديمة الى كائنات StudentAccount. |
| Decorator | Structural | StudentService, LibraryDecorator, RestaurantDecorator, InternetDecorator | اضافة خدمات اختيارية للطالب بتوليفات مختلفة وحساب الرسوم ديناميكيا دون وراثة متعددة. |
| Facade | Structural | UniAccountsFacade | اخفاء تعقيد النظام وتقديم واجهة بسيطة وموحدة للعميل. |

---

## اوامر Git لإنشاء الوسوم (Tags)

```bash
# العمل الاول
git init
git add .
git commit -m "TP1: Creational patterns (Builder, Singleton, Prototype)"
git tag work1

# العمل الثاني
git add .
git commit -m "TP2: Full system with Structural and Behavioral patterns"
git tag work2
```
