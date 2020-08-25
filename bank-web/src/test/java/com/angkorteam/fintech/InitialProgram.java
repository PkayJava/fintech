package com.angkorteam.fintech;

import com.angkorteam.fintech.meta.SchemaVersion;
import com.angkorteam.fintech.meta.infrastructure.Tenant;
import com.angkorteam.fintech.meta.infrastructure.TenantServerConnection;
import com.angkorteam.fintech.meta.infrastructure.TimeZone;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Driver;
import java.util.Date;

public class InitialProgram {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        String publicIp = "vpn.i365work.com";
        String privateIp = "192.168.1.6";
        String ip = privateIp;
        int privatePort = 3306;
        int publicPort = 21631;
        int port = privatePort;

        JdbcDataContext infrastructureDataContext = null;
        {
            DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
            properties.put("type", "jdbc");
            properties.put("url", "jdbc:mysql://" + ip + ":" + port + "/fineract_tenants?useSSL=true");
            properties.put("driver-class", Driver.class.getName());
            properties.put("username", "bank");
            properties.put("password", "password");
            infrastructureDataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        }

        Tenant tenant = Tenant.staticInitialize(infrastructureDataContext);
        TenantServerConnection tenantServerConnection = TenantServerConnection.staticInitialize(infrastructureDataContext);
        TimeZone timeZone = TimeZone.staticInitialize(infrastructureDataContext);
        SchemaVersion schemaVersion = SchemaVersion.staticInitialize(infrastructureDataContext);
        infrastructureDataContext.executeUpdate(callback -> {
            callback.insertInto(tenant)
                    .value(tenant.ID, 1)
                    .value(tenant.IDENTIFIER, "default")
                    .value(tenant.NAME, "Demo Bank")
                    .value(tenant.TIMEZONE_ID, "Asia/Phnom_Penh")
                    .value(tenant.OLTP_ID, 1)
                    .value(tenant.REPORT_ID, 1)
                    .execute();

            callback.insertInto(schemaVersion)
                    .value(schemaVersion.INSTALLED_RANK, 1)
                    .value(schemaVersion.VERSION, 1)
                    .value(schemaVersion.DESCRIPTION, "mifos-platform-shared-tenants")
                    .value(schemaVersion.TYPE, "SQL")
                    .value(schemaVersion.SCRIPT, "V1__mifos-platform-shared-tenants.sql")
                    .value(schemaVersion.CHECKSUM, -2077956380)
                    .value(schemaVersion.INSTALLED_BY, "bank")
                    .value(schemaVersion.INSTALLED_ON, new Date())
                    .value(schemaVersion.EXECUTION_TIME, 627)
                    .value(schemaVersion.SUCCESS, 1)
                    .execute();

            callback.insertInto(schemaVersion)
                    .value(schemaVersion.INSTALLED_RANK, 2)
                    .value(schemaVersion.VERSION, 2)
                    .value(schemaVersion.DESCRIPTION, "externalize-connection-properties")
                    .value(schemaVersion.TYPE, "SQL")
                    .value(schemaVersion.SCRIPT, "V2__externalize-connection-properties.sql")
                    .value(schemaVersion.CHECKSUM, -1714204083)
                    .value(schemaVersion.INSTALLED_BY, "bank")
                    .value(schemaVersion.INSTALLED_ON, new Date())
                    .value(schemaVersion.EXECUTION_TIME, 185)
                    .value(schemaVersion.SUCCESS, 1)
                    .execute();

            callback.insertInto(schemaVersion)
                    .value(schemaVersion.INSTALLED_RANK, 3)
                    .value(schemaVersion.VERSION, 3)
                    .value(schemaVersion.DESCRIPTION, "deadlock-retry-properties")
                    .value(schemaVersion.TYPE, "SQL")
                    .value(schemaVersion.SCRIPT, "V3__deadlock-retry-properties.sql")
                    .value(schemaVersion.CHECKSUM, -1359786045)
                    .value(schemaVersion.INSTALLED_BY, "bank")
                    .value(schemaVersion.INSTALLED_ON, new Date())
                    .value(schemaVersion.EXECUTION_TIME, 130)
                    .value(schemaVersion.SUCCESS, 1)
                    .execute();

            callback.insertInto(schemaVersion)
                    .value(schemaVersion.INSTALLED_RANK, 4)
                    .value(schemaVersion.VERSION, 4)
                    .value(schemaVersion.DESCRIPTION, "introduced oltpId reportId columns and tenants server connection table")
                    .value(schemaVersion.TYPE, "SQL")
                    .value(schemaVersion.SCRIPT, "V4__introduced_oltpId_reportId_columns_and_tenants_server_connection_table.sql")
                    .value(schemaVersion.CHECKSUM, -124371966)
                    .value(schemaVersion.INSTALLED_BY, "bank")
                    .value(schemaVersion.INSTALLED_ON, new Date())
                    .value(schemaVersion.EXECUTION_TIME, 681)
                    .value(schemaVersion.SUCCESS, 1)
                    .execute();

            callback.insertInto(tenantServerConnection)
                    .value(tenantServerConnection.ID, 1)
                    .value(tenantServerConnection.POOL_MAX_IDLE, 10)
                    .value(tenantServerConnection.POOL_INITIAL_SIZE, 5)
                    .value(tenantServerConnection.POOL_LOG_ABANDONED, 1)
                    .value(tenantServerConnection.POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS, 60000)
                    .value(tenantServerConnection.POOL_SUSPECT_TIMEOUT, 60)
                    .value(tenantServerConnection.POOL_ABANDON_WHEN_PERCENTAGE_FULL, 50)
                    .value(tenantServerConnection.POOL_REMOVE_ABANDONED, 1)
                    .value(tenantServerConnection.DEADLOCK_MAX_RETRY_INTERVAL, 1)
                    .value(tenantServerConnection.SCHEMA_NAME, "fineract_default")
                    .value(tenantServerConnection.SCHEMA_SERVER_PORT, 3306)
                    .value(tenantServerConnection.POOL_MAX_ACTIVE, 40)
                    .value(tenantServerConnection.POOL_VALIDATION_INTERVAL, 30000)
                    .value(tenantServerConnection.SCHEMA_PASSWORD, "password")
                    .value(tenantServerConnection.AUTO_UPDATE, 1)
                    .value(tenantServerConnection.POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS, 34000)
                    .value(tenantServerConnection.POOL_MIN_IDLE, 20)
                    .value(tenantServerConnection.POOL_TEST_ON_BORROW, 1)
                    .value(tenantServerConnection.DEADLOCK_MAX_RETRY, 0)
                    .value(tenantServerConnection.SCHEMA_SERVER, "192.168.1.6")
                    .value(tenantServerConnection.POOL_REMOVE_ABANDONED_TIMEOUT, 60)
                    .value(tenantServerConnection.SCHEMA_USERNAME, "bank")
                    .execute();

            insertTimeZone(timeZone, callback, 1, "AD", "Europe/Andorra");
            insertTimeZone(timeZone, callback, 2, "AE", "Asia/Dubai");
            insertTimeZone(timeZone, callback, 3, "AF", "Asia/Kabul");
            insertTimeZone(timeZone, callback, 4, "AG", "America/Antigua");
            insertTimeZone(timeZone, callback, 5, "AI", "America/Anguilla");
            insertTimeZone(timeZone, callback, 6, "AL", "Europe/Tirane");
            insertTimeZone(timeZone, callback, 7, "AM", "Asia/Yerevan");
            insertTimeZone(timeZone, callback, 8, "AO", "Africa/Luanda");
            insertTimeZone(timeZone, callback, 9, "AQ", "Antarctica/McMurdo");
            insertTimeZone(timeZone, callback, 10, "AQ", "Antarctica/South_Pole");
            insertTimeZone(timeZone, callback, 11, "AQ", "Antarctica/Rothera");
            insertTimeZone(timeZone, callback, 12, "AQ", "Antarctica/Palmer");
            insertTimeZone(timeZone, callback, 13, "AQ", "Antarctica/Mawson");
            insertTimeZone(timeZone, callback, 14, "AQ", "Antarctica/Davis");
            insertTimeZone(timeZone, callback, 15, "AQ", "Antarctica/Casey");
            insertTimeZone(timeZone, callback, 16, "AQ", "Antarctica/Vostok");
            insertTimeZone(timeZone, callback, 17, "AQ", "Antarctica/DumontDUrville");
            insertTimeZone(timeZone, callback, 18, "AQ", "Antarctica/Syowa");
            insertTimeZone(timeZone, callback, 19, "AQ", "Antarctica/Macquarie");
            insertTimeZone(timeZone, callback, 20, "AR", "America/Argentina/Buenos_Aires");
            insertTimeZone(timeZone, callback, 21, "AR", "America/Argentina/Cordoba");
            insertTimeZone(timeZone, callback, 22, "AR", "America/Argentina/Salta");
            insertTimeZone(timeZone, callback, 23, "AR", "America/Argentina/Jujuy");
            insertTimeZone(timeZone, callback, 24, "AR", "America/Argentina/Tucuman");
            insertTimeZone(timeZone, callback, 25, "AR", "America/Argentina/Catamarca");
            insertTimeZone(timeZone, callback, 26, "AR", "America/Argentina/La_Rioja");
            insertTimeZone(timeZone, callback, 27, "AR", "America/Argentina/San_Juan");
            insertTimeZone(timeZone, callback, 28, "AR", "America/Argentina/Mendoza");
            insertTimeZone(timeZone, callback, 29, "AR", "America/Argentina/San_Luis");
            insertTimeZone(timeZone, callback, 30, "AR", "America/Argentina/Rio_Gallegos");
            insertTimeZone(timeZone, callback, 31, "AR", "America/Argentina/Ushuaia");
            insertTimeZone(timeZone, callback, 32, "AS", "Pacific/Pago_Pago");
            insertTimeZone(timeZone, callback, 33, "AT", "Europe/Vienna");
            insertTimeZone(timeZone, callback, 34, "AU", "Australia/Lord_Howe");
            insertTimeZone(timeZone, callback, 35, "AU", "Australia/Hobart");
            insertTimeZone(timeZone, callback, 36, "AU", "Australia/Currie");
            insertTimeZone(timeZone, callback, 37, "AU", "Australia/Melbourne");
            insertTimeZone(timeZone, callback, 38, "AU", "Australia/Sydney");
            insertTimeZone(timeZone, callback, 39, "AU", "Australia/Broken_Hill");
            insertTimeZone(timeZone, callback, 40, "AU", "Australia/Brisbane");
            insertTimeZone(timeZone, callback, 41, "AU", "Australia/Lindeman");
            insertTimeZone(timeZone, callback, 42, "AU", "Australia/Adelaide");
            insertTimeZone(timeZone, callback, 43, "AU", "Australia/Darwin");
            insertTimeZone(timeZone, callback, 44, "AU", "Australia/Perth");
            insertTimeZone(timeZone, callback, 45, "AU", "Australia/Eucla");
            insertTimeZone(timeZone, callback, 46, "AW", "America/Aruba");
            insertTimeZone(timeZone, callback, 47, "AX", "Europe/Mariehamn");
            insertTimeZone(timeZone, callback, 48, "AZ", "Asia/Baku");
            insertTimeZone(timeZone, callback, 49, "BA", "Europe/Sarajevo");
            insertTimeZone(timeZone, callback, 50, "BB", "America/Barbados");
            insertTimeZone(timeZone, callback, 51, "BD", "Asia/Dhaka");
            insertTimeZone(timeZone, callback, 52, "BE", "Europe/Brussels");
            insertTimeZone(timeZone, callback, 53, "BF", "Africa/Ouagadougou");
            insertTimeZone(timeZone, callback, 54, "BG", "Europe/Sofia");
            insertTimeZone(timeZone, callback, 55, "BH", "Asia/Bahrain");
            insertTimeZone(timeZone, callback, 56, "BI", "Africa/Bujumbura");
            insertTimeZone(timeZone, callback, 57, "BJ", "Africa/Porto-Novo");
            insertTimeZone(timeZone, callback, 58, "BL", "America/St_Barthelemy");
            insertTimeZone(timeZone, callback, 59, "BM", "Atlantic/Bermuda");
            insertTimeZone(timeZone, callback, 60, "BN", "Asia/Brunei");
            insertTimeZone(timeZone, callback, 61, "BO", "America/La_Paz");
            insertTimeZone(timeZone, callback, 62, "BQ", "America/Kralendijk");
            insertTimeZone(timeZone, callback, 63, "BR", "America/Noronha");
            insertTimeZone(timeZone, callback, 64, "BR", "America/Belem");
            insertTimeZone(timeZone, callback, 65, "BR", "America/Fortaleza");
            insertTimeZone(timeZone, callback, 66, "BR", "America/Recife");
            insertTimeZone(timeZone, callback, 67, "BR", "America/Araguaina");
            insertTimeZone(timeZone, callback, 68, "BR", "America/Maceio");
            insertTimeZone(timeZone, callback, 69, "BR", "America/Bahia");
            insertTimeZone(timeZone, callback, 70, "BR", "America/Sao_Paulo");
            insertTimeZone(timeZone, callback, 71, "BR", "America/Campo_Grande");
            insertTimeZone(timeZone, callback, 72, "BR", "America/Cuiaba");
            insertTimeZone(timeZone, callback, 73, "BR", "America/Santarem");
            insertTimeZone(timeZone, callback, 74, "BR", "America/Porto_Velho");
            insertTimeZone(timeZone, callback, 75, "BR", "America/Boa_Vista");
            insertTimeZone(timeZone, callback, 76, "BR", "America/Manaus");
            insertTimeZone(timeZone, callback, 77, "BR", "America/Eirunepe");
            insertTimeZone(timeZone, callback, 78, "BR", "America/Rio_Branco");
            insertTimeZone(timeZone, callback, 79, "BS", "America/Nassau");
            insertTimeZone(timeZone, callback, 80, "BT", "Asia/Thimphu");
            insertTimeZone(timeZone, callback, 81, "BW", "Africa/Gaborone");
            insertTimeZone(timeZone, callback, 82, "BY", "Europe/Minsk");
            insertTimeZone(timeZone, callback, 83, "BZ", "America/Belize");
            insertTimeZone(timeZone, callback, 84, "CA", "America/St_Johns");
            insertTimeZone(timeZone, callback, 85, "CA", "America/Halifax");
            insertTimeZone(timeZone, callback, 86, "CA", "America/Glace_Bay");
            insertTimeZone(timeZone, callback, 87, "CA", "America/Moncton");
            insertTimeZone(timeZone, callback, 88, "CA", "America/Goose_Bay");
            insertTimeZone(timeZone, callback, 89, "CA", "America/Blanc-Sablon");
            insertTimeZone(timeZone, callback, 90, "CA", "America/Montreal");
            insertTimeZone(timeZone, callback, 91, "CA", "America/Toronto");
            insertTimeZone(timeZone, callback, 92, "CA", "America/Nipigon");
            insertTimeZone(timeZone, callback, 93, "CA", "America/Thunder_Bay");
            insertTimeZone(timeZone, callback, 94, "CA", "America/Iqaluit");
            insertTimeZone(timeZone, callback, 95, "CA", "America/Pangnirtung");
            insertTimeZone(timeZone, callback, 96, "CA", "America/Resolute");
            insertTimeZone(timeZone, callback, 97, "CA", "America/Atikokan");
            insertTimeZone(timeZone, callback, 98, "CA", "America/Rankin_Inlet");
            insertTimeZone(timeZone, callback, 99, "CA", "America/Winnipeg");
            insertTimeZone(timeZone, callback, 100, "CA", "America/Rainy_River");
            insertTimeZone(timeZone, callback, 101, "CA", "America/Regina");
            insertTimeZone(timeZone, callback, 102, "CA", "America/Swift_Current");
            insertTimeZone(timeZone, callback, 103, "CA", "America/Edmonton");
            insertTimeZone(timeZone, callback, 104, "CA", "America/Cambridge_Bay");
            insertTimeZone(timeZone, callback, 105, "CA", "America/Yellowknife");
            insertTimeZone(timeZone, callback, 106, "CA", "America/Inuvik");
            insertTimeZone(timeZone, callback, 107, "CA", "America/Creston");
            insertTimeZone(timeZone, callback, 108, "CA", "America/Dawson_Creek");
            insertTimeZone(timeZone, callback, 109, "CA", "America/Vancouver");
            insertTimeZone(timeZone, callback, 110, "CA", "America/Whitehorse");
            insertTimeZone(timeZone, callback, 111, "CA", "America/Dawson");
            insertTimeZone(timeZone, callback, 112, "CC", "Indian/Cocos");
            insertTimeZone(timeZone, callback, 113, "CD", "Africa/Kinshasa");
            insertTimeZone(timeZone, callback, 114, "CD", "Africa/Lubumbashi");
            insertTimeZone(timeZone, callback, 115, "CF", "Africa/Bangui");
            insertTimeZone(timeZone, callback, 116, "CG", "Africa/Brazzaville");
            insertTimeZone(timeZone, callback, 117, "CH", "Europe/Zurich");
            insertTimeZone(timeZone, callback, 118, "CI", "Africa/Abidjan");
            insertTimeZone(timeZone, callback, 119, "CK", "Pacific/Rarotonga");
            insertTimeZone(timeZone, callback, 120, "CL", "America/Santiago");
            insertTimeZone(timeZone, callback, 121, "CL", "Pacific/Easter");
            insertTimeZone(timeZone, callback, 122, "CM", "Africa/Douala");
            insertTimeZone(timeZone, callback, 123, "CN", "Asia/Shanghai");
            insertTimeZone(timeZone, callback, 124, "CN", "Asia/Harbin");
            insertTimeZone(timeZone, callback, 125, "CN", "Asia/Chongqing");
            insertTimeZone(timeZone, callback, 126, "CN", "Asia/Urumqi");
            insertTimeZone(timeZone, callback, 127, "CN", "Asia/Kashgar");
            insertTimeZone(timeZone, callback, 128, "CO", "America/Bogota");
            insertTimeZone(timeZone, callback, 129, "CR", "America/Costa_Rica");
            insertTimeZone(timeZone, callback, 130, "CU", "America/Havana");
            insertTimeZone(timeZone, callback, 131, "CV", "Atlantic/Cape_Verde");
            insertTimeZone(timeZone, callback, 132, "CW", "America/Curacao");
            insertTimeZone(timeZone, callback, 133, "CX", "Indian/Christmas");
            insertTimeZone(timeZone, callback, 134, "CY", "Asia/Nicosia");
            insertTimeZone(timeZone, callback, 135, "CZ", "Europe/Prague");
            insertTimeZone(timeZone, callback, 136, "DE", "Europe/Berlin");
            insertTimeZone(timeZone, callback, 137, "DJ", "Africa/Djibouti");
            insertTimeZone(timeZone, callback, 138, "DK", "Europe/Copenhagen");
            insertTimeZone(timeZone, callback, 139, "DM", "America/Dominica");
            insertTimeZone(timeZone, callback, 140, "DO", "America/Santo_Domingo");
            insertTimeZone(timeZone, callback, 141, "DZ", "Africa/Algiers");
            insertTimeZone(timeZone, callback, 142, "EC", "America/Guayaquil");
            insertTimeZone(timeZone, callback, 143, "EC", "Pacific/Galapagos");
            insertTimeZone(timeZone, callback, 144, "EE", "Europe/Tallinn");
            insertTimeZone(timeZone, callback, 145, "EG", "Africa/Cairo");
            insertTimeZone(timeZone, callback, 146, "EH", "Africa/El_Aaiun");
            insertTimeZone(timeZone, callback, 147, "ER", "Africa/Asmara");
            insertTimeZone(timeZone, callback, 148, "ES", "Europe/Madrid");
            insertTimeZone(timeZone, callback, 149, "ES", "Africa/Ceuta");
            insertTimeZone(timeZone, callback, 150, "ES", "Atlantic/Canary");
            insertTimeZone(timeZone, callback, 151, "ET", "Africa/Addis_Ababa");
            insertTimeZone(timeZone, callback, 152, "FI", "Europe/Helsinki");
            insertTimeZone(timeZone, callback, 153, "FJ", "Pacific/Fiji");
            insertTimeZone(timeZone, callback, 154, "FK", "Atlantic/Stanley");
            insertTimeZone(timeZone, callback, 155, "FM", "Pacific/Chuuk");
            insertTimeZone(timeZone, callback, 156, "FM", "Pacific/Pohnpei");
            insertTimeZone(timeZone, callback, 157, "FM", "Pacific/Kosrae");
            insertTimeZone(timeZone, callback, 158, "FO", "Atlantic/Faroe");
            insertTimeZone(timeZone, callback, 159, "FR", "Europe/Paris");
            insertTimeZone(timeZone, callback, 160, "GA", "Africa/Libreville");
            insertTimeZone(timeZone, callback, 161, "GB", "Europe/London");
            insertTimeZone(timeZone, callback, 162, "GD", "America/Grenada");
            insertTimeZone(timeZone, callback, 163, "GE", "Asia/Tbilisi");
            insertTimeZone(timeZone, callback, 164, "GF", "America/Cayenne");
            insertTimeZone(timeZone, callback, 165, "GG", "Europe/Guernsey");
            insertTimeZone(timeZone, callback, 166, "GH", "Africa/Accra");
            insertTimeZone(timeZone, callback, 167, "GI", "Europe/Gibraltar");
            insertTimeZone(timeZone, callback, 168, "GL", "America/Godthab");
            insertTimeZone(timeZone, callback, 169, "GL", "America/Danmarkshavn");
            insertTimeZone(timeZone, callback, 170, "GL", "America/Scoresbysund");
            insertTimeZone(timeZone, callback, 171, "GL", "America/Thule");
            insertTimeZone(timeZone, callback, 172, "GM", "Africa/Banjul");
            insertTimeZone(timeZone, callback, 173, "GN", "Africa/Conakry");
            insertTimeZone(timeZone, callback, 174, "GP", "America/Guadeloupe");
            insertTimeZone(timeZone, callback, 175, "GQ", "Africa/Malabo");
            insertTimeZone(timeZone, callback, 176, "GR", "Europe/Athens");
            insertTimeZone(timeZone, callback, 177, "GS", "Atlantic/South_Georgia");
            insertTimeZone(timeZone, callback, 178, "GT", "America/Guatemala");
            insertTimeZone(timeZone, callback, 179, "GU", "Pacific/Guam");
            insertTimeZone(timeZone, callback, 180, "GW", "Africa/Bissau");
            insertTimeZone(timeZone, callback, 181, "GY", "America/Guyana");
            insertTimeZone(timeZone, callback, 182, "HK", "Asia/Hong_Kong");
            insertTimeZone(timeZone, callback, 183, "HN", "America/Tegucigalpa");
            insertTimeZone(timeZone, callback, 184, "HR", "Europe/Zagreb");
            insertTimeZone(timeZone, callback, 185, "HT", "America/Port-au-Prince");
            insertTimeZone(timeZone, callback, 186, "HU", "Europe/Budapest");
            insertTimeZone(timeZone, callback, 187, "ID", "Asia/Jakarta");
            insertTimeZone(timeZone, callback, 188, "ID", "Asia/Pontianak");
            insertTimeZone(timeZone, callback, 189, "ID", "Asia/Makassar");
            insertTimeZone(timeZone, callback, 190, "ID", "Asia/Jayapura");
            insertTimeZone(timeZone, callback, 191, "IE", "Europe/Dublin");
            insertTimeZone(timeZone, callback, 192, "IL", "Asia/Jerusalem");
            insertTimeZone(timeZone, callback, 193, "IM", "Europe/Isle_of_Man");
            insertTimeZone(timeZone, callback, 194, "IN", "Asia/Kolkata");
            insertTimeZone(timeZone, callback, 195, "IO", "Indian/Chagos");
            insertTimeZone(timeZone, callback, 196, "IQ", "Asia/Baghdad");
            insertTimeZone(timeZone, callback, 197, "IR", "Asia/Tehran");
            insertTimeZone(timeZone, callback, 198, "IS", "Atlantic/Reykjavik");
            insertTimeZone(timeZone, callback, 199, "IT", "Europe/Rome");
            insertTimeZone(timeZone, callback, 200, "JE", "Europe/Jersey");
            insertTimeZone(timeZone, callback, 201, "JM", "America/Jamaica");
            insertTimeZone(timeZone, callback, 202, "JO", "Asia/Amman");
            insertTimeZone(timeZone, callback, 203, "JP", "Asia/Tokyo");
            insertTimeZone(timeZone, callback, 204, "KE", "Africa/Nairobi");
            insertTimeZone(timeZone, callback, 205, "KG", "Asia/Bishkek");
            insertTimeZone(timeZone, callback, 206, "KH", "Asia/Phnom_Penh");
            insertTimeZone(timeZone, callback, 207, "KI", "Pacific/Tarawa");
            insertTimeZone(timeZone, callback, 208, "KI", "Pacific/Enderbury");
            insertTimeZone(timeZone, callback, 209, "KI", "Pacific/Kiritimati");
            insertTimeZone(timeZone, callback, 210, "KM", "Indian/Comoro");
            insertTimeZone(timeZone, callback, 211, "KN", "America/St_Kitts");
            insertTimeZone(timeZone, callback, 212, "KP", "Asia/Pyongyang");
            insertTimeZone(timeZone, callback, 213, "KR", "Asia/Seoul");
            insertTimeZone(timeZone, callback, 214, "KW", "Asia/Kuwait");
            insertTimeZone(timeZone, callback, 215, "KY", "America/Cayman");
            insertTimeZone(timeZone, callback, 216, "KZ", "Asia/Almaty");
            insertTimeZone(timeZone, callback, 217, "KZ", "Asia/Qyzylorda");
            insertTimeZone(timeZone, callback, 218, "KZ", "Asia/Aqtobe");
            insertTimeZone(timeZone, callback, 219, "KZ", "Asia/Aqtau");
            insertTimeZone(timeZone, callback, 220, "KZ", "Asia/Oral");
            insertTimeZone(timeZone, callback, 221, "LA", "Asia/Vientiane");
            insertTimeZone(timeZone, callback, 222, "LB", "Asia/Beirut");
            insertTimeZone(timeZone, callback, 223, "LC", "America/St_Lucia");
            insertTimeZone(timeZone, callback, 224, "LI", "Europe/Vaduz");
            insertTimeZone(timeZone, callback, 225, "LK", "Asia/Colombo");
            insertTimeZone(timeZone, callback, 226, "LR", "Africa/Monrovia");
            insertTimeZone(timeZone, callback, 227, "LS", "Africa/Maseru");
            insertTimeZone(timeZone, callback, 228, "LT", "Europe/Vilnius");
            insertTimeZone(timeZone, callback, 229, "LU", "Europe/Luxembourg");
            insertTimeZone(timeZone, callback, 230, "LV", "Europe/Riga");
            insertTimeZone(timeZone, callback, 231, "LY", "Africa/Tripoli");
            insertTimeZone(timeZone, callback, 232, "MA", "Africa/Casablanca");
            insertTimeZone(timeZone, callback, 233, "MC", "Europe/Monaco");
            insertTimeZone(timeZone, callback, 234, "MD", "Europe/Chisinau");
            insertTimeZone(timeZone, callback, 235, "ME", "Europe/Podgorica");
            insertTimeZone(timeZone, callback, 236, "MF", "America/Marigot");
            insertTimeZone(timeZone, callback, 237, "MG", "Indian/Antananarivo");
            insertTimeZone(timeZone, callback, 238, "MH", "Pacific/Majuro");
            insertTimeZone(timeZone, callback, 239, "MH", "Pacific/Kwajalein");
            insertTimeZone(timeZone, callback, 240, "MK", "Europe/Skopje");
            insertTimeZone(timeZone, callback, 241, "ML", "Africa/Bamako");
            insertTimeZone(timeZone, callback, 242, "MM", "Asia/Rangoon");
            insertTimeZone(timeZone, callback, 243, "MN", "Asia/Ulaanbaatar");
            insertTimeZone(timeZone, callback, 244, "MN", "Asia/Hovd");
            insertTimeZone(timeZone, callback, 245, "MN", "Asia/Choibalsan");
            insertTimeZone(timeZone, callback, 246, "MO", "Asia/Macau");
            insertTimeZone(timeZone, callback, 247, "MP", "Pacific/Saipan");
            insertTimeZone(timeZone, callback, 248, "MQ", "America/Martinique");
            insertTimeZone(timeZone, callback, 249, "MR", "Africa/Nouakchott");
            insertTimeZone(timeZone, callback, 250, "MS", "America/Montserrat");
            insertTimeZone(timeZone, callback, 251, "MT", "Europe/Malta");
            insertTimeZone(timeZone, callback, 252, "MU", "Indian/Mauritius");
            insertTimeZone(timeZone, callback, 253, "MV", "Indian/Maldives");
            insertTimeZone(timeZone, callback, 254, "MW", "Africa/Blantyre");
            insertTimeZone(timeZone, callback, 255, "MX", "America/Mexico_City");
            insertTimeZone(timeZone, callback, 256, "MX", "America/Cancun");
            insertTimeZone(timeZone, callback, 257, "MX", "America/Merida");
            insertTimeZone(timeZone, callback, 258, "MX", "America/Monterrey");
            insertTimeZone(timeZone, callback, 259, "MX", "America/Matamoros");
            insertTimeZone(timeZone, callback, 260, "MX", "America/Mazatlan");
            insertTimeZone(timeZone, callback, 261, "MX", "America/Chihuahua");
            insertTimeZone(timeZone, callback, 262, "MX", "America/Ojinaga");
            insertTimeZone(timeZone, callback, 263, "MX", "America/Hermosillo");
            insertTimeZone(timeZone, callback, 264, "MX", "America/Tijuana");
            insertTimeZone(timeZone, callback, 265, "MX", "America/Santa_Isabel");
            insertTimeZone(timeZone, callback, 266, "MX", "America/Bahia_Banderas");
            insertTimeZone(timeZone, callback, 267, "MY", "Asia/Kuala_Lumpur");
            insertTimeZone(timeZone, callback, 268, "MY", "Asia/Kuching");
            insertTimeZone(timeZone, callback, 269, "MZ", "Africa/Maputo");
            insertTimeZone(timeZone, callback, 270, "NA", "Africa/Windhoek");
            insertTimeZone(timeZone, callback, 271, "NC", "Pacific/Noumea");
            insertTimeZone(timeZone, callback, 272, "NE", "Africa/Niamey");
            insertTimeZone(timeZone, callback, 273, "NF", "Pacific/Norfolk");
            insertTimeZone(timeZone, callback, 274, "NG", "Africa/Lagos");
            insertTimeZone(timeZone, callback, 275, "NI", "America/Managua");
            insertTimeZone(timeZone, callback, 276, "NL", "Europe/Amsterdam");
            insertTimeZone(timeZone, callback, 277, "NO", "Europe/Oslo");
            insertTimeZone(timeZone, callback, 278, "NP", "Asia/Kathmandu");
            insertTimeZone(timeZone, callback, 279, "NR", "Pacific/Nauru");
            insertTimeZone(timeZone, callback, 280, "NU", "Pacific/Niue");
            insertTimeZone(timeZone, callback, 281, "NZ", "Pacific/Auckland");
            insertTimeZone(timeZone, callback, 282, "NZ", "Pacific/Chatham");
            insertTimeZone(timeZone, callback, 283, "OM", "Asia/Muscat");
            insertTimeZone(timeZone, callback, 284, "PA", "America/Panama");
            insertTimeZone(timeZone, callback, 285, "PE", "America/Lima");
            insertTimeZone(timeZone, callback, 286, "PF", "Pacific/Tahiti");
            insertTimeZone(timeZone, callback, 287, "PF", "Pacific/Marquesas");
            insertTimeZone(timeZone, callback, 288, "PF", "Pacific/Gambier");
            insertTimeZone(timeZone, callback, 289, "PG", "Pacific/Port_Moresby");
            insertTimeZone(timeZone, callback, 290, "PH", "Asia/Manila");
            insertTimeZone(timeZone, callback, 291, "PK", "Asia/Karachi");
            insertTimeZone(timeZone, callback, 292, "PL", "Europe/Warsaw");
            insertTimeZone(timeZone, callback, 293, "PM", "America/Miquelon");
            insertTimeZone(timeZone, callback, 294, "PN", "Pacific/Pitcairn");
            insertTimeZone(timeZone, callback, 295, "PR", "America/Puerto_Rico");
            insertTimeZone(timeZone, callback, 296, "PS", "Asia/Gaza");
            insertTimeZone(timeZone, callback, 297, "PS", "Asia/Hebron");
            insertTimeZone(timeZone, callback, 298, "PT", "Europe/Lisbon");
            insertTimeZone(timeZone, callback, 299, "PT", "Atlantic/Madeira");
            insertTimeZone(timeZone, callback, 300, "PT", "Atlantic/Azores");
            insertTimeZone(timeZone, callback, 301, "PW", "Pacific/Palau");
            insertTimeZone(timeZone, callback, 302, "PY", "America/Asuncion");
            insertTimeZone(timeZone, callback, 303, "QA", "Asia/Qatar");
            insertTimeZone(timeZone, callback, 304, "RE", "Indian/Reunion");
            insertTimeZone(timeZone, callback, 305, "RO", "Europe/Bucharest");
            insertTimeZone(timeZone, callback, 306, "RS", "Europe/Belgrade");
            insertTimeZone(timeZone, callback, 307, "RU", "Europe/Kaliningrad");
            insertTimeZone(timeZone, callback, 308, "RU", "Europe/Moscow");
            insertTimeZone(timeZone, callback, 309, "RU", "Europe/Volgograd");
            insertTimeZone(timeZone, callback, 310, "RU", "Europe/Samara");
            insertTimeZone(timeZone, callback, 311, "RU", "Asia/Yekaterinburg");
            insertTimeZone(timeZone, callback, 312, "RU", "Asia/Omsk");
            insertTimeZone(timeZone, callback, 313, "RU", "Asia/Novosibirsk");
            insertTimeZone(timeZone, callback, 314, "RU", "Asia/Novokuznetsk");
            insertTimeZone(timeZone, callback, 315, "RU", "Asia/Krasnoyarsk");
            insertTimeZone(timeZone, callback, 316, "RU", "Asia/Irkutsk");
            insertTimeZone(timeZone, callback, 317, "RU", "Asia/Yakutsk");
            insertTimeZone(timeZone, callback, 318, "RU", "Asia/Vladivostok");
            insertTimeZone(timeZone, callback, 319, "RU", "Asia/Sakhalin");
            insertTimeZone(timeZone, callback, 320, "RU", "Asia/Magadan");
            insertTimeZone(timeZone, callback, 321, "RU", "Asia/Kamchatka");
            insertTimeZone(timeZone, callback, 322, "RU", "Asia/Anadyr");
            insertTimeZone(timeZone, callback, 323, "RW", "Africa/Kigali");
            insertTimeZone(timeZone, callback, 324, "SA", "Asia/Riyadh");
            insertTimeZone(timeZone, callback, 325, "SB", "Pacific/Guadalcanal");
            insertTimeZone(timeZone, callback, 326, "SC", "Indian/Mahe");
            insertTimeZone(timeZone, callback, 327, "SD", "Africa/Khartoum");
            insertTimeZone(timeZone, callback, 328, "SE", "Europe/Stockholm");
            insertTimeZone(timeZone, callback, 329, "SG", "Asia/Singapore");
            insertTimeZone(timeZone, callback, 330, "SH", "Atlantic/St_Helena");
            insertTimeZone(timeZone, callback, 331, "SI", "Europe/Ljubljana");
            insertTimeZone(timeZone, callback, 332, "SJ", "Arctic/Longyearbyen");
            insertTimeZone(timeZone, callback, 333, "SK", "Europe/Bratislava");
            insertTimeZone(timeZone, callback, 334, "SL", "Africa/Freetown");
            insertTimeZone(timeZone, callback, 335, "SM", "Europe/San_Marino");
            insertTimeZone(timeZone, callback, 336, "SN", "Africa/Dakar");
            insertTimeZone(timeZone, callback, 337, "SO", "Africa/Mogadishu");
            insertTimeZone(timeZone, callback, 338, "SR", "America/Paramaribo");
            insertTimeZone(timeZone, callback, 339, "SS", "Africa/Juba");
            insertTimeZone(timeZone, callback, 340, "ST", "Africa/Sao_Tome");
            insertTimeZone(timeZone, callback, 341, "SV", "America/El_Salvador");
            insertTimeZone(timeZone, callback, 342, "SX", "America/Lower_Princes");
            insertTimeZone(timeZone, callback, 343, "SY", "Asia/Damascus");
            insertTimeZone(timeZone, callback, 344, "SZ", "Africa/Mbabane");
            insertTimeZone(timeZone, callback, 345, "TC", "America/Grand_Turk");
            insertTimeZone(timeZone, callback, 346, "TD", "Africa/Ndjamena");
            insertTimeZone(timeZone, callback, 347, "TF", "Indian/Kerguelen");
            insertTimeZone(timeZone, callback, 348, "TG", "Africa/Lome");
            insertTimeZone(timeZone, callback, 349, "TH", "Asia/Bangkok");
            insertTimeZone(timeZone, callback, 350, "TJ", "Asia/Dushanbe");
            insertTimeZone(timeZone, callback, 351, "TK", "Pacific/Fakaofo");
            insertTimeZone(timeZone, callback, 352, "TL", "Asia/Dili");
            insertTimeZone(timeZone, callback, 353, "TM", "Asia/Ashgabat");
            insertTimeZone(timeZone, callback, 354, "TN", "Africa/Tunis");
            insertTimeZone(timeZone, callback, 355, "TO", "Pacific/Tongatapu");
            insertTimeZone(timeZone, callback, 356, "TR", "Europe/Istanbul");
            insertTimeZone(timeZone, callback, 357, "TT", "America/Port_of_Spain");
            insertTimeZone(timeZone, callback, 358, "TV", "Pacific/Funafuti");
            insertTimeZone(timeZone, callback, 359, "TW", "Asia/Taipei");
            insertTimeZone(timeZone, callback, 360, "TZ", "Africa/Dar_es_Salaam");
            insertTimeZone(timeZone, callback, 361, "UA", "Europe/Kiev");
            insertTimeZone(timeZone, callback, 362, "UA", "Europe/Uzhgorod");
            insertTimeZone(timeZone, callback, 363, "UA", "Europe/Zaporozhye");
            insertTimeZone(timeZone, callback, 364, "UA", "Europe/Simferopol");
            insertTimeZone(timeZone, callback, 365, "UG", "Africa/Kampala");
            insertTimeZone(timeZone, callback, 366, "UM", "Pacific/Johnston");
            insertTimeZone(timeZone, callback, 367, "UM", "Pacific/Midway");
            insertTimeZone(timeZone, callback, 368, "UM", "Pacific/Wake");
            insertTimeZone(timeZone, callback, 369, "US", "America/New_York");
            insertTimeZone(timeZone, callback, 370, "US", "America/Detroit");
            insertTimeZone(timeZone, callback, 371, "US", "America/Kentucky/Louisville");
            insertTimeZone(timeZone, callback, 372, "US", "America/Kentucky/Monticello");
            insertTimeZone(timeZone, callback, 373, "US", "America/Indiana/Indianapolis");
            insertTimeZone(timeZone, callback, 374, "US", "America/Indiana/Vincennes");
            insertTimeZone(timeZone, callback, 375, "US", "America/Indiana/Winamac");
            insertTimeZone(timeZone, callback, 376, "US", "America/Indiana/Marengo");
            insertTimeZone(timeZone, callback, 377, "US", "America/Indiana/Petersburg");
            insertTimeZone(timeZone, callback, 378, "US", "America/Indiana/Vevay");
            insertTimeZone(timeZone, callback, 379, "US", "America/Chicago");
            insertTimeZone(timeZone, callback, 380, "US", "America/Indiana/Tell_City");
            insertTimeZone(timeZone, callback, 381, "US", "America/Indiana/Knox");
            insertTimeZone(timeZone, callback, 382, "US", "America/Menominee");
            insertTimeZone(timeZone, callback, 383, "US", "America/North_Dakota/Center");
            insertTimeZone(timeZone, callback, 384, "US", "America/North_Dakota/New_Salem");
            insertTimeZone(timeZone, callback, 385, "US", "America/North_Dakota/Beulah");
            insertTimeZone(timeZone, callback, 386, "US", "America/Denver");
            insertTimeZone(timeZone, callback, 387, "US", "America/Boise");
            insertTimeZone(timeZone, callback, 388, "US", "America/Shiprock");
            insertTimeZone(timeZone, callback, 389, "US", "America/Phoenix");
            insertTimeZone(timeZone, callback, 390, "US", "America/Los_Angeles");
            insertTimeZone(timeZone, callback, 391, "US", "America/Anchorage");
            insertTimeZone(timeZone, callback, 392, "US", "America/Juneau");
            insertTimeZone(timeZone, callback, 393, "US", "America/Sitka");
            insertTimeZone(timeZone, callback, 394, "US", "America/Yakutat");
            insertTimeZone(timeZone, callback, 395, "US", "America/Nome");
            insertTimeZone(timeZone, callback, 396, "US", "America/Adak");
            insertTimeZone(timeZone, callback, 397, "US", "America/Metlakatla");
            insertTimeZone(timeZone, callback, 398, "US", "Pacific/Honolulu");
            insertTimeZone(timeZone, callback, 399, "UY", "America/Montevideo");
            insertTimeZone(timeZone, callback, 400, "UZ", "Asia/Samarkand");
            insertTimeZone(timeZone, callback, 401, "UZ", "Asia/Tashkent");
            insertTimeZone(timeZone, callback, 402, "VA", "Europe/Vatican");
            insertTimeZone(timeZone, callback, 403, "VC", "America/St_Vincent");
            insertTimeZone(timeZone, callback, 404, "VE", "America/Caracas");
            insertTimeZone(timeZone, callback, 405, "VG", "America/Tortola");
            insertTimeZone(timeZone, callback, 406, "VI", "America/St_Thomas");
            insertTimeZone(timeZone, callback, 407, "VN", "Asia/Ho_Chi_Minh");
            insertTimeZone(timeZone, callback, 408, "VU", "Pacific/Efate");
            insertTimeZone(timeZone, callback, 409, "WF", "Pacific/Wallis");
            insertTimeZone(timeZone, callback, 410, "WS", "Pacific/Apia");
            insertTimeZone(timeZone, callback, 411, "YE", "Asia/Aden");
            insertTimeZone(timeZone, callback, 412, "YT", "Indian/Mayotte");
            insertTimeZone(timeZone, callback, 413, "ZA", "Africa/Johannesburg");
            insertTimeZone(timeZone, callback, 414, "ZM", "Africa/Lusaka");
            insertTimeZone(timeZone, callback, 415, "ZW", "Africa/Harare");
        });
    }

    protected static void insertTimeZone(TimeZone timeZone, RowInsertable callback, long id, String code, String name) {
        callback.insertInto(timeZone)
                .value(timeZone.ID, id)
                .value(timeZone.COUNTRY_CODE, code)
                .value(timeZone.TIME_ZONE_NAME, name)
                .execute();
    }
}
