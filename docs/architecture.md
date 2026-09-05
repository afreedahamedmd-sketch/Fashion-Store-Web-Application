# 🏗️ System Architecture & Flow Sequence Documentation

This document describes the architectural design pattern, package component topology, class diagrams, and end-to-end sequence diagrams for all functional workflows in the **FashionStore** Java Web Application.

---

## 🏛️ Model-View-Controller (MVC) Architectural Pattern

FashionStore implements a clean, layered **Model-View-Controller (MVC)** architecture built on the Jakarta EE Enterprise Web Standard:

```mermaid
graph TB
    subgraph Client Tier
        UserAgent[🌐 Client Browser / User Agent]
    end

    subgraph Controller Tier (com.fashionstore.controller)
        HomeSvc[HomeServlet /home]
        ProdListSvc[ProductListServlet /products]
        ProdDetSvc[ProductDetailsServlet /product-details]
        CartSvc[CartServlet /cart]
        CheckSvc[CheckoutServlet /checkout]
        OrdSvc[OrdersServlet /orders]
        OrdSuccSvc[OrderSuccessServlet /order-success]
        AuthSvc[LoginServlet /login & RegisterServlet /register]
        ProfSvc[ProfileServlet /profile]
    end

    subgraph Service / Data Access Tier (com.fashionstore.dao)
        UserDAO[UserDAO / UserDAOImpl]
        CatDAO[CategoryDAO / CategoryDAOImpl]
        ProdDAO[ProductDAO / ProductDAOImpl]
        VarDAO[ProductVariantDAO / ProductVariantDAOImpl]
        ImgDAO[ProductImageDAO / ProductImageDAOImpl]
        CartDAO[CartDAO / CartDAOImpl]
        CartItemDAO[CartItemDAO / CartItemDAOImpl]
        CheckDAO[CheckoutDAO / CheckoutDAOImpl]
        OrdDAO[OrderDAO / OrderDAOImpl]
        OrdItemDAO[OrderItemDAO / OrderItemDAOImpl]
    end

    subgraph Model Tier (com.fashionstore.model)
        User[User]
        Product[Product]
        Category[Category]
        Variant[ProductVariant]
        Image[ProductImage]
        Cart[Cart]
        CartItem[CartItem]
        Order[Order]
        OrderItem[OrderItem]
    end

    subgraph Presentation Tier (WEB-INF/views)
        JSPViews[JSP Views & Partials<br/>home.jsp, products.jsp, product-details.jsp,<br/>cart.jsp, checkout.jsp, orders.jsp, etc.]
    end

    subgraph Database Tier (MySQL 8.0+)
        MySQL[(MySQL Database<br/>fashion_store)]
    end

    UserAgent -->|HTTP GET/POST Requests| Controller Tier
    Controller Tier -->|Instantiates / Invokes| Service / Data Access Tier
    Service / Data Access Tier -->|Maps Data To / From| Model Tier
    Service / Data Access Tier -->|JDBC PreparedStatement Queries| Database Tier
    Controller Tier -->|Attaches Models to Request Attributes| Presentation Tier
    Presentation Tier -->|Renders HTML Response| UserAgent
```

---

## 📦 Package Topology

```
src/main/java/com/fashionstore/
├── controller/            # Jakarta HTTP Servlets handling web routing, sessions & form processing
│   ├── HomeServlet.java
│   ├── ProductListServlet.java
│   ├── ProductDetailsServlet.java
│   ├── CartServlet.java
│   ├── CheckoutServlet.java
│   ├── OrdersServlet.java
│   ├── OrderSuccessServlet.java
│   ├── ProfileServlet.java
│   ├── LoginServlet.java
│   ├── RegisterServlet.java
│   └── LogoutServlet.java
├── dao/                   # Data Access Object Interfaces (Decoupled contracts)
│   ├── UserDAO.java
│   ├── CategoryDAO.java
│   ├── ProductDAO.java
│   ├── ProductVariantDAO.java
│   ├── ProductImageDAO.java
│   ├── CartDAO.java
│   ├── CartItemDAO.java
│   ├── CheckoutDAO.java
│   ├── OrderDAO.java
│   └── OrderItemDAO.java
│   └── impl/              # Data Access Object Concrete JDBC Implementations
│       ├── UserDAOImpl.java
│       ├── CategoryDAOImpl.java
│       ├── ProductDAOImpl.java
│       ├── ProductVariantDAOImpl.java
│       ├── ProductImageDAOImpl.java
│       ├── CartDAOImpl.java
│       ├── CartItemDAOImpl.java
│       ├── CheckoutDAOImpl.java
│       ├── OrderDAOImpl.java
│       └── OrderItemDAOImpl.java
├── model/                 # Plain Old Java Objects (POJOs) representing domain entities
│   ├── User.java
│   ├── Category.java
│   ├── Product.java
│   ├── ProductVariant.java
│   ├── ProductImage.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Order.java
│   └── OrderItem.java
└── util/                  # Technical infrastructure & utility classes
    ├── DBConnection.java  # JDBC DriverManager Connection Factory
    └── PasswordUtility.java # BCrypt Hash verification utility
```

