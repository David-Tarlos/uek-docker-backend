# Sequenzdiagramm: Admin Login & User Löschen

## Use Case Beschreibung

Ein Administrator meldet sich im System an und löscht anschließend einen Benutzer. Dieser Use Case zeigt den kompletten Authentifizierungs- und Autorisierungsflow mit JWT-Tokens.

---

## Sequenzdiagramm

```mermaid
sequenceDiagram
    autonumber
    participant Client
    participant WebSecurityConfig
    participant JWTAuthenticationFilter
    participant AuthenticationManager
    participant UserServiceImpl
    participant UserDetailsImpl
    participant BCryptEncoder
    participant Database
    participant JWTAuthorizationFilter
    participant UserController
    participant UserRepository

    Note over Client,Database: PHASE 1: LOGIN & JWT TOKEN GENERIERUNG

    Client->>+WebSecurityConfig: POST /user/login<br/>{"email": "admin@example.com", "password": "1234"}
    WebSecurityConfig->>+JWTAuthenticationFilter: Request durchläuft Filter Chain

    JWTAuthenticationFilter->>JWTAuthenticationFilter: attemptAuthentication()<br/>Email & Password aus JSON extrahieren
    JWTAuthenticationFilter->>+AuthenticationManager: authenticate(UsernamePasswordAuthenticationToken)

    AuthenticationManager->>+UserServiceImpl: loadUserByUsername("admin@example.com")
    UserServiceImpl->>+Database: SELECT * FROM users WHERE email = ?
    Database-->>-UserServiceImpl: User Entity (mit gehashtem Passwort)
    UserServiceImpl->>+UserDetailsImpl: new UserDetailsImpl(user)
    UserDetailsImpl->>UserDetailsImpl: getAuthorities()<br/>Roles zu Authorities flatten
    UserDetailsImpl-->>-UserServiceImpl: UserDetails mit Authorities
    UserServiceImpl-->>-AuthenticationManager: UserDetails

    AuthenticationManager->>+BCryptEncoder: matches("1234", hashedPassword)
    BCryptEncoder-->>-AuthenticationManager: true
    AuthenticationManager-->>-JWTAuthenticationFilter: Authentication (authenticated=true)

    JWTAuthenticationFilter->>JWTAuthenticationFilter: successfulAuthentication()<br/>JWT Token generieren
    Note right of JWTAuthenticationFilter: Token enthält:<br/>- sub: User-UUID<br/>- authorities: [USER_DELETE, ...]<br/>- exp: Ablaufzeit<br/>- iss: "uk223"

    JWTAuthenticationFilter-->>-WebSecurityConfig: Response mit Authorization Header
    WebSecurityConfig-->>-Client: 200 OK<br/>Header: Authorization: Bearer eyJhbG...

    Note over Client,Database: PHASE 2: GESCHÜTZTE AKTION - USER LÖSCHEN

    Client->>+WebSecurityConfig: DELETE /user/{targetUserId}<br/>Header: Authorization: Bearer eyJhbG...
    WebSecurityConfig->>+JWTAuthorizationFilter: Request durchläuft Filter Chain

    JWTAuthorizationFilter->>JWTAuthorizationFilter: doFilterInternal()<br/>Token aus Header extrahieren
    JWTAuthorizationFilter->>JWTAuthorizationFilter: JWT Signatur validieren<br/>mit Secret Key

    alt Token ungültig oder abgelaufen
        JWTAuthorizationFilter-->>Client: 401 Unauthorized
    end

    JWTAuthorizationFilter->>JWTAuthorizationFilter: Claims extrahieren<br/>(User-ID, Authorities)
    JWTAuthorizationFilter->>+UserServiceImpl: findById(userUUID)
    UserServiceImpl->>+Database: SELECT * FROM users WHERE id = ?
    Database-->>-UserServiceImpl: User Entity
    UserServiceImpl-->>-JWTAuthorizationFilter: User

    JWTAuthorizationFilter->>JWTAuthorizationFilter: SecurityContextHolder<br/>.setAuthentication()
    JWTAuthorizationFilter-->>-WebSecurityConfig: Request weitergeleitet

    WebSecurityConfig->>+UserController: deleteById(targetUserId)

    Note over UserController: @PreAuthorize("hasAuthority('USER_DELETE')")
    UserController->>UserController: Spring Security prüft<br/>Authorities im SecurityContext

    alt Keine USER_DELETE Authority
        UserController-->>Client: 403 Forbidden
    end

    UserController->>+UserServiceImpl: deleteById(targetUserId)
    UserServiceImpl->>+UserRepository: deleteById(targetUserId)
    UserRepository->>+Database: DELETE FROM users WHERE id = ?
    Database-->>-UserRepository: 1 row deleted
    UserRepository-->>-UserServiceImpl: void
    UserServiceImpl-->>-UserController: void

    UserController-->>-WebSecurityConfig: ResponseEntity(204 NO_CONTENT)
    WebSecurityConfig-->>-Client: 204 No Content

    Note over Client,Database: User erfolgreich gelöscht!
```

