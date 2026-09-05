# 🔄 Controller-to-DAO Mapping & Endpoint Action Specification

This document provides a comprehensive software engineering matrix detailing how each **Jakarta Servlet Controller** interacts with **DAO (Data Access Object)** classes, handles HTTP requests, parses parameters, manages session security, and routes data to JSP presentation views.

---

## 📊 Controller-to-DAO Interaction Matrix

The matrix below shows which DAO interfaces are instantiated and invoked by each Controller Servlet in the application:

| Controller Servlet | `UserDAO` | `CategoryDAO` | `ProductDAO` | `ProductVariantDAO` | `ProductImageDAO` | `CartDAO` | `CartItemDAO` | `CheckoutDAO` | `OrderDAO` | `OrderItemDAO` |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **`HomeServlet`** | | 🟢 | 🟢 | | 🟢 | | | | | |
| **`ProductListServlet`** | | 🟢 | 🟢 | | 🟢 | | | | | |
| **`ProductDetailsServlet`** | | | 🟢 | 🟢 | 🟢 | | | | | |
| **`CartServlet`** | | | | 🟢 | | 🟢 | 🟢 | | | |
| **`CheckoutServlet`** | 🟢 | | | | | 🟢 | 🟢 | 🟢 | | |
| **`OrdersServlet`** | | | | | | | | | 🟢 | 🟢 |
| **`OrderSuccessServlet`** | | | | | | | | | 🟢 | 🟢 |
| **`RegisterServlet`** | 🟢 | | | | | | | | | |
| **`LoginServlet`** | 🟢 | | | | | | | | | |
| **`LogoutServlet`** | | | | | | | | | | |
| **`ProfileServlet`** | 🟢 | | | | | | | | | |

*Key: 🟢 = Directly Instantiated & Invoked by Servlet*

---

## 🌐 Endpoint Action & Routing Specification Table

