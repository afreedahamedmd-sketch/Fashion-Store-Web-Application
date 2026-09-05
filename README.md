# 🛍️ FashionStore - E-Commerce Web Application

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-5.0.0-blue.svg)](https://jakarta.ee/)
[![MySQL](https://img.shields.io/badge/MySQL-9.4.0-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

**FashionStore** is a robust, lightweight full-stack Java Enterprise e-commerce web application engineered using the **Model-View-Controller (MVC)** architectural pattern, Jakarta Servlets, JSP/JSTL presentation views, and MySQL database persistence.

---

## ✨ Key Features

- 👤 **User Authentication & Security**: Secure registration, BCrypt password hashing (`jbcrypt`), login validation, session state management, and user profile management.
- 👗 **Product Catalog & Dynamic Filtering**: Multi-criteria search and filter engine by category, target gender (Men/Women), price range (Min/Max), apparel size (S/M/L/XL), and keyword search.
- 🔍 **Product Details & Gallery**: Detailed product pages featuring high-resolution image galleries and size/stock variant selectors.
- 🛒 **Interactive Shopping Cart**: Dynamic cart management supporting line item additions, real-time quantity updates, stock validation, and item deletion.
- 💳 **ACID-Compliant Checkout & Order Placement**: Transaction-safe order processing featuring row-level database locking (`FOR UPDATE`), stock verification, server-side price recalculations, and automated stock deductions.
- 📦 **Order Tracking & History**: Full customer purchase history with detailed item breakdowns, price snapshots, and fulfillment status indicators.

---

## 🛠️ Technology Stack

| Layer | Technology / Framework |
| :--- | :--- |
| **Language** | Java 21 (LTS) |
| **Server Engine** | Jakarta Servlet 5.0 (Jakarta EE) |
| **View Template** | Jakarta Server Pages (JSP 3.1.0) & JSTL 3.0.1 |
| **Database** | MySQL Server (8.0+ / 9.x) |
| **Driver / ORM** | JDBC / MySQL Connector/J 9.4.0 |
| **Security** | BCrypt Password Hashing (`org.mindrot:jbcrypt 0.4`) |
| **Build Tool** | Apache Maven (WAR packaging) |

---

## 📚 Technical Documentation Suite

For detailed architectural diagrams, class hierarchies, sequence flows, database ERDs, and controller-to-DAO mappings, refer to the `./docs` directory:

- 🏗️ **[System Architecture & Sequence Flows](./docs/architecture.md)**: Comprehensive MVC topology, Class diagrams, and 7 end-to-end Sequence Diagrams.
- 🗄️ **[Database Schema & ORM Mapping](./docs/database_schema.md)**: Full ER Diagram, 9 Schema table definitions, constraints, and Java Model-to-SQL mapping matrix.
- 🔄 **[Controller-to-DAO Mapping](./docs/controller_dao_mapping.md)**: Servlet matrix, `@WebServlet` URL endpoint specifications, parameter contracts, and session handlers.
- 📖 **[Documentation Index](./docs/README.md)**: Central documentation hub.

---

## 🚀 Getting Started

### Prerequisites

- **JDK 21** or later installed
- **Apache Maven 3.8+**
- **MySQL Server 8.0+**
- **Apache Tomcat 10.1+** (Jakarta Servlet 5.0 compatible web container)

### Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/afreedahamedmd-sketch/Fashion-Store-Web-Application.git
   cd Fashion-Store-Web-Application
   ```

2. **Configure Database**:
   - Create a MySQL database named `fashion_store`:
     ```sql
     CREATE DATABASE fashion_store;
     ```
   - Update database credentials in `src/main/java/com/fashionstore/util/DBConnection.java` if required:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/fashion_store";
     private static final String USERNAME = "root";
     private static final String PASSWORD = "your_mysql_password";
     ```

3. **Build the WAR Package**:
   ```bash
   mvn clean package
   ```

4. **Deploy & Run**:
   - Deploy `target/FashionStore.war` to your Tomcat server's `webapps/` folder.
   - Start Tomcat and visit `http://localhost:8080/FashionStore/`.

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for details.
