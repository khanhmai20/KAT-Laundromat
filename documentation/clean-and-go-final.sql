-- Clean and Go Schema
-- Group: Asel Sathkumara, Delancy Mai, Thien Huynh
-- Last Updated: 12/04/2020

CREATE SCHEMA cleanandgo_development;
USE cleanandgo_development;

CREATE TABLE IF NOT EXISTS Employee (
    Employee_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    First_Name VARCHAR(225) NOT NULL,
    Last_Name VARCHAR(225) NOT NULL,
    Gender CHAR(1) NOT NULL,
    Employment_Date DATE NOT NULL,
    Employee_Position VARCHAR(225) NOT NULL,
    Salary DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS Employee_User (
    Employee_ID INT NOT NULL PRIMARY KEY,
    User_Name VARCHAR(225) NOT NULL,
    User_Password VARCHAR(225) NOT NULL,
    FOREIGN KEY (Employee_ID)
        REFERENCES Employee (Employee_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Product (
	Product_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Product_Name VARCHAR(225) NOT NULL,
	List_Price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Supplier (
    Supplier_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Supplier_Name VARCHAR(225) NOT NULL
);

CREATE TABLE IF NOT EXISTS Supplier_Product (
	Supplier_Product_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Supplier_ID INT NOT NULL,
	FOREIGN KEY (Supplier_ID) REFERENCES Supplier(Supplier_ID)
			ON DELETE CASCADE ON UPDATE CASCADE,
	Product_ID INT NOT NULL,
    FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID)
			ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Address_Type (
    Address_Type_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_Name VARCHAR(225)
);

CREATE TABLE IF NOT EXISTS State (
	State_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    State_Name VARCHAR(255) NOT NULL,
    State_Code CHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Contact_Type (
    Contact_Type_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_Name VARCHAR(225)
);

CREATE TABLE IF NOT EXISTS Asset_Type (
	Asset_Type_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_Name VARCHAR(225) NOT NULL,
    Type_Description VARCHAR(225)
);

CREATE TABLE IF NOT EXISTS Asset (
    Asset_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Asset_Type_ID INT NOT NULL,
    Asset_Name VARCHAR(225) NOT NULL,
    Asset_Brand VARCHAR(225),
    Asset_Description VARCHAR(225),
    Safety_Information VARCHAR(225),
    Current_Inventory INT NOT NULL,
    Purchase_Date DATE,
    Purchase_Price DECIMAL(10, 2),
    FOREIGN KEY (Asset_Type_ID) 
		REFERENCES Asset_Type (Asset_Type_ID)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Asset_Management (
    Asset_Management_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Employee_ID INT,
    Asset_ID INT,
	Use_Date DATE NOT NULL,
    FOREIGN KEY (Employee_ID)
        REFERENCES Employee (Employee_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Asset_ID)
        REFERENCES Asset (Asset_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Service_Type (
	Service_Type_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_Name VARCHAR(225) NOT NULL,
    Type_Description VARCHAR(225)
);

CREATE TABLE IF NOT EXISTS Service (
    Service_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Service_Type_ID INT NOT NULL,
    Service_Name VARCHAR(225) NOT NULL,
    Service_Description VARCHAR(225) NOT NULL,
    Rate DECIMAL(10, 2) NOT NULL,
    Duration TIME NULL,
    FOREIGN KEY (Service_Type_ID) 
		REFERENCES Service_Type (Service_Type_ID)
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Customer (
    Customer_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    First_Name VARCHAR(225) NOT NULL,
    Last_Name VARCHAR(225) NOT NULL,
    Customer_Email VARCHAR(225) NOT NULL
);

CREATE TABLE IF NOT EXISTS Payment_Type (
    Payment_Type_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Type_Name VARCHAR(225)
);

#Employee Schedule to divide multi-values attribute schedule
CREATE TABLE IF NOT EXISTS Employee_Schedule (
	Employee_ID INT NOT NULL, 
	Work_Date DATE NOT NULL, 
	Start_Time TIME NOT NULL, 
	End_Time TIME NOT NULL, 
	Hours_Worked TIME GENERATED ALWAYS AS(End_Time - Start_Time), 
	PRIMARY KEY (Employee_ID,Work_Date), 
	FOREIGN KEY (Employee_ID) 
		REFERENCES Employee (Employee_ID)
		ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK(`End_Time` > `Start_Time`)
);

CREATE TABLE IF NOT EXISTS Employee_Address (
    Employee_ID INT NOT NULL,
    Address_Type_ID INT NOT NULL,
    Address_Line1 VARCHAR(225) NOT NULL,
    Address_Line2 VARCHAR(225),
    City VARCHAR(225) NOT NULL,
    -- State VARCHAR(225) NOT NULL,
    State_ID INT NOT NULL,
    Zip_Code CHAR(10) NOT NULL,
    PRIMARY KEY (Employee_ID, Address_Type_ID),
    FOREIGN KEY (Employee_ID)
        REFERENCES Employee (Employee_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Address_Type_ID)
        REFERENCES Address_Type (Address_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (State_ID)
        REFERENCES State (State_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Employee_Contact (
    Employee_ID INT NOT NULL,
    Contact_Type_ID INT NOT NULL,
    Phone_Number VARCHAR(225),
    PRIMARY KEY (Employee_ID , Contact_Type_ID),
    FOREIGN KEY (Employee_ID)
        REFERENCES Employee (Employee_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Contact_Type_ID)
        REFERENCES Contact_Type (Contact_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Customer_Transaction (
    Customer_Transaction_ID INT NOT NULL AUTO_INCREMENT,
    Customer_ID INT NOT NULL,
    PRIMARY KEY (Customer_Transaction_ID , Customer_ID),
    FOREIGN KEY (Customer_ID)
        REFERENCES Customer (Customer_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    Billing_Date DATE NOT NULL,
    Shipping_Date DATE NOT NULL,
    Due_Date DATE,
    Amount_Charged DECIMAL(10, 2) NOT NULL,
    Review VARCHAR(225),
    Service_ID INT NOT NULL,
    Employee_ID INT NOT NULL,
    FOREIGN KEY (Service_ID)
        REFERENCES Service (Service_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Employee_ID)
        REFERENCES Employee (Employee_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    Current_Balance DECIMAL(10, 2) NOT NULL,
    Payment_Type_ID INT NOT NULL,
    FOREIGN KEY (Payment_Type_ID)
        REFERENCES Payment_Type (Payment_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Customer_Address (
    Customer_ID INT NOT NULL,
    Address_Line1 VARCHAR(225) NOT NULL,
    Address_Line2 VARCHAR(225),
    City VARCHAR(225) NOT NULL,
    -- State VARCHAR(225) NOT NULL,
    State_ID INT NOT NULL,
    Zip_Code CHAR(10) NOT NULL,
    Address_Type_ID INT NOT NULL,
    PRIMARY KEY (Customer_ID, Address_Type_ID),
    FOREIGN KEY (Customer_ID)
        REFERENCES Customer (Customer_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Address_Type_ID)
        REFERENCES Address_Type (Address_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (State_ID)
        REFERENCES State (State_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Supplier_Address (
    Supplier_ID INT NOT NULL,
    Address_Type_ID INT NOT NULL,
    Address_Line1 VARCHAR(225) NOT NULL,
    Address_Line2 VARCHAR(225),
    City VARCHAR(225) NOT NULL,
    -- State VARCHAR(225) NOT NULL,
    State_ID INT NOT NULL,
    Zip_Code CHAR(10) NOT NULL,
    PRIMARY KEY (Supplier_ID, Address_Type_ID),
    FOREIGN KEY (Supplier_ID)
        REFERENCES Supplier (Supplier_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Address_Type_ID)
        REFERENCES Address_Type (Address_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (State_ID)
        REFERENCES State (State_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Supplier_Contact (
    Supplier_ID INT NOT NULL,
    Contact_Type_ID INT NOT NULL,
    Phone_Number VARCHAR(225) NOT NULL,
    PRIMARY KEY (Supplier_ID, Contact_Type_ID),
    FOREIGN KEY (Supplier_ID)
        REFERENCES Supplier (Supplier_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Contact_Type_ID)
        REFERENCES Contact_Type (Contact_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Customer_Contact (
    Customer_ID INT NOT NULL,
    Contact_Type_ID INT NOT NULL,
    Phone_Number VARCHAR(225) NOT NULL,
    PRIMARY KEY (Customer_ID, Contact_Type_ID),
    FOREIGN KEY (Customer_ID)
        REFERENCES Customer (Customer_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Contact_Type_ID)
        REFERENCES Contact_Type (Contact_Type_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Supplier_Transaction (
    Supplier_ID INT NOT NULL,
    Supplier_Transaction_ID INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (Supplier_Transaction_ID , Supplier_ID),
    FOREIGN KEY (Supplier_ID)
        REFERENCES Supplier (Supplier_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    Billing_Date DATE NOT NULL,
    Shipping_Date DATE NOT NULL,
    Due_Date DATE,
    Transaction_Description VARCHAR(225),
    Order_Quantity INT NOT NULL,
    Current_Balance INT NOT NULL,
    Amount_Charged DECIMAL(10, 2) GENERATED ALWAYS AS (Order_Quantity * Unit_Price),
    Unit_Price DECIMAL(10, 2) NOT NULL,
    CHECK(`Shipping_Date` > `Billing_Date`),
    CHECK(`Due_Date` > `Billing_Date`)
);

CREATE TABLE IF NOT EXISTS Asset_Maintenance_Schedule (
    Asset_ID INT NOT NULL,
    Next_Maintenance DATE NOT NULL,
    PRIMARY KEY (Asset_ID , Next_Maintenance),
    FOREIGN KEY (Asset_ID)
        REFERENCES Asset (Asset_ID)
        ON DELETE CASCADE ON UPDATE CASCADE
); 

#Uncomment these statements to drop the schema (CTRL + /).
-- DROP TABLE Asset_Maintenance_Schedule;
-- DROP TABLE Supplier_Transaction;
-- DROP TABLE Customer_Contact;
-- DROP TABLE Customer_Address; 
-- DROP TABLE Supplier_Contact;
-- DROP TABLE Supplier_Address;
-- DROP TABLE Employee_Address;
-- DROP TABLE Customer_Transaction;
-- DROP TABLE Employee_Contact;
-- DROP TABLE Employee_Schedule;
-- DROP TABLE Payment_Type;
-- DROP TABLE Customer;
-- DROP TABLE Service; 
-- DROP TABLE Service_Type;
-- DROP TABLE Asset_Management; 
-- DROP TABLE Asset; 
-- DROP TABLE Asset_Type;
-- DROP TABLE Contact_Type; 
-- DROP TABLE Address_Type; 
-- DROP TABLE Supplier_Product
-- DROP TABLE Product
-- DROP TABLE Supplier;
-- DROP TABLE Employee_User;
-- DROP TABLE Employee;

-- DROP SCHEMA cleanandgo_development;

