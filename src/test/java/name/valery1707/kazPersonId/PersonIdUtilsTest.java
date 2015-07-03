package name.valery1707.kazPersonId;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonIdUtilsTest {

	@Test(expected = IllegalStateException.class)
	public void testUtilsCreation() throws Exception {
		new PersonIdUtils();
	}

	@Test
	public void testIsValidCRC() throws Exception {
		assertThat(PersonIdUtils.isValidCRC(null)).isFalse();
		assertThat(PersonIdUtils.isValidCRC("")).isFalse();
		assertThat(PersonIdUtils.isValidCRC("0123456789AB")).isFalse();
	}

	@Test
	public void testCalculateCRC() throws Exception {
		assertThat(PersonIdUtils.calculateCRC(null)).isLessThan(0);
		assertThat(PersonIdUtils.calculateCRC("")).isLessThan(0);
		assertThat(PersonIdUtils.calculateCRC("0123456789AB")).isLessThan(0);
	}

	@Test
	public void testImportanceOf_11_Sign() throws Exception {
		//For ID's which can calculate CRC in first pass: 11-n sign does not important because it weight aliquot 11 and does not change final CRC value

		//For ID's which can calculate CRC only in second pass: all signs important
		testImportanceOfSign("830717350867", 11);
	}

	private void testImportanceOfSign(String id, int testSignIndex) {
		int testSign = Character.digit(id.charAt(testSignIndex - 1), 10);
		int testCrc = Character.digit(id.charAt(id.length() - 1), 10);
		String testIdPrefix = id.substring(0, testSignIndex - 1);
		String testIdSuffix = id.substring(testSignIndex);
		assertThat(PersonIdUtils.calculateCRC(id)).isEqualTo(testCrc);
		for (int sign = 0; sign <= 9; sign++) {
			if (sign == testSign) {
				continue;
			}
			String testId = testIdPrefix + Character.forDigit(sign, 10) + testIdSuffix;
			assertThat(PersonIdUtils.calculateCRC(testId))
					.describedAs("id[%d](%d=>%d)", testSignIndex, testSign, sign)
					.isNotEqualTo(testCrc);
		}
	}

	/**
	 * Testing case than after first pass CRC == 10 AND after second pass CRC still equals 10
	 *
	 * @throws Exception
	 */
	@Test
	public void testIncorrectCrcInSecondPass() throws Exception {
		assertThat(PersonIdUtils.isValidCRC("000813159890")).isFalse();
		assertThat(PersonIdUtils.calculateCRC("000813159890")).isEqualTo(10);
		assertThat(PersonIdUtils.isValidCRC("83071710098")).isFalse();
		assertThat(PersonIdUtils.calculateCRC("83071710098")).isEqualTo(10);
	}

	@Test
	public void testDetectSex() throws Exception {
		assertThat(PersonIdUtils.detectSex(null)).isNull();
		assertThat(PersonIdUtils.detectSex("")).isNull();
		assertThat(PersonIdUtils.detectSex("830717")).isNull();
		assertThat(PersonIdUtils.detectSex("8307170")).isNull();
	}

	@Test
	public void testDetectBirthDate() throws Exception {
		assertThat(PersonIdUtils.detectBirthDate(null)).isNull();
		assertThat(PersonIdUtils.detectBirthDate("")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("83071")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("830717")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("8307173").toString()).isEqualTo("1983-07-17");
		assertThat(PersonIdUtils.detectBirthDate("ABCDEF3")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("8307170").toString()).isEqualTo("1983-07-17");
		assertThat(PersonIdUtils.detectBirthDate("9999990")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("0002310")).isNull();
	}

	@Test
	public void testDetectRegistrationDate() throws Exception {
		assertThat(PersonIdUtils.detectRegistrationDate(null)).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("95")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("950")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("9503").toString()).isEqualTo("1995-03-01");
		assertThat(PersonIdUtils.detectRegistrationDate("ABCD")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("9999")).isNull();
	}

	@Test
	public void testDetectJuridicalPersonType() throws Exception {
		assertThat(PersonIdUtils.detectJuridicalPersonType(null)).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("9503")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("95034")).isSameAs(JuridicalPersonType.RESIDENT);
		String prefix = "9503";
		for (JuridicalPersonType type : JuridicalPersonType.values()) {
			assertThat(PersonIdUtils.detectJuridicalPersonType(prefix + Character.forDigit(type.ordinal() + 4, 10))).isSameAs(type);
		}
		assertThat(PersonIdUtils.detectJuridicalPersonType("95030")).isNull();
	}

	@Test
	public void testDetectJuridicalPersonAttribute() throws Exception {
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute(null)).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("9503")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("95034")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("950341")).isSameAs(JuridicalPersonAttribute.AFFILIATED_BRANCH);
		String prefix = "95034";
		for (JuridicalPersonAttribute attribute : JuridicalPersonAttribute.values()) {
			assertThat(PersonIdUtils.detectJuridicalPersonAttribute(prefix + Character.forDigit(attribute.ordinal(), 10))).isSameAs(attribute);
		}
	}

	public static void main(String[] args) {
		//Search for IDs with "incorrect" CRC after all pass of calculation CRC algorithm
		for (int year = 0; year <= 99; year++) {
			for (int month = 1; month <= 12; month++) {
				for (int day = 1; day <= 31; day++) {
					for (int sex = 1; sex <= 6; sex++) {
						for (int number = 0; number <= 9999; number++) {
							String id = String.format("%02d%02d%02d%d%04d", year, month, day, sex, number);
							int crc = PersonIdUtils.calculateCRC(id);
							if (crc > 9) {
								System.out.println(id);
							}
						}
					}
				}
			}
		}
	}
}