| Servlet Class | URL Pattern | Method | Key Parameters | DAOs & Methods Invoked | Session Auth | Forward / Redirect Target |
| :--- | :--- | :--- | :--- | :--- | :---: | :--- |
| `HomeServlet` | `/home` | `GET` | None | `categoryDAO.getAllCategories()`<br/>`productDAO.getAllProducts()`<br/>`productDAO.filterProducts(...)`<br/>`productImageDAO.getImagesByProductId(...)` | ❌ No | Forward: `/WEB-INF/views/home.jsp` |
| `ProductListServlet` | `/products` | `GET` | `search`, `category`, `gender`, `minPrice`, `maxPrice`, `size`, `sort` | `categoryDAO.getAllCategories()`<br/>`productDAO.filterProducts(...)`<br/>`productImageDAO.getImagesByProductId(...)` | ❌ No | Forward: `/WEB-INF/views/products.jsp` |
| `ProductDetailsServlet` | `/product-details` | `GET` | `id` (productId) | `productDAO.getProductById(id)`<br/>`productVariantDAO.getVariantsByProductId(id)`<br/>`productImageDAO.getImagesByProductId(id)` | ❌ No | Forward: `/WEB-INF/views/product-details.jsp` |
| `CartServlet` | `/cart` | `GET` | None | `cartDAO.getCartByUserId(userId)`<br/>`cartItemDAO.getCartItemsWithProductDetails(cartId)` | 🔒 Yes | Forward: `/WEB-INF/views/cart.jsp` |
| `CartServlet` | `/cart` | `POST` | `action` (`add`/`update`/`remove`), `variantId`, `quantity`, `cartItemId` | `productVariantDAO.getVariantById(...)`<br/>`cartDAO.getCartByUserId(...)`<br/>`cartDAO.addCart(...)`<br/>`cartItemDAO.addCartItem(...)`<br/>`cartItemDAO.updateCartItem(...)`<br/>`cartItemDAO.deleteCartItem(...)` | 🔒 Yes | Redirect: `/cart` |
| `CheckoutServlet` | `/checkout` | `GET` | None | `userDAO.getUserById(userId)`<br/>`cartDAO.getCartByUserId(userId)`<br/>`cartItemDAO.getCartItemsWithProductDetails(cartId)` | 🔒 Yes | Forward: `/WEB-INF/views/checkout.jsp` |
| `CheckoutServlet` | `/checkout` | `POST` | `userName`, `phone`, `shippingAddress`, `city`, `state`, `pincode`, `paymentMethod` | `cartDAO.getCartByUserId(userId)`<br/>`cartItemDAO.getCartItemsWithProductDetails(...)`<br/>`checkoutDAO.placeOrder(...)` | 🔒 Yes | Redirect: `/order-success?orderId={id}` (on success) or Reload Checkout (on error) |
| `OrdersServlet` | `/orders` | `GET` | None | `orderDAO.getOrdersByUserId(userId)`<br/>`orderItemDAO.getOrderItemsWithProductDetails(orderId)` | 🔒 Yes | Forward: `/WEB-INF/views/orders.jsp` |
| `OrderSuccessServlet` | `/order-success` | `GET` | `orderId` | `orderDAO.getOrderById(orderId)`<br/>`orderItemDAO.getOrderItemsWithProductDetails(orderId)` | 🔒 Yes | Forward: `/WEB-INF/views/order-success.jsp` |
| `RegisterServlet` | `/register` | `GET` | None | None | ❌ No | Forward: `/WEB-INF/views/register.jsp` |
| `RegisterServlet` | `/register` | `POST` | `userName`, `email`, `password`, `phone`, `address`, `city`, `state`, `pincode` | `userDAO.getUserByEmail(email)`<br/>`userDAO.addUser(User)` | ❌ No | Redirect: `/login?registered=true` (or Reload Register on error) |
| `LoginServlet` | `/login` | `GET` | `registered` | None | ❌ No | Forward: `/WEB-INF/views/login.jsp` |
| `LoginServlet` | `/login` | `POST` | `email`, `password` | `userDAO.getUserByEmail(email)`<br/>`userDAO.updateLastLogin(userId)` | ❌ No | Redirect: `/home` (on success) or Reload Login (on error) |
| `LogoutServlet` | `/logout` | `GET` | None | None | 🔒 Yes | Redirect: `/login?logout=true` (Invalidates Session) |
| `ProfileServlet` | `/profile` | `GET` | None | `userDAO.getUserById(userId)` | 🔒 Yes | Forward: `/WEB-INF/views/profile.jsp` |
| `ProfileServlet` | `/profile` | `POST` | `userName`, `phone`, `address`, `city`, `state`, `pincode` | `userDAO.getUserById(userId)`<br/>`userDAO.updateUser(User)` | 🔒 Yes | Redirect: `/profile?updated=true` |

---

## 🛠️ Detailed Controller Logic & Component Breakdown

### 1. `HomeServlet` (`/home`)
- **Purpose**: Serves as the landing page controller.
- **DAO Invocations**:
  - `CategoryDAO.getAllCategories()`: Fetches all category navigation links.
  - `ProductDAO.getAllProducts()`: Retrieves complete product list and slices top 8 featured products.
  - `ProductDAO.filterProducts(...)`: Selects newest items per category for banner cards.
  - `ProductImageDAO.getImagesByProductId(...)`: Resolves primary thumbnail images for featured products and categories.
- **View Forward**: Sets `categories`, `featuredProducts`, `primaryImages`, and `categoryImages` on request attributes and forwards to `/WEB-INF/views/home.jsp`.

---

### 2. `ProductListServlet` (`/products`)
- **Purpose**: Dynamic product search, filtering, sorting, and catalog browsing page.
- **DAO Invocations**:
  - `CategoryDAO.getAllCategories()`: Populates sidebar category filter list.
  - `ProductDAO.filterProducts(keyword, categoryId, gender, minPrice, maxPrice, size, sort)`: Executes multi-parameter dynamic SQL filtering query.
  - `ProductImageDAO.getImagesByProductId(...)`: Resolves thumbnail media paths for filtered products.
- **View Forward**: Attaches filtered products and query state attributes to `/WEB-INF/views/products.jsp`.

---

### 3. `ProductDetailsServlet` (`/product-details`)
- **Purpose**: Displays single product page with image gallery and size/stock variant selection.
- **DAO Invocations**:
  - `ProductDAO.getProductById(productId)`: Fetches main product details.
  - `ProductVariantDAO.getVariantsByProductId(productId)`: Fetches size variants (`S`, `M`, `L`, `XL`) and stock counts.
  - `ProductImageDAO.getImagesByProductId(productId)`: Fetches gallery image paths.
