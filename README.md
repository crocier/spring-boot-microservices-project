🛒 E-Commerce Microservices Project
This project is a scalable e-commerce backend built using Spring Boot with a Microservices Architecture, featuring an API Gateway, Service Discovery, and Kafka-based asynchronous communication.

🚀 Tech Stack
- Java 17, Spring Boot
- Spring Cloud Netflix Eureka (Service Discovery)
- Spring Cloud Gateway (API Gateway)
- Apache Kafka (Async messaging)
- Spring Data JPA, Hibernate
- MySQL/PostgreSQL (Database)
- Docker (optional for containerization)
  
🧩 Microservices Overview
  Product Service – Manages product catalog with CRUD operations
  Order Service – Places orders, checks inventory, and publishes Kafka events
  Inventory Service – Tracks product stock and responds to availability checks
  Notification Service – Listens to Kafka events and sends notifications
  API Gateway – Routes client requests to appropriate microservices
  Service Discovery (Eureka) – Registers and discovers services dynamically

🔄 Service Interaction Flow
- Client → API Gateway: All requests are routed through the gateway.
- Order Service → Inventory Service: Synchronous REST call to verify product availability.
- Order Service → Kafka Topic: On successful order placement, publishes an event.
- Notification Service → Kafka Listener: Consumes the event and sends notifications.

📦 Kafka Topics
- order-events: Used to publish order placement events.

📁 Project Structure
ecommerce-microservices/
├── api-gateway/
├── service-discovery/
├── product-service/
├── order-service/
├── inventory-service/
└── notification-service/


🛠️ Setup Instructions
- Clone the repo: git clone https://github.com/your-username/ecommerce-microservices.git
- Start Eureka Server
- Start API Gateway
- Start each microservice individually
- Ensure Kafka is running locally or via Docker
- Test endpoints via Postman or Swagger
📬 Contact
For queries or collaboration, feel free to reach out via LinkedIn or open an issue.