---

## 📊 Class Diagrams

### 1. Model Domain Class Diagram

```mermaid
classDiagram
    class User {
        -int userId
        -String userName
        -String email
        -String password
        -String phone
        -String address
        -String city
        -String state
        -String pincode
        -LocalDateTime createdDate
        -LocalDateTime lastLoginDate
        +getUserId() int
        +setUserId(int) void
        +getUserName() String
        +getEmail() String
        +getPassword() String
    }

    class Category {
        -int categoryId
        -String categoryName
        +getCategoryId() int
        +getCategoryName() String
    }

    class Product {
        -int productId
        -int categoryId
        -String productName
        -String description
        -BigDecimal price
        -BigDecimal discount
        -String gender
        -String brand
        -boolean isAvailable
        -LocalDateTime createdDate
        +getProductId() int
        +getPrice() BigDecimal
    }

    class ProductVariant {
        -int variantId
        -int productId
        -String size
        -int stock
        +getVariantId() int
        +getSize() String
        +getStock() int
    }

    class ProductImage {
        -int imageId
        -int productId
        -String imagePath
        -boolean isPrimary
        +getImagePath() String
        +isPrimary() boolean
    }

    class Cart {
        -int cartId
        -int userId
        -LocalDateTime createdDate
        -LocalDateTime updatedDate
        +getCartId() int
        +getUserId() int
    }

    class CartItem {
        -int cartItemId
        -int cartId
        -int variantId
        -int quantity
        -int productId
        -String productName
        -BigDecimal price
        -String size
        -int stock
        -String imagePath
        +getCartItemId() int
        +getQuantity() int
    }

    class Order {
        -int orderId
        -int userId
        -LocalDateTime orderDate
        -BigDecimal totalAmount
        -String status
        -String paymentMethod
        -String shippingAddress
        -String city
        -String state
        -String pincode
        +getOrderId() int
        +getTotalAmount() BigDecimal
    }

    class OrderItem {
        -int orderItemId
        -int orderId
        -int variantId
        -int quantity
        -BigDecimal price
        +getOrderItemId() int
        +getPrice() BigDecimal
    }

    User "1" -- "0..*" Cart : owns
    User "1" -- "0..*" Order : places
    Category "1" -- "0..*" Product : classifies
    Product "1" -- "0..*" ProductVariant : has
    Product "1" -- "0..*" ProductImage : media
    Cart "1" -- "0..*" CartItem : contains
    ProductVariant "1" -- "0..*" CartItem : references
    Order "1" -- "0..*" OrderItem : contains
    ProductVariant "1" -- "0..*" OrderItem : references
```

---

### 2. DAO Layer Hierarchy & DB Utility Diagram

