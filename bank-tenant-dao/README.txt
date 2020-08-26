mvn clean install \
  -Ddb.name=bank_infrastructure \
  -Ddb.url=jdbc:mysql://192.168.1.6:3306/bank_infrastructure?useSSL=true \
  -Ddb.user=bank \
  -Ddb.password=password \
  -Ddb.driver=com.mysql.cj.jdbc.Driver

mvn flyway:migrate \
  -Ddb.name=bank_infrastructure \
  -Ddb.tenant.name=bank \
  -Ddb.url=jdbc:mysql://192.168.1.6:3306/bank_infrastructure?useSSL=true \
  -Ddb.user=bank \
  -Ddb.password=password \
  -Ddb.driver=com.mysql.cj.jdbc.Driver
