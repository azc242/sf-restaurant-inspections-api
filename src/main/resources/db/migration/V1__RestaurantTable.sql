CREATE TABLE restaurant (
	id UUID NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	zip VARCHAR(255),
	address VARCHAR(255),
	phone VARCHAR(255)
);

CREATE TABLE inspection (
	restaurant_id UUID NOT NULL,
	date VARCHAR(255) NOT NULL,
	score int,
	violation VARCHAR(255),
	risk VARCHAR(255),
	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);