---

## Erklärung der Phasen

### Phase 1: Login & JWT Token Generierung

| Schritt | Komponente | Beschreibung |
|---------|------------|--------------|
| 1-2 | Client → WebSecurityConfig | Client sendet Login-Request mit Email und Passwort |
| 3-4 | JWTAuthenticationFilter | Extrahiert Credentials aus dem JSON-Body |
| 5-8 | AuthenticationManager → UserServiceImpl | Lädt User aus Datenbank anhand der Email |
| 9-10 | UserDetailsImpl | Konvertiert User-Rollen zu Spring Security Authorities |
| 11-12 | BCryptEncoder | Vergleicht eingegebenes Passwort mit gehashtem Passwort |
| 13-14 | JWTAuthenticationFilter | Generiert JWT-Token mit User-ID und Authorities |
| 15-16 | Response | Token wird im Authorization-Header zurückgegeben |

### Phase 2: Geschützte Aktion ausführen

| Schritt | Komponente | Beschreibung |
|---------|------------|--------------|
| 17-18 | Client → WebSecurityConfig | DELETE-Request mit JWT-Token im Header |
| 19-21 | JWTAuthorizationFilter | Extrahiert und validiert JWT-Token |
| 22 | Alt-Flow | Bei ungültigem Token: 401 Unauthorized |
| 23-26 | JWTAuthorizationFilter | Lädt User und setzt SecurityContext |
| 27-28 | UserController | Empfängt Request nach erfolgreicher Autorisierung |
| 29-30 | @PreAuthorize | Prüft ob USER_DELETE Authority vorhanden ist |
| 31 | Alt-Flow | Ohne Berechtigung: 403 Forbidden |
| 32-36 | UserServiceImpl → Database | Löscht User aus der Datenbank |
| 37-38 | Response | 204 No Content - Erfolgreich gelöscht |

---

## Beteiligte Dateien im Code

| Komponente | Datei | Pfad |
|------------|-------|------|
| WebSecurityConfig | `WebSecurityConfig.java` | `src/main/java/com/example/demo/core/security/` |
| JWTAuthenticationFilter | `JWTAuthenticationFilter.java` | `src/main/java/com/example/demo/core/security/` |
| JWTAuthorizationFilter | `JWTAuthorizationFilter.java` | `src/main/java/com/example/demo/core/security/` |
| UserServiceImpl | `UserServiceImpl.java` | `src/main/java/com/example/demo/domain/user/` |
| UserDetailsImpl | `UserDetailsImpl.java` | `src/main/java/com/example/demo/domain/user/` |
| UserController | `UserController.java` | `src/main/java/com/example/demo/domain/user/` |
| UserRepository | `UserRepository.java` | `src/main/java/com/example/demo/domain/user/` |
| BCryptEncoder | `Encoders.java` | `src/main/java/com/example/demo/core/security/` |

---

## Wichtige Sicherheitsaspekte

1. **Passwort-Hashing**: Passwörter werden nie im Klartext gespeichert (BCrypt)
2. **Stateless Authentication**: Keine Server-Sessions, alles im JWT-Token
3. **Token-Validierung**: Signatur wird bei jedem Request geprüft
4. **Role-Based Access Control**: Authorities bestimmen Zugriffsrechte
5. **Method-Level Security**: `@PreAuthorize` schützt einzelne Endpoints
