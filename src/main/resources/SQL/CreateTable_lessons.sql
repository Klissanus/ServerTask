create table lessons
(
	id integer not null
		constraint lessons_pkey
			primary key
)
;

create unique index lessons_lesson_id_uindex
	on lessons (id)
;