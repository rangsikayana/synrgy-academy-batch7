CREATE TABLE Users (
    id UUID PRIMARY KEY,
    username VARCHAR(255),
    email_address VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE Merchant (
    id UUID PRIMARY KEY,
    merchant_name VARCHAR(255),
    merchant_location VARCHAR(255),
    open BOOLEAN
);

CREATE TABLE Product (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255),
    price DECIMAL,
    merchant_id UUID REFERENCES Merchant(id)
);

CREATE TABLE Order (
    id UUID PRIMARY KEY,
    order_time TIMESTAMP,
    destination_address VARCHAR(255),
    user_id UUID REFERENCES Users(id),
    completed BOOLEAN
);

CREATE TABLE Order_Detail (
    id UUID PRIMARY KEY,
    order_id UUID REFERENCES Order(id),
    product_id UUID REFERENCES Product(id),
    quantity INTEGER,
    total_price DECIMAL
);

ALTER TABLE Order ADD FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE Product ADD FOREIGN KEY (merchant_id) REFERENCES Merchant(id);
ALTER TABLE Order_Detail ADD FOREIGN KEY (order_id) REFERENCES Order(id);
ALTER TABLE Order_Detail ADD FOREIGN KEY (product_id) REFERENCES Product(id);