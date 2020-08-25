mvn clean install \
  -Ddb.tenant.name=bank \
  -Ddb.tenant.url=jdbc:mysql://192.168.1.6:3306/bank?useSSL=true \
  -Ddb.tenant.user=bank \
  -Ddb.tenant.password=password \
  -Ddb.tenant.driver=com.mysql.cj.jdbc.Driver \
  -Ddb.name=bank_infrastructure \
  -Ddb.url=jdbc:mysql://192.168.1.6:3306/bank_infrastructure?useSSL=true \
  -Ddb.user=bank \
  -Ddb.password=password \
  -Ddb.driver=com.mysql.cj.jdbc.Driver

mvn flyway:migrate
  -Ddb.tenant.name=bank \
  -Ddb.tenant.url=jdbc:mysql://192.168.1.6:3306/bank?useSSL=true \
  -Ddb.tenant.user=bank \
  -Ddb.tenant.password=password \
  -Ddb.tenant.driver=com.mysql.cj.jdbc.Driver
