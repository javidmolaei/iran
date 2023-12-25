package ir.javid.iran.service;

import ir.javid.iran.model.Country;
import ir.javid.iran.model.State;
import ir.javid.iran.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: Mr.javidmolaei
 */

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void initializeCountries() {
        if (countryRepository.count() == 0) {

            String s = "\"Afghanistan\",\"Albania\",\"Algeria\",\"Andorra\",\"Angola\",\"Antigua & Deps\",\"Argentina\",\"Armenia\",\"Australia\",\"Austria\",\"Azerbaijan\",\"Bahamas\",\"Bahrain\",\"Bangladesh\",\"Barbados\",\"Belarus\",\"Belgium\",\"Belize\",\"Benin\",\"Bhutan\",\"Bolivia\",\"Bosnia Herzegovina\",\"Botswana\",\"Brazil\",\"Brunei\",\"Bulgaria\",\"Burkina\",\"Burundi\",\"Cambodia\",\"Cameroon\",\"Canada\",\"Cape Verde\",\"Central African Rep\",\"Chad\",\"Chile\",\"China\",\"Colombia\",\"Comoros\",\"Congo\",\"Congo Democratic Rep\",\"Costa Rica\",\"Croatia\",\"Cuba\",\"Cyprus\",\"Czech Republic\",\"Denmark\",\"Djibouti\",\"Dominica\",\"Dominican Republic\",\"East Timor\",\"Ecuador\",\"Egypt\",\"El Salvador\",\"Equatorial Guinea\",\"Eritrea\",\"Estonia\",\"Ethiopia\",\"Fiji\",\"Finland\",\"France\",\"Gabon\",\"Gambia\",\"Georgia\",\"Germany\",\"Ghana\",\"Greece\",\"Grenada\",\"Guatemala\",\"Guinea\",\"Guinea Bissau\",\"Guyana\",\"Haiti\",\"Honduras\",\"Hungary\",\"Iceland\",\"India\",\"Indonesia\",\"Iran\",\"Iraq\",\"Ireland Republic\",\"Israel\",\"Italy\",\"Ivory Coast\",\"Jamaica\",\"Japan\",\"Jordan\",\"Kazakhstan\",\"Kenya\",\"Kiribati\",\"Korea North\",\"Korea South\",\"Kosovo\",\"Kuwait\",\"Kyrgyzstan\",\"Laos\",\"Latvia\",\"Lebanon\",\"Lesotho\",\"Liberia\",\"Libya\",\"Liechtenstein\",\"Lithuania\",\"Luxembourg\",\"Macedonia\",\"Madagascar\",\"Malawi\",\"Malaysia\",\"Maldives\",\"Mali\",\"Malta\",\"Marshall Islands\",\"Mauritania\",\"Mauritius\",\"Mexico\",\"Micronesia\",\"Moldova\",\"Monaco\",\"Mongolia\",\"Montenegro\",\"Morocco\",\"Mozambique\",\"Myanmar\",\"Namibia\",\"Nauru\",\"Nepal\",\"Netherlands\",\"New Zealand\",\"Nicaragua\",\"Niger\",\"Nigeria\",\"Norway\",\"Oman\",\"Pakistan\",\"Palau\",\"Panama\",\"Papua New Guinea\",\"Paraguay\",\"Peru\",\"Philippines\",\"Poland\",\"Portugal\",\"Qatar\",\"Romania\",\"Russian Federation\",\"Rwanda\",\"St Kitts & Nevis\",\"St Lucia\",\"Saint Vincent & the Grenadines\",\"Samoa\",\"San Marino\",\"Sao Tome & Principe\",\"Saudi Arabia\",\"Senegal\",\"Serbia\",\"Seychelles\",\"Sierra Leone\",\"Singapore\",\"Slovakia\",\"Slovenia\",\"Solomon Islands\",\"Somalia\",\"South Africa\",\"South Sudan\",\"Spain\",\"Sri Lanka\",\"Sudan\",\"Suriname\",\"Swaziland\",\"Sweden\",\"Switzerland\",\"Syria\",\"Taiwan\",\"Tajikistan\",\"Tanzania\",\"Thailand\",\"Togo\",\"Tonga\",\"Trinidad & Tobago\",\"Tunisia\",\"Turkey\",\"Turkmenistan\",\"Tuvalu\",\"Uganda\",\"Ukraine\",\"United Arab Emirates\",\"United Kingdom\",\"United States\",\"Uruguay\",\"Uzbekistan\",\"Vanuatu\",\"Vatican City\",\"Venezuela\",\"Vietnam\",\"Yemen\",\"Zambia\",\"Zimbabwe\"";
            String[] split = s.split(",");
            for (String s1 : split) {
                addCountry(s1.replace("\"",""));
            }
        }
    }


    private void addCountry(String countryName) {
        countryRepository.save(new Country(countryName));
    }
}
