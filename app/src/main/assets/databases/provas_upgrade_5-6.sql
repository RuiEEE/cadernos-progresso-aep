alter table provas
add is_concluded integer default 0;

alter table provas
add concluded_at text;