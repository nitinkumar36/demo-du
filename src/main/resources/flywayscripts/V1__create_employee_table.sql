create sequence EMP_ID_SEQ start 100 increment 1;

create table EMPLOYEE (
                          id int8 not null,
                          age int4,
                          city varchar(255),
                          created_by varchar(255),
                          created_date timestamp,
                          first_name varchar(255),
                          last_modified_by varchar(255),
                          last_modified_date timestamp,
                          last_name varchar(255),
                          primary key (id)
);