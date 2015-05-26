package name.valery1707.kazPersonId;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public final class PersonIdUtils {
	protected PersonIdUtils() {
		throw new IllegalStateException("Utils can not be created");
	}

	private static final int ID_LENGTH = 12;

	/**
	 * <a href="http://egov.kz/wps/portal/Content?contentPath=/egovcontent/passport_id_card/article/iin_info&amp;lang=ru">Private person ID/ИИН</a>
	 */
	public static final String PRIVATE_ID_REGEXP =
			"^" +
			"\\d{2}" +                        //Year: any
			"(0[1-9]|1[0-2])" +               //Month: 01-12
			"(0[1-9]|[1-2][0-9]|3[0-1])" +    //Day: 01-31
			"[1-6]" +                         //Sex and Century bit mask
			// 1 - 19 Century, Male
			// 2 - 19 Century, Female
			// 3 - 20 Century, Male
			// 4 - 20 Century, Female
			// 5 - 21 Century, Male
			// 6 - 21 Century, Female
			"\\d{4}" +                        //Registration number
			"\\d" +                           //CRC
			"$";
	public static final Pattern PRIVATE_ID_PATTERN = Pattern.compile(PRIVATE_ID_REGEXP);

	/**
	 * <a href="http://egov.kz/wps/portal/Content?contentPath=/egovcontent/bus_business/for_businessmen/article/business_identification_number&amp;lang=ru">Juridical person ID/БИН</a>
	 */
	public static final String JURIDICAL_ID_REGEXP =
			"^" +
			"\\d{2}" +            //Year: any
			"(0[1-9]|1[0-2])" +   //Month: 01-12
			"[4-6]" +             //Juridical person type or Individual entrepreneur:
			// 4 - Juridical person resident     // Юридическое лицо резидент
			// 5 - Juridical person non-resident // Юридическое лице не резидент
			// 6 - Individual entrepreneur       // ИП
			"[0-3]" +             //Additional attribute:
			// 0 - Head unit of Juridical person or Individual entrepreneur             // Головное подразделение юридического лица или ИП(С)
			// 1 - Affiliated branch of Juridical person or Individual entrepreneur     // Филиал юридического лица или ИП(С)
			// 2 - Representation office of Juridical person or Individual entrepreneur // Представительство юридического лица или ИП(С)
			// 3 - Farming                                                              // Крестьянское (фермерское) хозяйство, осуществляющее деятельность на основе совместного предпринимательства
			"\\d{5}" +            //Registration number
			"\\d" +               //CRC
			"$";
	public static final Pattern JURIDICAL_ID_PATTERN = Pattern.compile(JURIDICAL_ID_REGEXP);

	protected static boolean isDigits(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	private static final int[][] CRC_WEIGHTS = new int[][]{
			new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
			new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2},
	};
	private static final int CRC_MODE = 11;
	private static final int CRC_INCORRECT = 10;

	public static int calculateCRC(String id) {
		if (id == null || id.length() < CRC_WEIGHTS[0].length || !isDigits(id)) {
			return -1;
		}
		int crc = CRC_INCORRECT;
		int weightPos = 0;
		while (crc == CRC_INCORRECT && weightPos < CRC_WEIGHTS.length) {
			crc = 0;
			for (int i = 0; i < ID_LENGTH - 1; i++) {
				crc += charAsDigit(id.charAt(i)) * CRC_WEIGHTS[weightPos][i];
			}
			crc = crc % CRC_MODE;
			weightPos++;
		}
		return crc;
	}

	public static boolean isValidCRC(String id) {
		if (id == null || id.length() != ID_LENGTH || !isDigits(id)) {
			return false;
		}
		int testCrc = charAsDigit(id.charAt(ID_LENGTH - 1));
		int crc = calculateCRC(id);
		return crc == testCrc;
	}

	private static int charAsDigit(char c) {
		return Character.digit(c, 10);
	}

	private static final int SEX_AND_CENTURY_POS = 6;

	public static Sex detectSex(String id) {
		if (id == null || id.length() <= SEX_AND_CENTURY_POS) {
			return null;
		}
		char sex = id.charAt(SEX_AND_CENTURY_POS);
		switch (sex) {
			case '1':
			case '3':
			case '5':
				return Sex.MALE;
			case '2':
			case '4':
			case '6':
				return Sex.FEMALE;
		}
		//Unknown Sex code
		return null;
	}

	public static final Pattern DATE_PATTERN = Pattern.compile(
			"^" +
			"\\d{2}" +                        //Year: any
			"(0[1-9]|1[0-2])" +               //Month: 01-12
			"(0[1-9]|[1-2][0-9]|3[0-1])" +    //Day: 01-31
			"$"
	);

	private static boolean isDate(String s) {
		return DATE_PATTERN.matcher(s).matches();
	}

	private static final String BIRTH_DATE_FORMAT = "yyMMdd";
	private static final Calendar[] YEAR_PREFIXES = new Calendar[]{
			//Unassigned: 0
			null,
			//18xx: 1, 2
			new GregorianCalendar(1800, Calendar.JANUARY, 1),
			new GregorianCalendar(1800, Calendar.JANUARY, 1),
			//19xx: 3, 4
			new GregorianCalendar(1900, Calendar.JANUARY, 1),
			new GregorianCalendar(1900, Calendar.JANUARY, 1),
			//20xx: 5, 6
			new GregorianCalendar(2000, Calendar.JANUARY, 1),
			new GregorianCalendar(2000, Calendar.JANUARY, 1),
			//Unassigned: 7, 8, 9
			null,
			null,
			null,
	};

	public static Date detectBirthDate(String id) {
		if (id == null || id.length() <= SEX_AND_CENTURY_POS) {
			return null;
		}
		String date = id.substring(0, SEX_AND_CENTURY_POS);
		if (!isDigits(date) || !isDate(date)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(BIRTH_DATE_FORMAT);
		dateFormat.setLenient(false);
		int yearPrefixIndex = charAsDigit(id.charAt(SEX_AND_CENTURY_POS));
		Calendar yearPrefix = YEAR_PREFIXES[(yearPrefixIndex)];
		if (yearPrefix != null) {
			dateFormat.set2DigitYearStart(yearPrefix.getTime());
		}
		try {
			return new Date(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			//todo Throw exception?
			return null;
		}
	}

	private static final String REGISTRATION_DATE_FORMAT = "yyMM";
	private static final int REGISTRATION_DATE_LENGTH = REGISTRATION_DATE_FORMAT.length();

	public static Date detectRegistrationDate(String id) {
		if (id == null || id.length() < REGISTRATION_DATE_LENGTH || !PersonIdUtils.isDigits(id.substring(0, REGISTRATION_DATE_LENGTH))) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(REGISTRATION_DATE_FORMAT);
		dateFormat.setLenient(false);
		try {
			return new Date(dateFormat.parse(id.substring(0, REGISTRATION_DATE_LENGTH)).getTime());
		} catch (ParseException e) {
			//todo Throw exception?
			return null;
		}
	}

	private static final int TYPE_POS = REGISTRATION_DATE_LENGTH;

	public static JuridicalPersonType detectJuridicalPersonType(String id) {
		if (id == null || id.length() <= TYPE_POS) {
			return null;
		}
		switch (id.charAt(TYPE_POS)) {
			case '4':
				return JuridicalPersonType.RESIDENT;
			case '5':
				return JuridicalPersonType.NON_RESIDENT;
			case '6':
				return JuridicalPersonType.INDIVIDUAL;
		}
		return null;
	}

	private static final int ATTRIBUTE_POS = TYPE_POS + 1;

	public static JuridicalPersonAttribute detectJuridicalPersonAttribute(String id) {
		if (id == null || id.length() <= ATTRIBUTE_POS) {
			return null;
		}
		switch (id.charAt(ATTRIBUTE_POS)) {
			case '0':
				return JuridicalPersonAttribute.HEAD_UNIT;
			case '1':
				return JuridicalPersonAttribute.AFFILIATED_BRANCH;
			case '2':
				return JuridicalPersonAttribute.REPRESENTATION_OFFICE;
			case '3':
				return JuridicalPersonAttribute.FARMING;
		}
		return null;
	}
}
