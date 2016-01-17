alter table provas_especialidade
add is_concluded integer default 0;

alter table provas_especialidade
add concluded_at text;