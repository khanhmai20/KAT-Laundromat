-- CleanAndGo Data Script
-- Group: Asel Sathkumara, Delancy Mai, Thien Huynh
-- Last Updated: 12/05/2020

USE cleanandgo_development;

INSERT INTO Employee 
	(First_Name, Last_Name, Gender, Employment_Date, Employee_Position, Salary) 
VALUES
	('Stephanie', 'Mills', 'F', '2015-12-01', 'Cleaner', 33820), 
	('Chad', 'Rios', 'M', '2018-02-23', 'Cleaner', 33820),
	('Douglas', 'Healy', 'M', '2017-12-10', 'Attendant', 33820),
	('Ronald', 'Seiber', 'M', '2020-03-04', 'Intern', 12480),
	('Michael', 'Adams', 'M', '2013-01-12', 'Manager', 40000),
	('Michelle', 'Stansel', 'F', '2015-05-08', 'Receptionist', 33820),
	('Melissa', 'Payne', 'F', '2020-01-12', 'Intern', 12480),
	('Sara', 'Farag', 'F', '2020-12-05', 'Manager', 40000),
    ('Derek', 'Hammond', 'M', '2020-12-04', 'Attendant', 33820);
    
INSERT INTO Employee_User
VALUES
	(1, 'smills@cleango', '$2a$10$ft7sGJN34edQYf4VjwiUluxrkUXOFFie6P4Ib3FzGQxNQJPed1xFy'),	
	(2, 'rchad@cleango', '$2a$10$FM4svW8SYES5.JWPC2UUlOFhyf9KxHGeqr46sLQlUEQepI2AcRa.C'),	
	(3, 'dhealy@cleango', '$2a$10$zKye6vd//ztZ3hIxrq8zh.obEfw3U4LyL2/lWUOg9iHm/zEzLIEIi'), 	
	(4, 'rseiber@cleango', '$2a$10$rfbyVGw2VLA6GEvDpqkf2eAIb.Uw7DWwPIiDyCu9r37yvHvI1BNES'), 	
	(5, 'madams@cleango', '$2a$10$PXD2W55W2mb9uQDkii0rP.98iwxbcpyLqb4g9JGzfhvfJKY7Bot9i'), 	
	(6, 'mstansel@cleango', '$2a$10$56AmT/rR6wYJi51YQnB0/ekQCReYrKVG8ljX8WTkI4ATa15gh4hNi'),	
	(7, 'mpayne@cleango'  , '$2a$10$betqa6eLt9P70lTGjiZsX.M5SVYxMuJr9im9aGlKDB7bHe5O1wttO'),
	(8, 'sfarag@cleango', '$2a$10$QWnmwNdBCSR5Zrhu.3J3hudhpzs92Oc6qX.bCBAK9Tn0qKMeFz9lS'),
    (9, 'dhammond@cleango', '$2a$10$sw.fYW/ysOVKT1kDZjtzIeIU1LdvZeT9go.L6vRo8F5d0sQP.nPWK');
    
INSERT INTO Product
	(Product_Name, List_Price)
VALUES
	('Panasonic 2018 Washer', 598.00), 
	('Panasonic 2018 Dryer', 400.00),
	('Clif Bar Vending Machine', 5000.00), 
	('Downy Detergent', 320.00), 
	('Downy Fabric Softener', 300.95), 
	('Vegan Market Trolley', 500.00), 
	('SEAGA Dollar Bill Exchanger', 1698.95),
	('Lysol all-purpose cleaner', 89.00),
	('ULINE Lobby Broom', 16.00), 
	('ULINE Sanitizing Wipe Dispenser', 12.00), 
	('Ikea Chairs', 1200.00),
	('Samsung Television', 800.00), 
	('ULINE Dust Pan', 14.00),
	('Clorox Bleach', 120.00);

