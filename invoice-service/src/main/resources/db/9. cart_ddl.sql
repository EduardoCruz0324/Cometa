DROP TABLE IF EXISTS cart_item;

CREATE TABLE cart_item(
	cart_item_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL ,
    gtin CHAR(13) NOT NULL,
	quantity INT NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (cart_item_id)
);