```mermaid
classDiagram
    class DBConnection {
        -String URL$
        -String USERNAME$
        -String PASSWORD$
        +getConnection()$ Connection
    }

    class PasswordUtility {
        +hashPassword(String)$ String
        +checkPassword(String, String)$ boolean
    }

    class UserDAO {
        <<interface>>
        +addUser(User) boolean
        +getUserById(int) User
        +getUserByEmail(String) User
        +updateUser(User) boolean
        +updateLastLogin(int) boolean
        +getAllUsers() List~User~
    }

    class UserDAOImpl {
        +addUser(User) boolean
        +getUserById(int) User
        +getUserByEmail(String) User
        +updateUser(User) boolean
        +updateLastLogin(int) boolean
        +getAllUsers() List~User~
    }

    class ProductDAO {
        <<interface>>
        +getProductById(int) Product
        +getAllProducts() List~Product~
        +filterProducts(String, Integer, String, Double, Double, String) List~Product~
        +getFeaturedProducts(int) List~Product~
    }

    class ProductDAOImpl {
        +getProductById(int) Product
        +getAllProducts() List~Product~
        +filterProducts(...) List~Product~
        +getFeaturedProducts(int) List~Product~
    }

    class CheckoutDAO {
        <<interface>>
        +placeOrder(Order, int, BigDecimal, BigDecimal, BigDecimal) int
    }

    class CheckoutDAOImpl {
        +placeOrder(Order, int, BigDecimal, BigDecimal, BigDecimal) int
    }

    UserDAO <|.. UserDAOImpl
    ProductDAO <|.. ProductDAOImpl
    CheckoutDAO <|.. CheckoutDAOImpl
    UserDAOImpl ..> DBConnection : uses
    ProductDAOImpl ..> DBConnection : uses
    CheckoutDAOImpl ..> DBConnection : uses
```

---

## 🔁 Detailed Sequence Diagrams

### Flow 1: User Registration & Authentication Flow

Covers user registration, credential hashing with BCrypt, authentication validation, session creation, and logout.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant RegSvc as RegisterServlet (/register)
    participant LogSvc as LoginServlet (/login)
    participant PassUtil as PasswordUtility
    participant UserDAO as UserDAOImpl
    participant DB as MySQL Database
    participant Session as HTTP Session

    %% Registration Flow
    Note over Client, DB: --- User Registration Flow ---
    Client->>RegSvc: POST /register (name, email, password, phone, address, city, state, pincode)
    RegSvc->>UserDAO: getUserByEmail(email)
    UserDAO->>DB: SELECT * FROM users WHERE email = ?
    DB-->>UserDAO: ResultSet (null)
    UserDAO-->>RegSvc: null (Email Available)
    RegSvc->>PassUtil: hashPassword(plainPassword)
    PassUtil-->>RegSvc: bcryptHashedPassword
    RegSvc->>UserDAO: addUser(newUser)
    UserDAO->>DB: INSERT INTO users (...) VALUES (...)
    DB-->>UserDAO: Success (1 row inserted)
    UserDAO-->>RegSvc: true
    RegSvc-->>Client: Redirect to /login?registered=true

    %% Login Flow
    Note over Client, DB: --- User Login Flow ---
    Client->>LogSvc: POST /login (email, password)
    LogSvc->>UserDAO: getUserByEmail(email)
    UserDAO->>DB: SELECT * FROM users WHERE email = ?
    DB-->>UserDAO: User Entity Record
    UserDAO-->>LogSvc: user
    LogSvc->>PassUtil: checkPassword(inputPassword, storedHash)
    PassUtil-->>LogSvc: true (Password Matches)
    LogSvc->>UserDAO: updateLastLogin(userId)
    UserDAO->>DB: UPDATE users SET lastLoginDate = NOW() WHERE userId = ?
    DB-->>UserDAO: Success
    LogSvc->>Session: setAttribute("userId", user.getUserId())
    LogSvc->>Session: setAttribute("userName", user.getUserName())
    LogSvc-->>Client: Redirect to /home
```

---

### Flow 2: Product Catalog Browsing & Multi-Criteria Filtering Flow

Demonstrates searching by keyword, category filtering, price range criteria, and gender/size selections.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant ListSvc as ProductListServlet (/products)
    participant ProdDAO as ProductDAOImpl
    participant CatDAO as CategoryDAOImpl
    participant ImgDAO as ProductImageDAOImpl
    participant DB as MySQL Database

    Client->>ListSvc: GET /products?search=Jeans&category=1&gender=Women&minPrice=1000&maxPrice=2500&size=M
    ListSvc->>CatDAO: getAllCategories()
    CatDAO->>DB: SELECT * FROM categories ORDER BY categoryName
    DB-->>CatDAO: List<Category>
    CatDAO-->>ListSvc: categories

    ListSvc->>ProdDAO: filterProducts("Jeans", 1, "Women", 1000.0, 2500.0, "M")
    ProdDAO->>DB: SELECT DISTINCT p.* FROM products p JOIN product_variants pv ON ... WHERE ...
    DB-->>ProdDAO: List<Product>
    ProdDAO-->>ListSvc: products

    loop For each Product
        ListSvc->>ImgDAO: getPrimaryImageByProductId(productId)
        ImgDAO->>DB: SELECT * FROM product_images WHERE productId = ? AND isPrimary = TRUE
        DB-->>ImgDAO: ProductImage
        ImgDAO-->>ListSvc: primaryImage
    end

    ListSvc->>Client: Forward to /WEB-INF/views/products.jsp (with products, categories, filters)
```