INSERT INTO Supplier(Supplier_Name)
VALUES
	('Clean Clean Clean'),
	('Mazdar Supply Inc'),
	('One Stop Clean Shop'),
	('Happy Maid Supply Co'),
	('HF Cleaning Supplies'),
	('Arnold’s Supply'),
	('Awon Janitorial Sales');

INSERT INTO Supplier_Product
	(Supplier_ID, Product_ID)
VALUES
	(1, 1),
	(5, 2),
	(7, 3),
	(1, 4),
	(3, 5),
	(5, 6),
	(2, 7),
    (3, 7),
    (4, 1),
    (2, 9),
    (4, 14),
    (5, 3),
    (6, 8),
    (1, 13);

INSERT INTO Address_Type(Type_Name)
VALUES 
	("Billing"),
	("Shipping");

INSERT INTO State 
	(State_Name, State_Code)
VALUES 
	('Alaska', 'AK'),
	('Alabama', 'AL'),
	('Arizona', 'AZ'),
	('Arkansas', 'AR'),
	('California', 'CA'),
	('Colorado', 'CO'),
	('Connecticut', 'CT'),
	('Delaware', 'DE'),
	('District of Columbia', 'DC'),
	('Florida', 'FL'),
	('Georgia', 'GA'),
	('Hawaii', 'HI'),
	('Idaho', 'ID'),
	('Illinois', 'IL'),
	('Indiana', 'IN'),
	('Iowa', 'IA'),
	('Kansas', 'KS'),
	('Kentucky', 'KY'),
	('Louisiana', 'LA'),
	('Maine', 'ME'),
	('Maryland', 'MD'),
	('Massachusetts', 'MA'),
	('Michigan', 'MI'),
	('Minnesota', 'MN'),
	('Mississippi', 'MS'),
	('Missouri', 'MO'),
	('Montana', 'MT'),
	('Nebraska', 'NE'),
	('Nevada', 'NV'),
	('New Hampshire', 'NH'),
	('New Jersey', 'NJ'),
	('New Mexico', 'NM'),
	('New York', 'NY'),
	('North Carolina', 'NC'),
	('North Dakota', 'ND'),
	('Ohio', 'OH'),
	('Oklahoma', 'OK'),
	('Oregon', 'OR'),
	('Pennsylvania', 'PA'),
	('Puerto Rico', 'PR'),
	('Rhode Island', 'RI'),
	('South Carolina', 'SC'),
	('South Dakota', 'SD'),
	('Tennessee', 'TN'),
	('Texas', 'TX'),
	('Utah', 'UT'),
	('Vermont', 'VT'),
	('Virginia', 'VA'),
	('Washington', 'WA'),
	('West Virginia', 'WV'),
	('Wisconsin', 'WI'),
	('Wyoming', 'WY');

INSERT INTO Contact_Type(Type_Name)
VALUES 
	("Home"),
	("Cell"),
	("Office"),
	("Fax");

INSERT INTO Asset_Type 
	(Type_Name, Type_Description)
VALUES
	("Equipment", "Machines including washers, dryers, trolleys etc."),
	("Cleaning Supplies", "Cleaning supplies");

INSERT INTO Asset 
	(Asset_Type_ID, Asset_Name, Asset_Brand, Asset_Description, Safety_Information, Current_Inventory, Purchase_Date, Purchase_Price)
