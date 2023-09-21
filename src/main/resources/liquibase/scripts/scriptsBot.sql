-- liquibase formatted sql

-- changeset krassotin:1
create table notification_task(

                                  id int8 primary key ,
                                  chat_id int8 not null ,
                                  task_text text not null ,
                                  date_time timestamp not null
);