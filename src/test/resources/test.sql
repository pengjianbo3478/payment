SELECT * from payment_data.pay_prepay ORDER BY create_time DESC;

SELECT * from payment_data.pay_stream ORDER by create_time DESC;

SELECT * from payment_data.pay_main ORDER by create_time DESC;

UPDATE payment_data.pay_main SET pay_status='01';

UPDATE payment_data.pay_main SET request_date='2017-05-09';

SELECT * from payment_data.pay_detail ORDER by create_time DESC;


DELETE FROM payment_data.pay_stream;
DELETE FROM payment_data.pay_prepay;
DELETE FROM payment_data.pay_main;
DELETE FROM payment_data.pay_detail;
DELETE FROM payment_data.pay_return;

SELECT * from payment_data.pay_stream order by create_time DESC;
SELECT * from payment_data.pay_prepay order by create_time DESC;
SELECT * from payment_data.pay_main order by create_time DESC;
SELECT * from payment_data.pay_detail order by create_time DESC;
SELECT * from payment_data.pay_return order by create_time DESC;




--测试给乐商户：   --付费通商号    --终端
1000001900003  --819290054111340   99902400 
1000001000419  --805024000000203
1000001000462  --100000100046210
1000001000507  --819290054111341
1000001900002  --819584073924714


-Dserver.port=8080
-Dspring.cloud.consul.discovery.hostname=192.168.0.181

查看生产日志
跳板机 120.78.184.76:63389 ->192.168.10.237:5601