# دليل التشغيل وسيناريوهات الاختبار - UniAccounts

هذا الدليل يشرح كيفية بناء وتشغيل المشروع واختبار جميع نماذج التصميم العشرة.

---

## اوامر التشغيل عبر الطرفية (Terminal / PowerShell)

### 1. الترجمة (Compilation)
```powershell
New-Item -ItemType Directory -Force -Path bin
javac -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

### 2. تشغيل العمل الاول (TP 1)
```powershell
java -cp bin dz.univ.accounts.MainWork1
```
المخرجات المتوقعة في العمل الاول:
- التحقق من ان repo1 == repo2 و logger1 == logger2 (نمط Singleton).
- بناء حساب طالب بالـ Builder مع التحقق من صحة البريد والمستوى.
- محاولات ادخال خاطئة والتقاط الاستثناءات (Negative Tests).
- استنساخ قالب دفعة وتعديل هوية الطالب مع اثبات النسخ العميق (Prototype).

---

### 3. تشغيل العمل الثاني الشامل (TP 2)
```powershell
java -cp bin dz.univ.accounts.MainWork2
```
المخرجات المتوقعة في العمل الثاني:
- دورة حياة الحساب: PENDING -> منع الدخول -> ACTIVE -> نجاح الدخول -> SUSPENDED -> حظر الدخول -> CLOSED -> منع الدخول والتفعيل (نمط State).
- اشعارات المراقبين الثلاثة: طباعة البريد، تدوين التدقيق، وتحديث عدادات لوحة التحكم (نمط Observer).
- استيراد 10 حسابات قديمة من LegacyStudentSystem عبر المحول (نمط Adapter).
- اشتراك في الخدمات الطلابية بتوليفات مختلفة وحساب الرسوم (نمط Decorator).
- تصدير البيانات بصيغتي CSV و JSON (نمط Strategy).
- طباعة الشجرة الهرمية وحساب عدد الطلبة تلقائيا عبر countAccounts() (نمط Composite).
- لوحة تحكم تلخص مجموع العمليات المنجزة.
