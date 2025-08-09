# Application Requirements

- **IDE:** Any IDE suitable for Java development (e.g., IntelliJ IDEA, Eclipse).
- **Java:** JDK 17 or higher must be configured on your machine.
---

# Setup Instructions

1. Clone the repository
2. Open the project in your IDE
3. Configure your IDE to use JDK 17 or higher
4. Enable Lombok plugin in your IDE
5. Build the project with Gradle
6. Run the application from your IDE

> **Note:** This application uses an in-memory H2 database. All data will be lost after a restart due to non-persistent storage.
---
# API Description & Usage:
Swagger URL:
http://localhost:8080/swagger-ui/index.html#
---
## Sales Data API:

### GET /api/total-commission  
API that accepts landing page code and date interval and returns the conversion rate
(conversion = purchase action / visit action) for that specific site and date interval.  

**Required query parameters:**  
`code`: select ABB or EKW or TBS  
`visitDate`: Start date (ISO 8601 format) (example: 2025-07-09T18:07:55.953+03:00)  
`saleDate`: End date (ISO 8601 format) (example: 2025-08-09T18:07:55.953+03:00)  
<br>
### GET /api/product
  
API that accepts date interval and returns all products, each with their respective
conversion rate for that date interval.  

**Required query parameters:**  
`visitDate`: Start date (ISO 8601 format) (example: 2025-07-09T18:07:55.953+03:00)  
`saleDate`: End date (ISO 8601 format) (example: 2025-08-09T18:07:55.953+03:00)  
<br>
### GET /api/conversion-rate
  
API that accepts landing page code and date interval and returns total commission
amount for that specific site and date interval.  

**Required query parameters:**  
`code`: select ABB or EKW or TBS  
`visitDate`: Start date (ISO 8601 format) (example: 2025-07-09T18:07:55.953+03:00)  
`saleDate`: End date (ISO 8601 format) (example: 2025-08-09T18:07:55.953+03:00)  