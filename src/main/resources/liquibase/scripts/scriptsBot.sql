-- liquibase formatted sql

-- changeset krassotin:1
create table notification_task(

                                  task_id serial primary key ,
                                  chat_id serial not null ,
                                  task_text text not null ,
                                  date_time timestamp not null
);