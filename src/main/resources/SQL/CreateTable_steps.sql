-- auto-generated definition
create table steps
(
	id integer not null
		constraint steps_pkey
			primary key,
	lesson_id integer not null
		constraint steps_lessons__fk
			references lessons
)
;

create unique index steps_id_uindex
	on steps (id)
;

