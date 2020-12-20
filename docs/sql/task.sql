-- auto-generated definition
create table task
(
    task_id     integer
        constraint task_pk
            primary key autoincrement,
    create_time long default current_timestamp,
    update_time long default current_timestamp,
    title       varchar,
    content     varchar,
    dayId       char not null,
    type        int,
    start_time  long,
    end_time    long
);

create unique index task_task_id_uindex
    on task (task_id);

