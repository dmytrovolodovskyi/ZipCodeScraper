package com.example.service;

import com.example.model.entity.Address;
import com.example.model.entity.Country;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataService {
    public static final Logger LOGGER = Logger.getLogger(DataService.class.getName());

    private static List<Country> countyEntities;
    private static List<Address> addressEntities;

    private final TranslatorService translator;

    public DataService() {
        countyEntities = new ArrayList<>();
        addressEntities = new ArrayList<>();
        translator = new TranslatorService();
    }

    public void convertData(List<String> data) {
        LOGGER.log(Level.INFO, "Converting data...");

        List<String> uniqueData = convertToUnique(data);

        for (String line : uniqueData) {
            setAddressEntity(line);
        }
        setCountryEntities();

        LOGGER.log(Level.INFO, "Converting data completed!");
    }

    public List<Address> getAddressEntities() {
        return addressEntities;
    }

    public List<Country> getCountryEntities() {
        return countyEntities;
    }

    private List<String> convertToUnique(List<String> data) {
        Set<String> uniqueData = new LinkedHashSet<>();
        Set<String> uniqueLines = new LinkedHashSet<>();

        for (String line : data) {
            if (uniqueLines.add(line)) {
                String latinLine = translate(line);
                uniqueData.add(latinLine);
            }
        }
        return uniqueData.stream().toList();
    }

    private String translate(String line) {
        String[] words = splitLine(line);

        String code = words[0];
        String city = words[2];
        String county = words[3];
        String district = words[5];

        return line
                .replace(city.trim(), translator.translate(code, city))
                .replace(county.trim(), translator.translate(code, county))
                .replace(district.trim(), translator.translate(code, district));
    }

    private String[] splitLine(String line) {
        line = line.replaceAll("\\t\\t", "|");
        line = line.replaceAll("\\t", "| ");
        return line.split("\\|");
    }

    private void setAddressEntity(String line) {
        String[] words = splitLine(line);

        String country = words[0];
        String county = words[3].length() <= 15 ? words[3] : words[3].substring(0,15);
        String district = words[5].length() <= 20 ? words[5] : words[5].substring(0,20);
        String city = words[2].replaceAll("\\(.*", "");
        city = city.length() <= 50 ? city : city.substring(0, 50);
        double latitude = Double.parseDouble(words[words.length - 3]);
        double longitude = Double.parseDouble(words[words.length - 2]);
        String zip = words[1].length() <= 10 ? words[1] : words[1].replaceAll("\\s\\D.+", "");

        Address address = new Address();
        address.setCountry(country);
        address.setProvince("");
        address.setCounty(county);
        address.setDistrict(district);
        address.setCity(city);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        address.setZip(zip);

        addressEntities.add(address);
    }

    private void setCountryEntities() {
        Map<String, String> iso = getISOMap();
        Map<String, String> countries = getCountriesMap();

        for (String isoVal : iso.keySet()) {
            Country country = new Country();
            country.setCountry(isoVal);
            String localISOVar = iso.get(isoVal);
            country.setCountryIso(localISOVar);
            country.setName(countries.get(localISOVar));

            countyEntities.add(country);
        }
    }

    private Map<String, String> getISOMap() {
        Map<String, String> iso = new HashMap<>();
        iso.put("AF", "AFG");
        iso.put("AL", "ALB");
        iso.put("DZ", "DZA");
        iso.put("AS", "ASM");
        iso.put("AD", "AND");
        iso.put("AO", "AGO");
        iso.put("AI", "AIA");
        iso.put("AQ", "ATA");
        iso.put("AG", "ATG");
        iso.put("AR", "ARG");
        iso.put("AM", "ARM");
        iso.put("AW", "ABW");
        iso.put("AU", "AUS");
        iso.put("AT", "AUT");
        iso.put("AZ", "AZE");
        iso.put("BS", "BHS");
        iso.put("BH", "BHR");
        iso.put("BD", "BGD");
        iso.put("BB", "BRB");
        iso.put("BY", "BLR");
        iso.put("BE", "BEL");
        iso.put("BZ", "BLZ");
        iso.put("BJ", "BEN");
        iso.put("BM", "BMU");
        iso.put("BT", "BTN");
        iso.put("BO", "BOL");
        iso.put("BQ", "BES");
        iso.put("BA", "BIH");
        iso.put("BW", "BWA");
        iso.put("BV", "BVT");
        iso.put("BR", "BRA");
        iso.put("IO", "IOT");
        iso.put("BN", "BRN");
        iso.put("BG", "BGR");
        iso.put("BF", "BFA");
        iso.put("BI", "BDI");
        iso.put("CV", "CPV");
        iso.put("KH", "KHM");
        iso.put("CM", "CMR");
        iso.put("CA", "CAN");
        iso.put("KY", "CYM");
        iso.put("CF", "CAF");
        iso.put("TD", "TCD");
        iso.put("CL", "CHL");
        iso.put("CN", "CHN");
        iso.put("CX", "CXR");
        iso.put("CC", "CCK");
        iso.put("CO", "COL");
        iso.put("KM", "COM");
        iso.put("CD", "COD");
        iso.put("CG", "COG");
        iso.put("CK", "COK");
        iso.put("CR", "CRI");
        iso.put("HR", "HRV");
        iso.put("CU", "CUB");
        iso.put("CW", "CUW");
        iso.put("CY", "CYP");
        iso.put("CZ", "CZE");
        iso.put("CI", "CIV");
        iso.put("DK", "DNK");
        iso.put("DJ", "DJI");
        iso.put("DM", "DMA");
        iso.put("DO", "DOM");
        iso.put("EC", "ECU");
        iso.put("EG", "EGY");
        iso.put("SV", "SLV");
        iso.put("GQ", "GNQ");
        iso.put("ER", "ERI");
        iso.put("EE", "EST");
        iso.put("SZ", "SWZ");
        iso.put("ET", "ETH");
        iso.put("FK", "FLK");
        iso.put("FO", "FRO");
        iso.put("FJ", "FJI");
        iso.put("FI", "FIN");
        iso.put("FR", "FRA");
        iso.put("GF", "GUF");
        iso.put("PF", "PYF");
        iso.put("TF", "ATF");
        iso.put("GA", "GAB");
        iso.put("GM", "GMB");
        iso.put("GE", "GEO");
        iso.put("DE", "DEU");
        iso.put("GH", "GHA");
        iso.put("GI", "GIB");
        iso.put("GR", "GRC");
        iso.put("GL", "GRL");
        iso.put("GD", "GRD");
        iso.put("GP", "GLP");
        iso.put("GU", "GUM");
        iso.put("GT", "GTM");
        iso.put("GG", "GGY");
        iso.put("GN", "GIN");
        iso.put("GW", "GNB");
        iso.put("GY", "GUY");
        iso.put("HT", "HTI");
        iso.put("HM", "HMD");
        iso.put("VA", "VAT");
        iso.put("HN", "HND");
        iso.put("HK", "HKG");
        iso.put("HU", "HUN");
        iso.put("IS", "ISL");
        iso.put("IN", "IND");
        iso.put("ID", "IDN");
        iso.put("IR", "IRN");
        iso.put("IQ", "IRQ");
        iso.put("IE", "IRL");
        iso.put("IM", "IMN");
        iso.put("IL", "ISR");
        iso.put("IT", "ITA");
        iso.put("JM", "JAM");
        iso.put("JP", "JPN");
        iso.put("JE", "JEY");
        iso.put("JO", "JOR");
        iso.put("KZ", "KAZ");
        iso.put("KE", "KEN");
        iso.put("KI", "KIR");
        iso.put("KP", "PRK");
        iso.put("KR", "KOR");
        iso.put("KW", "KWT");
        iso.put("KG", "KGZ");
        iso.put("LA", "LAO");
        iso.put("LV", "LVA");
        iso.put("LB", "LBN");
        iso.put("LS", "LSO");
        iso.put("LR", "LBR");
        iso.put("LY", "LBY");
        iso.put("LI", "LIE");
        iso.put("LT", "LTU");
        iso.put("LU", "LUX");
        iso.put("MO", "MAC");
        iso.put("MG", "MDG");
        iso.put("MW", "MWI");
        iso.put("MY", "MYS");
        iso.put("MV", "MDV");
        iso.put("ML", "MLI");
        iso.put("MT", "MLT");
        iso.put("MH", "MHL");
        iso.put("MQ", "MTQ");
        iso.put("MR", "MRT");
        iso.put("MU", "MUS");
        iso.put("YT", "MYT");
        iso.put("MX", "MEX");
        iso.put("FM", "FSM");
        iso.put("MD", "MDA");
        iso.put("MC", "MCO");
        iso.put("MN", "MNG");
        iso.put("ME", "MNE");
        iso.put("MS", "MSR");
        iso.put("MA", "MAR");
        iso.put("MZ", "MOZ");
        iso.put("MM", "MMR");
        iso.put("NA", "NAM");
        iso.put("NR", "NRU");
        iso.put("NP", "NPL");
        iso.put("NL", "NLD");
        iso.put("NC", "NCL");
        iso.put("NZ", "NZL");
        iso.put("NI", "NIC");
        iso.put("NE", "NER");
        iso.put("NG", "NGA");
        iso.put("NU", "NIU");
        iso.put("NF", "NFK");
        iso.put("MP", "MNP");
        iso.put("NO", "NOR");
        iso.put("OM", "OMN");
        iso.put("PK", "PAK");
        iso.put("PW", "PLW");
        iso.put("PS", "PSE");
        iso.put("PA", "PAN");
        iso.put("PG", "PNG");
        iso.put("PY", "PRY");
        iso.put("PE", "PER");
        iso.put("PH", "PHL");
        iso.put("PN", "PCN");
        iso.put("PL", "POL");
        iso.put("PT", "PRT");
        iso.put("PR", "PRI");
        iso.put("QA", "QAT");
        iso.put("MK", "MKD");
        iso.put("RO", "ROU");
        iso.put("RU", "RUS");
        iso.put("RW", "RWA");
        iso.put("RE", "REU");
        iso.put("BL", "BLM");
        iso.put("SH", "SHN");
        iso.put("KN", "KNA");
        iso.put("LC", "LCA");
        iso.put("MF", "MAF");
        iso.put("PM", "SPM");
        iso.put("VC", "VCT");
        iso.put("WS", "WSM");
        iso.put("SM", "SMR");
        iso.put("ST", "STP");
        iso.put("SA", "SAU");
        iso.put("SN", "SEN");
        iso.put("RS", "SRB");
        iso.put("SC", "SYC");
        iso.put("SL", "SLE");
        iso.put("SG", "SGP");
        iso.put("SX", "SXM");
        iso.put("SK", "SVK");
        iso.put("SI", "SVN");
        iso.put("SB", "SLB");
        iso.put("SO", "SOM");
        iso.put("ZA", "ZAF");
        iso.put("GS", "SGS");
        iso.put("SS", "SSD");
        iso.put("ES", "ESP");
        iso.put("LK", "LKA");
        iso.put("SD", "SDN");
        iso.put("SR", "SUR");
        iso.put("SJ", "SJM");
        iso.put("SE", "SWE");
        iso.put("CH", "CHE");
        iso.put("SY", "SYR");
        iso.put("TW", "TWN");
        iso.put("TJ", "TJK");
        iso.put("TZ", "TZA");
        iso.put("TH", "THA");
        iso.put("TL", "TLS");
        iso.put("TG", "TGO");
        iso.put("TK", "TKL");
        iso.put("TO", "TON");
        iso.put("TT", "TTO");
        iso.put("TN", "TUN");
        iso.put("TR", "TUR");
        iso.put("TM", "TKM");
        iso.put("TC", "TCA");
        iso.put("TV", "TUV");
        iso.put("UG", "UGA");
        iso.put("UA", "UKR");
        iso.put("AE", "ARE");
        iso.put("GB", "GBR");
        iso.put("UM", "UMI");
        iso.put("US", "USA");
        iso.put("UY", "URY");
        iso.put("UZ", "UZB");
        iso.put("VU", "VUT");
        iso.put("VE", "VEN");
        iso.put("VN", "VNM");
        iso.put("VG", "VGB");
        iso.put("VI", "VIR");
        iso.put("WF", "WLF");
        iso.put("EH", "ESH");
        iso.put("YE", "YEM");
        iso.put("ZM", "ZMB");
        iso.put("ZW", "ZWE");
        iso.put("AX", "ALA");

        return iso;
    }

    private Map<String, String> getCountriesMap() {
        Map<String, String> countries = new HashMap<>();
        countries.put("ABW", "Aruba");
        countries.put("AFG", "Afghanistan");
        countries.put("AGO", "Angola");
        countries.put("AIA", "Anguilla");
        countries.put("ALA", "Aland Islands");
        countries.put("ALB", "Albania");
        countries.put("AND", "Andorra");
        countries.put("ARE", "United Arab Emirates");
        countries.put("ARG", "Argentina");
        countries.put("ARM", "Armenia");
        countries.put("ASM", "American Samoa");
        countries.put("ATA", "Antarctica");
        countries.put("ATF", "French Southern Territories");
        countries.put("ATG", "Antigua and Barbuda");
        countries.put("AUS", "Australia");
        countries.put("AUT", "Austria");
        countries.put("AZE", "Azerbaijan");
        countries.put("BDI", "Burundi");
        countries.put("BEL", "Belgium");
        countries.put("BEN", "Benin");
        countries.put("BES", "Bonaire, Sint Eustatius and Saba");
        countries.put("BFA", "Burkina Faso");
        countries.put("BGD", "Bangladesh");
        countries.put("BGR", "Bulgaria");
        countries.put("BHR", "Bahrain");
        countries.put("BHS", "Bahamas");
        countries.put("BIH", "Bosnia and Herzegovina");
        countries.put("BLM", "Saint Barthelemy");
        countries.put("BLR", "Belarus");
        countries.put("BLZ", "Belize");
        countries.put("BMU", "Bermuda");
        countries.put("BOL", "Bolivia");
        countries.put("BRA", "Brazil");
        countries.put("BRB", "Barbados");
        countries.put("BRN", "Brunei Darussalam");
        countries.put("BTN", "Bhutan");
        countries.put("BVT", "Bouvet Island");
        countries.put("BWA", "Botswana");
        countries.put("CAF", "Central African Republic");
        countries.put("CAN", "Canada");
        countries.put("CCK", "Cocos (Keeling) Islands");
        countries.put("CHE", "Switzerland");
        countries.put("CHL", "Chile");
        countries.put("CHN", "China");
        countries.put("CIV", "Cote d'Ivoire");
        countries.put("CMR", "Cameroon");
        countries.put("COD", "Congo");
        countries.put("COG", "Congo");
        countries.put("COK", "Cook Islands");
        countries.put("COL", "Colombia");
        countries.put("COM", "Comoros");
        countries.put("CPV", "Cabo Verde");
        countries.put("CRI", "Costa Rica");
        countries.put("CUB", "Cuba");
        countries.put("CUW", "Curacao");
        countries.put("CXR", "Christmas Island");
        countries.put("CYM", "Cayman Islands");
        countries.put("CYP", "Cyprus");
        countries.put("CZE", "Czechia");
        countries.put("DEU", "Germany");
        countries.put("DJI", "Djibouti");
        countries.put("DMA", "Dominica");
        countries.put("DNK", "Denmark");
        countries.put("DOM", "Dominican Republic");
        countries.put("DZA", "Algeria");
        countries.put("ECU", "Ecuador");
        countries.put("EGY", "Egypt");
        countries.put("ERI", "Eritrea");
        countries.put("ESH", "Western Sahara");
        countries.put("ESP", "Spain");
        countries.put("EST", "Estonia");
        countries.put("ETH", "Ethiopia");
        countries.put("FIN", "Finland");
        countries.put("FJI", "Fiji");
        countries.put("FLK", "Falkland Islands (Malvinas)");
        countries.put("FRA", "France");
        countries.put("FRO", "Faroe Islands");
        countries.put("FSM", "Micronesia, Federated States of");
        countries.put("GAB", "Gabon");
        countries.put("GBR", "United Kingdom");
        countries.put("GEO", "Georgia");
        countries.put("GGY", "Guernsey");
        countries.put("GHA", "Ghana");
        countries.put("GIB", "Gibraltar");
        countries.put("GIN", "Guinea");
        countries.put("GLP", "Guadeloupe");
        countries.put("GMB", "Gambia");
        countries.put("GNB", "Guinea-Bissau");
        countries.put("GNQ", "Equatorial Guinea");
        countries.put("GRC", "Greece");
        countries.put("GRD", "Grenada");
        countries.put("GRL", "Greenland");
        countries.put("GTM", "Guatemala");
        countries.put("GUF", "French Guiana");
        countries.put("GUM", "Guam");
        countries.put("GUY", "Guyana");
        countries.put("HKG", "Hong Kong");
        countries.put("HMD", "Heard Island and McDonald Islands");
        countries.put("HND", "Honduras");
        countries.put("HRV", "Croatia");
        countries.put("HTI", "Haiti");
        countries.put("HUN", "Hungary");
        countries.put("IDN", "Indonesia");
        countries.put("IMN", "Isle of Man");
        countries.put("IND", "India");
        countries.put("IOT", "British Indian Ocean Territory");
        countries.put("IRL", "Ireland");
        countries.put("IRN", "Iran");
        countries.put("IRQ", "Iraq");
        countries.put("ISL", "Iceland");
        countries.put("ISR", "Israel");
        countries.put("ITA", "Italy");
        countries.put("JAM", "Jamaica");
        countries.put("JEY", "Jersey");
        countries.put("JOR", "Jordan");
        countries.put("JPN", "Japan");
        countries.put("KAZ", "Kazakhstan");
        countries.put("KEN", "Kenya");
        countries.put("KGZ", "Kyrgyzstan");
        countries.put("KHM", "Cambodia");
        countries.put("KIR", "Kiribati");
        countries.put("KNA", "Saint Kitts and Nevis");
        countries.put("KOR", "Korea, Republic of");
        countries.put("KWT", "Kuwait");
        countries.put("LAO", "Lao People's Democratic Republic");
        countries.put("LBN", "Lebanon");
        countries.put("LBR", "Liberia");
        countries.put("LBY", "Libya");
        countries.put("LCA", "Saint Lucia");
        countries.put("LIE", "Liechtenstein");
        countries.put("LKA", "Sri Lanka");
        countries.put("LSO", "Lesotho");
        countries.put("LTU", "Lithuania");
        countries.put("LUX", "Luxembourg");
        countries.put("LVA", "Latvia");
        countries.put("MAC", "Macao");
        countries.put("MAF", "Saint Martin (French part)");
        countries.put("MAR", "Morocco");
        countries.put("MCO", "Monaco");
        countries.put("MDA", "Moldova");
        countries.put("MDG", "Madagascar");
        countries.put("MDV", "Maldives");
        countries.put("MEX", "Mexico");
        countries.put("MHL", "Marshall Islands");
        countries.put("MKD", "North Macedonia");
        countries.put("MLI", "Mali");
        countries.put("MLT", "Malta");
        countries.put("MMR", "Myanmar");
        countries.put("MNE", "Montenegro");
        countries.put("MNG", "Mongolia");
        countries.put("MNP", "Northern Mariana Islands");
        countries.put("MOZ", "Mozambique");
        countries.put("MRT", "Mauritania");
        countries.put("MSR", "Montserrat");
        countries.put("MTQ", "Martinique");
        countries.put("MUS", "Mauritius");
        countries.put("MWI", "Malawi");
        countries.put("MYS", "Malaysia");
        countries.put("MYT", "Mayotte");
        countries.put("NAM", "Namibia");
        countries.put("NCL", "New Caledonia");
        countries.put("NER", "Niger");
        countries.put("NFK", "Norfolk Island");
        countries.put("NGA", "Nigeria");
        countries.put("NIC", "Nicaragua");
        countries.put("NIU", "Niue");
        countries.put("NLD", "Netherlands");
        countries.put("NOR", "Norway");
        countries.put("NPL", "Nepal");
        countries.put("NRU", "Nauru");
        countries.put("NZL", "New Zealand");
        countries.put("OMN", "Oman");
        countries.put("PAK", "Pakistan");
        countries.put("PAN", "Panama");
        countries.put("PCN", "Pitcairn");
        countries.put("PER", "Peru");
        countries.put("PHL", "Philippines");
        countries.put("PLW", "Palau");
        countries.put("PNG", "Papua New Guinea");
        countries.put("POL", "Poland");
        countries.put("PRI", "Puerto Rico");
        countries.put("PRK", "Korea");
        countries.put("PRT", "Portugal");
        countries.put("PRY", "Paraguay");
        countries.put("PSE", "Palestine");
        countries.put("PYF", "French Polynesia");
        countries.put("QAT", "Qatar");
        countries.put("REU", "Réunion");
        countries.put("ROU", "Romania");
        countries.put("RUS", "Russian Federation");
        countries.put("RWA", "Rwanda");
        countries.put("SAU", "Saudi Arabia");
        countries.put("SDN", "Sudan");
        countries.put("SEN", "Senegal");
        countries.put("SGP", "Singapore");
        countries.put("SGS", "South Georgia and the South Sandwich Islands");
        countries.put("SHN", "Saint Helena, Ascension and Tristan da Cunha");
        countries.put("SJM", "Svalbard and Jan Mayen");
        countries.put("SLB", "Solomon Islands");
        countries.put("SLE", "Sierra Leone");
        countries.put("SLV", "El Salvador");
        countries.put("SMR", "San Marino");
        countries.put("SOM", "Somalia");
        countries.put("SPM", "Saint Pierre and Miquelon");
        countries.put("SRB", "Serbia");
        countries.put("SSD", "South Sudan");
        countries.put("STP", "Sao Tome and Principe");
        countries.put("SUR", "Suriname");
        countries.put("SVK", "Slovakia");
        countries.put("SVN", "Slovenia");
        countries.put("SWE", "Sweden");
        countries.put("SWZ", "Eswatini");
        countries.put("SXM", "Sint Maarten (Dutch part)");
        countries.put("SYC", "Seychelles");
        countries.put("SYR", "Syrian Arab Republic");
        countries.put("TCA", "Turks and Caicos Islands");
        countries.put("TCD", "Chad");
        countries.put("TGO", "Togo");
        countries.put("THA", "Thailand");
        countries.put("TJK", "Tajikistan");
        countries.put("TKL", "Tokelau");
        countries.put("TKM", "Turkmenistan");
        countries.put("TLS", "Timor-Leste");
        countries.put("TON", "Tonga");
        countries.put("TTO", "Trinidad and Tobago");
        countries.put("TUN", "Tunisia");
        countries.put("TUR", "Türkiye");
        countries.put("TUV", "Tuvalu");
        countries.put("TWN", "Taiwan, Province of China");
        countries.put("TZA", "Tanzania");
        countries.put("UGA", "Uganda");
        countries.put("UKR", "Ukraine");
        countries.put("UMI", "United States Minor Outlying Islands");
        countries.put("URY", "Uruguay");
        countries.put("USA", "United States of America");
        countries.put("UZB", "Uzbekistan");
        countries.put("VAT", "Holy See");
        countries.put("VCT", "Saint Vincent and the Grenadines");
        countries.put("VEN", "Venezuela");
        countries.put("VGB", "Virgin Islands (British)");
        countries.put("VIR", "Virgin Islands (U.S.)");
        countries.put("VNM", "Viet Nam");
        countries.put("VUT", "Vanuatu");
        countries.put("WLF", "Wallis and Futuna");
        countries.put("WSM", "Samoa");
        countries.put("YEM", "Yemen");
        countries.put("ZAF", "South Africa");
        countries.put("ZMB", "Zambia");
        countries.put("ZWE", "Zimbabwe");

        return countries;
    }
}
