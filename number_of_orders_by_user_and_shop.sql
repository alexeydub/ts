-- retrieve the total number of orders that has been made by a user grouped by shop
select shop_ref, COUNT(id) as orders_count
from shop_order
where user_ref = 1
group by shop_ref

-- retrieve shops that has more than 1 order made by a user
select shop_ref, COUNT(id) as orders_count
from shop_order
where user_ref = 1
group by shop_ref
having COUNT(id) >1

-- retrieve the total number of orders that has been made by users grouped by shop
select user_ref, shop_ref, COUNT(id) as orders_count
from shop_order
group by user_ref, shop_ref