- **View Forward**: Forwards to `/WEB-INF/views/product-details.jsp`.

---

### 4. `CartServlet` (`/cart`)
- **Purpose**: Manages active user shopping cart state (Add, Update Quantity, Remove Item).
- **Session Security**: Enforces active `userId` check; redirects unauthenticated users to `/login`.
- **DAO Invocations**:
  - `CartDAO.getCartByUserId(userId)`: Resolves active cart instance ID.
  - `CartDAO.addCart(Cart)`: Auto-creates a new cart if user has no cart.
  - `CartItemDAO.getCartItemsWithProductDetails(cartId)`: Performs 4-table SQL JOIN (`cart_items`, `product_variants`, `products`, `product_images`) to aggregate full line item descriptions and stock.
  - `ProductVariantDAO.getVariantById(variantId)`: Validates stock sufficiency prior to adding/updating items.
  - `CartItemDAO.addCartItem(CartItem)` / `updateCartItem(CartItem)` / `deleteCartItem(cartItemId)`: Modifies cart line items.
- **View Forward**: Forwards `GET` to `/WEB-INF/views/cart.jsp` and redirects `POST` to `/cart`.

---

### 5. `CheckoutServlet` (`/checkout`)
- **Purpose**: Order checkout form rendering and transactional order placement handler.
- **Session Security**: Enforces active `userId` session check.
- **DAO Invocations**:
  - `UserDAO.getUserById(userId)`: Pre-fills shipping address details.
  - `CartDAO.getCartByUserId(userId)`: Resolves active user cart.
  - `CartItemDAO.getCartItemsWithProductDetails(cartId)`: Validates non-empty cart items and server-side pricing.
  - `CheckoutDAO.placeOrder(order, cartId, subtotal, shipping, total)`: Executes transaction-safe order creation, stock deduction (`FOR UPDATE`), order item batching, and cart clearing.
- **View Forward**: Forwards `GET` to `/WEB-INF/views/checkout.jsp`; redirects `POST` to `/order-success?orderId={id}` on success.

---

### 6. `OrdersServlet` (`/orders`)
- **Purpose**: Renders user's complete order history and detailed item breakdown.
- **Session Security**: Requires active user session.
- **DAO Invocations**:
  - `OrderDAO.getOrdersByUserId(userId)`: Fetches user order records sorted by `orderDate DESC`.
  - `OrderItemDAO.getOrderItemsWithProductDetails(orderId)`: Retrieves items, prices, sizes, and thumbnails per order.
- **View Forward**: Forwards to `/WEB-INF/views/orders.jsp`.

---

### 7. `OrderSuccessServlet` (`/order-success`)
- **Purpose**: Displays confirmation page for a newly placed order.
- **Session Security**: Verifies order ownership against active `userId`.
- **DAO Invocations**:
  - `OrderDAO.getOrderById(orderId)`: Fetches created order transaction summary.
  - `OrderItemDAO.getOrderItemsWithProductDetails(orderId)`: Fetches itemized line items.
- **View Forward**: Forwards to `/WEB-INF/views/order-success.jsp`.

---

### 8. `RegisterServlet` (`/register`) & `LoginServlet` (`/login`)
- **Purpose**: User account creation and BCrypt-secured authentication.
- **DAO Invocations**:
  - `UserDAO.getUserByEmail(email)`: Checks duplicate email during registration; fetches hash during login.
  - `UserDAO.addUser(user)`: Inserts new user record with hashed password.
  - `UserDAO.updateLastLogin(userId)`: Updates login audit timestamp.
- **View Routing**: Renders `/WEB-INF/views/register.jsp` and `/WEB-INF/views/login.jsp`.

---

### 9. `ProfileServlet` (`/profile`)
- **Purpose**: Profile view and address management page.
- **Session Security**: Requires active user session.
- **DAO Invocations**:
  - `UserDAO.getUserById(userId)`: Fetches existing user details.
  - `UserDAO.updateUser(user)`: Updates shipping address, phone, and city/state/pincode records.
- **View Forward**: Forwards `GET` to `/WEB-INF/views/profile.jsp`; redirects `POST` to `/profile?updated=true`.