VALUES 
	(1, 'Panasonic 2018 Washer', 'Panasonic', 'Washing heavy, light load', 'Handle with care, keep away from children', 10, '2020-10-20', 598.00), 
	(1, 'Panasonic 2018 Dryer', 'Panasonic', 'Drying with different heat settings', 'Handle with care, keep away from children', 10, '2020-11-20', 400.00),
	(1, 'Clif Bar Vending Machine', 'Clif Bar', 'Dispense grocery to customer', NULL, 2, '2020-12-12', 5000.00), 
	(2, 'Downy Detergent', 'Downy', 'Using for cleaning clothes, remove stain', 'Handle with care, keep away from children', 50, '2020-10-15', 320.00), 
	(2, 'Downy Fabric Softener', 'Downy', 'Using for smoothing clothes', 'Handle with care, keep away from children', 50, '2020-11-20',300.95), 
	(1, 'Vegan Market Trolley', 'Vegan Market', 'Using to store clothes, loads', NULL, 20, '2020-10-15', 500.00), 
	(1, 'SEAGA Dollar Bill Exchanger', 'SEAGA', 'Using to convert dollar bill into coins', NULL, 1, '2020-11-20',1698.95),
	(2, 'Lysol all-purpose cleaner', 'Lysol', 'General cleansing', 'Handle with care, keep away from children', 10, '2020-10-15', 89.00),
	(2, 'ULINE Lobby Broom', 'ULINE', 'Used for wiping dust', NULL, 2, '2020-10-15', 16.00), 
	(2, 'ULINE Sanitizing Wipe Dispenser', 'ULINE', 'Used for killing bateria', 'Handle with care, keep away from children', 1, '2020-10-15', 12.00), 
	(1, 'Ikea Chairs', 'IKEA', 'Used for waiting, sitting purpose', NULL, 10, '2020-10-15', 1200.00),
	(1, 'Samsung Television', 'Samsung', 'Used for entertaining customer', NULL, 1, '2020-10-15', 800.00), 
	(2, 'ULINE Dust Pan', 'ULINE', 'Used accompanied with ULINE BROOM', NULL, 2, '2020-10-15', 14.00),
	(2, 'Clorox Bleach', 'Clorox', 'Bleaching clothes', 'Handle with care, keep away from children', 50, '2020-10-15', 120.00);

INSERT INTO Asset_Management
	(Employee_ID, Asset_ID, Use_Date)
VALUES
	(1, 9, '2020-04-30'),
	(1, 8, '2020-05-11'),
	(2, 10, '2020-06-26'),
	(2, 13, '2020-07-03'),
	(3, 1, '2020-11-17'),
	(3, 2, '2020-01-13'),
	(4, 4, '2020-04-23'),
	(4, 5, '2020-05-14'),
	(5, 3, '2020-10-07'),
	(5, 7, '2020-03-26'),
	(6, 11, '2020-06-30'),
	(6, 12, '2020-08-10');

INSERT INTO Service_Type (Type_Name, Type_Description) 
VALUES
	('Washer', NULL ),
	('Dryer', NULL),
	('Drop Off', NULL),
	('Dry Cleaning', NULL);

INSERT INTO Service (Service_Type_ID, Service_Name, Service_Description, Rate, Duration)
VALUES
	(1, 'Small Washer', '30 lbs washer', 2.00, '01:00:00'),
	(1, 'Medium Washer', '45 lbs washer', 4.00, '01:00:00'),
	(1, 'Large Washer', '75 lbs washer', 6.00, '01:00:00'),
	(2, 'Small Dryer', '30 lbs dryer', 1.50, '01:00:00'),
	(2, 'Medium Dryer', '45 lbs dryer', 1.87, '01:00:00'),
	(2, 'Large Dryer', '75 lbs dryer', 3.75, '01:00:00'),
	(3, 'Drop Off', 'Same Day', 1.70, NULL),
	(3, 'Drop Off', 'Next Day', 2.05, NULL),
	(4, 'Laundered & Pressed Dress Shirt', '$2.50 per one', 2.50, '01:00:00'),
	(4, 'Blouse', '$5.85 per one', 5.85, '01:00:00'),
	(4, 'Coat (Lab /Heavy / Rain)', '$12.50 per one', 12.50, '01:00:00'),
	(4, 'Dress', '$12.50 per one', 12.50, '01:00:00'),
	(4, 'Dress (Formal)', '$17.50 per one', 17.50, '01:00:00'),
	(4, 'Jacket (Casual / Thin / Windbreaker)', '$8.50 per one', 8.50, '01:00:00'),
	(4, 'Pant', '$5.85 per one', 5.85, '01:00:00'),
	(4, 'Shirt', '$5.50 per one', 5.50, '01:00:00'),
	(4, 'Shorts', '$5.85 per one', 5.85, '01:00:00'),
	(4, 'Skirt', '$5.85 per one', 5.85, '01:00:00'),
	(4, 'Suit Jacket', '$7.75 per one', 7.75, '01:00:00'),
	(4, 'Sweater', '$7.75 per one', 7.75, '01:00:00');

