DROP TABLE IF EXISTS invoice;

CREATE TABLE invoice(
	invoice_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL ,
	subtotal FLOAT NOT NULL,
	taxes FLOAT NOT NULL,
	total FLOAT NOT NULL,
    created_at DATE NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (invoice_id)
);