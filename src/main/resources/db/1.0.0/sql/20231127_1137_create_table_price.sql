create table if not exists straycats.price
(
    id        serial,
    cat_id    integer not null,
    price     numeric(16,2) not null,
    create_ts timestamp not null,

    constraint pk_price primary key (id),
    constraint fk1_price_cat$cat foreign key (cat_id)
    references straycats.cat(id) on delete restrict
);

create index if not exists idx_cat_id
on straycats.price(cat_id);