INSERT INTO Customer (First_Name, Last_Name, Customer_Email)
VALUES
	('Jackson','White', 'jacksonwhite@gmail.com'),
	('Belle','Haynes', 'belle_hay@yahoo.com'), 
	('Mark', 'Meek', 'mak_menden@icloud.com'),
	('Benjamin', 'Ell', 'benj_staf100@gmail.com'),
	('Cece', 'Lee', 'cece1997@gmail.com'), 
	('Lark', 'Kent', 'kent_lark@gmail.com'), 
	('Marilyn', 'Parker', 'maryam1976@gmail.com');

INSERT INTO Payment_Type (Type_Name)
VALUES
	("Cash"),
	("Credit"),
	("Bitcoin");

INSERT INTO Employee_Schedule 
	(Employee_ID, Work_Date, Start_Time, End_Time)
VALUES
	(1, '2020-11-09', '10:00:00', '16:00:00'),
	(2, '2020-11-09', '10:00:00', '16:00:00'),
	(3, '2020-11-09', '10:00:00', '16:00:00'),
	(4, '2020-11-09', '10:00:00', '16:00:00'),
	(5, '2020-11-09', '10:00:00', '16:00:00'),
	(6, '2020-11-09', '10:00:00', '16:00:00'),
	(7, '2020-11-09', '10:00:00', '14:00:00'),
    (8, '2020-12-07', '10:00:00', '16:00:00'),
    (9, '2020-12-05', '10:00:00', '16:00:00');

INSERT INTO Employee_Address VALUES
	(1,1,'2971 Hidden Meadow Drive',null,'Grace City',49,58445),
	(1,2,'2971 Hidden Meadow Drive',null,'Grace City',49,58445),
	(2,1,'12th South Walker',null,'Seattle',49,98122),
	(2,2,'12th South Walker',null,'Seattle',49,98122),
	(3,1,'7749 Rainer Ave S',null,'Seattle',49,98106),
	(3,2,'7749 Rainer Ave S',null,'Seattle',49,98106),
	(4,1,'13 Terry Ave', null, 'Seattle', 49, 98145), 
	(4,2,'13 Terry Ave', null, 'Seattle', 49, 98145), 
	(5,1,'45th Aurora Ave N',null, 'Bellevue', 49, 98123), 
	(5,2,'45th Aurora Ave N',null, 'Bellevue', 49, 98123), 
	(6,1,'1449 Main St', null, 'Bellevue', 49, 98120), 
	(6,2,'1449 Main St', null, 'Bellevue', 49, 98120), 
	(7,1,'1305 E Fir', null, 'Seattle', 49, 98122),
	(7,2,'1305 E Fir', null, 'Seattle', 49, 98122),
    (8, 1, '3115 Walnut Avenue', null, 'Seattle', 49, 07102),
    (8, 2, '3115 Walnut Avenue', null, 'Seattle', 49, 07102),
    (9, 1, '1181 Mutton Town Road', null, 'Seattle', 49, 98106),
    (9, 2, '1181 Mutton Town Road', null, 'Seattle', 49, 98106);


INSERT INTO Employee_Contact 
VALUES 
-- Home
	(1, 1, '146-123-9816'),
	(2, 1, '215-462-3484'),
	(3, 1, '785-651-1365'),
	(4, 1, '916-024-7415'),
	(5, 1, '367-033-6485'),
	(6, 1, '853-111-1023'),
	(7, 1, '743-174-3641'),
    (8, 1, '201-535-8255'),
    (9, 1, '360-604-2966'),

