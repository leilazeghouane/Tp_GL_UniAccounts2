# مخططات الاصناف وحالات النظام (UML Diagrams)

---

## 1. مخطط حالات دورة حياة الحساب (State Machine Diagram)

يوضح انتقال الحساب بين الحالات الاربع (PENDING, ACTIVE, SUSPENDED, CLOSED):

```mermaid
stateDiagram-v2
    [*] --> PENDING: انشاء الحساب الجديد (Builder / Prototype)

    PENDING --> ACTIVE: activate() [تفعيل الحساب]
    PENDING --> CLOSED: close() [الغاء الحساب]

    ACTIVE --> SUSPENDED: suspend() [تعليق الحساب]
    ACTIVE --> CLOSED: close() [اغلاق عند التخرج]

    SUSPENDED --> ACTIVE: activate() [اعادة التفعيل]
    SUSPENDED --> CLOSED: close() [اغلاق الحساب نهائيا]

    CLOSED --> [*]: لا يمكن استعادة الحساب بعد الاغلاق
```

---

## 2. مخطط الاصناف العام للأنماط الانشائية (Work 1 - Creational Patterns)

```mermaid
classDiagram
    class StudentAccount {
        -String id
        -String fullName
        -String email
        -String phone
        -String level
        -String major
        -boolean scholarship
        -List~String~ courses
        -AccountState currentState
        +clone() StudentAccount
        +cloneWithNewIdentity(newId, newName, newEmail) StudentAccount
        +login() boolean
        +activate() void
        +suspend() void
        +close() void
    }

    class Builder {
        -String id
        -String fullName
        -String email
        -String phone
        -String level
        -String major
        -boolean scholarship
        -List~String~ courses
        +phone(String) Builder
        +level(String) Builder
        +major(String) Builder
        +scholarship(boolean) Builder
        +courses(List) Builder
        +build() StudentAccount
    }

    class AccountRepository {
        -static AccountRepository INSTANCE
        -Map~String, StudentAccount~ accountsMap
        -List~AccountObserver~ observers
        +getInstance() AccountRepository
        +addAccount(StudentAccount) void
        +findById(String) Optional~StudentAccount~
        +getAllAccounts() List~StudentAccount~
    }

    class AuditLogger {
        -static AuditLogger INSTANCE
        -List~String~ memoryLogs
        +getInstance() AuditLogger
        +log(action, details) void
        +getLogs() List~String~
    }

    StudentAccount +-- Builder : Nested Static Class
    AccountRepository ..> StudentAccount : stores
    AccountRepository ..> AuditLogger : logs operations
```

---

## 3. مخطط الاصناف الشامل للعملين معا (Work 2 - Comprehensive Class Diagram)

```mermaid
classDiagram
    %% Core & State
    class StudentAccount {
        -AccountState currentState
        +login() boolean
        +activate() void
        +suspend() void
        +close() void
    }

    class AccountState {
        <<interface>>
        +login(StudentAccount) boolean
        +activate(StudentAccount) void
        +suspend(StudentAccount) void
        +close(StudentAccount) void
    }
    class PendingState
    class ActiveState
    class SuspendedState
    class ClosedState

    AccountState <|.. PendingState
    AccountState <|.. ActiveState
    AccountState <|.. SuspendedState
    AccountState <|.. ClosedState
    StudentAccount o--> AccountState : state delegation

    %% Observer
    class AccountObserver {
        <<interface>>
        +onAccountEvent(AccountEvent) void
    }
    class AuditObserver
    class EmailNotifier
    class AdminDashboard

    AccountObserver <|.. AuditObserver
    AccountObserver <|.. EmailNotifier
    AccountObserver <|.. AdminDashboard
    AccountRepository o--> AccountObserver : notifies

    %% Strategy
    class ExportStrategy {
        <<interface>>
        +export(List~StudentAccount~) String
    }
    class CsvExport
    class JsonExport
    ExportStrategy <|.. CsvExport
    ExportStrategy <|.. JsonExport

    %% Adapter
    class StudentImporter {
        <<interface>>
        +importStudents() List~StudentAccount~
    }
    class LegacyStudentSystem {
        +fetchRecords() List~String~
    }
    class LegacyStudentAdapter {
        -LegacyStudentSystem legacySystem
        +importStudents() List~StudentAccount~
    }
    StudentImporter <|.. LegacyStudentAdapter
    LegacyStudentAdapter o--> LegacyStudentSystem : adapts

    %% Composite
    class UniversityComponent {
        <<interface>>
        +getName() String
        +countAccounts() int
        +printHierarchy(String) void
    }
    class StudentLeaf {
        -StudentAccount account
        +countAccounts() int
    }
    class BaseCompositeUnit {
        -List~UniversityComponent~ children
        +countAccounts() int
        +add(UniversityComponent) void
    }
    class GroupComposite
    class DepartmentComposite
    class FacultyComposite
    class UniversityComposite

    UniversityComponent <|.. StudentLeaf
    UniversityComponent <|.. BaseCompositeUnit
    BaseCompositeUnit <|-- GroupComposite
    BaseCompositeUnit <|-- DepartmentComposite
    BaseCompositeUnit <|-- FacultyComposite
    BaseCompositeUnit <|-- UniversityComposite
    BaseCompositeUnit o--> UniversityComponent : contains

    %% Decorator
    class StudentService {
        <<interface>>
        +getDescription() String
        +getMonthlyCost() double
        +getPrivileges() List~String~
    }
    class BasicStudentService
    class ServiceDecorator
    class LibraryDecorator
    class RestaurantDecorator
    class InternetDecorator

    StudentService <|.. BasicStudentService
    StudentService <|.. ServiceDecorator
    ServiceDecorator <|-- LibraryDecorator
    ServiceDecorator <|-- RestaurantDecorator
    ServiceDecorator <|-- InternetDecorator
    ServiceDecorator o--> StudentService : wraps

    %% Facade
    class UniAccountsFacade {
        -AccountRepository repository
        -AuditLogger logger
        -AdminDashboard dashboard
        +registerNewStudent(...) StudentAccount
        +importLegacyStudents() List~StudentAccount~
        +exportAccountsCsv() String
        +exportAccountsJson() String
        +addLibraryService(id) void
        +buildDemoUniversityHierarchy() UniversityComposite
    }
    UniAccountsFacade ..> StudentAccount
    UniAccountsFacade ..> AccountRepository
    UniAccountsFacade ..> ExportStrategy
    UniAccountsFacade ..> StudentImporter
    UniAccountsFacade ..> UniversityComposite
    UniAccountsFacade ..> StudentService
```
