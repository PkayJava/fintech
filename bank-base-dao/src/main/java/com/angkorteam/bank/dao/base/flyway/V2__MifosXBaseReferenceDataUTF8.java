package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.jdbc.query.InsertQuery;
import com.angkorteam.jdbc.query.SelectQuery;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class V2__MifosXBaseReferenceDataUTF8 extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V2__MifosXBaseReferenceDataUTF8;
    }

    @Override
    protected void doMigrate(Context context, DataSource dataSource, NamedParameterJdbcTemplate named, JdbcDataContext dataContext) throws Exception {
        {
            dataContext.refreshSchemas();
            Table ref_loan_transaction_processing_strategy = dataContext.getDefaultSchema().getTableByName("ref_loan_transaction_processing_strategy");
            insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 1, "mifos-standard-strategy", "Mifos style");
            insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 2, "heavensfamily-strategy", "Heavensfamily");
            insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 3, "creocore-strategy", "Creocore");
            insert_ref_loan_transaction_processing_strategy(named, ref_loan_transaction_processing_strategy, 4, "rbi-india-strategy", "RBI (India)");
        }
        {
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            insert_c_configuration(named, c_configuration, "maker-checker", 0);
        }
        {
            dataContext.refreshSchemas();
            Table r_enum_value = dataContext.getDefaultSchema().getTableByName("r_enum_value");
            insert_r_enum_value(named, r_enum_value, "amortization_method_enum", 0, "Equal principle payments", "Equal principle payments");
            insert_r_enum_value(named, r_enum_value, "amortization_method_enum", 1, "Equal installments", "Equal installments");

            insert_r_enum_value(named, r_enum_value, "interest_calculated_in_period_enum", 0, "Daily", "Daily");
            insert_r_enum_value(named, r_enum_value, "interest_calculated_in_period_enum", 1, "Same as repayment period", "Same as repayment period");

            insert_r_enum_value(named, r_enum_value, "interest_method_enum", 0, "Declining Balance", "Declining Balance");
            insert_r_enum_value(named, r_enum_value, "interest_method_enum", 1, "Flat", "Flat");

            insert_r_enum_value(named, r_enum_value, "interest_period_frequency_enum", 2, "Per month", "Per month");
            insert_r_enum_value(named, r_enum_value, "interest_period_frequency_enum", 3, "Per year", "Per year");

            insert_r_enum_value(named, r_enum_value, "loan_status_id", 100, "Submitted and awaiting approval", "Submitted and awaiting approval");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 200, "Approved", "Approved");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 300, "Active", "Active");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 400, "Withdrawn by client", "Withdrawn by client");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 500, "Rejected", "Rejected");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 600, "Closed", "Closed");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 601, "Written-Off", "Written-Off");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 602, "Rescheduled", "Rescheduled");
            insert_r_enum_value(named, r_enum_value, "loan_status_id", 700, "Overpaid", "Overpaid");

            insert_r_enum_value(named, r_enum_value, "loan_transaction_strategy_id", 1, "mifos-standard-strategy", "Mifos style");
            insert_r_enum_value(named, r_enum_value, "loan_transaction_strategy_id", 2, "heavensfamily-strategy", "Heavensfamily");
            insert_r_enum_value(named, r_enum_value, "loan_transaction_strategy_id", 3, "creocore-strategy", "Creocore");
            insert_r_enum_value(named, r_enum_value, "loan_transaction_strategy_id", 4, "rbi-india-strategy", "RBI (India)");

            insert_r_enum_value(named, r_enum_value, "processing_result_enum", 0, "invalid", "Invalid");
            insert_r_enum_value(named, r_enum_value, "processing_result_enum", 1, "processed", "Processed");
            insert_r_enum_value(named, r_enum_value, "processing_result_enum", 2, "awaiting.approval", "Awaiting Approval");
            insert_r_enum_value(named, r_enum_value, "processing_result_enum", 3, "rejected", "Rejected");

            insert_r_enum_value(named, r_enum_value, "repayment_period_frequency_enum", 0, "Days", "Days");
            insert_r_enum_value(named, r_enum_value, "repayment_period_frequency_enum", 1, "Weeks", "Weeks");
            insert_r_enum_value(named, r_enum_value, "repayment_period_frequency_enum", 2, "Months", "Months");

            insert_r_enum_value(named, r_enum_value, "term_period_frequency_enum", 0, "Days", "Days");
            insert_r_enum_value(named, r_enum_value, "term_period_frequency_enum", 1, "Weeks", "Weeks");
            insert_r_enum_value(named, r_enum_value, "term_period_frequency_enum", 2, "Months", "Months");
            insert_r_enum_value(named, r_enum_value, "term_period_frequency_enum", 3, "Years", "Year");

            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 1, "Disbursement", "Disbursement");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 2, "Repayment", "Repayment");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 3, "Contra", "Contra");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 4, "Waive Interest", "Waive Interest");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 5, "Repayment At Disbursement", "Repayment At Disbursement");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 6, "Write-Off", "Write-Off");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 7, "Marked for Rescheduling", "Marked for Rescheduling");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 8, "Recovery Repayment", "Recovery Repayment");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 9, "Waive Charges", "Waive Charges");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 10, "Apply Charges", "Apply Charges");
            insert_r_enum_value(named, r_enum_value, "transaction_type_enum", 11, "Apply Interest", "Apply Interest");
        }
        {
            dataContext.refreshSchemas();
            Table m_currency = dataContext.getDefaultSchema().getTableByName("m_currency");
            insert_m_currency(named, m_currency, 1, "AED", 2, null, "UAE Dirham", "currency.AED");
            insert_m_currency(named, m_currency, 2, "AFN", 2, null, "Afghanistan Afghani", "currency.AFN");
            insert_m_currency(named, m_currency, 3, "ALL", 2, null, "Albanian Lek", "currency.ALL");
            insert_m_currency(named, m_currency, 4, "AMD", 2, null, "Armenian Dram", "currency.AMD");
            insert_m_currency(named, m_currency, 5, "ANG", 2, null, "Netherlands Antillian Guilder", "currency.ANG");
            insert_m_currency(named, m_currency, 6, "AOA", 2, null, "Angolan Kwanza", "currency.AOA");
            insert_m_currency(named, m_currency, 7, "ARS", 2, "$", "Argentine Peso", "currency.ARS");
            insert_m_currency(named, m_currency, 8, "AUD", 2, "A$", "Australian Dollar", "currency.AUD");
            insert_m_currency(named, m_currency, 9, "AWG", 2, null, "Aruban Guilder", "currency.AWG");
            insert_m_currency(named, m_currency, 10, "AZM", 2, null, "Azerbaijanian Manat", "currency.AZM");
            insert_m_currency(named, m_currency, 11, "BAM", 2, null, "Bosnia and Herzegovina Convertible Marks", "currency.BAM");
            insert_m_currency(named, m_currency, 12, "BBD", 2, null, "Barbados Dollar", "currency.BBD");
            insert_m_currency(named, m_currency, 13, "BDT", 2, null, "Bangladesh Taka", "currency.BDT");
            insert_m_currency(named, m_currency, 14, "BGN", 2, null, "Bulgarian Lev", "currency.BGN");
            insert_m_currency(named, m_currency, 15, "BHD", 3, null, "Bahraini Dinar", "currency.BHD");
            insert_m_currency(named, m_currency, 16, "BIF", 0, null, "Burundi Franc", "currency.BIF");
            insert_m_currency(named, m_currency, 17, "BMD", 2, null, "Bermudian Dollar", "currency.BMD");
            insert_m_currency(named, m_currency, 18, "BND", 2, "B$", "Brunei Dollar", "currency.BND");
            insert_m_currency(named, m_currency, 19, "BOB", 2, "Bs.", "Bolivian Boliviano", "currency.BOB");
            insert_m_currency(named, m_currency, 20, "BRL", 2, "R$", "Brazilian Real", "currency.BRL");
            insert_m_currency(named, m_currency, 21, "BSD", 2, null, "Bahamian Dollar", "currency.BSD");
            insert_m_currency(named, m_currency, 22, "BTN", 2, null, "Bhutan Ngultrum", "currency.BTN");
            insert_m_currency(named, m_currency, 23, "BWP", 2, null, "Botswana Pula", "currency.BWP");
            insert_m_currency(named, m_currency, 24, "BYR", 0, null, "Belarussian Ruble", "currency.BYR");
            insert_m_currency(named, m_currency, 25, "BZD", 2, "BZ$", "Belize Dollar", "currency.BZD");
            insert_m_currency(named, m_currency, 26, "CAD", 2, null, "Canadian Dollar", "currency.CAD");
            insert_m_currency(named, m_currency, 27, "CDF", 2, null, "Franc Congolais", "currency.CDF");
            insert_m_currency(named, m_currency, 28, "CHF", 2, null, "Swiss Franc", "currency.CHF");
            insert_m_currency(named, m_currency, 29, "CLP", 0, "$", "Chilean Peso", "currency.CLP");
            insert_m_currency(named, m_currency, 30, "CNY", 2, null, "Chinese Yuan Renminbi", "currency.CNY");
            insert_m_currency(named, m_currency, 31, "COP", 2, "$", "Colombian Peso", "currency.COP");
            insert_m_currency(named, m_currency, 32, "CRC", 2, "₡", "Costa Rican Colon", "currency.CRC");
            insert_m_currency(named, m_currency, 33, "CSD", 2, null, "Serbian Dinar", "currency.CSD");
            insert_m_currency(named, m_currency, 34, "CUP", 2, "$MN", "Cuban Peso", "currency.CUP");
            insert_m_currency(named, m_currency, 35, "CVE", 2, null, "Cape Verde Escudo", "currency.CVE");
            insert_m_currency(named, m_currency, 36, "CYP", 2, null, "Cyprus Pound", "currency.CYP");
            insert_m_currency(named, m_currency, 37, "CZK", 2, null, "Czech Koruna", "currency.CZK");
            insert_m_currency(named, m_currency, 38, "DJF", 0, null, "Djibouti Franc", "currency.DJF");
            insert_m_currency(named, m_currency, 39, "DKK", 2, null, "Danish Krone", "currency.DKK");
            insert_m_currency(named, m_currency, 40, "DOP", 2, "RD$", "Dominican Peso", "currency.DOP");
            insert_m_currency(named, m_currency, 41, "DZD", 2, null, "Algerian Dinar", "currency.DZD");
            insert_m_currency(named, m_currency, 42, "EEK", 2, null, "Estonian Kroon", "currency.EEK");
            insert_m_currency(named, m_currency, 43, "EGP", 2, null, "Egyptian Pound", "currency.EGP");
            insert_m_currency(named, m_currency, 44, "ERN", 2, null, "Eritrea Nafka", "currency.ERN");
            insert_m_currency(named, m_currency, 45, "ETB", 2, null, "Ethiopian Birr", "currency.ETB");
            insert_m_currency(named, m_currency, 46, "EUR", 2, "€", "Euro", "currency.EUR");
            insert_m_currency(named, m_currency, 47, "FJD", 2, null, "Fiji Dollar", "currency.FJD");
            insert_m_currency(named, m_currency, 48, "FKP", 2, null, "Falkland Islands Pound", "currency.FKP");
            insert_m_currency(named, m_currency, 49, "GBP", 2, null, "Pound Sterling", "currency.GBP");
            insert_m_currency(named, m_currency, 50, "GEL", 2, null, "Georgian Lari", "currency.GEL");
            insert_m_currency(named, m_currency, 51, "GHC", 2, "GHc", "Ghana Cedi", "currency.GHC");
            insert_m_currency(named, m_currency, 52, "GIP", 2, null, "Gibraltar Pound", "currency.GIP");
            insert_m_currency(named, m_currency, 53, "GMD", 2, null, "Gambian Dalasi", "currency.GMD");
            insert_m_currency(named, m_currency, 54, "GNF", 0, null, "Guinea Franc", "currency.GNF");
            insert_m_currency(named, m_currency, 55, "GTQ", 2, "Q", "Guatemala Quetzal", "currency.GTQ");
            insert_m_currency(named, m_currency, 56, "GYD", 2, null, "Guyana Dollar", "currency.GYD");
            insert_m_currency(named, m_currency, 57, "HKD", 2, null, "Hong Kong Dollar", "currency.HKD");
            insert_m_currency(named, m_currency, 58, "HNL", 2, "L", "Honduras Lempira", "currency.HNL");
            insert_m_currency(named, m_currency, 59, "HRK", 2, null, "Croatian Kuna", "currency.HRK");
            insert_m_currency(named, m_currency, 60, "HTG", 2, "G", "Haiti Gourde", "currency.HTG");
            insert_m_currency(named, m_currency, 61, "HUF", 2, null, "Hungarian Forint", "currency.HUF");
            insert_m_currency(named, m_currency, 62, "IDR", 2, null, "Indonesian Rupiah", "currency.IDR");
            insert_m_currency(named, m_currency, 63, "ILS", 2, null, "New Israeli Shekel", "currency.ILS");
            insert_m_currency(named, m_currency, 64, "INR", 2, "₹", "Indian Rupee", "currency.INR");
            insert_m_currency(named, m_currency, 65, "IQD", 3, null, "Iraqi Dinar", "currency.IQD");
            insert_m_currency(named, m_currency, 66, "IRR", 2, null, "Iranian Rial", "currency.IRR");
            insert_m_currency(named, m_currency, 67, "ISK", 0, null, "Iceland Krona", "currency.ISK");
            insert_m_currency(named, m_currency, 68, "JMD", 2, null, "Jamaican Dollar", "currency.JMD");
            insert_m_currency(named, m_currency, 69, "JOD", 3, null, "Jordanian Dinar", "currency.JOD");
            insert_m_currency(named, m_currency, 70, "JPY", 0, null, "Japanese Yen", "currency.JPY");
            insert_m_currency(named, m_currency, 71, "KES", 2, "KSh", "Kenyan Shilling", "currency.KES");
            insert_m_currency(named, m_currency, 72, "KGS", 2, null, "Kyrgyzstan Som", "currency.KGS");
            insert_m_currency(named, m_currency, 73, "KHR", 2, null, "Cambodia Riel", "currency.KHR");
            insert_m_currency(named, m_currency, 74, "KMF", 0, null, "Comoro Franc", "currency.KMF");
            insert_m_currency(named, m_currency, 75, "KPW", 2, null, "North Korean Won", "currency.KPW");
            insert_m_currency(named, m_currency, 76, "KRW", 0, null, "Korean Won", "currency.KRW");
            insert_m_currency(named, m_currency, 77, "KWD", 3, null, "Kuwaiti Dinar", "currency.KWD");
            insert_m_currency(named, m_currency, 78, "KYD", 2, null, "Cayman Islands Dollar", "currency.KYD");
            insert_m_currency(named, m_currency, 79, "KZT", 2, null, "Kazakhstan Tenge", "currency.KZT");
            insert_m_currency(named, m_currency, 80, "LAK", 2, null, "Lao Kip", "currency.LAK");
            insert_m_currency(named, m_currency, 81, "LBP", 2, "L£", "Lebanese Pound", "currency.LBP");
            insert_m_currency(named, m_currency, 82, "LKR", 2, null, "Sri Lanka Rupee", "currency.LKR");
            insert_m_currency(named, m_currency, 83, "LRD", 2, null, "Liberian Dollar", "currency.LRD");
            insert_m_currency(named, m_currency, 84, "LSL", 2, null, "Lesotho Loti", "currency.LSL");
            insert_m_currency(named, m_currency, 85, "LTL", 2, null, "Lithuanian Litas", "currency.LTL");
            insert_m_currency(named, m_currency, 86, "LVL", 2, null, "Latvian Lats", "currency.LVL");
            insert_m_currency(named, m_currency, 87, "LYD", 3, null, "Libyan Dinar", "currency.LYD");
            insert_m_currency(named, m_currency, 88, "MAD", 2, null, "Moroccan Dirham", "currency.MAD");
            insert_m_currency(named, m_currency, 89, "MDL", 2, null, "Moldovan Leu", "currency.MDL");
            insert_m_currency(named, m_currency, 90, "MGA", 2, null, "Malagasy Ariary", "currency.MGA");
            insert_m_currency(named, m_currency, 91, "MKD", 2, null, "Macedonian Denar", "currency.MKD");
            insert_m_currency(named, m_currency, 92, "MMK", 2, "K", "Myanmar Kyat", "currency.MMK");
            insert_m_currency(named, m_currency, 93, "MNT", 2, null, "Mongolian Tugrik", "currency.MNT");
            insert_m_currency(named, m_currency, 94, "MOP", 2, null, "Macau Pataca", "currency.MOP");
            insert_m_currency(named, m_currency, 95, "MRO", 2, null, "Mauritania Ouguiya", "currency.MRO");
            insert_m_currency(named, m_currency, 96, "MTL", 2, null, "Maltese Lira", "currency.MTL");
            insert_m_currency(named, m_currency, 97, "MUR", 2, null, "Mauritius Rupee", "currency.MUR");
            insert_m_currency(named, m_currency, 98, "MVR", 2, null, "Maldives Rufiyaa", "currency.MVR");
            insert_m_currency(named, m_currency, 99, "MWK", 2, null, "Malawi Kwacha", "currency.MWK");
            insert_m_currency(named, m_currency, 100, "MXN", 2, "$", "Mexican Peso", "currency.MXN");
            insert_m_currency(named, m_currency, 101, "MYR", 2, null, "Malaysian Ringgit", "currency.MYR");
            insert_m_currency(named, m_currency, 102, "MZM", 2, null, "Mozambique Metical", "currency.MZM");
            insert_m_currency(named, m_currency, 103, "NAD", 2, null, "Namibia Dollar", "currency.NAD");
            insert_m_currency(named, m_currency, 104, "NGN", 2, null, "Nigerian Naira", "currency.NGN");
            insert_m_currency(named, m_currency, 105, "NIO", 2, "C$", "Nicaragua Cordoba Oro", "currency.NIO");
            insert_m_currency(named, m_currency, 106, "NOK", 2, null, "Norwegian Krone", "currency.NOK");
            insert_m_currency(named, m_currency, 107, "NPR", 2, null, "Nepalese Rupee", "currency.NPR");
            insert_m_currency(named, m_currency, 108, "NZD", 2, null, "New Zealand Dollar", "currency.NZD");
            insert_m_currency(named, m_currency, 109, "OMR", 3, null, "Rial Omani", "currency.OMR");
            insert_m_currency(named, m_currency, 110, "PAB", 2, "B/.", "Panama Balboa", "currency.PAB");
            insert_m_currency(named, m_currency, 111, "PEN", 2, "S/.", "Peruvian Nuevo Sol", "currency.PEN");
            insert_m_currency(named, m_currency, 112, "PGK", 2, null, "Papua New Guinea Kina", "currency.PGK");
            insert_m_currency(named, m_currency, 113, "PHP", 2, null, "Philippine Peso", "currency.PHP");
            insert_m_currency(named, m_currency, 114, "PKR", 2, null, "Pakistan Rupee", "currency.PKR");
            insert_m_currency(named, m_currency, 115, "PLN", 2, null, "Polish Zloty", "currency.PLN");
            insert_m_currency(named, m_currency, 116, "PYG", 0, "₲", "Paraguayan Guarani", "currency.PYG");
            insert_m_currency(named, m_currency, 117, "QAR", 2, null, "Qatari Rial", "currency.QAR");
            insert_m_currency(named, m_currency, 118, "RON", 2, null, "Romanian Leu", "currency.RON");
            insert_m_currency(named, m_currency, 119, "RUB", 2, null, "Russian Ruble", "currency.RUB");
            insert_m_currency(named, m_currency, 120, "RWF", 0, null, "Rwanda Franc", "currency.RWF");
            insert_m_currency(named, m_currency, 121, "SAR", 2, null, "Saudi Riyal", "currency.SAR");
            insert_m_currency(named, m_currency, 122, "SBD", 2, null, "Solomon Islands Dollar", "currency.SBD");
            insert_m_currency(named, m_currency, 123, "SCR", 2, null, "Seychelles Rupee", "currency.SCR");
            insert_m_currency(named, m_currency, 124, "SDD", 2, null, "Sudanese Dinar", "currency.SDD");
            insert_m_currency(named, m_currency, 125, "SEK", 2, null, "Swedish Krona", "currency.SEK");
            insert_m_currency(named, m_currency, 126, "SGD", 2, null, "Singapore Dollar", "currency.SGD");
            insert_m_currency(named, m_currency, 127, "SHP", 2, null, "St Helena Pound", "currency.SHP");
            insert_m_currency(named, m_currency, 128, "SIT", 2, null, "Slovenian Tolar", "currency.SIT");
            insert_m_currency(named, m_currency, 129, "SKK", 2, null, "Slovak Koruna", "currency.SKK");
            insert_m_currency(named, m_currency, 130, "SLL", 2, null, "Sierra Leone Leone", "currency.SLL");
            insert_m_currency(named, m_currency, 131, "SOS", 2, null, "Somali Shilling", "currency.SOS");
            insert_m_currency(named, m_currency, 132, "SRD", 2, null, "Surinam Dollar", "currency.SRD");
            insert_m_currency(named, m_currency, 133, "STD", 2, null, "Sao Tome and Principe Dobra", "currency.STD");
            insert_m_currency(named, m_currency, 134, "SVC", 2, null, "El Salvador Colon", "currency.SVC");
            insert_m_currency(named, m_currency, 135, "SYP", 2, null, "Syrian Pound", "currency.SYP");
            insert_m_currency(named, m_currency, 136, "SZL", 2, null, "Swaziland Lilangeni", "currency.SZL");
            insert_m_currency(named, m_currency, 137, "THB", 2, null, "Thai Baht", "currency.THB");
            insert_m_currency(named, m_currency, 138, "TJS", 2, null, "Tajik Somoni", "currency.TJS");
            insert_m_currency(named, m_currency, 139, "TMM", 2, null, "Turkmenistan Manat", "currency.TMM");
            insert_m_currency(named, m_currency, 140, "TND", 3, "DT", "Tunisian Dinar", "currency.TND");
            insert_m_currency(named, m_currency, 141, "TOP", 2, null, "Tonga Pa'anga", "currency.TOP");
            insert_m_currency(named, m_currency, 142, "TRY", 2, null, "Turkish Lira", "currency.TRY");
            insert_m_currency(named, m_currency, 143, "TTD", 2, null, "Trinidad and Tobago Dollar", "currency.TTD");
            insert_m_currency(named, m_currency, 144, "TWD", 2, null, "New Taiwan Dollar", "currency.TWD");
            insert_m_currency(named, m_currency, 145, "TZS", 2, null, "Tanzanian Shilling", "currency.TZS");
            insert_m_currency(named, m_currency, 146, "UAH", 2, null, "Ukraine Hryvnia", "currency.UAH");
            insert_m_currency(named, m_currency, 147, "UGX", 2, "USh", "Uganda Shilling", "currency.UGX");
            insert_m_currency(named, m_currency, 148, "USD", 2, "$", "US Dollar", "currency.USD");
            insert_m_currency(named, m_currency, 149, "UYU", 2, "$U", "Peso Uruguayo", "currency.UYU");
            insert_m_currency(named, m_currency, 150, "UZS", 2, null, "Uzbekistan Sum", "currency.UZS");
            insert_m_currency(named, m_currency, 151, "VEB", 2, "Bs.F.", "Venezuelan Bolivar", "currency.VEB");
            insert_m_currency(named, m_currency, 152, "VND", 2, null, "Vietnamese Dong", "currency.VND");
            insert_m_currency(named, m_currency, 153, "VUV", 0, null, "Vanuatu Vatu", "currency.VUV");
            insert_m_currency(named, m_currency, 154, "WST", 2, null, "Samoa Tala", "currency.WST");
            insert_m_currency(named, m_currency, 155, "XAF", 0, null, "CFA Franc BEAC", "currency.XAF");
            insert_m_currency(named, m_currency, 156, "XCD", 2, null, "East Caribbean Dollar", "currency.XCD");
            insert_m_currency(named, m_currency, 157, "XDR", 5, null, "SDR (Special Drawing Rights)", "currency.XDR");
            insert_m_currency(named, m_currency, 158, "XOF", 0, "CFA", "CFA Franc BCEAO", "currency.XOF");
            insert_m_currency(named, m_currency, 159, "XPF", 0, null, "CFP Franc", "currency.XPF");
            insert_m_currency(named, m_currency, 160, "YER", 2, null, "Yemeni Rial", "currency.YER");
            insert_m_currency(named, m_currency, 161, "ZAR", 2, "R", "South African Rand", "currency.ZAR");
            insert_m_currency(named, m_currency, 162, "ZMK", 2, null, "Zambian Kwacha", "currency.ZMK");
            insert_m_currency(named, m_currency, 163, "ZWD", 2, null, "Zimbabwe Dollar", "currency.ZWD");
        }
        {
            dataContext.refreshSchemas();
            Table m_organisation_currency = dataContext.getDefaultSchema().getTableByName("m_organisation_currency");
            insert_m_organisation_currency(named, m_organisation_currency, 21, "USD", 2, "US Dollar", "$", "currency.USD");
        }
        {
            dataContext.refreshSchemas();
            Table m_office = dataContext.getDefaultSchema().getTableByName("m_office");
            insert_m_office(named, m_office, 1, null, ".", "1", "Head Office", "2009-01-01");
        }
        {
            dataContext.refreshSchemas();
            Table m_group_level = dataContext.getDefaultSchema().getTableByName("m_group_level");
            insert_m_group_level(named, m_group_level, 1, null, 1, "Center", 1, 0);
            insert_m_group_level(named, m_group_level, 2, "1", 0, "Group", 0, 1);
        }
        {
            dataContext.refreshSchemas();
            Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
            insert_m_code(named, m_code, "Customer Identifier", 1);
            insert_m_code(named, m_code, "LoanCollateral", 1);
            insert_m_code(named, m_code, "LoanPurpose", 1);
            insert_m_code(named, m_code, "Gender", 1);
            insert_m_code(named, m_code, "YesNo", 1);
            insert_m_code(named, m_code, "GuarantorRelationship", 1);
        }
        {
            dataContext.refreshSchemas();
            Table m_code_value = dataContext.getDefaultSchema().getTableByName("m_code_value");
            {
                long order_position = 0;
                long code_id = lookup_m_code(named, dataContext, "Customer Identifier");
                insert_m_code_value(named, m_code_value, code_id, "Passport", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Id", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Drivers License", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Any Other Id Type", order_position++);
            }
            {
                long order_position = 0;
                long code_id = lookup_m_code(named, dataContext, "GuarantorRelationship");
                insert_m_code_value(named, m_code_value, code_id, "Spouse", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Parent", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Sibling", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Business Associate", order_position++);
                insert_m_code_value(named, m_code_value, code_id, "Other", order_position++);
            }
        }
    }

    protected void insert_m_code_value(NamedParameterJdbcTemplate named, Table table, long code_id, String code_value, long order_position) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_id").getName(), code_id);
        insertQuery.addValue(table.getColumnByName("code_value").getName(), code_value);
        insertQuery.addValue(table.getColumnByName("order_position").getName(), order_position);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_group_level(NamedParameterJdbcTemplate named, Table table, long id, String parent_id, long super_parent, String level_name, long recursable, long can_have_clients) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("parent_id").getName(), parent_id);
        insertQuery.addValue(table.getColumnByName("super_parent").getName(), super_parent);
        insertQuery.addValue(table.getColumnByName("level_name").getName(), level_name);
        insertQuery.addValue(table.getColumnByName("recursable").getName(), recursable);
        insertQuery.addValue(table.getColumnByName("can_have_clients").getName(), can_have_clients);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_office(NamedParameterJdbcTemplate named, Table table, long id, String parent_id, String hierarchy, String external_id, String name, String opening_date) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("parent_id").getName(), parent_id);
        insertQuery.addValue(table.getColumnByName("hierarchy").getName(), hierarchy);
        insertQuery.addValue(table.getColumnByName("external_id").getName(), external_id);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("opening_date").getName(), opening_date);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_organisation_currency(NamedParameterJdbcTemplate named, Table table, long id, String code, int decimal_places, String name, String display_symbol, String internationalized_name_code) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("decimal_places").getName(), decimal_places);
        insertQuery.addValue(table.getColumnByName("display_symbol").getName(), display_symbol);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("internationalized_name_code").getName(), internationalized_name_code);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_currency(NamedParameterJdbcTemplate named, Table table, long id, String code, int decimal_places, String display_symbol, String name, String internationalized_name_code) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("decimal_places").getName(), decimal_places);
        insertQuery.addValue(table.getColumnByName("display_symbol").getName(), display_symbol);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("internationalized_name_code").getName(), internationalized_name_code);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_r_enum_value(NamedParameterJdbcTemplate named, Table table, String enum_name, long enum_id, String enum_message_property, String enum_value) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("enum_name").getName(), enum_name);
        insertQuery.addValue(table.getColumnByName("enum_id").getName(), enum_id);
        insertQuery.addValue(table.getColumnByName("enum_message_property").getName(), enum_message_property);
        insertQuery.addValue(table.getColumnByName("enum_value").getName(), enum_value);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_c_configuration(NamedParameterJdbcTemplate named, Table table, String name, long enabled) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        insertQuery.addValue(table.getColumnByName("enabled").getName(), enabled);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_ref_loan_transaction_processing_strategy(NamedParameterJdbcTemplate named, Table table, long id, String code, String name) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("id").getName(), id);
        insertQuery.addValue(table.getColumnByName("code").getName(), code);
        insertQuery.addValue(table.getColumnByName("name").getName(), name);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected void insert_m_code(NamedParameterJdbcTemplate named, Table table, String code_name, long is_system_defined) {
        InsertQuery insertQuery = new InsertQuery(table.getName());
        insertQuery.addValue(table.getColumnByName("code_name").getName(), code_name);
        insertQuery.addValue(table.getColumnByName("is_system_defined").getName(), is_system_defined);
        named.update(insertQuery.toSQL(), insertQuery.toParam());
    }

    protected int lookup_m_code(NamedParameterJdbcTemplate named, DataContext dataContext, String code_name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_code");
        SelectQuery selectQuery = new SelectQuery(table.getName());
        selectQuery.addField(table.getColumnByName("id").getName());
        selectQuery.addWhere(table.getColumnByName("code_name").getName() + " = :code_name", code_name);
        Integer id = named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), int.class);
        return id == null ? 0 : id;
    }
}