---

### Flow 3: Product Details & Variant Selection Flow

Retrieves detailed product metadata, image gallery, and inventory stock availability per size.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant DetSvc as ProductDetailsServlet (/product-details)
    participant ProdDAO as ProductDAOImpl
    participant VarDAO as ProductVariantDAOImpl
    participant ImgDAO as ProductImageDAOImpl
    participant DB as MySQL Database

    Client->>DetSvc: GET /product-details?id=5
    DetSvc->>ProdDAO: getProductById(5)
    ProdDAO->>DB: SELECT * FROM products WHERE productId = 5
    DB-->>ProdDAO: Product
    ProdDAO-->>DetSvc: product

    DetSvc->>VarDAO: getVariantsByProductId(5)
    VarDAO->>DB: SELECT * FROM product_variants WHERE productId = 5
    DB-->>VarDAO: List<ProductVariant> (Sizes S, M, L, XL with stock)
    VarDAO-->>DetSvc: variants

    DetSvc->>ImgDAO: getImagesByProductId(5)
    ImgDAO->>DB: SELECT * FROM product_images WHERE productId = 5
    DB-->>ImgDAO: List<ProductImage> (Image gallery URLs)
    ImgDAO-->>DetSvc: images

    DetSvc->>Client: Forward to /WEB-INF/views/product-details.jsp
```

---

### Flow 4: Shopping Cart Operations Flow (Add, Update, Remove)

Covers adding items to cart, stock verification, quantity updates, and deletion.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant CartSvc as CartServlet (/cart)
    participant CartDAO as CartDAOImpl
    participant CartItemDAO as CartItemDAOImpl
    participant VarDAO as ProductVariantDAOImpl
    participant DB as MySQL Database

    %% Add to Cart
    Note over Client, DB: --- Add Item to Cart (action=add) ---
    Client->>CartSvc: POST /cart (action=add, variantId=12, quantity=2)
    CartSvc->>VarDAO: getVariantById(12)
    VarDAO->>DB: SELECT * FROM product_variants WHERE variantId = 12
    DB-->>VarDAO: ProductVariant (stock=10)
    VarDAO-->>CartSvc: variant

    CartSvc->>CartDAO: getCartByUserId(userId)
    CartDAO->>DB: SELECT * FROM cart WHERE userId = ?
    DB-->>CartDAO: Cart (cartId=3)
    CartDAO-->>CartSvc: cart

    CartSvc->>CartItemDAO: getCartItemsByCartId(3)
    CartItemDAO->>DB: SELECT * FROM cart_items WHERE cartId = 3
    DB-->>CartItemDAO: List<CartItem>
    CartItemDAO-->>CartSvc: existingItems

    CartSvc->>CartItemDAO: addCartItem(newCartItem)
    CartItemDAO->>DB: INSERT INTO cart_items (cartId, variantId, quantity) VALUES (3, 12, 2)
    DB-->>CartItemDAO: Success
    CartItemDAO-->>CartSvc: true
    CartSvc-->>Client: Redirect to /cart

    %% View Cart
    Note over Client, DB: --- Render Cart Page ---
    Client->>CartSvc: GET /cart
    CartSvc->>CartDAO: getCartByUserId(userId)
    CartDAO-->>CartSvc: cart (cartId=3)
    CartSvc->>CartItemDAO: getCartItemsWithProductDetails(3)
    CartItemDAO->>DB: JOIN cart_items, product_variants, products, product_images
    DB-->>CartItemDAO: List<CartItem> (Detailed product info, stock, price)
    CartItemDAO-->>CartSvc: cartItems
    CartSvc->>Client: Forward to /WEB-INF/views/cart.jsp
```