-- Cell
	(1, 2, '546-102-6461'),
	(2, 2, '478-125-7964'),
	(3, 2, '124-983-2154'),
	(4, 2, '145-853-1473'),
	(5, 2, '458-215-1999'),
	(6, 2, '743-215-2545'),
	(7, 2, '894-206-4665'),
	(8, 2, '862-452-8021'),
    (9, 2, '206-954-6721'),
    
-- Fax
	(5, 3, '420-871-3244'),

-- Office
	(5, 4, '420-441-4144');

INSERT INTO Customer_Transaction
	(Customer_ID, Billing_Date, Shipping_Date, Due_Date, Amount_Charged, Review, Service_ID, Employee_ID, Current_Balance, Payment_Type_ID)
VALUES
	(4, '2020-11-10', '2020-11-10', NULL, 542.12, NULL, 4, 3, 234.56, 3),
	(2, '2020-11-13','2020-11-14', NULL, 459.03, NULL, 3, 2, 0.00, 2),
	(3, '2020-11-11','2020-11-12', NULL, 652.26, NULL, 2, 5, 145.00, 1),
	(7, '2020-10-30', '2020-10-31', NULL, 878.15, NULL, 4, 4, 542.00, 1),
	(5, '2020-09-12','2020-09-13', NULL, 213.98, NULL, 2, 7, 0.00, 1),
	(6, '2020-05-18','2020-05-19', NULL, 793.26, NULL, 1, 2, 125.83, 1),
	(5, '2020-02-28','2020-03-01', NULL, 1230.71, NULL, 2, 3, 640.11, 1),
	(3, '2020-06-16','2020-06-17', NULL, 846.12, NULL, 3, 5, 55.00, 1),
	(6, '2020-12-19','2020-12-20', NULL, 131.05, NULL, 1, 5, 0.00, 1),
	(3, '2020-03-12','2020-03-12', NULL, 946.16, NULL, 4, 2, 130.22, 1);

INSERT INTO Customer_Address
VALUES
	(1,'49th Rainer Ave S', null, 'SEATTLE', 49, 98109,1),
	(1,'49th Rainer Ave S', null, 'SEATTLE', 49, 98109,2),
	(2,'133th Martin Luther King', null, 'SEATTLE', 49, 98109,1),
	(2,'133th Martin Luther King', null, 'SEATTLE', 49, 98109,2),
	(3,'9900th Denny Way', null, 'Kent', 49, 98559,1), 
	(3,'9900th Denny Way', null, 'Kent', 49, 98559,2), 
	(4,'12th University Way', null, 'Seattle', 49, 98132,1), 
	(4,'12th University Way', null, 'Seattle', 49, 98132,2), 
	(5,'Avery North East', null,'Lynwood',49,98111,1), 
	(5,'Avery North East', null,'Lynwood',49,98111,2),
	(6,'112th Roxbury', null, 'Everret', 49,98222,1), 
	(6,'112th Roxbury', null, 'Everret', 49,98222,2), 
	(7,'212th Alaska Junction', null, 'Seattle', 49, 98102,1), 
	(7,'212th Alaska Junction', null, 'Seattle', 49, 98102,2);

INSERT INTO Supplier_Address 
VALUES
	(1, 1, '2903th Clark Ave',  null,  'Portland',  38, 97207), 
	(1, 2, '2903th Clark Ave', null, 'Portland', 38, 97207), 
	(2, 1, '79 Alley St', null, 'Portland', 38, 97222), 
	(2, 2, '79 Alley St', null, 'Portland', 38, 97222), 
	(3, 1, '888 Benley Ave', null,  'Portland', 38, 97209), 
	(3, 2, '888 Benley Ave', null,  'Portland', 38, 97209), 
	(4, 1, '111th Aurora Ave N', null, 'Seattle', 49, 98122),  
	(4, 2, '111th Aurora Ave N', null, 'Seattle', 49, 98122),  
	(5, 1, '100 Bruce Denny', null,  'Portland',  38, 97120), 
	(5, 2, '100 Bruce Denny', null,  'Portland',  38, 97120), 
	(6, 1, 'Wolver Way NW', null,  'Portland', 38, 97111),  
	(6, 2, 'Wolver Way NW', null,  'Portland', 38, 97111),  
	(7, 1, 'Havard Ave S', null,  'Portland', 38, 97432),  
	(7, 2, 'Havard Ave S', null,  'Portland', 38, 97432);

