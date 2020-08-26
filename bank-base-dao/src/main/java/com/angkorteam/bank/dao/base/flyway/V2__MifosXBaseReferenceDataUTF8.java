package com.angkorteam.bank.dao.base.flyway;

import com.angkorteam.bank.dao.base.Checksum;
import com.angkorteam.metamodel.LiquibaseJavaMigration;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.insert.RowInsertable;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Table;
import org.flywaydb.core.api.migration.Context;

public class V2__MifosXBaseReferenceDataUTF8 extends LiquibaseJavaMigration {

    @Override
    public Integer getChecksum() {
        return Checksum.V2__MifosXBaseReferenceDataUTF8;
    }

    @Override
    public void migrate(Context context) throws Exception {
        JdbcDataContext dataContext = lookupDataContext(context);
        {
            dataContext.refreshSchemas();
            Table ref_loan_transaction_processing_strategy = dataContext.getDefaultSchema().getTableByName("ref_loan_transaction_processing_strategy");
            dataContext.executeUpdate(callback -> {
                insert_ref_loan_transaction_processing_strategy(ref_loan_transaction_processing_strategy, callback, 1, "mifos-standard-strategy", "Mifos style");
                insert_ref_loan_transaction_processing_strategy(ref_loan_transaction_processing_strategy, callback, 2, "heavensfamily-strategy", "Heavensfamily");
                insert_ref_loan_transaction_processing_strategy(ref_loan_transaction_processing_strategy, callback, 3, "creocore-strategy", "Creocore");
                insert_ref_loan_transaction_processing_strategy(ref_loan_transaction_processing_strategy, callback, 4, "rbi-india-strategy", "RBI (India)");
            });
        }
        {
            dataContext.refreshSchemas();
            Table c_configuration = dataContext.getDefaultSchema().getTableByName("c_configuration");
            dataContext.executeUpdate(callback -> {
                insert_c_configuration(c_configuration, callback, "maker-checker", 0);
            });
        }
        {
            dataContext.refreshSchemas();
            Table r_enum_value = dataContext.getDefaultSchema().getTableByName("r_enum_value");
            dataContext.executeUpdate(callback -> {
                insert_r_enum_value(r_enum_value, callback, "amortization_method_enum", 0, "Equal principle payments", "Equal principle payments");
                insert_r_enum_value(r_enum_value, callback, "amortization_method_enum", 1, "Equal installments", "Equal installments");

                insert_r_enum_value(r_enum_value, callback, "interest_calculated_in_period_enum", 0, "Daily", "Daily");
                insert_r_enum_value(r_enum_value, callback, "interest_calculated_in_period_enum", 1, "Same as repayment period", "Same as repayment period");

                insert_r_enum_value(r_enum_value, callback, "interest_method_enum", 0, "Declining Balance", "Declining Balance");
                insert_r_enum_value(r_enum_value, callback, "interest_method_enum", 1, "Flat", "Flat");

                insert_r_enum_value(r_enum_value, callback, "interest_period_frequency_enum", 2, "Per month", "Per month");
                insert_r_enum_value(r_enum_value, callback, "interest_period_frequency_enum", 3, "Per year", "Per year");

                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 100, "Submitted and awaiting approval", "Submitted and awaiting approval");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 200, "Approved", "Approved");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 300, "Active", "Active");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 400, "Withdrawn by client", "Withdrawn by client");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 500, "Rejected", "Rejected");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 600, "Closed", "Closed");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 601, "Written-Off", "Written-Off");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 602, "Rescheduled", "Rescheduled");
                insert_r_enum_value(r_enum_value, callback, "loan_status_id", 700, "Overpaid", "Overpaid");

                insert_r_enum_value(r_enum_value, callback, "loan_transaction_strategy_id", 1, "mifos-standard-strategy", "Mifos style");
                insert_r_enum_value(r_enum_value, callback, "loan_transaction_strategy_id", 2, "heavensfamily-strategy", "Heavensfamily");
                insert_r_enum_value(r_enum_value, callback, "loan_transaction_strategy_id", 3, "creocore-strategy", "Creocore");
                insert_r_enum_value(r_enum_value, callback, "loan_transaction_strategy_id", 4, "rbi-india-strategy", "RBI (India)");

                insert_r_enum_value(r_enum_value, callback, "processing_result_enum", 0, "invalid", "Invalid");
                insert_r_enum_value(r_enum_value, callback, "processing_result_enum", 1, "processed", "Processed");
                insert_r_enum_value(r_enum_value, callback, "processing_result_enum", 2, "awaiting.approval", "Awaiting Approval");
                insert_r_enum_value(r_enum_value, callback, "processing_result_enum", 3, "rejected", "Rejected");

                insert_r_enum_value(r_enum_value, callback, "repayment_period_frequency_enum", 0, "Days", "Days");
                insert_r_enum_value(r_enum_value, callback, "repayment_period_frequency_enum", 1, "Weeks", "Weeks");
                insert_r_enum_value(r_enum_value, callback, "repayment_period_frequency_enum", 2, "Months", "Months");

                insert_r_enum_value(r_enum_value, callback, "term_period_frequency_enum", 0, "Days", "Days");
                insert_r_enum_value(r_enum_value, callback, "term_period_frequency_enum", 1, "Weeks", "Weeks");
                insert_r_enum_value(r_enum_value, callback, "term_period_frequency_enum", 2, "Months", "Months");
                insert_r_enum_value(r_enum_value, callback, "term_period_frequency_enum", 3, "Years", "Year");

                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 1, "Disbursement", "Disbursement");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 2, "Repayment", "Repayment");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 3, "Contra", "Contra");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 4, "Waive Interest", "Waive Interest");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 5, "Repayment At Disbursement", "Repayment At Disbursement");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 6, "Write-Off", "Write-Off");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 7, "Marked for Rescheduling", "Marked for Rescheduling");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 8, "Recovery Repayment", "Recovery Repayment");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 9, "Waive Charges", "Waive Charges");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 10, "Apply Charges", "Apply Charges");
                insert_r_enum_value(r_enum_value, callback, "transaction_type_enum", 11, "Apply Interest", "Apply Interest");
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_currency = dataContext.getDefaultSchema().getTableByName("m_currency");
            dataContext.executeUpdate(callback -> {
                insert_m_currency(m_currency, callback, 1, "AED", 2, null, "UAE Dirham", "currency.AED");
                insert_m_currency(m_currency, callback, 2, "AFN", 2, null, "Afghanistan Afghani", "currency.AFN");
                insert_m_currency(m_currency, callback, 3, "ALL", 2, null, "Albanian Lek", "currency.ALL");
                insert_m_currency(m_currency, callback, 4, "AMD", 2, null, "Armenian Dram", "currency.AMD");
                insert_m_currency(m_currency, callback, 5, "ANG", 2, null, "Netherlands Antillian Guilder", "currency.ANG");
                insert_m_currency(m_currency, callback, 6, "AOA", 2, null, "Angolan Kwanza", "currency.AOA");
                insert_m_currency(m_currency, callback, 7, "ARS", 2, "$", "Argentine Peso", "currency.ARS");
                insert_m_currency(m_currency, callback, 8, "AUD", 2, "A$", "Australian Dollar", "currency.AUD");
                insert_m_currency(m_currency, callback, 9, "AWG", 2, null, "Aruban Guilder", "currency.AWG");
                insert_m_currency(m_currency, callback, 10, "AZM", 2, null, "Azerbaijanian Manat", "currency.AZM");
                insert_m_currency(m_currency, callback, 11, "BAM", 2, null, "Bosnia and Herzegovina Convertible Marks", "currency.BAM");
                insert_m_currency(m_currency, callback, 12, "BBD", 2, null, "Barbados Dollar", "currency.BBD");
                insert_m_currency(m_currency, callback, 13, "BDT", 2, null, "Bangladesh Taka", "currency.BDT");
                insert_m_currency(m_currency, callback, 14, "BGN", 2, null, "Bulgarian Lev", "currency.BGN");
                insert_m_currency(m_currency, callback, 15, "BHD", 3, null, "Bahraini Dinar", "currency.BHD");
                insert_m_currency(m_currency, callback, 16, "BIF", 0, null, "Burundi Franc", "currency.BIF");
                insert_m_currency(m_currency, callback, 17, "BMD", 2, null, "Bermudian Dollar", "currency.BMD");
                insert_m_currency(m_currency, callback, 18, "BND", 2, "B$", "Brunei Dollar", "currency.BND");
                insert_m_currency(m_currency, callback, 19, "BOB", 2, "Bs.", "Bolivian Boliviano", "currency.BOB");
                insert_m_currency(m_currency, callback, 20, "BRL", 2, "R$", "Brazilian Real", "currency.BRL");
                insert_m_currency(m_currency, callback, 21, "BSD", 2, null, "Bahamian Dollar", "currency.BSD");
                insert_m_currency(m_currency, callback, 22, "BTN", 2, null, "Bhutan Ngultrum", "currency.BTN");
                insert_m_currency(m_currency, callback, 23, "BWP", 2, null, "Botswana Pula", "currency.BWP");
                insert_m_currency(m_currency, callback, 24, "BYR", 0, null, "Belarussian Ruble", "currency.BYR");
                insert_m_currency(m_currency, callback, 25, "BZD", 2, "BZ$", "Belize Dollar", "currency.BZD");
                insert_m_currency(m_currency, callback, 26, "CAD", 2, null, "Canadian Dollar", "currency.CAD");
                insert_m_currency(m_currency, callback, 27, "CDF", 2, null, "Franc Congolais", "currency.CDF");
                insert_m_currency(m_currency, callback, 28, "CHF", 2, null, "Swiss Franc", "currency.CHF");
                insert_m_currency(m_currency, callback, 29, "CLP", 0, "$", "Chilean Peso", "currency.CLP");
                insert_m_currency(m_currency, callback, 30, "CNY", 2, null, "Chinese Yuan Renminbi", "currency.CNY");
                insert_m_currency(m_currency, callback, 31, "COP", 2, "$", "Colombian Peso", "currency.COP");
                insert_m_currency(m_currency, callback, 32, "CRC", 2, "₡", "Costa Rican Colon", "currency.CRC");
                insert_m_currency(m_currency, callback, 33, "CSD", 2, null, "Serbian Dinar", "currency.CSD");
                insert_m_currency(m_currency, callback, 34, "CUP", 2, "$MN", "Cuban Peso", "currency.CUP");
                insert_m_currency(m_currency, callback, 35, "CVE", 2, null, "Cape Verde Escudo", "currency.CVE");
                insert_m_currency(m_currency, callback, 36, "CYP", 2, null, "Cyprus Pound", "currency.CYP");
                insert_m_currency(m_currency, callback, 37, "CZK", 2, null, "Czech Koruna", "currency.CZK");
                insert_m_currency(m_currency, callback, 38, "DJF", 0, null, "Djibouti Franc", "currency.DJF");
                insert_m_currency(m_currency, callback, 39, "DKK", 2, null, "Danish Krone", "currency.DKK");
                insert_m_currency(m_currency, callback, 40, "DOP", 2, "RD$", "Dominican Peso", "currency.DOP");
                insert_m_currency(m_currency, callback, 41, "DZD", 2, null, "Algerian Dinar", "currency.DZD");
                insert_m_currency(m_currency, callback, 42, "EEK", 2, null, "Estonian Kroon", "currency.EEK");
                insert_m_currency(m_currency, callback, 43, "EGP", 2, null, "Egyptian Pound", "currency.EGP");
                insert_m_currency(m_currency, callback, 44, "ERN", 2, null, "Eritrea Nafka", "currency.ERN");
                insert_m_currency(m_currency, callback, 45, "ETB", 2, null, "Ethiopian Birr", "currency.ETB");
                insert_m_currency(m_currency, callback, 46, "EUR", 2, "€", "Euro", "currency.EUR");
                insert_m_currency(m_currency, callback, 47, "FJD", 2, null, "Fiji Dollar", "currency.FJD");
                insert_m_currency(m_currency, callback, 48, "FKP", 2, null, "Falkland Islands Pound", "currency.FKP");
                insert_m_currency(m_currency, callback, 49, "GBP", 2, null, "Pound Sterling", "currency.GBP");
                insert_m_currency(m_currency, callback, 50, "GEL", 2, null, "Georgian Lari", "currency.GEL");
                insert_m_currency(m_currency, callback, 51, "GHC", 2, "GHc", "Ghana Cedi", "currency.GHC");
                insert_m_currency(m_currency, callback, 52, "GIP", 2, null, "Gibraltar Pound", "currency.GIP");
                insert_m_currency(m_currency, callback, 53, "GMD", 2, null, "Gambian Dalasi", "currency.GMD");
                insert_m_currency(m_currency, callback, 54, "GNF", 0, null, "Guinea Franc", "currency.GNF");
                insert_m_currency(m_currency, callback, 55, "GTQ", 2, "Q", "Guatemala Quetzal", "currency.GTQ");
                insert_m_currency(m_currency, callback, 56, "GYD", 2, null, "Guyana Dollar", "currency.GYD");
                insert_m_currency(m_currency, callback, 57, "HKD", 2, null, "Hong Kong Dollar", "currency.HKD");
                insert_m_currency(m_currency, callback, 58, "HNL", 2, "L", "Honduras Lempira", "currency.HNL");
                insert_m_currency(m_currency, callback, 59, "HRK", 2, null, "Croatian Kuna", "currency.HRK");
                insert_m_currency(m_currency, callback, 60, "HTG", 2, "G", "Haiti Gourde", "currency.HTG");
                insert_m_currency(m_currency, callback, 61, "HUF", 2, null, "Hungarian Forint", "currency.HUF");
                insert_m_currency(m_currency, callback, 62, "IDR", 2, null, "Indonesian Rupiah", "currency.IDR");
                insert_m_currency(m_currency, callback, 63, "ILS", 2, null, "New Israeli Shekel", "currency.ILS");
                insert_m_currency(m_currency, callback, 64, "INR", 2, "₹", "Indian Rupee", "currency.INR");
                insert_m_currency(m_currency, callback, 65, "IQD", 3, null, "Iraqi Dinar", "currency.IQD");
                insert_m_currency(m_currency, callback, 66, "IRR", 2, null, "Iranian Rial", "currency.IRR");
                insert_m_currency(m_currency, callback, 67, "ISK", 0, null, "Iceland Krona", "currency.ISK");
                insert_m_currency(m_currency, callback, 68, "JMD", 2, null, "Jamaican Dollar", "currency.JMD");
                insert_m_currency(m_currency, callback, 69, "JOD", 3, null, "Jordanian Dinar", "currency.JOD");
                insert_m_currency(m_currency, callback, 70, "JPY", 0, null, "Japanese Yen", "currency.JPY");
                insert_m_currency(m_currency, callback, 71, "KES", 2, "KSh", "Kenyan Shilling", "currency.KES");
                insert_m_currency(m_currency, callback, 72, "KGS", 2, null, "Kyrgyzstan Som", "currency.KGS");
                insert_m_currency(m_currency, callback, 73, "KHR", 2, null, "Cambodia Riel", "currency.KHR");
                insert_m_currency(m_currency, callback, 74, "KMF", 0, null, "Comoro Franc", "currency.KMF");
                insert_m_currency(m_currency, callback, 75, "KPW", 2, null, "North Korean Won", "currency.KPW");
                insert_m_currency(m_currency, callback, 76, "KRW", 0, null, "Korean Won", "currency.KRW");
                insert_m_currency(m_currency, callback, 77, "KWD", 3, null, "Kuwaiti Dinar", "currency.KWD");
                insert_m_currency(m_currency, callback, 78, "KYD", 2, null, "Cayman Islands Dollar", "currency.KYD");
                insert_m_currency(m_currency, callback, 79, "KZT", 2, null, "Kazakhstan Tenge", "currency.KZT");
                insert_m_currency(m_currency, callback, 80, "LAK", 2, null, "Lao Kip", "currency.LAK");
                insert_m_currency(m_currency, callback, 81, "LBP", 2, "L£", "Lebanese Pound", "currency.LBP");
                insert_m_currency(m_currency, callback, 82, "LKR", 2, null, "Sri Lanka Rupee", "currency.LKR");
                insert_m_currency(m_currency, callback, 83, "LRD", 2, null, "Liberian Dollar", "currency.LRD");
                insert_m_currency(m_currency, callback, 84, "LSL", 2, null, "Lesotho Loti", "currency.LSL");
                insert_m_currency(m_currency, callback, 85, "LTL", 2, null, "Lithuanian Litas", "currency.LTL");
                insert_m_currency(m_currency, callback, 86, "LVL", 2, null, "Latvian Lats", "currency.LVL");
                insert_m_currency(m_currency, callback, 87, "LYD", 3, null, "Libyan Dinar", "currency.LYD");
                insert_m_currency(m_currency, callback, 88, "MAD", 2, null, "Moroccan Dirham", "currency.MAD");
                insert_m_currency(m_currency, callback, 89, "MDL", 2, null, "Moldovan Leu", "currency.MDL");
                insert_m_currency(m_currency, callback, 90, "MGA", 2, null, "Malagasy Ariary", "currency.MGA");
                insert_m_currency(m_currency, callback, 91, "MKD", 2, null, "Macedonian Denar", "currency.MKD");
                insert_m_currency(m_currency, callback, 92, "MMK", 2, "K", "Myanmar Kyat", "currency.MMK");
                insert_m_currency(m_currency, callback, 93, "MNT", 2, null, "Mongolian Tugrik", "currency.MNT");
                insert_m_currency(m_currency, callback, 94, "MOP", 2, null, "Macau Pataca", "currency.MOP");
                insert_m_currency(m_currency, callback, 95, "MRO", 2, null, "Mauritania Ouguiya", "currency.MRO");
                insert_m_currency(m_currency, callback, 96, "MTL", 2, null, "Maltese Lira", "currency.MTL");
                insert_m_currency(m_currency, callback, 97, "MUR", 2, null, "Mauritius Rupee", "currency.MUR");
                insert_m_currency(m_currency, callback, 98, "MVR", 2, null, "Maldives Rufiyaa", "currency.MVR");
                insert_m_currency(m_currency, callback, 99, "MWK", 2, null, "Malawi Kwacha", "currency.MWK");
                insert_m_currency(m_currency, callback, 100, "MXN", 2, "$", "Mexican Peso", "currency.MXN");
                insert_m_currency(m_currency, callback, 101, "MYR", 2, null, "Malaysian Ringgit", "currency.MYR");
                insert_m_currency(m_currency, callback, 102, "MZM", 2, null, "Mozambique Metical", "currency.MZM");
                insert_m_currency(m_currency, callback, 103, "NAD", 2, null, "Namibia Dollar", "currency.NAD");
                insert_m_currency(m_currency, callback, 104, "NGN", 2, null, "Nigerian Naira", "currency.NGN");
                insert_m_currency(m_currency, callback, 105, "NIO", 2, "C$", "Nicaragua Cordoba Oro", "currency.NIO");
                insert_m_currency(m_currency, callback, 106, "NOK", 2, null, "Norwegian Krone", "currency.NOK");
                insert_m_currency(m_currency, callback, 107, "NPR", 2, null, "Nepalese Rupee", "currency.NPR");
                insert_m_currency(m_currency, callback, 108, "NZD", 2, null, "New Zealand Dollar", "currency.NZD");
                insert_m_currency(m_currency, callback, 109, "OMR", 3, null, "Rial Omani", "currency.OMR");
                insert_m_currency(m_currency, callback, 110, "PAB", 2, "B/.", "Panama Balboa", "currency.PAB");
                insert_m_currency(m_currency, callback, 111, "PEN", 2, "S/.", "Peruvian Nuevo Sol", "currency.PEN");
                insert_m_currency(m_currency, callback, 112, "PGK", 2, null, "Papua New Guinea Kina", "currency.PGK");
                insert_m_currency(m_currency, callback, 113, "PHP", 2, null, "Philippine Peso", "currency.PHP");
                insert_m_currency(m_currency, callback, 114, "PKR", 2, null, "Pakistan Rupee", "currency.PKR");
                insert_m_currency(m_currency, callback, 115, "PLN", 2, null, "Polish Zloty", "currency.PLN");
                insert_m_currency(m_currency, callback, 116, "PYG", 0, "₲", "Paraguayan Guarani", "currency.PYG");
                insert_m_currency(m_currency, callback, 117, "QAR", 2, null, "Qatari Rial", "currency.QAR");
                insert_m_currency(m_currency, callback, 118, "RON", 2, null, "Romanian Leu", "currency.RON");
                insert_m_currency(m_currency, callback, 119, "RUB", 2, null, "Russian Ruble", "currency.RUB");
                insert_m_currency(m_currency, callback, 120, "RWF", 0, null, "Rwanda Franc", "currency.RWF");
                insert_m_currency(m_currency, callback, 121, "SAR", 2, null, "Saudi Riyal", "currency.SAR");
                insert_m_currency(m_currency, callback, 122, "SBD", 2, null, "Solomon Islands Dollar", "currency.SBD");
                insert_m_currency(m_currency, callback, 123, "SCR", 2, null, "Seychelles Rupee", "currency.SCR");
                insert_m_currency(m_currency, callback, 124, "SDD", 2, null, "Sudanese Dinar", "currency.SDD");
                insert_m_currency(m_currency, callback, 125, "SEK", 2, null, "Swedish Krona", "currency.SEK");
                insert_m_currency(m_currency, callback, 126, "SGD", 2, null, "Singapore Dollar", "currency.SGD");
                insert_m_currency(m_currency, callback, 127, "SHP", 2, null, "St Helena Pound", "currency.SHP");
                insert_m_currency(m_currency, callback, 128, "SIT", 2, null, "Slovenian Tolar", "currency.SIT");
                insert_m_currency(m_currency, callback, 129, "SKK", 2, null, "Slovak Koruna", "currency.SKK");
                insert_m_currency(m_currency, callback, 130, "SLL", 2, null, "Sierra Leone Leone", "currency.SLL");
                insert_m_currency(m_currency, callback, 131, "SOS", 2, null, "Somali Shilling", "currency.SOS");
                insert_m_currency(m_currency, callback, 132, "SRD", 2, null, "Surinam Dollar", "currency.SRD");
                insert_m_currency(m_currency, callback, 133, "STD", 2, null, "Sao Tome and Principe Dobra", "currency.STD");
                insert_m_currency(m_currency, callback, 134, "SVC", 2, null, "El Salvador Colon", "currency.SVC");
                insert_m_currency(m_currency, callback, 135, "SYP", 2, null, "Syrian Pound", "currency.SYP");
                insert_m_currency(m_currency, callback, 136, "SZL", 2, null, "Swaziland Lilangeni", "currency.SZL");
                insert_m_currency(m_currency, callback, 137, "THB", 2, null, "Thai Baht", "currency.THB");
                insert_m_currency(m_currency, callback, 138, "TJS", 2, null, "Tajik Somoni", "currency.TJS");
                insert_m_currency(m_currency, callback, 139, "TMM", 2, null, "Turkmenistan Manat", "currency.TMM");
                insert_m_currency(m_currency, callback, 140, "TND", 3, "DT", "Tunisian Dinar", "currency.TND");
                insert_m_currency(m_currency, callback, 141, "TOP", 2, null, "Tonga Pa'anga", "currency.TOP");
                insert_m_currency(m_currency, callback, 142, "TRY", 2, null, "Turkish Lira", "currency.TRY");
                insert_m_currency(m_currency, callback, 143, "TTD", 2, null, "Trinidad and Tobago Dollar", "currency.TTD");
                insert_m_currency(m_currency, callback, 144, "TWD", 2, null, "New Taiwan Dollar", "currency.TWD");
                insert_m_currency(m_currency, callback, 145, "TZS", 2, null, "Tanzanian Shilling", "currency.TZS");
                insert_m_currency(m_currency, callback, 146, "UAH", 2, null, "Ukraine Hryvnia", "currency.UAH");
                insert_m_currency(m_currency, callback, 147, "UGX", 2, "USh", "Uganda Shilling", "currency.UGX");
                insert_m_currency(m_currency, callback, 148, "USD", 2, "$", "US Dollar", "currency.USD");
                insert_m_currency(m_currency, callback, 149, "UYU", 2, "$U", "Peso Uruguayo", "currency.UYU");
                insert_m_currency(m_currency, callback, 150, "UZS", 2, null, "Uzbekistan Sum", "currency.UZS");
                insert_m_currency(m_currency, callback, 151, "VEB", 2, "Bs.F.", "Venezuelan Bolivar", "currency.VEB");
                insert_m_currency(m_currency, callback, 152, "VND", 2, null, "Vietnamese Dong", "currency.VND");
                insert_m_currency(m_currency, callback, 153, "VUV", 0, null, "Vanuatu Vatu", "currency.VUV");
                insert_m_currency(m_currency, callback, 154, "WST", 2, null, "Samoa Tala", "currency.WST");
                insert_m_currency(m_currency, callback, 155, "XAF", 0, null, "CFA Franc BEAC", "currency.XAF");
                insert_m_currency(m_currency, callback, 156, "XCD", 2, null, "East Caribbean Dollar", "currency.XCD");
                insert_m_currency(m_currency, callback, 157, "XDR", 5, null, "SDR (Special Drawing Rights)", "currency.XDR");
                insert_m_currency(m_currency, callback, 158, "XOF", 0, "CFA", "CFA Franc BCEAO", "currency.XOF");
                insert_m_currency(m_currency, callback, 159, "XPF", 0, null, "CFP Franc", "currency.XPF");
                insert_m_currency(m_currency, callback, 160, "YER", 2, null, "Yemeni Rial", "currency.YER");
                insert_m_currency(m_currency, callback, 161, "ZAR", 2, "R", "South African Rand", "currency.ZAR");
                insert_m_currency(m_currency, callback, 162, "ZMK", 2, null, "Zambian Kwacha", "currency.ZMK");
                insert_m_currency(m_currency, callback, 163, "ZWD", 2, null, "Zimbabwe Dollar", "currency.ZWD");
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_organisation_currency = dataContext.getDefaultSchema().getTableByName("m_organisation_currency");
            dataContext.executeUpdate(callback -> {
                insert_m_organisation_currency(m_organisation_currency, callback, 21, "USD", 2, "US Dollar", "$", "currency.USD");
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_office = dataContext.getDefaultSchema().getTableByName("m_office");
            dataContext.executeUpdate(callback -> {
                insert_m_office(m_office, callback, 1, null, ".", "1", "Head Office", "2009-01-01");

            });
        }
        {
            dataContext.refreshSchemas();
            Table m_group_level = dataContext.getDefaultSchema().getTableByName("m_group_level");
            dataContext.executeUpdate(callback -> {
                insert_m_group_level(m_group_level, callback, 1, null, 1, "Center", 1, 0);
                insert_m_group_level(m_group_level, callback, 2, "1", 0, "Group", 0, 1);
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_code = dataContext.getDefaultSchema().getTableByName("m_code");
            dataContext.executeUpdate(callback -> {
                insert_m_code(m_code, callback, "Customer Identifier", 1);
                insert_m_code(m_code, callback, "LoanCollateral", 1);
                insert_m_code(m_code, callback, "LoanPurpose", 1);
                insert_m_code(m_code, callback, "Gender", 1);
                insert_m_code(m_code, callback, "YesNo", 1);
                insert_m_code(m_code, callback, "GuarantorRelationship", 1);
            });
        }
        {
            dataContext.refreshSchemas();
            Table m_code_value = dataContext.getDefaultSchema().getTableByName("m_code_value");
            dataContext.executeUpdate(callback -> {
                long order_position = 0;
                long code_id = lookup_m_code(dataContext, "Customer Identifier");
                insert_m_code_value(m_code_value, callback, code_id, "Passport", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Id", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Drivers License", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Any Other Id Type", order_position++);
            });
            dataContext.executeUpdate(callback -> {
                long order_position = 0;
                long code_id = lookup_m_code(dataContext, "GuarantorRelationship");
                insert_m_code_value(m_code_value, callback, code_id, "Spouse", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Parent", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Sibling", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Business Associate", order_position++);
                insert_m_code_value(m_code_value, callback, code_id, "Other", order_position++);
            });
        }
    }

    protected void insert_m_code_value(Table table, RowInsertable callback, long code_id, String code_value, long order_position) {
        callback.insertInto(table)
                .value(table.getColumnByName("code_id"), code_id)
                .value(table.getColumnByName("code_value"), code_value)
                .value(table.getColumnByName("order_position"), order_position)
                .execute();
    }

    protected void insert_m_group_level(Table table, RowInsertable callback, long id, String parent_id, long super_parent, String level_name, long recursable, long can_have_clients) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("parent_id"), parent_id)
                .value(table.getColumnByName("super_parent"), super_parent)
                .value(table.getColumnByName("level_name"), level_name)
                .value(table.getColumnByName("recursable"), recursable)
                .value(table.getColumnByName("can_have_clients"), can_have_clients)
                .execute();
    }

    protected void insert_m_office(Table table, RowInsertable callback, long id, String parent_id, String hierarchy, String external_id, String name, String opening_date) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("parent_id"), parent_id)
                .value(table.getColumnByName("hierarchy"), hierarchy)
                .value(table.getColumnByName("external_id"), external_id)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("opening_date"), opening_date)
                .execute();
    }

    protected void insert_m_organisation_currency(Table table, RowInsertable callback, long id, String code, int decimal_places, String name, String display_symbol, String internationalized_name_code) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("code"), code)
                .value(table.getColumnByName("decimal_places"), decimal_places)
                .value(table.getColumnByName("display_symbol"), display_symbol)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("internationalized_name_code"), internationalized_name_code)
                .execute();
    }

    protected void insert_m_currency(Table table, RowInsertable callback, long id, String code, int decimal_places, String display_symbol, String name, String internationalized_name_code) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("code"), code)
                .value(table.getColumnByName("decimal_places"), decimal_places)
                .value(table.getColumnByName("display_symbol"), display_symbol)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("internationalized_name_code"), internationalized_name_code)
                .execute();
    }

    protected void insert_r_enum_value(Table table, RowInsertable callback, String enum_name, long enum_id, String enum_message_property, String enum_value) {
        callback.insertInto(table)
                .value(table.getColumnByName("enum_name"), enum_name)
                .value(table.getColumnByName("enum_id"), enum_id)
                .value(table.getColumnByName("enum_message_property"), enum_message_property)
                .value(table.getColumnByName("enum_value"), enum_value)
                .execute();
    }

    protected void insert_c_configuration(Table table, RowInsertable callback, String name, long enabled) {
        callback.insertInto(table)
                .value(table.getColumnByName("name"), name)
                .value(table.getColumnByName("enabled"), enabled)
                .execute();
    }

    protected void insert_ref_loan_transaction_processing_strategy(Table table, RowInsertable callback, long id, String code, String name) {
        callback.insertInto(table)
                .value(table.getColumnByName("id"), id)
                .value(table.getColumnByName("code"), code)
                .value(table.getColumnByName("name"), name)
                .execute();
    }

    protected void insert_m_code(Table table, RowInsertable callback, String code_name, long is_system_defined) {
        callback.insertInto(table)
                .value(table.getColumnByName("code_name"), code_name)
                .value(table.getColumnByName("is_system_defined"), is_system_defined)
                .execute();
    }

    protected int lookup_m_code(DataContext dataContext, String code_name) {
        Table table = dataContext.getDefaultSchema().getTableByName("m_code");
        try (DataSet rows = dataContext.query().from(table).select(table.getColumnByName("id")).where(table.getColumnByName("code_name")).eq(code_name).execute()) {
            rows.next();
            return (int) rows.getRow().getValue(0);
        }
    }
}