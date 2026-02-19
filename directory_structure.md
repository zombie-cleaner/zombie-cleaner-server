spring-boot-app/
│
├── pom.xml                                    # Maven build configuration
├── README.md                                  # Project documentation
├── .gitignore                                 # Git ignore rules
├── User-Management-API.postman_collection.json # API testing collection
│
└── src/
└── main/
├── java/
│   └── com/
│       └── example/
│           └── usermanagement/
│               │
│               ├── config/                      # Configuration Layer
│               │   ├── AppConfig.java          # Bean configurations (ModelMapper)
│               │   └── SecurityConfig.java     # Security & JWT configuration
│               │
│               ├── controller/                  # Presentation Layer (REST API)
│               │   ├── AuthController.java     # /api/auth/* endpoints
│               │   └── UserController.java     # /api/users/* endpoints
│               │
│               ├── dto/                        # Data Transfer Objects
│               │   ├── request/               # Input DTOs
│               │   │   ├── LoginRequest.java
│               │   │   ├── UserRegistrationRequest.java
│               │   │   └── UserUpdateRequest.java
│               │   └── response/              # Output DTOs
│               │       ├── ApiResponse.java         # Standard response wrapper
│               │       ├── AuthenticationResponse.java
│               │       └── UserResponse.java
│               │
│               ├── entity/                     # Persistence Layer (Domain Models)
│               │   └── User.java              # User JPA entity
│               │
│               ├── exception/                  # Exception Handling
│               │   ├── AuthenticationException.java
│               │   ├── DuplicateResourceException.java
│               │   ├── GlobalExceptionHandler.java  # @RestControllerAdvice
│               │   └── ResourceNotFoundException.java
│               │
│               ├── repository/                 # Data Access Layer
│               │   └── UserRepository.java    # JPA Repository
│               │
│               ├── security/                   # Security Components
│               │   ├── CustomUserDetailsService.java
│               │   ├── JwtAuthenticationFilter.java
│               │   └── JwtTokenProvider.java
│               │
│               ├── service/                    # Business Logic Layer
│               │   ├── AuthService.java       # Interface
│               │   ├── UserService.java       # Interface
│               │   └── impl/                  # Implementations
│               │       ├── AuthServiceImpl.java
│               │       └── UserServiceImpl.java
│               │
│               └── UserManagementApplication.java  # Main Spring Boot class
│
└── resources/
└── application.properties         # Application configuration