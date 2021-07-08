DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS department;

CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   UNIQUE(name)
);



CREATE TABLE department (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  information VARCHAR(250) NOT NULL,
  owner_id BIGINT NOT NULL,
  parent_id BIGINT DEFAULT NULL,
  foreign key (owner_id) references users(id)
);

--INSERT INTO department(information,owner_id,parent_id) VALUES
--    ('Marketing',1,null),
--    ('Business',2,1),
--    ('Design',3,2),
--    ('Finance',4,3),
--    ('HR',3,null),
--    ('Development',4,null);
--
--
--INSERT INTO users(name) VALUES
--    ('Bil'),
--    ('Tom'),
--    ('Mark'),
--    ('Jane');