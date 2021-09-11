drop table if exists task_tmp;
drop index if exists task_tmp_task_id_uindex;
drop table if exists task_20210910;
create table task_tmp
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
                                         ('NONE', 'EVERY_DAY', 'EVERY_WEEK', 'EVERY_MONTH', 'EVERY_YEAR',
                                          'EVERY_LUNAR_YEAR',
                                          'LEGAL_WORKDAY', 'EBBINGHAUS_MNEMONICS'))

);

create unique index task_tmp_task_id_uindex
    on task_tmp (task_id);

insert into task_tmp(task_id, create_time, update_time, title, content, day_id, type_id, start_time, end_time,
                     priority_order, importance_urgency_axis, finished, attachment, store_status, percent,
                     expiration_day, expiration_time, repetition)
select task_id,
       create_time,
       update_time,
       title,
       content,
       day_id,
       type_id,
       start_time,
       end_time,
       priority_order,
       importance_urgency_axis,
       finished,
       attachment,
       store_status,
       0,
       0,
       0,
       'NONE'
from task;

alter table task
    rename to task_20210910;
alter table task_tmp
    rename to task;