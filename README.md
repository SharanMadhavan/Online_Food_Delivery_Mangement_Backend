# Food Delivery App - Complete Spring Boot Backend

A comprehensive, production-ready Spring Boot monolithic backend for a food delivery application with JWT authentication, role-based access control, and complete business modules.

## 🚀 Features

- **JWT Authentication & Authorization** with role-based access control (USER/ADMIN)
- **Restaurant Management** - CRUD operations for restaurants
- **Menu Management** - CRUD operations for menu items
- **Order Management** - Complete order lifecycle with order items
- **Payment Processing** - Mock payment gateway integration
- **Delivery Tracking** - Real-time delivery status management
- **Swagger UI Documentation** - Interactive API documentation
- **Global Exception Handling** - Comprehensive error handling
- **Input Validation** - Bean validation with custom error messages
- **Database Integration** - MySQL with JPA/Hibernate

## 🏗️ Architecture

### Modules
1. **Authentication Module** - User registration, login, JWT token management
2. **Restaurant Module** - Restaurant CRUD operations
3. **Menu Module** - Menu item management
4. **Order Module** - Order creation, tracking, and management
5. **Payment Module** - Mock payment processing
6. **Delivery Module** - Delivery tracking and status updates

### Technology Stack
- **Spring Boot 3.2.5**
- **Spring Security** with JWT
- **Spring Data JPA** with Hibernate
- **MySQL Database**
- **Springdoc OpenAPI 2.5.0** (Swagger UI)
- **Spring Boot Validation**
- **Spring Boot Actuator**

## 📋 API Endpoints

### Authentication Endpoints
- `POST /auth/signup` - User registration
- `POST /auth/login` - User login
- `GET /auth/me` - Get current user info

### Restaurant Management
- `GET /api/restaurants` - Get all restaurants (PUBLIC)
- `GET /api/restaurants/{id}` - Get restaurant by ID (PUBLIC)
- `POST /api/restaurants` - Create restaurant (ADMIN only)
- `PUT /api/restaurants/{id}` - Update restaurant (ADMIN only)
- `DELETE /api/restaurants/{id}` - Delete restaurant (ADMIN only)
- `GET /api/restaurants/cuisine/{cuisine}` - Get restaurants by cuisine (PUBLIC)
- `GET /api/restaurants/rating/{minRating}` - Get restaurants by rating (PUBLIC)

### Menu Management
- `GET /api/menus/restaurant/{restaurantId}` - Get menu items by restaurant (PUBLIC)
- `POST /api/menus` - Create menu item (ADMIN only)
- `PUT /api/menus/{id}` - Update menu item (ADMIN only)
- `DELETE /api/menus/{id}` - Delete menu item (ADMIN only)
- `GET /api/menus/category/{category}` - Get menu items by category (PUBLIC)
- `GET /api/menus/restaurant/{restaurantId}/category/{category}` - Get menu items by restaurant and category (PUBLIC)

### Order Management
- `POST /api/orders` - Create order (USER only)
- `GET /api/orders/user/{userId}` - Get orders by user (USER only)
- `GET /api/orders/{id}` - Get order by ID (USER or ADMIN)
- `PUT /api/orders/{id}/status` - Update order status (ADMIN only)

### Payment Processing
- `POST /api/payments` - Process payment (USER only)

### Delivery Tracking
- `GET /api/deliveries/order/{orderId}` - Get delivery by order (USER only)
- `PUT /api/deliveries/{id}/status` - Update delivery status (ADMIN only)
- `GET /api/deliveries` - Get all deliveries (ADMIN only)
- `GET /api/deliveries/status/{status}` - Get deliveries by status (ADMIN only)

## 🔐 Security

### JWT Authentication
- All API endpoints (except public ones) require JWT token in Authorization header
- Format: `Authorization: Bearer <token>`
- Token expiration: 24 hours

### Role-Based Access Control
- **USER Role**: Can create orders, view their own orders, process payments, track deliveries
- **ADMIN Role**: Can manage restaurants, menu items, update order/delivery status, view all data

### Public Endpoints
- Authentication endpoints (`/auth/*`)
- Restaurant listing (`/api/restaurants`)
- Menu items by restaurant (`/api/menus/restaurant/*`)
- Swagger UI (`/swagger-ui.html`)

## 🗄️ Database Schema

### Entities
- **User** - User accounts with roles
- **Restaurant** - Restaurant information
- **MenuItem** - Menu items with restaurant relationship
- **Order** - Orders with user and restaurant relationships
- **OrderItem** - Individual items in an order
- **Delivery** - Delivery tracking information

### Relationships
- User → Orders (One-to-Many)
- Restaurant → MenuItems (One-to-Many)
- Restaurant → Orders (One-to-Many)
- Order → OrderItems (One-to-Many)
- Order → Delivery (One-to-One)

## 🚀 Getting Started

### Prerequisites
- Java 21+
- MySQL 8.0+
- Maven 3.6+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd food-delivery-app
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE food_delivery_db;
   ```

3. **Update Configuration**
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the Application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Actuator Health: `http://localhost:8080/actuator/health`

## 📖 API Documentation

### Swagger UI
Access the interactive API documentation at `http://localhost:8080/swagger-ui.html`

### Sample API Calls

#### 1. User Registration
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

#### 2. User Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### 3. Create Restaurant (Admin)
```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "name": "Pizza Palace",
    "cuisine": "Italian",
    "rating": 4.5,
    "deliveryTime": "30-45 minutes",
    "imageUrl": "https://example.com/pizza-palace.jpg"
  }'
```

#### 4. Create Order (User)
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "restaurantId": 1,
    "totalAmount": 25.99,
    "deliveryAddress": "123 Main St, City, State",
    "orderItems": [
      {
        "menuItemId": 1,
        "quantity": 2,
        "price": 12.99
      }
    ]
  }'
```

## 🔧 Configuration

### Application Properties
- **Database**: MySQL configuration
- **JWT**: Secret key and expiration settings
- **CORS**: Cross-origin resource sharing configuration
- **Logging**: Debug and trace logging levels
- **Actuator**: Health check and metrics endpoints

### Environment Variables
You can override configuration using environment variables:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET_KEY`

## 🧪 Testing

### Manual Testing
1. Use Swagger UI for interactive testing
2. Use Postman or curl for API testing
3. Test both USER and ADMIN role functionalities

### Test Scenarios
1. **User Registration & Login**
2. **Restaurant Management** (Admin)
3. **Menu Item Management** (Admin)
4. **Order Creation** (User)
5. **Payment Processing** (User)
6. **Delivery Tracking** (User/Admin)

## 📝 Logging

The application includes comprehensive logging:
- **DEBUG**: Service layer operations
- **INFO**: Business logic operations
- **ERROR**: Exception handling
- **SQL**: Database queries (in development)

## 🚀 Deployment

### Docker Support
The application includes Docker configuration:
- `Dockerfile` for containerization
- `docker-compose.yml` for multi-container setup
- `docker.env` for environment variables

### Production Considerations
1. **Database**: Use production-grade MySQL instance
2. **Security**: Change JWT secret key
3. **Logging**: Configure appropriate log levels
4. **Monitoring**: Enable actuator endpoints
5. **CORS**: Configure for production domains

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the Swagger UI documentation
- Review the application logs

---

**Built with ❤️ using Spring Boot**
