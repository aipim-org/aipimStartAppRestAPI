use smartcarth;
INSERT INTO tasks (name,description,priority,status,start_time,end_time,archived)
VALUES ('Gathering Requirement','Requirement Gathering','MEDIUM','COMPLETED','2015-08-30 16:55:12','2015-08-30 19:55:12',0);

INSERT INTO tasks (name,description,priority,status,start_time,end_time,archived)
VALUES ('Application Designing','Application Designing','MEDIUM','ACTIVE','2015-08-30 16:55:12','2015-08-30 18:55:12',0);

INSERT INTO tasks (name,description,priority,status,start_time,end_time,archived)
VALUES ('Implementation','Implementation','MEDIUM','ACTIVE','2015-08-30 16:55:13','2015-08-30 19:55:13',0);

INSERT INTO tasks (name,description,priority,status,start_time,end_time,archived)
VALUES ('Unit Testing','Unit Testing','LOW','ACTIVE','2015-08-30 16:55:13','2015-08-30 20:55:13',0);

INSERT INTO tasks (name,description,priority,status,start_time,end_time,archived)
VALUES ('Maintanence','Maintanence','LOW','ACTIVE','2015-08-30 16:55:13','2015-08-30 21:55:13',0);

SELECT * FROM tasks;