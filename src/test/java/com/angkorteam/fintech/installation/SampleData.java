package com.angkorteam.fintech.installation;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.dto.Dropdown;
import com.angkorteam.fintech.dto.builder.StaffBuilder;
import com.angkorteam.fintech.dto.constant.TellerStatus;
import com.angkorteam.fintech.dto.enums.*;
import com.angkorteam.fintech.meta.tenant.*;
import com.angkorteam.fintech.spring.NumberGenerator;
import com.angkorteam.fintech.spring.NumberGeneratorImpl;
import com.angkorteam.fintech.spring.StringGenerator;
import com.angkorteam.fintech.spring.StringGeneratorImpl;
import com.google.common.collect.Lists;
import io.github.openunirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.FunctionType;
import org.joda.time.DateTime;

import java.sql.Driver;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

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
        CURRENCIES.add("JPY");
        CURRENCIES.add("CNY");

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

        ACCOUNTS.add("Account Asset/Payment Type Fund Source" + "=>" + Dropdown.AssetAccountTags);
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
        ACCOUNTS.add("Account Liability/Escheat Liability" + "=>" + Dropdown.LiabilityAccountTags);

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

        TAX_COMPONENTS.add("T.C. Asset/Debit=>5=>" + GLAccountType.Asset.name() + "=>" + "Account Asset/Debit");
        TAX_COMPONENTS.add("T.C. Asset/Credit=>5=>" + GLAccountType.Asset.name() + "=>" + "Account Asset/Credit");
        TAX_COMPONENTS.add("T.C. Liability/Debit=>5=>" + GLAccountType.Liability.name() + "=>" + "Account Liability/Debit");
        TAX_COMPONENTS.add("T.C. Liability/Credit=>5=>" + GLAccountType.Liability.name() + "=>" + "Account Liability/Credit");
        TAX_COMPONENTS.add("T.C. Equity/Debit=>5=>" + GLAccountType.Equity.name() + "=>" + "Account Equity/Debit");
        TAX_COMPONENTS.add("T.C. Equity/Credit=>5=>" + GLAccountType.Equity.name() + "=>" + "Account Equity/Credit");
        TAX_COMPONENTS.add("T.C. Income/Debit=>5=>" + GLAccountType.Income.name() + "=>" + "Account Income/Debit");
        TAX_COMPONENTS.add("T.C. Income/Credit=>5=>" + GLAccountType.Income.name() + "=>" + "Account Income/Credit");
        TAX_COMPONENTS.add("T.C. Expense/Debit=>5=>" + GLAccountType.Expense.name() + "=>" + "Account Expense/Debit");
        TAX_COMPONENTS.add("T.C. Expense/Credit=>5=>" + GLAccountType.Expense.name() + "=>" + "Account Expense/Credit");

        TAX_GROUPS.add("T.G. 01");

        FLOATING_RATES.add("Floating Rate 01=>false=>false=>5");
        FLOATING_RATES.add("Floating Rate 03 B=>true=>false=>5");
        FLOATING_RATES.add("Floating Rate 04 D=>false=>true=>5");
    }

    public static void main(String[] args) throws ParseException {
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

        JdbcDataContext appDataContext = null;
        {
            DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
            properties.put("type", "jdbc");
            properties.put("url", "jdbc:mysql://" + ip + ":" + port + "/fineract_default?useSSL=true");
            properties.put("driver-class", Driver.class.getName());
            properties.put("username", "bank");
            properties.put("password", "password");
            appDataContext = (JdbcDataContext) DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(properties);
        }

        String mifosUrl = "https://bank-api.i365work.com:21632/fineract-provider/api/v1";

        boolean fileout = true;

        String tenant = "default";

        FineractClient client = new FineractClient(mifosUrl);

        PostAuthenticationResponse response = client.authentication(tenant, new AuthenticateRequest("mifos", "password"));

        String token = response.getBase64EncodedAuthenticationKey();

        StringGenerator stringGenerator = new StringGeneratorImpl();
        NumberGenerator numberGenerator = new NumberGeneratorImpl();

        Function.setupSystemParameter(client, tenant, token, appDataContext, PARAMS);
        setupOffice(client, tenant, token, appDataContext, stringGenerator);
        Function.setupWorkingDay(client, tenant, token);
        setupCurrency(client, tenant, token, appDataContext);
        setupFund(client, tenant, token, appDataContext, stringGenerator);
        setupTeller(client, tenant, token, appDataContext);
        setupPaymentType(client, tenant, token, appDataContext);
        setupHoliday(client, tenant, token, appDataContext);
        setupEmployee(client, tenant, token, appDataContext, stringGenerator, numberGenerator);
        Function.setupGLAccount(client, tenant, token, appDataContext, ACCOUNTS, stringGenerator);
        setupAccountingRule(client, tenant, token, appDataContext);
        Function.setupTaxComponent(client, tenant, token, appDataContext, TAX_COMPONENTS, stringGenerator);
        Function.setupTaxGroup(client, tenant, token, appDataContext, TAX_GROUPS, stringGenerator);
        Function.setupFloatingRate(client, tenant, token, appDataContext, FLOATING_RATES, stringGenerator);
        setupCharge(client, tenant, token, appDataContext);
        // setupFixedDepositProduct(this, this.wicket.getJdbcTemplate());
        setupClient(client, tenant, token, appDataContext, stringGenerator);
        setupGroup(client, tenant, token, appDataContext, stringGenerator);
        setupCenter(client, tenant, token, appDataContext, stringGenerator);
    }

    protected static void setupCenter(FineractClient client, String tenant, String token, DataContext appDataContext, StringGenerator generator) throws UnirestException {
        MGroup mGroup = MGroup.staticInitialize(appDataContext);
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        MStaff mStaff = MStaff.staticInitialize(appDataContext);
        MClient mClient = MClient.staticInitialize(appDataContext);
        long count = 0L;

        try (DataSet rows = appDataContext.query().from(mGroup).select(FunctionType.COUNT, mGroup.ID).where(mGroup.DISPLAY_NAME).eq("Weekend Startup").execute()) {
            rows.next();
            count = (long) rows.getRow().getValue(0);
        }

        if (count > 0L) {
            return;
        }

        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Phnom Penh").execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(mOffice.ID);
        }

        long staffId = 0L;
        try (DataSet rows = appDataContext.query().from(mStaff).select(mStaff.ID).where(mStaff.OFFICE_ID).eq(officeId).limit(1).execute()) {
            rows.next();
            staffId = (long) rows.getRow().getValue(mStaff.ID);
        }

        long groupId = 0L;
        try (DataSet rows = appDataContext.query().from(mGroup).select(mGroup.ID).where(mGroup.DISPLAY_NAME).eq("IT Group").limit(1).execute()) {
            rows.next();
            groupId = (long) rows.getRow().getValue(mGroup.ID);
        }

        PostCentersRequest request = new PostCentersRequest();
        request.setOfficeId(officeId);

        request.setExternalId(generator.externalId());
        request.setName("Weekend Startup");
        request.setSubmittedOnDate(DateTime.now().toDate());
        request.setStaffId(staffId);

        request.setActive(true);
        request.setActivationDate(DateTime.now().toDate());
        request.getGroupMembers().add(groupId);

        client.centerCreate(tenant, token, request);
    }

    protected static void setupGroup(FineractClient client, String tenant, String token, DataContext appDataContext, StringGenerator generator) throws UnirestException {
        MGroup mGroup = MGroup.staticInitialize(appDataContext);
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        MStaff mStaff = MStaff.staticInitialize(appDataContext);
        MClient mClient = MClient.staticInitialize(appDataContext);
        long count = 0L;

        try (DataSet rows = appDataContext.query().from(mGroup).select(FunctionType.COUNT, mGroup.ID).where(mGroup.DISPLAY_NAME).eq("IT Group").execute()) {
            rows.next();
            count = (long) rows.getRow().getValue(0);
        }

        if (count > 0L) {
            return;
        }

        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Phnom Penh").execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(mOffice.ID);
        }

        long staffId = 0L;
        try (DataSet rows = appDataContext.query().from(mStaff).select(mStaff.ID).where(mStaff.OFFICE_ID).eq(officeId).limit(1).execute()) {
            rows.next();
            staffId = (long) rows.getRow().getValue(mStaff.ID);
        }

        long clientId = 0L;
        try (DataSet rows = appDataContext.query().from(mClient).select(mClient.ID).where(mClient.FULL_NAME).eq("Angkor Team").limit(1).execute()) {
            rows.next();
            clientId = (long) rows.getRow().getValue(mClient.ID);
        }

        PostGroupsRequest request = new PostGroupsRequest();
        request.setOfficeId(officeId);
        request.setExternalId(generator.externalId());
        request.setName("IT Group");
        request.setSubmittedOnDate(DateTime.now().toDate());
        request.setStaffId(staffId);
        request.setActive(true);
        request.setActivationDate(DateTime.now().toDate());

        request.getClientMembers().add(clientId);
        client.groupCreate(tenant, token, request);
    }

    protected static void setupClient(FineractClient client, String tenant, String token, DataContext appDataContext, StringGenerator generator) throws UnirestException {
        setupClientSocheatKHAUV(client, tenant, token, appDataContext, generator);
        setupClientAngkorTeam(client, tenant, token, appDataContext, generator);
    }

    protected static void setupClientAngkorTeam(FineractClient client, String tenant, String token, DataContext appDataContext, StringGenerator generator) throws UnirestException {
        MClient mClient = MClient.staticInitialize(appDataContext);
        MStaff mStaff = MStaff.staticInitialize(appDataContext);
        MCodeValue mCodeValue = MCodeValue.staticInitialize(appDataContext);
        MCode mCode = MCode.staticInitialize(appDataContext);
        MOffice mOffice = MOffice.staticInitialize(appDataContext);

        long count = 0L;
        try (DataSet rows = appDataContext.query().from(mClient).select(FunctionType.COUNT, mClient.ID).where(mClient.FULL_NAME).eq("Angkor Team").execute()) {
            rows.next();
            count = (long) rows.getRow().getValue(0);
        }
        if (count > 0L) {
            return;
        }

        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Phnom Penh").execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(mOffice.ID);
        }

        long staffId = 0;
        try (DataSet rows = appDataContext.query().from(mStaff).select(mStaff.ID).where(mStaff.OFFICE_ID).eq(officeId).limit(1).execute()) {
            rows.next();
            staffId = (long) rows.getRow().getValue(mStaff.ID);
        }

        int clientTypeId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("ClientType").limit(1).execute()) {
            rows.next();
            clientTypeId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        int clientClassificationId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("ClientClassification").limit(1).execute()) {
            rows.next();
            clientClassificationId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        int mainBusinessLineId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("Main Business Line").limit(1).execute()) {
            rows.next();
            mainBusinessLineId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        int constitutionId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("Constitution").limit(1).execute()) {
            rows.next();
            constitutionId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        PostClientsRequest request = new PostClientsRequest();
        request.setLegalForm(LegalForm.Person);
        request.setFullName("Angkor Team");
        request.setDateOfBirth(DateTime.now().toDate());
        String incorpNumber = generator.generate(5);
        String remarks = "Remarks";
        Date incorpValidityTillDate = DateTime.now().plusYears(10).toDate();
        request.setClientNonPersonDetail(new PostClientsRequest.ClientNonPersonDetail(incorpNumber, remarks, incorpValidityTillDate, constitutionId, mainBusinessLineId));

        request.setOfficeId(officeId);
        request.setClientClassificationId(clientClassificationId);
        request.setStaffId(staffId);
        request.setMobileNo("+85593777091");
        request.setClientTypeId(clientTypeId);
        request.setExternalId(generator.externalId());
        request.setActive(true);
        request.setActivationDate(DateTime.now().toDate());
        request.setSubmittedOnDate(DateTime.now().toDate());

        client.clientCreate(tenant, token, request);
    }

    protected static void setupClientSocheatKHAUV(FineractClient client, String tenant, String token, DataContext appDataContext, StringGenerator generator) throws UnirestException {
        MClient mClient = MClient.staticInitialize(appDataContext);
        MStaff mStaff = MStaff.staticInitialize(appDataContext);
        MCodeValue mCodeValue = MCodeValue.staticInitialize(appDataContext);
        MCode mCode = MCode.staticInitialize(appDataContext);
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        long count = 0L;
        try (DataSet rows = appDataContext.query().from(mClient).select(FunctionType.COUNT, mClient.ID).where(mClient.FIRST_NAME).eq("Socheat").and(mClient.LAST_NAME).eq("KHAUV").execute()) {
            rows.next();
            count = (long) rows.getRow().getValue(0);
        }
        if (count > 0L) {
            return;
        }

        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Phnom Penh").execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(mOffice.ID);
        }

        long staffId = 0L;
        try (DataSet rows = appDataContext.query().from(mStaff).select(mStaff.ID).where(mStaff.OFFICE_ID).eq(officeId).limit(1).execute()) {
            rows.next();
            staffId = (long) rows.getRow().getValue(mStaff.ID);
        }

        int genderId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("Gender").and(mCodeValue.CODE_VALUE).eq("M").execute()) {
            rows.next();
            genderId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        int clientTypeId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("ClientType").limit(1).execute()) {
            rows.next();
            clientTypeId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        int clientClassificationId = 0;
        try (DataSet rows = appDataContext.query().from(mCodeValue).innerJoin(mCode).on(mCodeValue.CODE_ID, mCode.ID).select(mCodeValue.ID).where(mCode.CODE_NAME).eq("ClientClassification").limit(1).execute()) {
            rows.next();
            clientClassificationId = (int) rows.getRow().getValue(mCodeValue.ID);
        }

        PostClientsRequest request = new PostClientsRequest();

        request.setLegalForm(LegalForm.Person);
        request.setFirstName("Socheat");
        request.setStaff(false);
        request.setLastName("KHAUV");
        request.setDateOfBirth(DateTime.now().minusYears(20).toDate());
        request.setGenderId(genderId);
        request.setOfficeId(officeId);
        request.setClientClassificationId(clientClassificationId);
        request.setStaffId(staffId);
        request.setMobileNo("+85577777091");
        request.setClientTypeId(clientTypeId);
        request.setExternalId(generator.externalId());
        request.setActive(true);
        request.setActivationDate(DateTime.now().toDate());
        request.setSubmittedOnDate(DateTime.now().toDate());

        client.clientCreate(tenant, token, request);

    }