---

### Flow 5: Transactional Checkout & Order Processing Flow

Illustrates the transaction boundary, SQL row locking (`FOR UPDATE`), server-side total recalculation, stock deduction, and cart cleanup.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant CheckSvc as CheckoutServlet (/checkout)
    participant CheckDAO as CheckoutDAOImpl
    participant DB as MySQL Database

    Client->>CheckSvc: POST /checkout (paymentMethod, shippingAddress, city, state, pincode)
    CheckSvc->>CheckDAO: placeOrder(order, cartId, subtotal, shipping, total)
    
    rect rgb(240, 248, 255)
        Note over CheckDAO, DB: --- BEGIN ACID TRANSACTION (setAutoCommit(false)) ---
        CheckDAO->>DB: SELECT ci.variantId, ci.quantity, pv.stock, p.price FROM cart_items ci JOIN product_variants pv ... WHERE ci.cartId = ? FOR UPDATE
        DB-->>CheckDAO: ResultSet (Locked cart items with current stock and prices)
        
        Note over CheckDAO: Recalculate Subtotal & Free Shipping Rule (Threshold >= ₹2000)
        
        CheckDAO->>DB: INSERT INTO orders (userId, totalAmount, status, paymentMethod, shippingAddress, city, state, pincode) VALUES (...)
        DB-->>CheckDAO: Generated Order ID (e.g. orderId = 101)
        
        loop For each Cart Item
            CheckDAO->>DB: INSERT INTO order_items (orderId, variantId, quantity, price) VALUES (101, variantId, quantity, price)
            CheckDAO->>DB: UPDATE product_variants SET stock = stock - ? WHERE variantId = ? AND stock >= ?
        end
        
        CheckDAO->>DB: DELETE FROM cart_items WHERE cartId = ?
        CheckDAO->>DB: COMMIT TRANSACTION
        Note over CheckDAO, DB: --- END TRANSACTION ---
    end

    CheckDAO-->>CheckSvc: orderId (101)
    CheckSvc-->>Client: Redirect to /order-success?orderId=101
```

---

### Flow 6: Order History & Order Details Flow

Demonstrates retrieving past orders and line item summaries.

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant OrdSvc as OrdersServlet (/orders)
    participant OrdDAO as OrderDAOImpl
    participant OrdItemDAO as OrderItemDAOImpl
    participant DB as MySQL Database

    Client->>OrdSvc: GET /orders
    OrdSvc->>OrdDAO: getOrdersByUserId(userId)
    OrdDAO->>DB: SELECT * FROM orders WHERE userId = ? ORDER BY orderDate DESC
    DB-->>OrdDAO: List<Order>
    OrdDAO-->>OrdSvc: orders

    loop For each Order
        OrdSvc->>OrdItemDAO: getOrderItemsWithProductDetails(orderId)
        OrdItemDAO->>DB: SELECT oi.*, p.productName, p.brand, pv.size, pi.imagePath FROM order_items oi JOIN ...
        DB-->>OrdItemDAO: List<OrderItem>
        OrdItemDAO-->>OrdSvc: orderItems
    end

    OrdSvc->>Client: Forward to /WEB-INF/views/orders.jsp
```

---

### Flow 7: User Profile Management Flow

Updating personal user information (Address, Phone, City, State, Pincode).

```mermaid
sequenceDiagram
    autonumber
    actor Client as User / Browser
    participant ProfSvc as ProfileServlet (/profile)
    participant UserDAO as UserDAOImpl
    participant DB as MySQL Database

    Client->>ProfSvc: POST /profile (userName, phone, address, city, state, pincode)
    ProfSvc->>UserDAO: getUserById(userId)
    UserDAO->>DB: SELECT * FROM users WHERE userId = ?
    DB-->>UserDAO: User Entity
    UserDAO-->>ProfSvc: currentUser

    ProfSvc->>UserDAO: updateUser(updatedUser)
    UserDAO->>DB: UPDATE users SET userName=?, phone=?, address=?, city=?, state=?, pincode=? WHERE userId=?
    DB-->>UserDAO: Success (1 row updated)
    UserDAO-->>ProfSvc: true

    ProfSvc-->>Client: Redirect to /profile?updated=true
```
