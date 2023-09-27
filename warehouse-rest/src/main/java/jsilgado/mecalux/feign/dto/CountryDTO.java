package jsilgado.mecalux.feign.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDTO {

	public Name name;
	
	
	public class Ara{
	    public String official;
	    public String common;
	}

	public class Bre{
	    public String official;
	    public String common;
	}

	public class CapitalInfo{
	    public List<Double> latlng;
	}

	public class Car{
	    public List<String> signs;
	    public String side;
	}

	public class Ces{
	    public String official;
	    public String common;
	}

	public class CoatOfArms{
	    public String png;
	    public String svg;
	}

	public class COP{
	    public String name;
	    public String symbol;
	}

	public class Currencies{
	    @JsonProperty("COP") 
	    public COP cOP;
	}

	public class Cym{
	    public String official;
	    public String common;
	}

	public class Demonyms{
	    public Eng eng;
	    public Fra fra;
	}

	public class Deu{
	    public String official;
	    public String common;
	}

	public class Eng{
	    public String f;
	    public String m;
	}

	public class Est{
	    public String official;
	    public String common;
	}

	public class Fin{
	    public String official;
	    public String common;
	}

	public class Flags{
	    public String png;
	    public String svg;
	    public String alt;
	}

	public class Fra{
	    public String official;
	    public String common;
	    public String f;
	    public String m;
	}

	public class Gini{
	    @JsonProperty("2019") 
	    public double _2019;
	}

	public class Hrv{
	    public String official;
	    public String common;
	}

	public class Hun{
	    public String official;
	    public String common;
	}

	public class Idd{
	    public String root;
	    public List<String> suffixes;
	}

	public class Ita{
	    public String official;
	    public String common;
	}

	public class Jpn{
	    public String official;
	    public String common;
	}

	public class Kor{
	    public String official;
	    public String common;
	}

	public class Languages{
	    public String spa;
	}

	public class Maps{
	    public String googleMaps;
	    public String openStreetMaps;
	}

	@Data
	public class Name{
	    public String common;
	    public String official;
	   // public NativeName nativeName;
	}

	public class NativeName{
	    public Spa spa;
	}

	public class Nld{
	    public String official;
	    public String common;
	}

	public class Per{
	    public String official;
	    public String common;
	}

	public class Pol{
	    public String official;
	    public String common;
	}

	public class Por{
	    public String official;
	    public String common;
	}

	public class Root{
	    public Name name;
	    public List<String> tld;
	    public String cca2;
	    public String ccn3;
	    public String cca3;
	    public String cioc;
	    public boolean independent;
	    public String status;
	    public boolean unMember;
	    public Currencies currencies;
	    public Idd idd;
	    public List<String> capital;
	    public List<String> altSpellings;
	    public String region;
	    public String subregion;
	    public Languages languages;
	    public Translations translations;
	    public List<Integer> latlng;
	    public boolean landlocked;
	    public List<String> borders;
	    public int area;
	    public Demonyms demonyms;
	    public String flag;
	    public Maps maps;
	    public int population;
	    public Gini gini;
	    public String fifa;
	    public Car car;
	    public List<String> timezones;
	    public List<String> continents;
	    public Flags flags;
	    public CoatOfArms coatOfArms;
	    public String startOfWeek;
	    public CapitalInfo capitalInfo;
	}

	public class Rus{
	    public String official;
	    public String common;
	}

	public class Slk{
	    public String official;
	    public String common;
	}

	public class Spa{
	    public String official;
	    public String common;
	}

	public class Srp{
	    public String official;
	    public String common;
	}

	public class Swe{
	    public String official;
	    public String common;
	}

	public class Translations{
	    public Ara ara;
	    public Bre bre;
	    public Ces ces;
	    public Cym cym;
	    public Deu deu;
	    public Est est;
	    public Fin fin;
	    public Fra fra;
	    public Hrv hrv;
	    public Hun hun;
	    public Ita ita;
	    public Jpn jpn;
	    public Kor kor;
	    public Nld nld;
	    public Per per;
	    public Pol pol;
	    public Por por;
	    public Rus rus;
	    public Slk slk;
	    public Spa spa;
	    public Srp srp;
	    public Swe swe;
	    public Tur tur;
	    public Urd urd;
	    public Zho zho;
	}

	public class Tur{
	    public String official;
	    public String common;
	}

	public class Urd{
	    public String official;
	    public String common;
	}

	public class Zho{
	    public String official;
	    public String common;
	}


}
