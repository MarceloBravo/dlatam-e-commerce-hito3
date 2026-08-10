# E-Shoping

Aplicación de comercio electrónico que gestiona un carrito de compras con productos, marcas y categorías.

Proyecto integrador reestructurado bajo **Arquitectura Limpia** y patrones tácticos de **DDD** (patrón Repositorio como frontera tecnológica). El núcleo de negocio es **Java puro**: las capas internas (`domain` y `application`) no dependen de frameworks ni librerías externas.

## Arquitectura

La solución separa estrictamente tres capas con regla de dependencia unidireccional (las capas externas dependen de las internas, jamás al revés):

```
e-shoping/
└── src/
    ├── main/java/com/mabc/
    │   ├── domain/                    <- Java puro, CERO frameworks
    │   │   ├── entity/                <- Product, Cart, CartItem, Category, Mark
    │   │   ├── valueobject/           <- Name, Description, Price, Stock, Weight, Quantity (records)
    │   │   ├── exception/             <- DomainException, InvalidNameException, ...
    │   │   └── repository/            <- Contratos abstractos (interfaces puras)
    │   ├── application/
    │   │   └── usecase/               <- CreateProductUseCase, AddItemToCartUseCase, ...
    │   └── infrastructure/
    │       └── persistence/           <- Spring Data JPA, entidades JPA, adaptadores, in-memory
    └── test/java/com/mabc/
        ├── domain/                    <- Tests de entidades y Value Objects
        └── application/usecase/       <- Tests de casos de uso (desacoplamiento)
```

- **`domain`**: Entidades con identidad única e inmutable (`id` final), Value Objects auto-validantes (`record` con validación defensiva en el constructor) y excepciones de dominio. No contiene importaciones ni anotaciones de Jakarta, Spring, Jackson o JPA.
- **`application`**: Casos de uso cohesivos (un solo flujo de negocio) que reciben la interfaz del repositorio por **inyección por constructor**, sin instanciar implementaciones concretas (`new`).
- **`infrastructure`**: Implementaciones tecnológicas de los contratos de dominio: repositorios Spring Data JPA, entidades JPA, mapeadores dominio↔persistencia y una implementación en memoria para la ejecución de demostración.

## Ejecución

Todos los comandos se ejecutan desde la raíz del módulo Maven (`e-shoping/`).

Para compilar y verificar el proyecto:

```bash
mvn clean compile
```

Para ejecutar la suite de pruebas unitarias que validan el desacoplamiento (38 tests + reporte de cobertura JaCoCo):

```bash
mvn test
```

Para ejecutar la aplicación de demostración (wiring de implementaciones en casos de uso):

```bash
mvn exec:java -Dexec.mainClass="com.mabc.App"
```