INSERT INTO Supplier_Contact
VALUES
(1,  1, '244-234-1023'),
(1, 2, '432-101-4383'),
(1, 3, '616-156-1054'),
(1, 4, '616-467-0174'),
(2, 1, '245-505-5056'),
(2, 2, '245-171-5152'),
(2, 3, '155-021-8923'),
(3, 1, '215-104-7813'),
(3, 2, '204-781-3310'),
(4, 1, '132-884-6666'),
(4, 2, '510-456-7894'),
(4, 3, '159-888-0215'),
(5, 1, '781-145-1782'),
(5, 2, '156-451-1563'),
(5, 3, '146-310-9645'),
(5, 4, '206-605-6012'),
(6, 1, '878-021-9430'),
(6, 2, '451-840-6541'),
(7, 1, '146-980-1573'),
(7, 2, '556-747-1912');

INSERT INTO Customer_Contact 
VALUES
	(1, 1, '199-789-1212'),
	(1, 2, '669-707-1234'),
	(2, 1, '190-101-9999'),
	(2, 2, '330-797-0000'),
	(3, 1, '980-998-1331'),
	(3, 2, '222-120-1234'),
	(4, 1, '599-909-4412'),
	(4, 2, '101-222-3454'),
	(5, 1, '909-101-3311'),
	(5, 2, '707-669-4455'),
	(6, 1, '555-894-9368'),
	(6, 2, '206-910-5506'),
	(7, 1, '669-101-4422'),
	(7, 2, '123-654-0987');
	
INSERT INTO Supplier_Transaction
	(Supplier_ID, Billing_Date, Shipping_Date, Due_Date, Transaction_Description, Order_Quantity, Current_Balance, Unit_Price)
VALUES
	(1, '2020-11-18', '2020-11-19', NULL, 'Invoice paid in full', 100, 0, 3.92),
	(1, '2020-11-11', '2020-11-12', NULL, 'Remaining invoice: $203', 100, 203, 4.06),
	(2, '2020-11-12', '2020-11-13', NULL, 'Remaining invoice: $203', 100, 203, 4.06),
	(2, '2020-11-16', '2020-11-17', NULL, 'Invoice paid in full', 100, 0, 5.26),
	(3, '2020-11-15', '2020-11-16', NULL, 'invoice paid in full', 200, 0, 1.37),
	(3, '2020-11-17', '2020-11-18', NULL, 'Invoice paid in full', 300, 0, 7.76),
	(4, '2020-11-19', '2020-11-20', NULL, 'Remaining invoice: $1,608', 300, 1068, 4.06),
	(4, '2020-11-19', '2020-11-20', NULL, 'Invoice paid in full', 500, 0, 5.26),
	(5, '2020-11-19', '2020-11-20', NULL, 'Invoice paid in full', 500, 0, 4.06),
	(6, '2020-11-16', '2020-11-17', NULL, 'Invoice paid in full', 100, 0, 5.26),
	(7, '2020-11-11', '2020-11-12', NULL, 'Remaining invoice: $5,060', 1000, 5060, 5.26);

INSERT INTO Asset_Maintenance_Schedule            
VALUES 
	(1, '2021-03-14'),
	(2, '2021-01-24'),
	(3, '2021-08-27'),
	(6, '2021-10-05'),
	(7, '2021-03-06'),
	(11, '2021-02-28'),
	(1, '2021-12-14'),
	(2, '2021-06-06'),
	(3, '2021-09-25'),
    (3, '2020-12-04'),
	(3, '2020-11-30'),
	(3, '2020-12-30'),
	(3, '2020-12-06') 



