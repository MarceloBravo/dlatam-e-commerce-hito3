# E-Shoping

Aplicacion de comercio electronico que gestiona un carrito de compras con productos, marcas y categorias.

## Tecnologias

- Java 25
- Maven
- Spring Data JPA
- Hibernate
- Jakarta Persistence
- JUnit 5 + Mockito
- JaCoCo (cobertura de codigo)

## Estructura del proyecto

```
e-shoping/
├── pom.xml
└── src/
    ├── main/java/com/mabc/
    │   ├── App.java
    │   ├── dto/
    │   │   ├── CartDTO.java
    │   │   ├── CartItemDTO.java
    │   │   ├── CategoryDTO.java
    │   │   ├── MarkDTO.java
    │   │   └── ProductDTO.java
    │   ├── entities/
    │   │   ├── Cart.java
    │   │   ├── CartItem.java
    │   │   ├── Category.java
    │   │   ├── Mark.java
    │   │   └── Product.java
    │   ├── repositories/
    │   │   ├── CartRepository.java
    │   │   ├── CategoryRepository.java
    │   │   ├── MarkRepository.java
    │   │   └── ProductRepository.java
    │   └── services/
    │       ├── cart/
    │       │   ├── CartService.java
    │       │   └── CartServiceImpl.java
    │       ├── category/
    │       │   ├── CategoryService.java
    │       │   └── CategoryServiceImpl.java
    │       ├── mark/
    │       │   ├── MarkService.java
    │       │   └── MarkServiceImpl.java
    │       └── product/
    │           ├── ProductService.java
    │           └── ProductServiceImpl.java
    └── test/java/com/mabc/
        ├── CartServiceImplTest.java
        ├── CategoryServiceImplTest.java
        ├── ConsoleCoverageReporter.java
        ├── MarkServiceImplTest.java
        └── ProductServiceImplTest.java
```

## Ejecucion de tests

Desde la raiz del proyecto (`e-shoping/`):

```bash
mvn test
```

Esto ejecuta los 38 tests y genera un reporte de cobertura en consola.

### Ver reporte de cobertura en HTML

```bash
mvn test
start target/site/jacoco/index.html
```

### Ver reporte de cobertura en consola

```bash
mvn exec:java -Dexec.mainClass="com.mabc.ConsoleCoverageReporter"
```

## Metricas de cobertura

| Metrica       | Cobertura  |
|---------------|------------|
| INSTRUCTION   | 95.85%     |
| BRANCH        | 100.00%    |
| LINE          | 94.70%     |
