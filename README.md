# PARKEE Technical Test

Reno Fathoni

Tech Stack: Java Springboot, Postgresql

## Installation
1. Clone the repository
2. Update database configuration on applcation.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=myuser
spring.datasource.password=mypassword
```
3. Run the Application


## Database Schema (DDL)
the following sql defines the scheme for the database, you can also find it on `resources/ddl.sql`
```sql
create table book (
	id VARCHAR(36) primary key,
	title VARCHAR(100) not null,
	isbn VARCHAR(13) unique not null,
	stock INTEGER not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE borrower (
    id SERIAL PRIMARY key,
    ktp_number VARCHAR(16) UNIQUE NOT NULL,
    name VARCHAR(255) NOT null,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table borrow_history(
	id VARCHAR(36) primary key,
	borrower_id INT not null,
	book_id VARCHAR(36) not null,
	borrow_date DATE,
    return_date DATE,
    status VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
```

