create index if not exists idx_price
on straycats.price(price);

create index if not exists idx_removed_from_sale_gender
on straycats.cat(removed_from_sale, gender);