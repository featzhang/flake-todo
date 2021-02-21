create table task
(
    task_id                 integer
        constraint task_pk
            primary key autoincrement,
    create_time             long    default current_timestamp,
    update_time             long    default current_timestamp,
    title                   varchar,
    content                 varchar,
    day_id                  integer not null,
    type_id                 int,
    start_time              long,
    end_time                long,
    priority_order          int     default -1,
    importance_urgency_axis int     default 0,
    finished                boolean default false,
    attachment              varchar,
    store_status            int     default 1 not null
);

create unique index task_task_id_uindex
    on task (task_id);