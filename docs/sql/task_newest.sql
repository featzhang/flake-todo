drop table if exists task;
create table task
(
    task_id                 integer
        constraint task_pk
            primary key autoincrement,
    create_time             long                                                                            default current_timestamp,
    update_time             long                                                                            default current_timestamp,
    title                   varchar not null,
    content                 varchar,
    day_id                  integer not null,
    type_id                 int,
    start_time              long,
    end_time                long,
    priority_order          int                                                                             default -1,
    importance_urgency_axis int check ( importance_urgency_axis >= 0 and importance_urgency_axis <= 4 )     default 0,
    finished                boolean                                                                         default false,
    attachment              varchar,
    store_status            int check ( store_status in ('0', '1') )                                        default 1 not null,
    percent                 int check ( percent in
                                        ('0', '10', '20', '30', '40', '50', '60', '70', '90', '90', '100')) default 0,
    expiration_day          int check (expiration_day >= 0 and expiration_day <= 1231)                      default 0,
    expiration_time         int check ( expiration_time >= 0 and expiration_time <= 5959 )                  default 0,
    repetition              text check ( repetition in
                                         ('every_day', 'every_week', 'every_month', 'every_year', 'every_lunar_year',
                                          'legal_workday', 'ebbinghaus_mnemonics'))

);

create unique index task_task_id_uindex
    on task (task_id);