//    protected void setupFixedDepositProduct(IMifos session, JdbcTemplate jdbcTemplate) throws UnirestException {
//
//    }

    protected static void setupCharge(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException {
        MOrganisationCurrency mOrganisationCurrency = MOrganisationCurrency.staticInitialize(appDataContext);
        MTaxGroup mTaxGroup = MTaxGroup.staticInitialize(appDataContext);
        AccGLAccount accGLAccount = AccGLAccount.staticInitialize(appDataContext);
        MCharge mCharge = MCharge.staticInitialize(appDataContext);

        List<String> currencies = new ArrayList<>();
        try (DataSet rows = appDataContext.query().from(mOrganisationCurrency).select(mOrganisationCurrency.CODE).execute()) {
            while (rows.next()) {
                currencies.add((String) rows.getRow().getValue(mOrganisationCurrency.CODE));
            }
        }

        long taxGroupId = 0L;
        try (DataSet rows = appDataContext.query().from(mTaxGroup).select(mTaxGroup.ID).where(mTaxGroup.NAME).eq("T.G. 01").execute()) {
            rows.next();
            taxGroupId = (long) rows.getRow().getValue(0);
        }

        long accountId = 0L;
        try (DataSet rows = appDataContext.query().from(accGLAccount).select(accGLAccount.ID).where(accGLAccount.NAME).eq("Account Charge").execute()) {
            rows.next();
            accountId = (long) rows.getRow().getValue(0);
        }


        for (String currency : currencies) {
            // Loan
            for (boolean penalty : new boolean[]{true, false}) {
                for (ChargeTimeType chargeTime : new ChargeTimeType[]{ChargeTimeType.Disbursement, ChargeTimeType.SpecifiedDueDate, ChargeTimeType.InstallmentFee, ChargeTimeType.OverdueInstallment, ChargeTimeType.TrancheDisbursement}) {
                    for (ChargePaymentMode chargePayment : new ChargePaymentMode[]{ChargePaymentMode.Regular, ChargePaymentMode.AccountTransfer}) {
                        if ((chargeTime == ChargeTimeType.Disbursement || chargeTime == ChargeTimeType.TrancheDisbursement) && penalty) {
                            continue;

                        }
                        if ((chargeTime == ChargeTimeType.OverdueInstallment) && !penalty) {
                            continue;
                        }
                        String name = "Charge [LOAN] [" + currency + "] " + chargeTime.getDescription() + " Flat " + chargePayment.getDescription() + " " + (penalty ? "Penalty" : "");
                        try (DataSet rows = appDataContext.query().from(mCharge).select(FunctionType.COUNT, mCharge.ID).where(mCharge.NAME).eq(name).execute()) {
                            rows.next();
                            long count = (long) rows.getRow().getValue(0);
                            boolean has = count > 0L;
                            if (!has) {
                                PostChargesRequest request = new PostChargesRequest();
                                request.setChargeAppliesTo(ChargeAppliesTo.Loan);
                                if (chargeTime == ChargeTimeType.OverdueInstallment) {
                                    request.setFeeInterval(1);
                                    request.setFeeFrequency(ChargeFrequency.Day);
                                }
                                request.setName(name);
                                request.setCurrencyCode(currency);
                                request.setChargeTimeType(chargeTime);
                                request.setChargeCalculationType(ChargeCalculationType.Flat);
                                request.setChargePaymentMode(chargePayment);
                                request.setPenalty(penalty);
                                request.setAmount(1d);
                                request.setActive(true);
                                request.setTaxGroupId(taxGroupId);
                                client.chargeCreate(tenant, token, request);
                            }
                        }
                    }
                }
            }
            // Saving & Deposit
            for (boolean penalty : new boolean[]{true, false}) {
                for (ChargeTimeType chargeTime : new ChargeTimeType[]{ChargeTimeType.SpecifiedDueDate, ChargeTimeType.SavingActivation, ChargeTimeType.WithdrawalFee, ChargeTimeType.AnnualFee, ChargeTimeType.MonthlyFee, ChargeTimeType.WeeklyFee, ChargeTimeType.OverdraftFee, ChargeTimeType.SavingNoActivityFee}) {
                    String name = "Charge [S.D.] [" + currency + "] " + chargeTime.getDescription() + " Flat " + (penalty ? "Penalty" : "");
                    try (DataSet rows = appDataContext.query().from(mCharge).select(FunctionType.COUNT, mCharge.ID).where(mCharge.NAME).eq(name).execute()) {
                        rows.next();
                        long count = (long) rows.getRow().getValue(0);
                        boolean has = count > 0L;
                        if (!has) {
                            PostChargesRequest request = new PostChargesRequest();
                            request.setChargeAppliesTo(ChargeAppliesTo.SavingDeposit);
                            if (chargeTime == ChargeTimeType.AnnualFee) {
                                request.setFeeOnMonthDay(DateTime.now().toDate());
                            } else if (chargeTime == ChargeTimeType.MonthlyFee) {
                                request.setFeeOnMonthDay(DateTime.now().toDate());
                                request.setFeeInterval(1);
                            } else if (chargeTime == ChargeTimeType.WeeklyFee) {
                                request.setFeeInterval(1);
                            }
                            request.setName(name);
                            request.setCurrencyCode(currency);
                            request.setChargeTimeType(chargeTime);
                            request.setChargeCalculationType(ChargeCalculationType.Flat);
                            request.setPenalty(penalty);
                            request.setAmount(1d);
                            request.setActive(true);
                            request.setTaxGroupId(taxGroupId);
                            client.chargeCreate(tenant, token, request);
                        }
                    }
                }
            }
            // Client
            for (boolean penalty : new boolean[]{true, false}) {
                String name = "Charge [Client] [" + currency + "] " + ChargeTimeType.SpecifiedDueDate.getDescription() + " Flat " + (penalty ? "Penalty" : "");
                try (DataSet rows = appDataContext.query().from(mCharge).select(FunctionType.COUNT, mCharge.ID).where(mCharge.NAME).eq(name).execute()) {
                    rows.next();
                    long count = (long) rows.getRow().getValue(0);
                    boolean has = count > 0L;
                    if (!has) {
                        PostChargesRequest request = new PostChargesRequest();
                        request.setChargeAppliesTo(ChargeAppliesTo.Client);
                        request.setName(name);
                        request.setCurrencyCode(currency);
                        request.setChargeTimeType(ChargeTimeType.SpecifiedDueDate);
                        request.setChargeCalculationType(ChargeCalculationType.Flat);
                        request.setPenalty(penalty);
                        request.setAmount(1d);
                        request.setActive(true);
                        request.setIncomeAccountId(accountId);
                        request.setTaxGroupId(taxGroupId);
                        client.chargeCreate(tenant, token, request);
                    }
                }
            }
            // Share
            for (ChargeTimeType chargeTime : new ChargeTimeType[]{ChargeTimeType.ShareAccountActivate, ChargeTimeType.SharePurchase, ChargeTimeType.ShareRedeem}) {
                String name = "Charge [Share] [" + currency + "] " + chargeTime.getDescription() + " Flat";
                try (DataSet rows = appDataContext.query().from(mCharge).select(FunctionType.COUNT, mCharge.ID).where(mCharge.NAME).eq(name).execute()) {
                    rows.next();
                    long count = (long) rows.getRow().getValue(0);
                    boolean has = count > 0L;
                    if (!has) {
                        PostChargesRequest request = new PostChargesRequest();
                        request.setChargeAppliesTo(ChargeAppliesTo.Share);
                        request.setName(name);
                        request.setCurrencyCode(currency);
                        request.setChargeTimeType(chargeTime);
                        request.setChargeCalculationType(ChargeCalculationType.Flat);
                        request.setAmount(1d);
                        request.setActive(true);
                        request.setTaxGroupId(taxGroupId);
                        client.chargeCreate(tenant, token, request);
                    }
                }
            }
        }
    }

    protected static void setupHoliday(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException, ParseException {
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq("Head Office").execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(0);
        }
        Function.setupHoliday(client, tenant, token, appDataContext, officeId, HOLIDAYS);
    }

    protected static void setupAccountingRule(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException {
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        AccGLAccount accGLAccount = AccGLAccount.staticInitialize(appDataContext);

        long officeId = 0L;
        try (DataSet rows = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).notLike("JUnit%").limit(1).execute()) {
            rows.next();
            officeId = (long) rows.getRow().getValue(0);
        }
        long creditId = 0L;
        try (DataSet rows = appDataContext.query().from(accGLAccount).select(accGLAccount.ID).where(accGLAccount.NAME).notLike("JUnit%").limit(1).execute()) {
            rows.next();
            creditId = (long) rows.getRow().getValue(0);
        }
        long debitId = 0L;
        try (DataSet rows = appDataContext.query().from(accGLAccount).select(accGLAccount.ID).where(accGLAccount.NAME).notLike("JUnit%").limit(1).execute()) {
            rows.next();
            debitId = (long) rows.getRow().getValue(0);
        }
        Function.setupAccountingRule(client, tenant, token, appDataContext, officeId, debitId, creditId, ACCOUNT_RULES);
    }

    protected static void setupPaymentType(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException {
        MPaymentType mPaymentType = MPaymentType.staticInitialize(appDataContext);
        for (String temps : PAYMENTS) {
            String temp[] = StringUtils.split(temps, "=>");
            String name = temp[0];
            try (DataSet rows = appDataContext.query().from(mPaymentType).select(FunctionType.COUNT, mPaymentType.ID).where(mPaymentType.VALUE).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count > 0L) {
                    boolean cashPayment = Boolean.valueOf(temp[1]);
                    PostPaymentTypesRequest request = new PostPaymentTypesRequest();
                    request.setCashPayment(cashPayment);
                    request.setName(name);
                    request.setPosition(1);
                    request.setDescription(name);
                    client.paymentTypeCreate(tenant, token, request);
                }
            }
        }
    }

    protected static void setupTeller(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException, ParseException {
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        MTeller mTeller = MTeller.staticInitialize(appDataContext);
        List<Long> offices = new ArrayList<>();
        try (DataSet rows = appDataContext.query().from(mOffice).selectAll().where(mOffice.NAME).in(OFFICES).execute()) {
            while (rows.next()) {
                offices.add((long) rows.getRow().getValue(mOffice.ID));
            }
        }
        NumberFormat format = new DecimalFormat("000");
        Date startDate = Function.DATE_FORMAT.parse("2017-01-01");
        for (int i = 1; i <= 400; i++) {
            String name = "TELLER " + format.format(i);
            try (DataSet rows = appDataContext.query().from(mTeller).select(FunctionType.COUNT, mTeller.ID).where(mTeller.NAME).eq(name).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count <= 0L) {
                    long officeId = offices.get(RandomUtils.nextInt(0, offices.size()));
                    PostTellersRequest request = new PostTellersRequest();
                    request.setDescription(name);
                    request.setName(name);
                    request.setStatus(TellerStatus.Active);
                    request.setOfficeId(officeId);
                    request.setStartDate(startDate);
                    client.tellerCreate(tenant, token, request);
                }
            }
        }
    }

    protected static void setupFund(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, StringGenerator generator) throws UnirestException {
        Function.setupFund(client, tenant, token, appDataContext, FUNDS, generator);
    }

    protected static void setupOffice(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, StringGenerator stringGenerator) throws ParseException {
        Date openingDate = Function.DATE_FORMAT.parse("2017-01-01");
        Function.setupOffice(client, tenant, token, appDataContext, openingDate, OFFICES, stringGenerator);
    }

    protected static void setupCurrency(FineractClient client, String tenant, String token, JdbcDataContext appDataContext) throws UnirestException {
        Function.setupCurrency(client, tenant, token, appDataContext, CURRENCIES);
    }

    protected static void setupEmployee(FineractClient client, String tenant, String token, JdbcDataContext appDataContext, StringGenerator generator, NumberGenerator numberGenerator) throws ParseException, UnirestException {
        MStaff mStaff = MStaff.staticInitialize(appDataContext);
        MOffice mOffice = MOffice.staticInitialize(appDataContext);
        for (String employee : EMPLOYEES) {
            int index = employee.indexOf(" ");
            String firstName = employee.substring(0, index);
            String lastName = employee.substring(index + 1);
            try (DataSet rows = appDataContext.query().from(mStaff).select(FunctionType.COUNT, mStaff.ID).where(mStaff.FIRST_NAME).eq(firstName).and(mStaff.LAST_NAME).eq(lastName).execute()) {
                rows.next();
                long count = (long) rows.getRow().getValue(0);
                if (count <= 0L) {
                    StaffBuilder builder = new StaffBuilder();
                    String office = OFFICES.get(RandomUtils.nextInt(0, OFFICES.size()));
                    long officeId = 0L;
                    try (DataSet rows1 = appDataContext.query().from(mOffice).select(mOffice.ID).where(mOffice.NAME).eq(office).execute()) {
                        rows1.next();
                        officeId = (long) rows1.getRow().getValue(mOffice.ID);
                    }
                    PostStaffRequest request = new PostStaffRequest();
                    request.setExternalId(generator.externalId());
                    request.setJoiningDate(Function.DATE_FORMAT.parse("2017-01-01"));
                    request.setMobileNo(numberGenerator.generate(10));
                    request.setActive(RandomUtils.nextBoolean());
                    request.setLoanOfficer(RandomUtils.nextBoolean());
                    request.setLastName(lastName);
                    request.setFirstName(firstName);
                    request.setOfficeId(officeId);
                    client.staffCreate(tenant, token, request);
                }
            }
        }
    }

}
