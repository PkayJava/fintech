package com.angkorteam.fintech.installation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.Constants;
import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.dto.Dropdown;
import com.angkorteam.fintech.dto.builder.ChargeBuilder;
import com.angkorteam.fintech.dto.builder.PaymentTypeBuilder;
import com.angkorteam.fintech.dto.builder.StaffBuilder;
import com.angkorteam.fintech.dto.builder.TellerBuilder;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.fintech.dto.enums.ChargeCalculation;
import com.angkorteam.fintech.dto.enums.ChargeFrequency;
import com.angkorteam.fintech.dto.enums.ChargePayment;
import com.angkorteam.fintech.dto.enums.ChargeTime;
import com.angkorteam.fintech.dto.enums.ChargeType;
import com.angkorteam.fintech.helper.ChargeHelper;
import com.angkorteam.fintech.helper.LoginHelper;
import com.angkorteam.fintech.helper.PaymentTypeHelper;
import com.angkorteam.fintech.helper.StaffHelper;
import com.angkorteam.fintech.helper.TellerHelper;
import com.angkorteam.fintech.junit.JUnit;
import com.angkorteam.fintech.junit.JUnitWicketTester;
import com.angkorteam.framework.spring.JdbcNamed;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class SampleData implements IMifos {

    private String token;

    private JUnitWicketTester wicket;

    private static final List<String> OFFICES = Lists.newArrayList();
    private static final List<String> CURRENCIES = Lists.newArrayList();
    private static final List<String> FUNDS = Lists.newArrayList();
    private static final List<String> PAYMENTS = Lists.newArrayList();
    private static final List<String> HOLIDAYS = Lists.newArrayList();
    private static final List<String> EMPLOYEES = Lists.newArrayList();
    private static final List<String> ACCOUNTS = Lists.newArrayList();
    private static final List<String> ACCOUNT_RULES = Lists.newArrayList();
    private static final List<String> PARAMS = Lists.newArrayList();
    private static final List<String> TAX_COMPONENTS = Lists.newArrayList();
    private static final List<String> TAX_GROUPS = Lists.newArrayList();
    private static final List<String> FLOATING_RATES = Lists.newArrayList();

    static {

        PARAMS.add(Dropdown.Customer_Identifier + "=>" + "Mr." + "=>" + "Mr.");
        PARAMS.add(Dropdown.Customer_Identifier + "=>" + "Miss." + "=>" + "Miss");
        PARAMS.add(Dropdown.Customer_Identifier + "=>" + "Mrs." + "=>" + "Mrs.");
        PARAMS.add(Dropdown.LoanCollateral + "=>" + "LoanCollateral01" + "=>" + "LoanCollateral01");
        PARAMS.add(Dropdown.LoanCollateral + "=>" + "LoanCollateral02" + "=>" + "LoanCollateral02");
        PARAMS.add(Dropdown.LoanPurpose + "=>" + "LoanPurpose01" + "=>" + "LoanPurpose01");
        PARAMS.add(Dropdown.LoanPurpose + "=>" + "LoanPurpose02" + "=>" + "LoanPurpose02");
        PARAMS.add(Dropdown.Gender + "=>" + "M" + "=>" + "Male");
        PARAMS.add(Dropdown.Gender + "=>" + "F" + "=>" + "Female");
        PARAMS.add(Dropdown.YesNo + "=>" + "Y" + "=>" + "Yes");
        PARAMS.add(Dropdown.YesNo + "=>" + "N" + "=>" + "No");
        PARAMS.add(Dropdown.GuarantorRelationship + "=>" + "GuarantorRelationship01" + "=>" + "GuarantorRelationship01");
        PARAMS.add(Dropdown.GuarantorRelationship + "=>" + "GuarantorRelationship02" + "=>" + "GuarantorRelationship02");
        PARAMS.add(Dropdown.AssetAccountTags + "=>" + "AssetAccountTags01" + "=>" + "AssetAccountTags01");
        PARAMS.add(Dropdown.AssetAccountTags + "=>" + "AssetAccountTags02" + "=>" + "AssetAccountTags02");
        PARAMS.add(Dropdown.LiabilityAccountTags + "=>" + "LiabilityAccountTags01" + "=>" + "LiabilityAccountTags01");
        PARAMS.add(Dropdown.LiabilityAccountTags + "=>" + "LiabilityAccountTags02" + "=>" + "LiabilityAccountTags02");
        PARAMS.add(Dropdown.EquityAccountTags + "=>" + "EquityAccountTags01" + "=>" + "EquityAccountTags01");
        PARAMS.add(Dropdown.EquityAccountTags + "=>" + "EquityAccountTags02" + "=>" + "EquityAccountTags02");
        PARAMS.add(Dropdown.IncomeAccountTags + "=>" + "IncomeAccountTags01" + "=>" + "IncomeAccountTags01");
        PARAMS.add(Dropdown.IncomeAccountTags + "=>" + "IncomeAccountTags02" + "=>" + "IncomeAccountTags02");
        PARAMS.add(Dropdown.ExpenseAccountTags + "=>" + "ExpenseAccountTags01" + "=>" + "ExpenseAccountTags01");
        PARAMS.add(Dropdown.ExpenseAccountTags + "=>" + "ExpenseAccountTags02" + "=>" + "ExpenseAccountTags02");
        PARAMS.add(Dropdown.GroupRole + "=>" + "GroupRole01" + "=>" + "GroupRole01");
        PARAMS.add(Dropdown.GroupRole + "=>" + "GroupRole02" + "=>" + "GroupRole02");
        PARAMS.add(Dropdown.ClientClosureReason + "=>" + "ClientClosureReason01" + "=>" + "ClientClosureReason01");
        PARAMS.add(Dropdown.ClientClosureReason + "=>" + "ClientClosureReason02" + "=>" + "ClientClosureReason02");
        PARAMS.add(Dropdown.GroupClosureReason + "=>" + "GroupClosureReason01" + "=>" + "GroupClosureReason01");
        PARAMS.add(Dropdown.GroupClosureReason + "=>" + "GroupClosureReason02" + "=>" + "GroupClosureReason02");
        PARAMS.add(Dropdown.ClientType + "=>" + "ClientType01" + "=>" + "ClientType01");
        PARAMS.add(Dropdown.ClientType + "=>" + "ClientType02" + "=>" + "ClientType02");
        PARAMS.add(Dropdown.ClientClassification + "=>" + "ClientClassification01" + "=>" + "ClientClassification01");
        PARAMS.add(Dropdown.ClientClassification + "=>" + "ClientClassification02" + "=>" + "ClientClassification02");
        PARAMS.add(Dropdown.ClientSubStatus + "=>" + "ClientSubStatus01" + "=>" + "ClientSubStatus01");
        PARAMS.add(Dropdown.ClientSubStatus + "=>" + "ClientSubStatus02" + "=>" + "ClientSubStatus02");
        PARAMS.add(Dropdown.ClientRejectReason + "=>" + "ClientRejectReason01" + "=>" + "ClientRejectReason01");
        PARAMS.add(Dropdown.ClientRejectReason + "=>" + "ClientRejectReason02" + "=>" + "ClientRejectReason02");
        PARAMS.add(Dropdown.ClientWithdrawReason + "=>" + "ClientWithdrawReason01" + "=>" + "ClientWithdrawReason01");
        PARAMS.add(Dropdown.ClientWithdrawReason + "=>" + "ClientWithdrawReason02" + "=>" + "ClientWithdrawReason02");
        PARAMS.add(Dropdown.EntityToEntityAccessTypes + "=>" + "EntityToEntityAccessTypes01" + "=>" + "EntityToEntityAccessTypes01");
        PARAMS.add(Dropdown.EntityToEntityAccessTypes + "=>" + "EntityToEntityAccessTypes02" + "=>" + "EntityToEntityAccessTypes02");
        PARAMS.add(Dropdown.CenterClosureReason + "=>" + "CenterClosureReason01" + "=>" + "CenterClosureReason01");
        PARAMS.add(Dropdown.CenterClosureReason + "=>" + "CenterClosureReason02" + "=>" + "CenterClosureReason02");
        PARAMS.add(Dropdown.LoanRescheduleReason + "=>" + "LoanRescheduleReason01" + "=>" + "LoanRescheduleReason01");
        PARAMS.add(Dropdown.LoanRescheduleReason + "=>" + "LoanRescheduleReason02" + "=>" + "LoanRescheduleReason02");
        PARAMS.add(Dropdown.Constitution + "=>" + "Constitution01" + "=>" + "Constitution01");
        PARAMS.add(Dropdown.Constitution + "=>" + "Constitution02" + "=>" + "Constitution02");
        PARAMS.add(Dropdown.MainBusinessLine + "=>" + "MainBusinessLine01" + "=>" + "MainBusinessLine01");
        PARAMS.add(Dropdown.MainBusinessLine + "=>" + "MainBusinessLine02" + "=>" + "MainBusinessLine02");
        PARAMS.add(Dropdown.WriteOffReasons + "=>" + "WriteOffReasons01" + "=>" + "WriteOffReasons01");
        PARAMS.add(Dropdown.WriteOffReasons + "=>" + "WriteOffReasons02" + "=>" + "WriteOffReasons02");
        PARAMS.add(Dropdown.State + "=>" + "State01" + "=>" + "State01");
        PARAMS.add(Dropdown.State + "=>" + "State02" + "=>" + "State02");
        PARAMS.add(Dropdown.Country + "=>" + "Country01" + "=>" + "Country01");
        PARAMS.add(Dropdown.Country + "=>" + "Country02" + "=>" + "Country02");
        PARAMS.add(Dropdown.AddressType + "=>" + "AddressType01" + "=>" + "AddressType01");
        PARAMS.add(Dropdown.AddressType + "=>" + "AddressType02" + "=>" + "AddressType02");
        PARAMS.add(Dropdown.MaritalStatus + "=>" + "MaritalStatus01" + "=>" + "MaritalStatus01");
        PARAMS.add(Dropdown.MaritalStatus + "=>" + "MaritalStatus02" + "=>" + "MaritalStatus02");
        PARAMS.add(Dropdown.Profession + "=>" + "Profession01" + "=>" + "Profession01");
        PARAMS.add(Dropdown.Profession + "=>" + "Profession02" + "=>" + "Profession02");
        PARAMS.add(Dropdown.Relationship + "=>" + "Relationship01" + "=>" + "Relationship01");
        PARAMS.add(Dropdown.Relationship + "=>" + "Relationship02" + "=>" + "Relationship02");

        OFFICES.add("Banteay Meanchey");
        OFFICES.add("Battambang");
        OFFICES.add("Kampong Cham");
        OFFICES.add("Kampong Chhnang");
        OFFICES.add("Kampong Speu");
        OFFICES.add("Tboung Khmum");
        OFFICES.add("Preah Sihanouk");
        OFFICES.add("Phnom Penh");
        OFFICES.add("Pailin");
        OFFICES.add("Kep");
        OFFICES.add("Takéo");
        OFFICES.add("Svay Rieng");
        OFFICES.add("Stung Treng");
        OFFICES.add("Siem Reap");
        OFFICES.add("Ratanakiri");
        OFFICES.add("Prey Veng");
        OFFICES.add("Pursat");
        OFFICES.add("Preah Vihear");
        OFFICES.add("Oddar Meanchey");
        OFFICES.add("Mondulkiri");
        OFFICES.add("Kratié");
        OFFICES.add("Koh Kong");
        OFFICES.add("Kandal");
        OFFICES.add("Kampot");
        OFFICES.add("Kampong Thom");

        CURRENCIES.add("USD");
        CURRENCIES.add("VND");
        CURRENCIES.add("KHR");
        CURRENCIES.add("MYR");
        CURRENCIES.add("SGD");
        CURRENCIES.add("THB");

        FUNDS.add("Asian Fund");
        FUNDS.add("World Bank Fund");
        FUNDS.add("Agriculture Fund");
        FUNDS.add("Startup Fund");
        FUNDS.add("Small Loan Fund");
        FUNDS.add("Education Fund");
        FUNDS.add("Housing Fund");

        PAYMENTS.add("Cash=>true");
        PAYMENTS.add("VISA=>false");
        PAYMENTS.add("Mastercard=>false");
        PAYMENTS.add("Cheque=>false");
        PAYMENTS.add("American Express=>false");
        PAYMENTS.add("JCB=>false");

        // NAME->FROM->TO->RESCHEDULED
        HOLIDAYS.add("International New Year Day (2007)=>2017-01-01=>2017-01-01=>2017-01-02");
        HOLIDAYS.add("Victory over Genocide Day (2007)=>2017-01-07=>2017-01-07=>2017-01-09");
        HOLIDAYS.add("Meak Bochea Day (2007)=>2017-02-11=>2017-02-11=>2017-02-13");
        HOLIDAYS.add("International Women's Day (2007)=>2017-03-08=>2017-03-08=>2017-03-09");
        HOLIDAYS.add("Khmer New Year Day (2007)=>2017-04-14=>2017-04-16=>2017-04-17");
        HOLIDAYS.add("International Labor Day (2007)=>2017-05-01=>2017-05-01=>2017-05-02");
        HOLIDAYS.add("Visak Bochea Day (2007)=>2017-05-10=>2017-05-10=>2017-05-11");
        HOLIDAYS.add("King's Birthday, Norodom Sihamoni (2007)=>2017-05-13=>2017-05-15=>2017-05-16");
        HOLIDAYS.add("International Children Day (2007)=>2017-07-01=>2017-07-01=>2017-07-03");
        HOLIDAYS.add("King's Mother Birthday, Norodom Monineath Sihanouk (2007)=>2017-07-18=>2017-07-18=>2017-07-19");
        HOLIDAYS.add("Pchum Ben Day (2007)=>2017-09-19=>2017-09-21=>2017-09-22");
        HOLIDAYS.add("Constitutional Day (2007)=>2017-09-24=>2017-09-24=>2017-09-25");
        HOLIDAYS.add("Commemoration Day of King's Father, Norodom Sihanouk (2007)=>2017-10-15=>2017-10-15=>2017-10-16");
        HOLIDAYS.add("Anniversary of the Paris Peace Accord (2007)=>2017-10-23=>2017-10-23=>2017-10-24");
        HOLIDAYS.add("King's Coronation Day, Norodom Sihamoni (2007)=>2017-10-29=>2017-10-29=>2017-10-30");
        HOLIDAYS.add("Water Festival Ceremony (2007)=>2017-11-02=>2017-11-04=>2017-11-06");
        HOLIDAYS.add("Independence Day (2007)=>2017-11-09=>2017-11-09=>2017-11-10");
        HOLIDAYS.add("International Human Rights Day (2007)=>2017-12-10=>2017-12-10=>2017-12-11");

        EMPLOYEES.add("Abbie Fernando");
        EMPLOYEES.add("Adela Angeles");
        EMPLOYEES.add("Adela Colberg");
        EMPLOYEES.add("Adelia Gilpin");
        EMPLOYEES.add("Adena Shorey");
        EMPLOYEES.add("Adria Stelzer");
        EMPLOYEES.add("Agustin Englert");
        EMPLOYEES.add("Akiko Garduno");
        EMPLOYEES.add("Alana Gabriele");
        EMPLOYEES.add("Alene Kellogg");
        EMPLOYEES.add("Alexia Gettinger");
        EMPLOYEES.add("Alfred Malpass");
        EMPLOYEES.add("Alfredo Wilkes");
        EMPLOYEES.add("Aline Borrego");
        EMPLOYEES.add("Alvina Honey");
        EMPLOYEES.add("Ami Shivers");
        EMPLOYEES.add("An Mccomb");
        EMPLOYEES.add("Andra Vanguilder");
        EMPLOYEES.add("Andrew Alvidrez");
        EMPLOYEES.add("Anja Dickman");
        EMPLOYEES.add("Annemarie Elsass");
        EMPLOYEES.add("Apryl Mayen");
        EMPLOYEES.add("Arnulfo Alger");
        EMPLOYEES.add("Aron Berggren");
        EMPLOYEES.add("Arvilla Carbo");
        EMPLOYEES.add("Ashly Depaolo");
        EMPLOYEES.add("Austin Salva");
        EMPLOYEES.add("Bailey Vanderpool");
        EMPLOYEES.add("Bari Godby");
        EMPLOYEES.add("Barry Lollis");
        EMPLOYEES.add("Belia Mackay");
        EMPLOYEES.add("Benjamin Beaupre");
        EMPLOYEES.add("Benny Beauchamp");
        EMPLOYEES.add("Berna Spinner");
        EMPLOYEES.add("Bess Bruning");
        EMPLOYEES.add("Betsey Marinez");
        EMPLOYEES.add("Betsy Munsey");
        EMPLOYEES.add("Bettyann Persinger");
        EMPLOYEES.add("Bev Foucher");
        EMPLOYEES.add("Bianca Stricker");
        EMPLOYEES.add("Bill Sizemore");
        EMPLOYEES.add("Breanne Tiner");
        EMPLOYEES.add("Brenna Mcmickle");
        EMPLOYEES.add("Brianna Moris");
        EMPLOYEES.add("Bridget Mccranie");
        EMPLOYEES.add("Brigid Evelyn");
        EMPLOYEES.add("Bryon Izzard");
        EMPLOYEES.add("Bryon Mcmickle");
        EMPLOYEES.add("Burma Bitton");
        EMPLOYEES.add("Caitlin Henderson");
        EMPLOYEES.add("Calista Hursey");
        EMPLOYEES.add("Cameron Pribble");
        EMPLOYEES.add("Caridad Kleine");
        EMPLOYEES.add("Carisa Snyder");
        EMPLOYEES.add("Carlena Angstadt");
        EMPLOYEES.add("Carlita Hooser");
        EMPLOYEES.add("Carlos Simonson");
        EMPLOYEES.add("Carolyne Kyser");
        EMPLOYEES.add("Carrol Estepp");
        EMPLOYEES.add("Celeste Babich");
        EMPLOYEES.add("Celina Ruple");
        EMPLOYEES.add("Celinda Welte");
        EMPLOYEES.add("Cesar Sandberg");
        EMPLOYEES.add("Chanell Espinosa");
        EMPLOYEES.add("Chara Hartt");
        EMPLOYEES.add("Charlena Coombes");
        EMPLOYEES.add("Charmain Ferro");
        EMPLOYEES.add("Chastity Grosch");
        EMPLOYEES.add("Cherie Larson");
        EMPLOYEES.add("Chester Bryden");
        EMPLOYEES.add("Chester Sliger");
        EMPLOYEES.add("Chia Bastian");
        EMPLOYEES.add("Ching Elsey");
        EMPLOYEES.add("Chiquita Doughtie");
        EMPLOYEES.add("Christin Neville");
        EMPLOYEES.add("Cinthia Sutton");
        EMPLOYEES.add("Clarice Toole");
        EMPLOYEES.add("Claudie Bozeman");
        EMPLOYEES.add("Clementina Vangilder");
        EMPLOYEES.add("Colene Shankles");
        EMPLOYEES.add("Contessa Carlos");
        EMPLOYEES.add("Cordell Seyfried");
        EMPLOYEES.add("Cordell Weekes");
        EMPLOYEES.add("Corinna Searle");
        EMPLOYEES.add("Coy Ratley");
        EMPLOYEES.add("Creola Mortimer");
        EMPLOYEES.add("Cristobal Wilker");
        EMPLOYEES.add("Daniele Reichenbach");
        EMPLOYEES.add("Dante Drayton");
        EMPLOYEES.add("Darcel Vivanco");
        EMPLOYEES.add("Dario Thurmon");
        EMPLOYEES.add("Darline Rentas");
        EMPLOYEES.add("Daron Shrout");
        EMPLOYEES.add("Deandra Cunha");
        EMPLOYEES.add("Deandra Johns");
        EMPLOYEES.add("Deandre Imai");
        EMPLOYEES.add("Deedee Schenck");
        EMPLOYEES.add("Deedra Veatch");
        EMPLOYEES.add("Deidre Rhoton");
        EMPLOYEES.add("Delois Montague");
        EMPLOYEES.add("Denae Pickert");
        EMPLOYEES.add("Deonna Spoor");
        EMPLOYEES.add("Despina Dover");
        EMPLOYEES.add("Dessie Zelaya");
        EMPLOYEES.add("Destiny Hammer");
        EMPLOYEES.add("Dia Ivory");
        EMPLOYEES.add("Diedra Flagg");
        EMPLOYEES.add("Diego Alvino");
        EMPLOYEES.add("Dina Lockridge");
        EMPLOYEES.add("Dino Peele");
        EMPLOYEES.add("Divina Sanjuan");
        EMPLOYEES.add("Dolores Belvin");
        EMPLOYEES.add("Donita Stanbery");
        EMPLOYEES.add("Dorene Stegall");
        EMPLOYEES.add("Dorine Calderon");
        EMPLOYEES.add("Drema Shellman");
        EMPLOYEES.add("Drucilla Tomasi");
        EMPLOYEES.add("Duane Trout");
        EMPLOYEES.add("Dung Adamski");
        EMPLOYEES.add("Earnest Vannorman");
        EMPLOYEES.add("Easter Dibble");
        EMPLOYEES.add("Eboni Morin");
        EMPLOYEES.add("Edgar Mossman");
        EMPLOYEES.add("Eduardo Debruyn");
        EMPLOYEES.add("Eleanora Harte");
        EMPLOYEES.add("Elfrieda Doan");
        EMPLOYEES.add("Elfrieda Vanish");
        EMPLOYEES.add("Elina Litherland");
        EMPLOYEES.add("Eliza Ochsner");
        EMPLOYEES.add("Ellena Vandusen");
        EMPLOYEES.add("Elsie Everest");
        EMPLOYEES.add("Elvis Alger");
        EMPLOYEES.add("Elwanda Steil");
        EMPLOYEES.add("Ema Nicolosi");
        EMPLOYEES.add("Emmie Oltman");
        EMPLOYEES.add("Erich Dampier");
        EMPLOYEES.add("Erick Piasecki");
        EMPLOYEES.add("Ernestine Cory");
        EMPLOYEES.add("Esmeralda Bruns");
        EMPLOYEES.add("Estela Carrasco");
        EMPLOYEES.add("Evelin Meissner");
        EMPLOYEES.add("Evelyn Toone");
        EMPLOYEES.add("Evelynn Almanza");
        EMPLOYEES.add("Evonne Cheyne");
        EMPLOYEES.add("Fay Eckler");
        EMPLOYEES.add("Felice Yokum");
        EMPLOYEES.add("Felisha Meis");
        EMPLOYEES.add("Florentina Keisler");
        EMPLOYEES.add("Florentino Villafane");
        EMPLOYEES.add("Florinda Mitts");
        EMPLOYEES.add("Fonda Monge");
        EMPLOYEES.add("Francesco Spoor");
        EMPLOYEES.add("Frida Dowdy");
        EMPLOYEES.add("Garnet Doby");
        EMPLOYEES.add("Gavin Scanlon");
        EMPLOYEES.add("Gaynelle Mcintire");
        EMPLOYEES.add("Gena Duron");
        EMPLOYEES.add("Gia Swigert");
        EMPLOYEES.add("Gilberte Kelch");
        EMPLOYEES.add("Gilma Winn");
        EMPLOYEES.add("Glen Seyal");
        EMPLOYEES.add("Grady Castellano");
        EMPLOYEES.add("Guillermo Szymanski");
        EMPLOYEES.add("Gwen Plamondon");
        EMPLOYEES.add("Hae Novello");
        EMPLOYEES.add("Hai Whitesell");
        EMPLOYEES.add("Harley Wickliffe");
        EMPLOYEES.add("Harmony Branson");
        EMPLOYEES.add("Harrison Shadwick");
        EMPLOYEES.add("Haywood Maloy");
        EMPLOYEES.add("Heike Dekker");
        EMPLOYEES.add("Helga Domenico");
        EMPLOYEES.add("Heriberto Mulholland");
        EMPLOYEES.add("Hermelinda Selig");
        EMPLOYEES.add("Hildegard Tuck");
        EMPLOYEES.add("Hoyt Pauls");
        EMPLOYEES.add("Hyo Oakley");
        EMPLOYEES.add("Ilana Maltos");
        EMPLOYEES.add("Iona Cribb");
        EMPLOYEES.add("Iona Muncie");
        EMPLOYEES.add("Irwin Sears");
        EMPLOYEES.add("Ismael Braz");
        EMPLOYEES.add("Ivy Isom");
        EMPLOYEES.add("Jacelyn Kriss");
        EMPLOYEES.add("Jackeline Wilding");
        EMPLOYEES.add("Jacques Brissette");
        EMPLOYEES.add("Jamaal Tozier");
        EMPLOYEES.add("Janie Slye");
        EMPLOYEES.add("Jaqueline Cecil");
        EMPLOYEES.add("Jasmin Maggard");
        EMPLOYEES.add("Jazmin Ostrowski");
        EMPLOYEES.add("Jean Gassaway");
        EMPLOYEES.add("Jeannette Laracuente");
        EMPLOYEES.add("Jen Mcnear");
        EMPLOYEES.add("Jenell Trahan");
        EMPLOYEES.add("Jeremiah Stratford");
        EMPLOYEES.add("Jerrold Beville");
        EMPLOYEES.add("Jina Sanderson");
        EMPLOYEES.add("Jodi Merry");
        EMPLOYEES.add("Joi Brunson");
        EMPLOYEES.add("Joni Bartel");
        EMPLOYEES.add("Josie Fancher");
        EMPLOYEES.add("Joslyn Demott");
        EMPLOYEES.add("Jospeh Witherite");
        EMPLOYEES.add("Juan Yarborough");
        EMPLOYEES.add("Juliana Walsh");
        EMPLOYEES.add("Julieann Wingo");
        EMPLOYEES.add("June Canavan");
        EMPLOYEES.add("Ka Dahm");
        EMPLOYEES.add("Kandis Berrios");
        EMPLOYEES.add("Karan Ohair");
        EMPLOYEES.add("Karen Bickett");
        EMPLOYEES.add("Karina Schiele");
        EMPLOYEES.add("Karma Haverland");
        EMPLOYEES.add("Karon Ahrens");
        EMPLOYEES.add("Karyl Langner");
        EMPLOYEES.add("Katelynn Lindeman");
        EMPLOYEES.add("Kathy Gaver");
        EMPLOYEES.add("Kayce Morquecho");
        EMPLOYEES.add("Kayla Bomberger");
        EMPLOYEES.add("Kayleigh Nishimura");
        EMPLOYEES.add("Keenan Hollifield");
        EMPLOYEES.add("Kendall Shoaf");
        EMPLOYEES.add("Kenton Ko");
        EMPLOYEES.add("Kerri Mccraw");
        EMPLOYEES.add("Kiana Brehm");
        EMPLOYEES.add("Kiersten Reny");
        EMPLOYEES.add("Kimbery Hunnicutt");
        EMPLOYEES.add("Kira Kung");
        EMPLOYEES.add("Kristel Enlow");
        EMPLOYEES.add("Krystyna Shockey");
        EMPLOYEES.add("Kyoko Bockman");
        EMPLOYEES.add("Lacresha Thon");
        EMPLOYEES.add("Ladawn Liu");
        EMPLOYEES.add("Lai Krom");
        EMPLOYEES.add("Lai Wilmes");
        EMPLOYEES.add("Laine Lieser");
        EMPLOYEES.add("Lakia Worman");
        EMPLOYEES.add("Lakita Dowd");
        EMPLOYEES.add("Lanny Jury");
        EMPLOYEES.add("Larry Burton");
        EMPLOYEES.add("Lashawnda Stancil");
        EMPLOYEES.add("Lashon Fennessey");
        EMPLOYEES.add("Latarsha Hayden");
        EMPLOYEES.add("Latia Teti");
        EMPLOYEES.add("Latoya Nolen");
        EMPLOYEES.add("Leatha Zirbel");
        EMPLOYEES.add("Leda Maggio");
        EMPLOYEES.add("Lenard Dyck");
        EMPLOYEES.add("Leonarda Chand");
        EMPLOYEES.add("Leonel Weissinger");
        EMPLOYEES.add("Letisha Zakrzewski");
        EMPLOYEES.add("Lettie Slaughter");
        EMPLOYEES.add("Liana Barnes");
        EMPLOYEES.add("Librada Drolet");
        EMPLOYEES.add("Lien Hankin");
        EMPLOYEES.add("Lindsay Gervasi");
        EMPLOYEES.add("Linn Donlon");
        EMPLOYEES.add("Lita Holaway");
        EMPLOYEES.add("Liza Kesinger");
        EMPLOYEES.add("Lona Gullett");
        EMPLOYEES.add("Lonnie Absher");
        EMPLOYEES.add("Lora Lefevre");
        EMPLOYEES.add("Loreen Valverde");
        EMPLOYEES.add("Lorena Weil");
        EMPLOYEES.add("Loria Detty");
        EMPLOYEES.add("Lorinda Kober");
        EMPLOYEES.add("Lorinda Landers");
        EMPLOYEES.add("Lory Andreasen");
        EMPLOYEES.add("Lottie Weitzel");
        EMPLOYEES.add("Louvenia Copes");
        EMPLOYEES.add("Love Sabella");
        EMPLOYEES.add("Lowell Knuckles");
        EMPLOYEES.add("Luanne Abdalla");
        EMPLOYEES.add("Lucienne Enz");
        EMPLOYEES.add("Lucrecia Euell");
        EMPLOYEES.add("Luisa Sabala");
        EMPLOYEES.add("Lupita Wey");
        EMPLOYEES.add("Lyn Grell");
        EMPLOYEES.add("Lynetta Roots");
        EMPLOYEES.add("Lynwood Mullin");
        EMPLOYEES.add("Machelle Hinchey");
        EMPLOYEES.add("Maddie Tynan");
        EMPLOYEES.add("Mafalda Cayton");
        EMPLOYEES.add("Maira Stadler");
        EMPLOYEES.add("Majorie Joly");
        EMPLOYEES.add("Makeda Oehler");
        EMPLOYEES.add("Mandie Wittig");
        EMPLOYEES.add("Mara Bonfiglio");
        EMPLOYEES.add("Marc Digby");
        EMPLOYEES.add("Marcela Surace");
        EMPLOYEES.add("Marg Hilt");
        EMPLOYEES.add("Marge Biron");
        EMPLOYEES.add("Marianna Renna");
        EMPLOYEES.add("Maribel Barberio");
        EMPLOYEES.add("Marina Crooker");
        EMPLOYEES.add("Marlys Naab");
        EMPLOYEES.add("Marquetta Thompkins");
        EMPLOYEES.add("Marquitta Burd");
        EMPLOYEES.add("Maryanne Zambrana");
        EMPLOYEES.add("Maryellen Holleran");
        EMPLOYEES.add("Matilde Fillion");
        EMPLOYEES.add("Maxwell Oba");
        EMPLOYEES.add("May Freudenthal");
        EMPLOYEES.add("Mei Andujar");
        EMPLOYEES.add("Melony Bodiford");
        EMPLOYEES.add("Melvin Monica");
        EMPLOYEES.add("Michele Burrier");
        EMPLOYEES.add("Milan Mencer");
        EMPLOYEES.add("Milissa Muldrew");
        EMPLOYEES.add("Mirella Stoneman");
        EMPLOYEES.add("Misti Karg");
        EMPLOYEES.add("Moon Leaman");
        EMPLOYEES.add("Nadia Hogg");
        EMPLOYEES.add("Nannie Mccafferty");
        EMPLOYEES.add("Natalie Cousin");
        EMPLOYEES.add("Natasha Blackstone");
        EMPLOYEES.add("Nella Gaskin");
        EMPLOYEES.add("Ngoc Merrell");
        EMPLOYEES.add("Nia Hollabaugh");
        EMPLOYEES.add("Nia Vaugh");
        EMPLOYEES.add("Nicky Heer");
        EMPLOYEES.add("Nida Cajigas");
        EMPLOYEES.add("Nida Staub");
        EMPLOYEES.add("Nieves Schneider");
        EMPLOYEES.add("Nieves Stfleur");
        EMPLOYEES.add("Nigel Gittens");
        EMPLOYEES.add("Noah Tomblin");
        EMPLOYEES.add("Norma Hines");
        EMPLOYEES.add("Odelia Pifer");
        EMPLOYEES.add("Olga Kamerer");
        EMPLOYEES.add("Oliver Omarah");
        EMPLOYEES.add("Omer Vittetoe");
        EMPLOYEES.add("Oneida Cardon");
        EMPLOYEES.add("Onie Berry");
        EMPLOYEES.add("Oretha Derrico");
        EMPLOYEES.add("Otelia Graybill");
        EMPLOYEES.add("Ouida Dansie");
        EMPLOYEES.add("Owen Deem");
        EMPLOYEES.add("Palma Lomonaco");
        EMPLOYEES.add("Palmira Chavers");
        EMPLOYEES.add("Patience Baze");
        EMPLOYEES.add("Patrick Oneil");
        EMPLOYEES.add("Paulita Grippo");
        EMPLOYEES.add("Penni Lauder");
        EMPLOYEES.add("Penni Water");
        EMPLOYEES.add("Phebe Perillo");
        EMPLOYEES.add("Pok Weisser");
        EMPLOYEES.add("Porsha Lavallie");
        EMPLOYEES.add("Raeann Mahaney");
        EMPLOYEES.add("Randee Stifter");
        EMPLOYEES.add("Raymundo Ference");
        EMPLOYEES.add("Reid Harrison");
        EMPLOYEES.add("Renaldo Kato");
        EMPLOYEES.add("Renato Keaton");
        EMPLOYEES.add("Rene Normandin");
        EMPLOYEES.add("Renee Caesar");
        EMPLOYEES.add("Renna Moudy");
        EMPLOYEES.add("Robbyn Rall");
        EMPLOYEES.add("Robert Paille");
        EMPLOYEES.add("Robin Poll");
        EMPLOYEES.add("Rosalba Streeter");
        EMPLOYEES.add("Rosamaria Dedrick");
        EMPLOYEES.add("Rosana Mudge");
        EMPLOYEES.add("Rosanna Dieter");
        EMPLOYEES.add("Roxie Selvage");
        EMPLOYEES.add("Ruthann Laubscher");
        EMPLOYEES.add("Ruthie Carlsen");
        EMPLOYEES.add("Ryan Hoeft");
        EMPLOYEES.add("Ryann Millar");
        EMPLOYEES.add("Sammie Malick");
        EMPLOYEES.add("Santos Palomba");
        EMPLOYEES.add("Sarai Lupton");
        EMPLOYEES.add("Saturnina Look");
        EMPLOYEES.add("Season Mcphillips");
        EMPLOYEES.add("Senaida Mumm");
        EMPLOYEES.add("Serafina Walberg");
        EMPLOYEES.add("Shakira Tennyson");
        EMPLOYEES.add("Shandi Twigg");
        EMPLOYEES.add("Shanti Ochsner");
        EMPLOYEES.add("Shaquana Sison");
        EMPLOYEES.add("Sharan Ricketts");
        EMPLOYEES.add("Sharla Nester");
        EMPLOYEES.add("Sharlene Doyle");
        EMPLOYEES.add("Sharmaine Royalty");
        EMPLOYEES.add("Sharolyn Connelly");
        EMPLOYEES.add("Shawanna Altman");
        EMPLOYEES.add("Shellie Fairman");
        EMPLOYEES.add("Shelly Manahan");
        EMPLOYEES.add("Shemeka Books");
        EMPLOYEES.add("Sherell Wegner");
        EMPLOYEES.add("Sherlene Flanary");
        EMPLOYEES.add("Sherman Sherwood");
        EMPLOYEES.add("Sheryll Greene");
        EMPLOYEES.add("Shila Mcbroom");
        EMPLOYEES.add("Sina Sen");
        EMPLOYEES.add("Slyvia Storch");
        EMPLOYEES.add("Socorro Manzano");
        EMPLOYEES.add("Solomon Judkins");
        EMPLOYEES.add("Sophia Moak");
        EMPLOYEES.add("Stanton Seavey");
        EMPLOYEES.add("Stepanie Letchworth");
        EMPLOYEES.add("Stuart Sasser");
        EMPLOYEES.add("Sueann Garrels");
        EMPLOYEES.add("Suzanne Sylvester");
        EMPLOYEES.add("Syble Schlagel");
        EMPLOYEES.add("Tameika Lanham");
        EMPLOYEES.add("Tanesha Blanton");
        EMPLOYEES.add("Tanja Arn");
        EMPLOYEES.add("Tatum Beers");
        EMPLOYEES.add("Taunya Pettway");
        EMPLOYEES.add("Taylor Shiflet");
        EMPLOYEES.add("Teddy Koth");
        EMPLOYEES.add("Teena Koran");
        EMPLOYEES.add("Terina Wigfall");
        EMPLOYEES.add("Tessie Sherman");
        EMPLOYEES.add("Tina Nale");
        EMPLOYEES.add("Tiny Byham");
        EMPLOYEES.add("Tisa Wilkens");
        EMPLOYEES.add("Tomoko Farabee");
        EMPLOYEES.add("Tora Ortman");
        EMPLOYEES.add("Torie Connon");
        EMPLOYEES.add("Tresa Reamer");
        EMPLOYEES.add("Tricia Champagne");
        EMPLOYEES.add("Trinity Gower");
        EMPLOYEES.add("Tyree Hubler");
        EMPLOYEES.add("Tyron Covey");
        EMPLOYEES.add("Ulysses Schmucker");
        EMPLOYEES.add("Ulysses Storch");
        EMPLOYEES.add("Valentine Fane");
        EMPLOYEES.add("Vannesa Tower");
        EMPLOYEES.add("Vaughn Meese");
        EMPLOYEES.add("Venice Hitt");
        EMPLOYEES.add("Verdell Wattley");
        EMPLOYEES.add("Vernon Nova");
        EMPLOYEES.add("Victor Faucher");
        EMPLOYEES.add("Virgie Loos");
        EMPLOYEES.add("Vivan Aquilino");
        EMPLOYEES.add("Wanetta Schmelzer");
        EMPLOYEES.add("Wenona Richie");
        EMPLOYEES.add("Whitley Blanke");
        EMPLOYEES.add("Willena Weast");
        EMPLOYEES.add("Willene Hobby");
        EMPLOYEES.add("Williams Dewald");
        EMPLOYEES.add("Winnie Hazard");
        EMPLOYEES.add("Xiao Lanni");
        EMPLOYEES.add("Yael Wickham");
        EMPLOYEES.add("Yee Kearney");
        EMPLOYEES.add("Yoshie Bengtson");
        EMPLOYEES.add("Zula Guthridge");

        // NAME=>TAG
        ACCOUNTS.add("Account Asset/Debit" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Credit" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Liability/Debit" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Liability/Credit" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Equity/Debit" + "=>" + Dropdown.EquityAccountTags);
        ACCOUNTS.add("Account Equity/Credit" + "=>" + Dropdown.EquityAccountTags);
        ACCOUNTS.add("Account Income/Debit" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Credit" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Expense/Debit" + "=>" + Dropdown.ExpenseAccountTags);
        ACCOUNTS.add("Account Expense/Credit" + "=>" + Dropdown.ExpenseAccountTags);

        ACCOUNTS.add("Account Charge" + "=>" + Dropdown.IncomeAccountTags);

        ACCOUNTS.add("Account Asset/Fund Source" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Saving Reference" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Share Reference" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Interest Receivable" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Fee Receivable" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Penalty Receivable" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Loan Portfolio" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Overdraft Portfolio" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/Transfer In Suspense" + "=>" + Dropdown.AssetAccountTags);

        ACCOUNTS.add("Account Income/Income From Interest" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Income From Penalty" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Income From Fee" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Income From Recovery Repayment" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Overdraft Interest Income" + "=>" + Dropdown.IncomeAccountTags);

        ACCOUNTS.add("Account Income/Penalty Income Account" + "=>" + Dropdown.IncomeAccountTags);
        ACCOUNTS.add("Account Income/Fee Income Account" + "=>" + Dropdown.IncomeAccountTags);

        ACCOUNTS.add("Account Expense/Losses Written Off" + "=>" + Dropdown.ExpenseAccountTags);
        ACCOUNTS.add("Account Expense/Interest On Saving" + "=>" + Dropdown.ExpenseAccountTags);
        ACCOUNTS.add("Account Expense/Write-Off" + "=>" + Dropdown.ExpenseAccountTags);

        ACCOUNTS.add("Account Liability/Over Payment Liability" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Liability/Saving Control" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Liability/Saving Transfer In Suspense" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Liability/Share Suspense Control" + "=>" + Dropdown.LiabilityAccountTags);

        ACCOUNTS.add("Account Liability/Fee Income Account" + "=>" + Dropdown.LiabilityAccountTags);

        ACCOUNTS.add("Account Share Equity/Equity" + "=>" + Dropdown.EquityAccountTags);

        ACCOUNTS.add("Account Asset/F.A. Asset Transfer" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/F.A. Main Cash Account" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/F.A. Cash Teller" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Asset/F.A. Fund Source" + "=>" + Dropdown.AssetAccountTags);
        ACCOUNTS.add("Account Liability/F.A. Liability Transfer" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Liability/F.A. Payable Dividends" + "=>" + Dropdown.LiabilityAccountTags);
        ACCOUNTS.add("Account Equity/F.A. Opening Balance Transfer" + "=>" + Dropdown.EquityAccountTags);

        ACCOUNT_RULES.add("Account Rule 01");

        TAX_COMPONENTS.add("T.C. Asset/Debit=>5=>" + AccountType.Asset.name() + "=>" + "Account Asset/Debit");
        TAX_COMPONENTS.add("T.C. Asset/Credit=>5=>" + AccountType.Asset.name() + "=>" + "Account Asset/Credit");
        TAX_COMPONENTS.add("T.C. Liability/Debit=>5=>" + AccountType.Liability.name() + "=>" + "Account Liability/Debit");
        TAX_COMPONENTS.add("T.C. Liability/Credit=>5=>" + AccountType.Liability.name() + "=>" + "Account Liability/Credit");
        TAX_COMPONENTS.add("T.C. Equity/Debit=>5=>" + AccountType.Equity.name() + "=>" + "Account Equity/Debit");
        TAX_COMPONENTS.add("T.C. Equity/Credit=>5=>" + AccountType.Equity.name() + "=>" + "Account Equity/Credit");
        TAX_COMPONENTS.add("T.C. Income/Debit=>5=>" + AccountType.Income.name() + "=>" + "Account Income/Debit");
        TAX_COMPONENTS.add("T.C. Income/Credit=>5=>" + AccountType.Income.name() + "=>" + "Account Income/Credit");
        TAX_COMPONENTS.add("T.C. Expense/Debit=>5=>" + AccountType.Expense.name() + "=>" + "Account Expense/Debit");
        TAX_COMPONENTS.add("T.C. Expense/Credit=>5=>" + AccountType.Expense.name() + "=>" + "Account Expense/Credit");

        TAX_GROUPS.add("T.G. 01");

        FLOATING_RATES.add("Floating Rate 01=>false=>false=>5");
        FLOATING_RATES.add("Floating Rate 03 B=>true=>false=>5");
        FLOATING_RATES.add("Floating Rate 04 D=>false=>true=>5");
    }

    @Before
    public void before() throws UnirestException {
        this.wicket = JUnit.getWicket();
        JsonNode tokenObject = LoginHelper.authenticate(Constants.AID, Constants.UID, Constants.PWD);
        this.token = tokenObject.getObject().getString("base64EncodedAuthenticationKey");
    }

    @Test
    public void initSampleData() throws ParseException, UnirestException {
        Function.setupSystemParameter(this, this.wicket.getJdbcTemplate(), PARAMS);
        setupOffice();
        Function.setupWorkingDay(this);
        setupCurrency();
        setupFund();
        setupTeller(this, this.wicket.getJdbcTemplate(), this.wicket.getJdbcNamed());
        setupPaymentType(this, this.wicket.getJdbcTemplate());
        setupHoliday(this, this.wicket.getJdbcTemplate());
        setupEmployee(this, this.wicket.getJdbcTemplate());
        Function.setupGLAccount(this, this.wicket.getJdbcTemplate(), ACCOUNTS, this.wicket.getStringGenerator());
        setupAccountingRule();
        Function.setupTaxComponent(this, this.wicket.getJdbcTemplate(), TAX_COMPONENTS, this.wicket.getStringGenerator());
        Function.setupTaxGroup(this, this.wicket.getJdbcTemplate(), TAX_GROUPS, this.wicket.getStringGenerator());
        Function.setupFloatingRate(this, this.wicket.getJdbcTemplate(), FLOATING_RATES, this.wicket.getStringGenerator());
        setupCharge(this, this.wicket.getJdbcTemplate());
    }

    protected void setupCharge(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        List<String> currencies = jdbcTemplate.queryForList("select code from m_organisation_currency", String.class);
        String taxGroupId = jdbcTemplate.queryForObject("select id from m_tax_group where name = ?", String.class, "T.G. 01");
        String accountId = jdbcTemplate.queryForObject("select id from acc_gl_account where name = ?", String.class, "Account Charge");
        for (String currency : currencies) {
            // Loan
            for (boolean penalty : new boolean[] { true, false }) {
                for (ChargeTime chargeTime : new ChargeTime[] { ChargeTime.Disbursement, ChargeTime.SpecifiedDueDate, ChargeTime.InstallmentFee, ChargeTime.OverdueFees, ChargeTime.TrancheDisbursement }) {
                    for (ChargePayment chargePayment : new ChargePayment[] { ChargePayment.RegularMode, ChargePayment.AccountTransferMode }) {
                        if ((chargeTime == ChargeTime.Disbursement || chargeTime == ChargeTime.TrancheDisbursement) && penalty) {
                            continue;

                        }
                        if ((chargeTime == ChargeTime.OverdueFees) && !penalty) {
                            continue;
                        }
                        String name = "Charge [LOAN] [" + currency + "] " + chargeTime.getDescription() + " Flat " + chargePayment.getDescription() + " " + (penalty ? "Penalty" : "");
                        boolean has = jdbcTemplate.queryForObject("select count(*) from m_charge where name = ?", boolean.class, name);
                        if (!has) {
                            ChargeBuilder builder = new ChargeBuilder();
                            builder.withChargeAppliesTo(ChargeType.Loan);
                            if (chargeTime == ChargeTime.OverdueFees) {
                                builder.withFeeInterval(1);
                                builder.withFeeFrequency(ChargeFrequency.Day);
                            }
                            builder.withName(name);
                            builder.withCurrencyCode(currency);
                            builder.withChargeTimeType(chargeTime);
                            builder.withChargeCalculationType(ChargeCalculation.Flat);
                            builder.withChargePaymentMode(chargePayment);
                            builder.withPenalty(penalty);
                            builder.withAmount(1d);
                            builder.withActive(true);
                            builder.withTaxGroupId(taxGroupId);
                            ChargeHelper.create(session, builder.build());
                        }
                    }
                }
            }
            // Saving & Deposit
            for (boolean penalty : new boolean[] { true, false }) {
                for (ChargeTime chargeTime : new ChargeTime[] { ChargeTime.SpecifiedDueDate, ChargeTime.SavingsActivation, ChargeTime.WithdrawalFee, ChargeTime.AnnualFee, ChargeTime.MonthlyFee, ChargeTime.WeeklyFee, ChargeTime.OverdraftFee, ChargeTime.SavingNoActivityFee }) {
                    String name = "Charge [S.D.] [" + currency + "] " + chargeTime.getDescription() + " Flat " + (penalty ? "Penalty" : "");
                    boolean has = jdbcTemplate.queryForObject("select count(*) from m_charge where name = ?", boolean.class, name);
                    if (!has) {
                        ChargeBuilder builder = new ChargeBuilder();
                        builder.withChargeAppliesTo(ChargeType.SavingDeposit);
                        if (chargeTime == ChargeTime.AnnualFee) {
                            builder.withFeeOnMonthDay(DateTime.now().toDate());
                        } else if (chargeTime == ChargeTime.MonthlyFee) {
                            builder.withFeeOnMonthDay(DateTime.now().toDate());
                            builder.withFeeInterval(1);
                        } else if (chargeTime == ChargeTime.WeeklyFee) {
                            builder.withFeeInterval(1);
                        }
                        builder.withName(name);
                        builder.withCurrencyCode(currency);
                        builder.withChargeTimeType(chargeTime);
                        builder.withChargeCalculationType(ChargeCalculation.Flat);
                        builder.withPenalty(penalty);
                        builder.withAmount(1d);
                        builder.withActive(true);
                        builder.withTaxGroupId(taxGroupId);
                        ChargeHelper.create(session, builder.build());
                    }
                }
            }
            // Client
            for (boolean penalty : new boolean[] { true, false }) {
                String name = "Charge [Client] [" + currency + "] " + ChargeTime.SpecifiedDueDate.getDescription() + " Flat " + (penalty ? "Penalty" : "");
                boolean has = jdbcTemplate.queryForObject("select count(*) from m_charge where name = ?", boolean.class, name);
                if (!has) {
                    ChargeBuilder builder = new ChargeBuilder();
                    builder.withChargeAppliesTo(ChargeType.Client);
                    builder.withName(name);
                    builder.withCurrencyCode(currency);
                    builder.withChargeTimeType(ChargeTime.SpecifiedDueDate);
                    builder.withChargeCalculationType(ChargeCalculation.Flat);
                    builder.withPenalty(penalty);
                    builder.withAmount(1d);
                    builder.withActive(true);
                    builder.withIncomeAccountId(accountId);
                    builder.withTaxGroupId(taxGroupId);
                    ChargeHelper.create(session, builder.build());
                }
            }
            // Share
            for (ChargeTime chargeTime : new ChargeTime[] { ChargeTime.ShareAccountActivate, ChargeTime.SharePurchase, ChargeTime.ShareRedeem }) {
                String name = "Charge [Share] [" + currency + "] " + chargeTime.getDescription() + " Flat";
                boolean has = jdbcTemplate.queryForObject("select count(*) from m_charge where name = ?", boolean.class, name);
                if (!has) {
                    ChargeBuilder builder = new ChargeBuilder();
                    builder.withChargeAppliesTo(ChargeType.Share);
                    builder.withName(name);
                    builder.withCurrencyCode(currency);
                    builder.withChargeTimeType(chargeTime);
                    builder.withChargeCalculationType(ChargeCalculation.Flat);
                    builder.withAmount(1d);
                    builder.withActive(true);
                    builder.withTaxGroupId(taxGroupId);
                    ChargeHelper.create(session, builder.build());
                }
            }
        }
    }

    protected void setupHoliday(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException, ParseException {
        String officeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_office where name = ?", String.class, "Head Office");
        Function.setupHoliday(this, this.wicket.getJdbcTemplate(), officeId, HOLIDAYS);
    }

    protected void setupAccountingRule() throws UnirestException {
        String officeId = this.wicket.getJdbcTemplate().queryForObject("select id from m_office where name not like ? limit 1", String.class, "JUnit%");
        String creditId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where name not like ? limit 1", String.class, "JUnit%");
        String debitId = this.wicket.getJdbcTemplate().queryForObject("select id from acc_gl_account where name not like ? limit 1", String.class, "JUnit%");
        Function.setupAccountingRule(this, this.wicket.getJdbcTemplate(), officeId, debitId, creditId, ACCOUNT_RULES);
    }

    protected void setupPaymentType(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
        for (String temps : PAYMENTS) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            if (!jdbcTemplate.queryForObject("select count(*) from m_payment_type where value = ?", Boolean.class, name)) {
                boolean cashPayment = Boolean.valueOf(temp[1]);
                PaymentTypeBuilder builder = new PaymentTypeBuilder();
                builder.withDescription(name);
                builder.withName(name);
                builder.withCashPayment(cashPayment);
                builder.withPosition(1);
                PaymentTypeHelper.create(session, builder.build());
            }
        }
    }

    protected void setupTeller(IMifos session, JdbcTemplate jdbcTemplate, JdbcNamed jdbcNamed) throws UnirestException, ParseException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", OFFICES);
        List<String> offices = jdbcNamed.queryForList("select id from m_office where name in (:name)", params, String.class);
        NumberFormat format = new DecimalFormat("000");
        Date startDate = Function.DATE_FORMAT.parse("2017-01-01");
        for (int i = 1; i <= 400; i++) {
            String name = "TELLER " + format.format(i);
            if (!jdbcTemplate.queryForObject("select count(*) from m_tellers where name = ?", Boolean.class, name)) {
                String officeId = offices.get(RandomUtils.nextInt(0, offices.size()));
                TellerBuilder builder = new TellerBuilder();
                builder.withDescription(name);
                builder.withName(name);
                builder.withStatus(TellerStatus.Active);
                builder.withOfficeId(officeId);
                builder.withStartDate(startDate);
                TellerHelper.create(session, builder.build());
            }
        }
    }

    protected void setupFund() throws UnirestException {
        Function.setupFund(this, this.wicket.getJdbcTemplate(), FUNDS);
    }

    protected void setupOffice() throws ParseException, UnirestException {
        Date openingDate = Function.DATE_FORMAT.parse("2017-01-01");
        Function.setupOffice(this, this.wicket.getJdbcTemplate(), openingDate, OFFICES);
    }

    protected void setupCurrency() throws UnirestException {
        Function.setupCurrency(this, this.wicket.getJdbcTemplate(), CURRENCIES);
    }

    @Override
    public String getIdentifier() {
        return Constants.AID;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    protected void setupEmployee(IMifos session, JdbcTemplate jdbcTemplate) throws ParseException, UnirestException {
        for (String employee : EMPLOYEES) {
            int index = employee.indexOf(" ");
            String firstName = employee.substring(0, index);
            String lastName = employee.substring(index + 1, employee.length());
            if (!jdbcTemplate.queryForObject("select count(*) from m_staff where firstname = ? and lastname = ?", Boolean.class, firstName, lastName)) {
                StaffBuilder builder = new StaffBuilder();
                String office = OFFICES.get(RandomUtils.nextInt(0, OFFICES.size()));
                String officeId = jdbcTemplate.queryForObject("select id from m_office where name = ?", String.class, office);
                builder.withExternalId(StringUtils.upperCase(UUID.randomUUID().toString()));
                builder.withJoiningDate(Function.DATE_FORMAT.parse("2017-01-01"));
                builder.withMobileNo(this.wicket.getNumberGenerator().generate(10));
                builder.withLoanOfficer(Integer.valueOf(this.wicket.getNumberGenerator().generate(3)) % 2 == 0);
                builder.withFirstName(firstName);
                builder.withOfficeId(officeId);
                builder.withLastName(lastName);
                StaffHelper.create(session, builder.build());
            }
        }
    }

}
