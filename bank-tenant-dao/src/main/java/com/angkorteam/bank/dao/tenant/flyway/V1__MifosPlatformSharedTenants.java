package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.metamodel.LiquibaseJavaMigration;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.internal.jdbc.DriverDataSource;

public class V1__MifosPlatformSharedTenants extends LiquibaseJavaMigration {


    @Override
    public Integer getChecksum() {
        return getInternalChecksum("V1__mifos-platform-shared-tenants-001.xml",
                "V1__mifos-platform-shared-tenants-003.xml");
    }

    @Override
    public void migrate(Context context) throws Exception {
        Database database = lookupDatabase(context);
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            // sub change 001
            Liquibase liquibase = new Liquibase("V1__mifos-platform-shared-tenants-001.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 002
            DriverDataSource driver = (DriverDataSource) context.getConfiguration().getDataSource();
            String schemaName = System.getProperty("db.tenant.name");
            String url = driver.getUrl();
            String schemaServer = null;
            String schemaServerPort = null;
            url = url.substring(url.indexOf(":") + 1);
            String type = url.substring(0, url.indexOf(":"));
            url = url.substring(url.indexOf(":") + 1);
            if ("mysql".equals(type)) {
                url = url.substring(2, url.lastIndexOf("/"));
                if (url.contains(":")) {
                    String temp[] = url.split(":");
                    schemaServer = temp[0];
                    schemaServerPort = temp[1];
                } else {
                    schemaServer = url;
                    schemaServerPort = "3306";
                }
            } else if ("oracle".equals(type)) {
                url = url.substring(url.indexOf("@"));
                schemaServer = url.substring(0, url.indexOf(":"));
                url = url.substring(url.indexOf(":") + 1);
                schemaServerPort = url.substring(0, url.indexOf(":"));
            }
            String finalSchemaServer = schemaServer;
            String finalSchemaServerPort = schemaServerPort;
            dataContext.refreshSchemas();
            dataContext.executeUpdate(callback -> {
                Table table = dataContext.getDefaultSchema().getTableByName("tenants");
                callback.insertInto(table)
                        .value(table.getColumnByName("id"), 1)
                        .value(table.getColumnByName("identifier"), "default")
                        .value(table.getColumnByName("name"), "Default Demo Tenant")
                        .value(table.getColumnByName("schema_name"), schemaName)
                        .value(table.getColumnByName("timezone_id"), "Asia/Kolkata")
                        .value(table.getColumnByName("schema_server"), finalSchemaServer)
                        .value(table.getColumnByName("schema_server_port"), finalSchemaServerPort)
                        .value(table.getColumnByName("schema_username"), driver.getUser())
                        .value(table.getColumnByName("schema_password"), driver.getPassword())
                        .value(table.getColumnByName("auto_update"), "1")
                        .execute();
            });
        }
        {
            // sub change 003
            Liquibase liquibase = new Liquibase("V1__mifos-platform-shared-tenants-003.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
        {
            // sub change 004
            dataContext.refreshSchemas();
            Table timeZone = dataContext.getDefaultSchema().getTableByName("timezones");
            dataContext.executeUpdate(callback -> {
                insertTimeZone(timeZone, callback, 1, "AD", "Europe/Andorra", "");
                insertTimeZone(timeZone, callback, 2, "AE", "Asia/Dubai", "");
                insertTimeZone(timeZone, callback, 3, "AF", "Asia/Kabul", "");
                insertTimeZone(timeZone, callback, 4, "AG", "America/Antigua", "");
                insertTimeZone(timeZone, callback, 5, "AI", "America/Anguilla", "");
                insertTimeZone(timeZone, callback, 6, "AL", "Europe/Tirane", "");
                insertTimeZone(timeZone, callback, 7, "AM", "Asia/Yerevan", "");
                insertTimeZone(timeZone, callback, 8, "AO", "Africa/Luanda", "");
                insertTimeZone(timeZone, callback, 9, "AQ", "Antarctica/McMurdo", "McMurdo Station, Ross Island");
                insertTimeZone(timeZone, callback, 10, "AQ", "Antarctica/South_Pole", "Amundsen-Scott Station, South Pole");
                insertTimeZone(timeZone, callback, 11, "AQ", "Antarctica/Rothera", "Rothera Station, Adelaide Island");
                insertTimeZone(timeZone, callback, 12, "AQ", "Antarctica/Palmer", "Palmer Station, Anvers Island");
                insertTimeZone(timeZone, callback, 13, "AQ", "Antarctica/Mawson", "Mawson Station, Holme Bay");
                insertTimeZone(timeZone, callback, 14, "AQ", "Antarctica/Davis", "Davis Station, Vestfold Hills");
                insertTimeZone(timeZone, callback, 15, "AQ", "Antarctica/Casey", "Casey Station, Bailey Peninsula");
                insertTimeZone(timeZone, callback, 16, "AQ", "Antarctica/Vostok", "Vostok Station, Lake Vostok");
                insertTimeZone(timeZone, callback, 17, "AQ", "Antarctica/DumontDUrville", "Dumont-dUrville Station, Terre Adelie");
                insertTimeZone(timeZone, callback, 18, "AQ", "Antarctica/Syowa", "Syowa Station, E Ongul I");
                insertTimeZone(timeZone, callback, 19, "AQ", "Antarctica/Macquarie", "Macquarie Island Station, Macquarie Island");
                insertTimeZone(timeZone, callback, 20, "AR", "America/Argentina/Buenos_Aires", "Buenos Aires (BA, CF)");
                insertTimeZone(timeZone, callback, 21, "AR", "America/Argentina/Cordoba", "most locations (CB, CC, CN, ER, FM, MN, SE, SF)");
                insertTimeZone(timeZone, callback, 22, "AR", "America/Argentina/Salta", "(SA, LP, NQ, RN)");
                insertTimeZone(timeZone, callback, 23, "AR", "America/Argentina/Jujuy", "Jujuy (JY)");
                insertTimeZone(timeZone, callback, 24, "AR", "America/Argentina/Tucuman", "Tucuman (TM)");
                insertTimeZone(timeZone, callback, 25, "AR", "America/Argentina/Catamarca", "Catamarca (CT), Chubut (CH)");
                insertTimeZone(timeZone, callback, 26, "AR", "America/Argentina/La_Rioja", "La Rioja (LR)");
                insertTimeZone(timeZone, callback, 27, "AR", "America/Argentina/San_Juan", "San Juan (SJ)");
                insertTimeZone(timeZone, callback, 28, "AR", "America/Argentina/Mendoza", "Mendoza (MZ)");
                insertTimeZone(timeZone, callback, 29, "AR", "America/Argentina/San_Luis", "San Luis (SL)");
                insertTimeZone(timeZone, callback, 30, "AR", "America/Argentina/Rio_Gallegos", "Santa Cruz (SC)");
                insertTimeZone(timeZone, callback, 31, "AR", "America/Argentina/Ushuaia", "Tierra del Fuego (TF)");
                insertTimeZone(timeZone, callback, 32, "AS", "Pacific/Pago_Pago", "");
                insertTimeZone(timeZone, callback, 33, "AT", "Europe/Vienna", "");
                insertTimeZone(timeZone, callback, 34, "AU", "Australia/Lord_Howe", "Lord Howe Island");
                insertTimeZone(timeZone, callback, 35, "AU", "Australia/Hobart", "Tasmania - most locations");
                insertTimeZone(timeZone, callback, 36, "AU", "Australia/Currie", "Tasmania - King Island");
                insertTimeZone(timeZone, callback, 37, "AU", "Australia/Melbourne", "Victoria");
                insertTimeZone(timeZone, callback, 38, "AU", "Australia/Sydney", "New South Wales - most locations");
                insertTimeZone(timeZone, callback, 39, "AU", "Australia/Broken_Hill", "New South Wales - Yancowinna");
                insertTimeZone(timeZone, callback, 40, "AU", "Australia/Brisbane", "Queensland - most locations");
                insertTimeZone(timeZone, callback, 41, "AU", "Australia/Lindeman", "Queensland - Holiday Islands");
                insertTimeZone(timeZone, callback, 42, "AU", "Australia/Adelaide", "South Australia");
                insertTimeZone(timeZone, callback, 43, "AU", "Australia/Darwin", "Northern Territory");
                insertTimeZone(timeZone, callback, 44, "AU", "Australia/Perth", "Western Australia - most locations");
                insertTimeZone(timeZone, callback, 45, "AU", "Australia/Eucla", "Western Australia - Eucla area");
                insertTimeZone(timeZone, callback, 46, "AW", "America/Aruba", "");
                insertTimeZone(timeZone, callback, 47, "AX", "Europe/Mariehamn", "");
                insertTimeZone(timeZone, callback, 48, "AZ", "Asia/Baku", "");
                insertTimeZone(timeZone, callback, 49, "BA", "Europe/Sarajevo", "");
                insertTimeZone(timeZone, callback, 50, "BB", "America/Barbados", "");
                insertTimeZone(timeZone, callback, 51, "BD", "Asia/Dhaka", "");
                insertTimeZone(timeZone, callback, 52, "BE", "Europe/Brussels", "");
                insertTimeZone(timeZone, callback, 53, "BF", "Africa/Ouagadougou", "");
                insertTimeZone(timeZone, callback, 54, "BG", "Europe/Sofia", "");
                insertTimeZone(timeZone, callback, 55, "BH", "Asia/Bahrain", "");
                insertTimeZone(timeZone, callback, 56, "BI", "Africa/Bujumbura", "");
                insertTimeZone(timeZone, callback, 57, "BJ", "Africa/Porto-Novo", "");
                insertTimeZone(timeZone, callback, 58, "BL", "America/St_Barthelemy", "");
                insertTimeZone(timeZone, callback, 59, "BM", "Atlantic/Bermuda", "");
                insertTimeZone(timeZone, callback, 60, "BN", "Asia/Brunei", "");
                insertTimeZone(timeZone, callback, 61, "BO", "America/La_Paz", "");
                insertTimeZone(timeZone, callback, 62, "BQ", "America/Kralendijk", "");
                insertTimeZone(timeZone, callback, 63, "BR", "America/Noronha", "Atlantic islands");
                insertTimeZone(timeZone, callback, 64, "BR", "America/Belem", "Amapa, E Para");
                insertTimeZone(timeZone, callback, 65, "BR", "America/Fortaleza", "NE Brazil (MA, PI, CE, RN, PB)");
                insertTimeZone(timeZone, callback, 66, "BR", "America/Recife", "Pernambuco");
                insertTimeZone(timeZone, callback, 67, "BR", "America/Araguaina", "Tocantins");
                insertTimeZone(timeZone, callback, 68, "BR", "America/Maceio", "Alagoas, Sergipe");
                insertTimeZone(timeZone, callback, 69, "BR", "America/Bahia", "Bahia");
                insertTimeZone(timeZone, callback, 70, "BR", "America/Sao_Paulo", "S & SE Brazil (GO, DF, MG, ES, RJ, SP, PR, SC, RS)");
                insertTimeZone(timeZone, callback, 71, "BR", "America/Campo_Grande", "Mato Grosso do Sul");
                insertTimeZone(timeZone, callback, 72, "BR", "America/Cuiaba", "Mato Grosso");
                insertTimeZone(timeZone, callback, 73, "BR", "America/Santarem", "W Para");
                insertTimeZone(timeZone, callback, 74, "BR", "America/Porto_Velho", "Rondonia");
                insertTimeZone(timeZone, callback, 75, "BR", "America/Boa_Vista", "Roraima");
                insertTimeZone(timeZone, callback, 76, "BR", "America/Manaus", "E Amazonas");
                insertTimeZone(timeZone, callback, 77, "BR", "America/Eirunepe", "W Amazonas");
                insertTimeZone(timeZone, callback, 78, "BR", "America/Rio_Branco", "Acre");
                insertTimeZone(timeZone, callback, 79, "BS", "America/Nassau", "");
                insertTimeZone(timeZone, callback, 80, "BT", "Asia/Thimphu", "");
                insertTimeZone(timeZone, callback, 81, "BW", "Africa/Gaborone", "");
                insertTimeZone(timeZone, callback, 82, "BY", "Europe/Minsk", "");
                insertTimeZone(timeZone, callback, 83, "BZ", "America/Belize", "");
                insertTimeZone(timeZone, callback, 84, "CA", "America/St_Johns", "Newfoundland Time, including SE Labrador");
                insertTimeZone(timeZone, callback, 85, "CA", "America/Halifax", "Atlantic Time - Nova Scotia (most places), PEI");
                insertTimeZone(timeZone, callback, 86, "CA", "America/Glace_Bay", "Atlantic Time - Nova Scotia - places that did not observe DST 1966-1971");
                insertTimeZone(timeZone, callback, 87, "CA", "America/Moncton", "Atlantic Time - New Brunswick");
                insertTimeZone(timeZone, callback, 88, "CA", "America/Goose_Bay", "Atlantic Time - Labrador - most locations");
                insertTimeZone(timeZone, callback, 89, "CA", "America/Blanc-Sablon", "Atlantic Standard Time - Quebec - Lower North Shore");
                insertTimeZone(timeZone, callback, 90, "CA", "America/Montreal", "Eastern Time - Quebec - most locations");
                insertTimeZone(timeZone, callback, 91, "CA", "America/Toronto", "Eastern Time - Ontario - most locations");
                insertTimeZone(timeZone, callback, 92, "CA", "America/Nipigon", "Eastern Time - Ontario & Quebec - places that did not observe DST 1967-1973");
                insertTimeZone(timeZone, callback, 93, "CA", "America/Thunder_Bay", "Eastern Time - Thunder Bay, Ontario");
                insertTimeZone(timeZone, callback, 94, "CA", "America/Iqaluit", "Eastern Time - east Nunavut - most locations");
                insertTimeZone(timeZone, callback, 95, "CA", "America/Pangnirtung", "Eastern Time - Pangnirtung, Nunavut");
                insertTimeZone(timeZone, callback, 96, "CA", "America/Resolute", "Central Standard Time - Resolute, Nunavut");
                insertTimeZone(timeZone, callback, 97, "CA", "America/Atikokan", "Eastern Standard Time - Atikokan, Ontario and Southampton I, Nunavut");
                insertTimeZone(timeZone, callback, 98, "CA", "America/Rankin_Inlet", "Central Time - central Nunavut");
                insertTimeZone(timeZone, callback, 99, "CA", "America/Winnipeg", "Central Time - Manitoba & west Ontario");
                insertTimeZone(timeZone, callback, 100, "CA", "America/Rainy_River", "Central Time - Rainy River & Fort Frances, Ontario");
                insertTimeZone(timeZone, callback, 101, "CA", "America/Regina", "Central Standard Time - Saskatchewan - most locations");
                insertTimeZone(timeZone, callback, 102, "CA", "America/Swift_Current", "Central Standard Time - Saskatchewan - midwest");
                insertTimeZone(timeZone, callback, 103, "CA", "America/Edmonton", "Mountain Time - Alberta, east British Columbia & west Saskatchewan");
                insertTimeZone(timeZone, callback, 104, "CA", "America/Cambridge_Bay", "Mountain Time - west Nunavut");
                insertTimeZone(timeZone, callback, 105, "CA", "America/Yellowknife", "Mountain Time - central Northwest Territories");
                insertTimeZone(timeZone, callback, 106, "CA", "America/Inuvik", "Mountain Time - west Northwest Territories");
                insertTimeZone(timeZone, callback, 107, "CA", "America/Creston", "Mountain Standard Time - Creston, British Columbia");
                insertTimeZone(timeZone, callback, 108, "CA", "America/Dawson_Creek", "Mountain Standard Time - Dawson Creek & Fort Saint John, British Columbia");
                insertTimeZone(timeZone, callback, 109, "CA", "America/Vancouver", "Pacific Time - west British Columbia");
                insertTimeZone(timeZone, callback, 110, "CA", "America/Whitehorse", "Pacific Time - south Yukon");
                insertTimeZone(timeZone, callback, 111, "CA", "America/Dawson", "Pacific Time - north Yukon");
                insertTimeZone(timeZone, callback, 112, "CC", "Indian/Cocos", "");
                insertTimeZone(timeZone, callback, 113, "CD", "Africa/Kinshasa", "west Dem. Rep. of Congo");
                insertTimeZone(timeZone, callback, 114, "CD", "Africa/Lubumbashi", "east Dem. Rep. of Congo");
                insertTimeZone(timeZone, callback, 115, "CF", "Africa/Bangui", "");
                insertTimeZone(timeZone, callback, 116, "CG", "Africa/Brazzaville", "");
                insertTimeZone(timeZone, callback, 117, "CH", "Europe/Zurich", "");
                insertTimeZone(timeZone, callback, 118, "CI", "Africa/Abidjan", "");
                insertTimeZone(timeZone, callback, 119, "CK", "Pacific/Rarotonga", "");
                insertTimeZone(timeZone, callback, 120, "CL", "America/Santiago", "most locations");
                insertTimeZone(timeZone, callback, 121, "CL", "Pacific/Easter", "Easter Island & Sala y Gomez");
                insertTimeZone(timeZone, callback, 122, "CM", "Africa/Douala", "");
                insertTimeZone(timeZone, callback, 123, "CN", "Asia/Shanghai", "east China - Beijing, Guangdong, Shanghai, etc.");
                insertTimeZone(timeZone, callback, 124, "CN", "Asia/Harbin", "Heilongjiang (except Mohe), Jilin");
                insertTimeZone(timeZone, callback, 125, "CN", "Asia/Chongqing", "central China - Sichuan, Yunnan, Guangxi, Shaanxi, Guizhou, etc.");
                insertTimeZone(timeZone, callback, 126, "CN", "Asia/Urumqi", "most of Tibet & Xinjiang");
                insertTimeZone(timeZone, callback, 127, "CN", "Asia/Kashgar", "west Tibet & Xinjiang");
                insertTimeZone(timeZone, callback, 128, "CO", "America/Bogota", "");
                insertTimeZone(timeZone, callback, 129, "CR", "America/Costa_Rica", "");
                insertTimeZone(timeZone, callback, 130, "CU", "America/Havana", "");
                insertTimeZone(timeZone, callback, 131, "CV", "Atlantic/Cape_Verde", "");
                insertTimeZone(timeZone, callback, 132, "CW", "America/Curacao", "");
                insertTimeZone(timeZone, callback, 133, "CX", "Indian/Christmas", "");
                insertTimeZone(timeZone, callback, 134, "CY", "Asia/Nicosia", "");
                insertTimeZone(timeZone, callback, 135, "CZ", "Europe/Prague", "");
                insertTimeZone(timeZone, callback, 136, "DE", "Europe/Berlin", "");
                insertTimeZone(timeZone, callback, 137, "DJ", "Africa/Djibouti", "");
                insertTimeZone(timeZone, callback, 138, "DK", "Europe/Copenhagen", "");
                insertTimeZone(timeZone, callback, 139, "DM", "America/Dominica", "");
                insertTimeZone(timeZone, callback, 140, "DO", "America/Santo_Domingo", "");
                insertTimeZone(timeZone, callback, 141, "DZ", "Africa/Algiers", "");
                insertTimeZone(timeZone, callback, 142, "EC", "America/Guayaquil", "mainland");
                insertTimeZone(timeZone, callback, 143, "EC", "Pacific/Galapagos", "Galapagos Islands");
                insertTimeZone(timeZone, callback, 144, "EE", "Europe/Tallinn", "");
                insertTimeZone(timeZone, callback, 145, "EG", "Africa/Cairo", "");
                insertTimeZone(timeZone, callback, 146, "EH", "Africa/El_Aaiun", "");
                insertTimeZone(timeZone, callback, 147, "ER", "Africa/Asmara", "");
                insertTimeZone(timeZone, callback, 148, "ES", "Europe/Madrid", "mainland");
                insertTimeZone(timeZone, callback, 149, "ES", "Africa/Ceuta", "Ceuta & Melilla");
                insertTimeZone(timeZone, callback, 150, "ES", "Atlantic/Canary", "Canary Islands");
                insertTimeZone(timeZone, callback, 151, "ET", "Africa/Addis_Ababa", "");
                insertTimeZone(timeZone, callback, 152, "FI", "Europe/Helsinki", "");
                insertTimeZone(timeZone, callback, 153, "FJ", "Pacific/Fiji", "");
                insertTimeZone(timeZone, callback, 154, "FK", "Atlantic/Stanley", "");
                insertTimeZone(timeZone, callback, 155, "FM", "Pacific/Chuuk", "Chuuk (Truk) and Yap");
                insertTimeZone(timeZone, callback, 156, "FM", "Pacific/Pohnpei", "Pohnpei (Ponape)");
                insertTimeZone(timeZone, callback, 157, "FM", "Pacific/Kosrae", "Kosrae");
                insertTimeZone(timeZone, callback, 158, "FO", "Atlantic/Faroe", "");
                insertTimeZone(timeZone, callback, 159, "FR", "Europe/Paris", "");
                insertTimeZone(timeZone, callback, 160, "GA", "Africa/Libreville", "");
                insertTimeZone(timeZone, callback, 161, "GB", "Europe/London", "");
                insertTimeZone(timeZone, callback, 162, "GD", "America/Grenada", "");
                insertTimeZone(timeZone, callback, 163, "GE", "Asia/Tbilisi", "");
                insertTimeZone(timeZone, callback, 164, "GF", "America/Cayenne", "");
                insertTimeZone(timeZone, callback, 165, "GG", "Europe/Guernsey", "");
                insertTimeZone(timeZone, callback, 166, "GH", "Africa/Accra", "");
                insertTimeZone(timeZone, callback, 167, "GI", "Europe/Gibraltar", "");
                insertTimeZone(timeZone, callback, 168, "GL", "America/Godthab", "most locations");
                insertTimeZone(timeZone, callback, 169, "GL", "America/Danmarkshavn", "east coast, north of Scoresbysund");
                insertTimeZone(timeZone, callback, 170, "GL", "America/Scoresbysund", "Scoresbysund / Ittoqqortoormiit");
                insertTimeZone(timeZone, callback, 171, "GL", "America/Thule", "Thule / Pituffik");
                insertTimeZone(timeZone, callback, 172, "GM", "Africa/Banjul", "");
                insertTimeZone(timeZone, callback, 173, "GN", "Africa/Conakry", "");
                insertTimeZone(timeZone, callback, 174, "GP", "America/Guadeloupe", "");
                insertTimeZone(timeZone, callback, 175, "GQ", "Africa/Malabo", "");
                insertTimeZone(timeZone, callback, 176, "GR", "Europe/Athens", "");
                insertTimeZone(timeZone, callback, 177, "GS", "Atlantic/South_Georgia", "");
                insertTimeZone(timeZone, callback, 178, "GT", "America/Guatemala", "");
                insertTimeZone(timeZone, callback, 179, "GU", "Pacific/Guam", "");
                insertTimeZone(timeZone, callback, 180, "GW", "Africa/Bissau", "");
                insertTimeZone(timeZone, callback, 181, "GY", "America/Guyana", "");
                insertTimeZone(timeZone, callback, 182, "HK", "Asia/Hong_Kong", "");
                insertTimeZone(timeZone, callback, 183, "HN", "America/Tegucigalpa", "");
                insertTimeZone(timeZone, callback, 184, "HR", "Europe/Zagreb", "");
                insertTimeZone(timeZone, callback, 185, "HT", "America/Port-au-Prince", "");
                insertTimeZone(timeZone, callback, 186, "HU", "Europe/Budapest", "");
                insertTimeZone(timeZone, callback, 187, "ID", "Asia/Jakarta", "Java & Sumatra");
                insertTimeZone(timeZone, callback, 188, "ID", "Asia/Pontianak", "west & central Borneo");
                insertTimeZone(timeZone, callback, 189, "ID", "Asia/Makassar", "east & south Borneo, Sulawesi (Celebes), Bali, Nusa Tengarra, west Timor");
                insertTimeZone(timeZone, callback, 190, "ID", "Asia/Jayapura", "west New Guinea (Irian Jaya) & Malukus (Moluccas)");
                insertTimeZone(timeZone, callback, 191, "IE", "Europe/Dublin", "");
                insertTimeZone(timeZone, callback, 192, "IL", "Asia/Jerusalem", "");
                insertTimeZone(timeZone, callback, 193, "IM", "Europe/Isle_of_Man", "");
                insertTimeZone(timeZone, callback, 194, "IN", "Asia/Kolkata", "");
                insertTimeZone(timeZone, callback, 195, "IO", "Indian/Chagos", "");
                insertTimeZone(timeZone, callback, 196, "IQ", "Asia/Baghdad", "");
                insertTimeZone(timeZone, callback, 197, "IR", "Asia/Tehran", "");
                insertTimeZone(timeZone, callback, 198, "IS", "Atlantic/Reykjavik", "");
                insertTimeZone(timeZone, callback, 199, "IT", "Europe/Rome", "");
                insertTimeZone(timeZone, callback, 200, "JE", "Europe/Jersey", "");
                insertTimeZone(timeZone, callback, 201, "JM", "America/Jamaica", "");
                insertTimeZone(timeZone, callback, 202, "JO", "Asia/Amman", "");
                insertTimeZone(timeZone, callback, 203, "JP", "Asia/Tokyo", "");
                insertTimeZone(timeZone, callback, 204, "KE", "Africa/Nairobi", "");
                insertTimeZone(timeZone, callback, 205, "KG", "Asia/Bishkek", "");
                insertTimeZone(timeZone, callback, 206, "KH", "Asia/Phnom_Penh", "");
                insertTimeZone(timeZone, callback, 207, "KI", "Pacific/Tarawa", "Gilbert Islands");
                insertTimeZone(timeZone, callback, 208, "KI", "Pacific/Enderbury", "Phoenix Islands");
                insertTimeZone(timeZone, callback, 209, "KI", "Pacific/Kiritimati", "Line Islands");
                insertTimeZone(timeZone, callback, 210, "KM", "Indian/Comoro", "");
                insertTimeZone(timeZone, callback, 211, "KN", "America/St_Kitts", "");
                insertTimeZone(timeZone, callback, 212, "KP", "Asia/Pyongyang", "");
                insertTimeZone(timeZone, callback, 213, "KR", "Asia/Seoul", "");
                insertTimeZone(timeZone, callback, 214, "KW", "Asia/Kuwait", "");
                insertTimeZone(timeZone, callback, 215, "KY", "America/Cayman", "");
                insertTimeZone(timeZone, callback, 216, "KZ", "Asia/Almaty", "most locations");
                insertTimeZone(timeZone, callback, 217, "KZ", "Asia/Qyzylorda", "Qyzylorda (Kyzylorda, Kzyl-Orda)");
                insertTimeZone(timeZone, callback, 218, "KZ", "Asia/Aqtobe", "Aqtobe (Aktobe)");
                insertTimeZone(timeZone, callback, 219, "KZ", "Asia/Aqtau", "Atyrau (Atirau, Guryev), Mangghystau (Mankistau)");
                insertTimeZone(timeZone, callback, 220, "KZ", "Asia/Oral", "West Kazakhstan");
                insertTimeZone(timeZone, callback, 221, "LA", "Asia/Vientiane", "");
                insertTimeZone(timeZone, callback, 222, "LB", "Asia/Beirut", "");
                insertTimeZone(timeZone, callback, 223, "LC", "America/St_Lucia", "");
                insertTimeZone(timeZone, callback, 224, "LI", "Europe/Vaduz", "");
                insertTimeZone(timeZone, callback, 225, "LK", "Asia/Colombo", "");
                insertTimeZone(timeZone, callback, 226, "LR", "Africa/Monrovia", "");
                insertTimeZone(timeZone, callback, 227, "LS", "Africa/Maseru", "");
                insertTimeZone(timeZone, callback, 228, "LT", "Europe/Vilnius", "");
                insertTimeZone(timeZone, callback, 229, "LU", "Europe/Luxembourg", "");
                insertTimeZone(timeZone, callback, 230, "LV", "Europe/Riga", "");
                insertTimeZone(timeZone, callback, 231, "LY", "Africa/Tripoli", "");
                insertTimeZone(timeZone, callback, 232, "MA", "Africa/Casablanca", "");
                insertTimeZone(timeZone, callback, 233, "MC", "Europe/Monaco", "");
                insertTimeZone(timeZone, callback, 234, "MD", "Europe/Chisinau", "");
                insertTimeZone(timeZone, callback, 235, "ME", "Europe/Podgorica", "");
                insertTimeZone(timeZone, callback, 236, "MF", "America/Marigot", "");
                insertTimeZone(timeZone, callback, 237, "MG", "Indian/Antananarivo", "");
                insertTimeZone(timeZone, callback, 238, "MH", "Pacific/Majuro", "most locations");
                insertTimeZone(timeZone, callback, 239, "MH", "Pacific/Kwajalein", "Kwajalein");
                insertTimeZone(timeZone, callback, 240, "MK", "Europe/Skopje", "");
                insertTimeZone(timeZone, callback, 241, "ML", "Africa/Bamako", "");
                insertTimeZone(timeZone, callback, 242, "MM", "Asia/Rangoon", "");
                insertTimeZone(timeZone, callback, 243, "MN", "Asia/Ulaanbaatar", "most locations");
                insertTimeZone(timeZone, callback, 244, "MN", "Asia/Hovd", "Bayan-Olgiy, Govi-Altai, Hovd, Uvs, Zavkhan");
                insertTimeZone(timeZone, callback, 245, "MN", "Asia/Choibalsan", "Dornod, Sukhbaatar");
                insertTimeZone(timeZone, callback, 246, "MO", "Asia/Macau", "");
                insertTimeZone(timeZone, callback, 247, "MP", "Pacific/Saipan", "");
                insertTimeZone(timeZone, callback, 248, "MQ", "America/Martinique", "");
                insertTimeZone(timeZone, callback, 249, "MR", "Africa/Nouakchott", "");
                insertTimeZone(timeZone, callback, 250, "MS", "America/Montserrat", "");
                insertTimeZone(timeZone, callback, 251, "MT", "Europe/Malta", "");
                insertTimeZone(timeZone, callback, 252, "MU", "Indian/Mauritius", "");
                insertTimeZone(timeZone, callback, 253, "MV", "Indian/Maldives", "");
                insertTimeZone(timeZone, callback, 254, "MW", "Africa/Blantyre", "");
                insertTimeZone(timeZone, callback, 255, "MX", "America/Mexico_City", "Central Time - most locations");
                insertTimeZone(timeZone, callback, 256, "MX", "America/Cancun", "Central Time - Quintana Roo");
                insertTimeZone(timeZone, callback, 257, "MX", "America/Merida", "Central Time - Campeche, Yucatan");
                insertTimeZone(timeZone, callback, 258, "MX", "America/Monterrey", "Mexican Central Time - Coahuila, Durango, Nuevo Leon, Tamaulipas away from US border");
                insertTimeZone(timeZone, callback, 259, "MX", "America/Matamoros", "US Central Time - Coahuila, Durango, Nuevo Leon, Tamaulipas near US border");
                insertTimeZone(timeZone, callback, 260, "MX", "America/Mazatlan", "Mountain Time - S Baja, Nayarit, Sinaloa");
                insertTimeZone(timeZone, callback, 261, "MX", "America/Chihuahua", "Mexican Mountain Time - Chihuahua away from US border");
                insertTimeZone(timeZone, callback, 262, "MX", "America/Ojinaga", "US Mountain Time - Chihuahua near US border");
                insertTimeZone(timeZone, callback, 263, "MX", "America/Hermosillo", "Mountain Standard Time - Sonora");
                insertTimeZone(timeZone, callback, 264, "MX", "America/Tijuana", "US Pacific Time - Baja California near US border");
                insertTimeZone(timeZone, callback, 265, "MX", "America/Santa_Isabel", "Mexican Pacific Time - Baja California away from US border");
                insertTimeZone(timeZone, callback, 266, "MX", "America/Bahia_Banderas", "Mexican Central Time - Bahia de Banderas");
                insertTimeZone(timeZone, callback, 267, "MY", "Asia/Kuala_Lumpur", "peninsular Malaysia");
                insertTimeZone(timeZone, callback, 268, "MY", "Asia/Kuching", "Sabah & Sarawak");
                insertTimeZone(timeZone, callback, 269, "MZ", "Africa/Maputo", "");
                insertTimeZone(timeZone, callback, 270, "NA", "Africa/Windhoek", "");
                insertTimeZone(timeZone, callback, 271, "NC", "Pacific/Noumea", "");
                insertTimeZone(timeZone, callback, 272, "NE", "Africa/Niamey", "");
                insertTimeZone(timeZone, callback, 273, "NF", "Pacific/Norfolk", "");
                insertTimeZone(timeZone, callback, 274, "NG", "Africa/Lagos", "");
                insertTimeZone(timeZone, callback, 275, "NI", "America/Managua", "");
                insertTimeZone(timeZone, callback, 276, "NL", "Europe/Amsterdam", "");
                insertTimeZone(timeZone, callback, 277, "NO", "Europe/Oslo", "");
                insertTimeZone(timeZone, callback, 278, "NP", "Asia/Kathmandu", "");
                insertTimeZone(timeZone, callback, 279, "NR", "Pacific/Nauru", "");
                insertTimeZone(timeZone, callback, 280, "NU", "Pacific/Niue", "");
                insertTimeZone(timeZone, callback, 281, "NZ", "Pacific/Auckland", "most locations");
                insertTimeZone(timeZone, callback, 282, "NZ", "Pacific/Chatham", "Chatham Islands");
                insertTimeZone(timeZone, callback, 283, "OM", "Asia/Muscat", "");
                insertTimeZone(timeZone, callback, 284, "PA", "America/Panama", "");
                insertTimeZone(timeZone, callback, 285, "PE", "America/Lima", "");
                insertTimeZone(timeZone, callback, 286, "PF", "Pacific/Tahiti", "Society Islands");
                insertTimeZone(timeZone, callback, 287, "PF", "Pacific/Marquesas", "Marquesas Islands");
                insertTimeZone(timeZone, callback, 288, "PF", "Pacific/Gambier", "Gambier Islands");
                insertTimeZone(timeZone, callback, 289, "PG", "Pacific/Port_Moresby", "");
                insertTimeZone(timeZone, callback, 290, "PH", "Asia/Manila", "");
                insertTimeZone(timeZone, callback, 291, "PK", "Asia/Karachi", "");
                insertTimeZone(timeZone, callback, 292, "PL", "Europe/Warsaw", "");
                insertTimeZone(timeZone, callback, 293, "PM", "America/Miquelon", "");
                insertTimeZone(timeZone, callback, 294, "PN", "Pacific/Pitcairn", "");
                insertTimeZone(timeZone, callback, 295, "PR", "America/Puerto_Rico", "");
                insertTimeZone(timeZone, callback, 296, "PS", "Asia/Gaza", "Gaza Strip");
                insertTimeZone(timeZone, callback, 297, "PS", "Asia/Hebron", "West Bank");
                insertTimeZone(timeZone, callback, 298, "PT", "Europe/Lisbon", "mainland");
                insertTimeZone(timeZone, callback, 299, "PT", "Atlantic/Madeira", "Madeira Islands");
                insertTimeZone(timeZone, callback, 300, "PT", "Atlantic/Azores", "Azores");
                insertTimeZone(timeZone, callback, 301, "PW", "Pacific/Palau", "");
                insertTimeZone(timeZone, callback, 302, "PY", "America/Asuncion", "");
                insertTimeZone(timeZone, callback, 303, "QA", "Asia/Qatar", "");
                insertTimeZone(timeZone, callback, 304, "RE", "Indian/Reunion", "");
                insertTimeZone(timeZone, callback, 305, "RO", "Europe/Bucharest", "");
                insertTimeZone(timeZone, callback, 306, "RS", "Europe/Belgrade", "");
                insertTimeZone(timeZone, callback, 307, "RU", "Europe/Kaliningrad", "Moscow-01 - Kaliningrad");
                insertTimeZone(timeZone, callback, 308, "RU", "Europe/Moscow", "Moscow+00 - west Russia");
                insertTimeZone(timeZone, callback, 309, "RU", "Europe/Volgograd", "Moscow+00 - Caspian Sea");
                insertTimeZone(timeZone, callback, 310, "RU", "Europe/Samara", "Moscow+00 - Samara, Udmurtia");
                insertTimeZone(timeZone, callback, 311, "RU", "Asia/Yekaterinburg", "Moscow+02 - Urals");
                insertTimeZone(timeZone, callback, 312, "RU", "Asia/Omsk", "Moscow+03 - west Siberia");
                insertTimeZone(timeZone, callback, 313, "RU", "Asia/Novosibirsk", "Moscow+03 - Novosibirsk");
                insertTimeZone(timeZone, callback, 314, "RU", "Asia/Novokuznetsk", "Moscow+03 - Novokuznetsk");
                insertTimeZone(timeZone, callback, 315, "RU", "Asia/Krasnoyarsk", "Moscow+04 - Yenisei River");
                insertTimeZone(timeZone, callback, 316, "RU", "Asia/Irkutsk", "Moscow+05 - Lake Baikal");
                insertTimeZone(timeZone, callback, 317, "RU", "Asia/Yakutsk", "Moscow+06 - Lena River");
                insertTimeZone(timeZone, callback, 318, "RU", "Asia/Vladivostok", "Moscow+07 - Amur River");
                insertTimeZone(timeZone, callback, 319, "RU", "Asia/Sakhalin", "Moscow+07 - Sakhalin Island");
                insertTimeZone(timeZone, callback, 320, "RU", "Asia/Magadan", "Moscow+08 - Magadan");
                insertTimeZone(timeZone, callback, 321, "RU", "Asia/Kamchatka", "Moscow+08 - Kamchatka");
                insertTimeZone(timeZone, callback, 322, "RU", "Asia/Anadyr", "Moscow+08 - Bering Sea");
                insertTimeZone(timeZone, callback, 323, "RW", "Africa/Kigali", "");
                insertTimeZone(timeZone, callback, 324, "SA", "Asia/Riyadh", "");
                insertTimeZone(timeZone, callback, 325, "SB", "Pacific/Guadalcanal", "");
                insertTimeZone(timeZone, callback, 326, "SC", "Indian/Mahe", "");
                insertTimeZone(timeZone, callback, 327, "SD", "Africa/Khartoum", "");
                insertTimeZone(timeZone, callback, 328, "SE", "Europe/Stockholm", "");
                insertTimeZone(timeZone, callback, 329, "SG", "Asia/Singapore", "");
                insertTimeZone(timeZone, callback, 330, "SH", "Atlantic/St_Helena", "");
                insertTimeZone(timeZone, callback, 331, "SI", "Europe/Ljubljana", "");
                insertTimeZone(timeZone, callback, 332, "SJ", "Arctic/Longyearbyen", "");
                insertTimeZone(timeZone, callback, 333, "SK", "Europe/Bratislava", "");
                insertTimeZone(timeZone, callback, 334, "SL", "Africa/Freetown", "");
                insertTimeZone(timeZone, callback, 335, "SM", "Europe/San_Marino", "");
                insertTimeZone(timeZone, callback, 336, "SN", "Africa/Dakar", "");
                insertTimeZone(timeZone, callback, 337, "SO", "Africa/Mogadishu", "");
                insertTimeZone(timeZone, callback, 338, "SR", "America/Paramaribo", "");
                insertTimeZone(timeZone, callback, 339, "SS", "Africa/Juba", "");
                insertTimeZone(timeZone, callback, 340, "ST", "Africa/Sao_Tome", "");
                insertTimeZone(timeZone, callback, 341, "SV", "America/El_Salvador", "");
                insertTimeZone(timeZone, callback, 342, "SX", "America/Lower_Princes", "");
                insertTimeZone(timeZone, callback, 343, "SY", "Asia/Damascus", "");
                insertTimeZone(timeZone, callback, 344, "SZ", "Africa/Mbabane", "");
                insertTimeZone(timeZone, callback, 345, "TC", "America/Grand_Turk", "");
                insertTimeZone(timeZone, callback, 346, "TD", "Africa/Ndjamena", "");
                insertTimeZone(timeZone, callback, 347, "TF", "Indian/Kerguelen", "");
                insertTimeZone(timeZone, callback, 348, "TG", "Africa/Lome", "");
                insertTimeZone(timeZone, callback, 349, "TH", "Asia/Bangkok", "");
                insertTimeZone(timeZone, callback, 350, "TJ", "Asia/Dushanbe", "");
                insertTimeZone(timeZone, callback, 351, "TK", "Pacific/Fakaofo", "");
                insertTimeZone(timeZone, callback, 352, "TL", "Asia/Dili", "");
                insertTimeZone(timeZone, callback, 353, "TM", "Asia/Ashgabat", "");
                insertTimeZone(timeZone, callback, 354, "TN", "Africa/Tunis", "");
                insertTimeZone(timeZone, callback, 355, "TO", "Pacific/Tongatapu", "");
                insertTimeZone(timeZone, callback, 356, "TR", "Europe/Istanbul", "");
                insertTimeZone(timeZone, callback, 357, "TT", "America/Port_of_Spain", "");
                insertTimeZone(timeZone, callback, 358, "TV", "Pacific/Funafuti", "");
                insertTimeZone(timeZone, callback, 359, "TW", "Asia/Taipei", "");
                insertTimeZone(timeZone, callback, 360, "TZ", "Africa/Dar_es_Salaam", "");
                insertTimeZone(timeZone, callback, 361, "UA", "Europe/Kiev", "most locations");
                insertTimeZone(timeZone, callback, 362, "UA", "Europe/Uzhgorod", "Ruthenia");
                insertTimeZone(timeZone, callback, 363, "UA", "Europe/Zaporozhye", "Zaporozhye, E Lugansk / Zaporizhia, E Luhansk");
                insertTimeZone(timeZone, callback, 364, "UA", "Europe/Simferopol", "central Crimea");
                insertTimeZone(timeZone, callback, 365, "UG", "Africa/Kampala", "");
                insertTimeZone(timeZone, callback, 366, "UM", "Pacific/Johnston", "Johnston Atoll");
                insertTimeZone(timeZone, callback, 367, "UM", "Pacific/Midway", "Midway Islands");
                insertTimeZone(timeZone, callback, 368, "UM", "Pacific/Wake", "Wake Island");
                insertTimeZone(timeZone, callback, 369, "US", "America/New_York", "Eastern Time");
                insertTimeZone(timeZone, callback, 370, "US", "America/Detroit", "Eastern Time - Michigan - most locations");
                insertTimeZone(timeZone, callback, 371, "US", "America/Kentucky/Louisville", "Eastern Time - Kentucky - Louisville area");
                insertTimeZone(timeZone, callback, 372, "US", "America/Kentucky/Monticello", "Eastern Time - Kentucky - Wayne County");
                insertTimeZone(timeZone, callback, 373, "US", "America/Indiana/Indianapolis", "Eastern Time - Indiana - most locations");
                insertTimeZone(timeZone, callback, 374, "US", "America/Indiana/Vincennes", "Eastern Time - Indiana - Daviess, Dubois, Knox & Martin Counties");
                insertTimeZone(timeZone, callback, 375, "US", "America/Indiana/Winamac", "Eastern Time - Indiana - Pulaski County");
                insertTimeZone(timeZone, callback, 376, "US", "America/Indiana/Marengo", "Eastern Time - Indiana - Crawford County");
                insertTimeZone(timeZone, callback, 377, "US", "America/Indiana/Petersburg", "Eastern Time - Indiana - Pike County");
                insertTimeZone(timeZone, callback, 378, "US", "America/Indiana/Vevay", "Eastern Time - Indiana - Switzerland County");
                insertTimeZone(timeZone, callback, 379, "US", "America/Chicago", "Central Time");
                insertTimeZone(timeZone, callback, 380, "US", "America/Indiana/Tell_City", "Central Time - Indiana - Perry County");
                insertTimeZone(timeZone, callback, 381, "US", "America/Indiana/Knox", "Central Time - Indiana - Starke County");
                insertTimeZone(timeZone, callback, 382, "US", "America/Menominee", "Central Time - Michigan - Dickinson, Gogebic, Iron & Menominee Counties");
                insertTimeZone(timeZone, callback, 383, "US", "America/North_Dakota/Center", "Central Time - North Dakota - Oliver County");
                insertTimeZone(timeZone, callback, 384, "US", "America/North_Dakota/New_Salem", "Central Time - North Dakota - Morton County (except Mandan area)");
                insertTimeZone(timeZone, callback, 385, "US", "America/North_Dakota/Beulah", "Central Time - North Dakota - Mercer County");
                insertTimeZone(timeZone, callback, 386, "US", "America/Denver", "Mountain Time");
                insertTimeZone(timeZone, callback, 387, "US", "America/Boise", "Mountain Time - south Idaho & east Oregon");
                insertTimeZone(timeZone, callback, 388, "US", "America/Shiprock", "Mountain Time - Navajo");
                insertTimeZone(timeZone, callback, 389, "US", "America/Phoenix", "Mountain Standard Time - Arizona");
                insertTimeZone(timeZone, callback, 390, "US", "America/Los_Angeles", "Pacific Time");
                insertTimeZone(timeZone, callback, 391, "US", "America/Anchorage", "Alaska Time");
                insertTimeZone(timeZone, callback, 392, "US", "America/Juneau", "Alaska Time - Alaska panhandle");
                insertTimeZone(timeZone, callback, 393, "US", "America/Sitka", "Alaska Time - southeast Alaska panhandle");
                insertTimeZone(timeZone, callback, 394, "US", "America/Yakutat", "Alaska Time - Alaska panhandle neck");
                insertTimeZone(timeZone, callback, 395, "US", "America/Nome", "Alaska Time - west Alaska");
                insertTimeZone(timeZone, callback, 396, "US", "America/Adak", "Aleutian Islands");
                insertTimeZone(timeZone, callback, 397, "US", "America/Metlakatla", "Metlakatla Time - Annette Island");
                insertTimeZone(timeZone, callback, 398, "US", "Pacific/Honolulu", "Hawaii");
                insertTimeZone(timeZone, callback, 399, "UY", "America/Montevideo", "");
                insertTimeZone(timeZone, callback, 400, "UZ", "Asia/Samarkand", "west Uzbekistan");
                insertTimeZone(timeZone, callback, 401, "UZ", "Asia/Tashkent", "east Uzbekistan");
                insertTimeZone(timeZone, callback, 402, "VA", "Europe/Vatican", "");
                insertTimeZone(timeZone, callback, 403, "VC", "America/St_Vincent", "");
                insertTimeZone(timeZone, callback, 404, "VE", "America/Caracas", "");
                insertTimeZone(timeZone, callback, 405, "VG", "America/Tortola", "");
                insertTimeZone(timeZone, callback, 406, "VI", "America/St_Thomas", "");
                insertTimeZone(timeZone, callback, 407, "VN", "Asia/Ho_Chi_Minh", "");
                insertTimeZone(timeZone, callback, 408, "VU", "Pacific/Efate", "");
                insertTimeZone(timeZone, callback, 409, "WF", "Pacific/Wallis", "");
                insertTimeZone(timeZone, callback, 410, "WS", "Pacific/Apia", "");
                insertTimeZone(timeZone, callback, 411, "YE", "Asia/Aden", "");
                insertTimeZone(timeZone, callback, 412, "YT", "Indian/Mayotte", "");
                insertTimeZone(timeZone, callback, 413, "ZA", "Africa/Johannesburg", "");
                insertTimeZone(timeZone, callback, 414, "ZM", "Africa/Lusaka", "");
                insertTimeZone(timeZone, callback, 415, "ZW", "Africa/Harare", "");
            });
        }
    }

    protected static void insertTimeZone(Table timeZone, RowInsertable callback, long id, String code, String name, String comment) {
        callback.insertInto(timeZone)
                .value(timeZone.getColumnByName("id"), id)
                .value(timeZone.getColumnByName("country_code"), code)
                .value(timeZone.getColumnByName("timezonename"), name)
                .value(timeZone.getColumnByName("comments"), comment)
                .execute();
    }
}