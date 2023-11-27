create schema if not exists straycats;

create table if not exists straycats.cat
(
    id                serial,
    name              varchar(255) not null,
    birth_date        date not null,
    breed             varchar(255) not null,
    gender            smallint not null,
    removed_from_sale boolean not null default false,
    price_id          integer not null,

    constraint pk_cat primary key (id)
);

create index if not exists idx_gender_breed
on straycats.cat(gender, breed);