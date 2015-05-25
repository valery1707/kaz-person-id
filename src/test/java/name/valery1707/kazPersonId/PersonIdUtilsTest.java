package name.valery1707.kazPersonId;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PersonIdUtilsTest {

	@Test
	public void testIsValidCRC() throws Exception {
		assertThat(PersonIdUtils.isValidCRC(null)).isFalse();
		assertThat(PersonIdUtils.isValidCRC("")).isFalse();
		assertThat(PersonIdUtils.isValidCRC("0123456789AB")).isFalse();
	}

	@Test
	public void testDetectSex() throws Exception {
		assertThat(PersonIdUtils.detectSex(null)).isNull();
		assertThat(PersonIdUtils.detectSex("")).isNull();
		assertThat(PersonIdUtils.detectSex("830717")).isNull();
	}

	@Test
	public void testDetectBirthDate() throws Exception {
		assertThat(PersonIdUtils.detectBirthDate(null)).isNull();
		assertThat(PersonIdUtils.detectBirthDate("")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("83071")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("830717")).isNull();
		assertThat(PersonIdUtils.detectBirthDate("8307173").toString()).isEqualTo("1983-07-17");
	}

	@Test
	public void testDetectRegistrationDate() throws Exception {
		assertThat(PersonIdUtils.detectRegistrationDate(null)).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("95")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("950")).isNull();
		assertThat(PersonIdUtils.detectRegistrationDate("9503").toString()).isEqualTo("1995-03-01");
	}

	@Test
	public void testDetectJuridicalPersonType() throws Exception {
		assertThat(PersonIdUtils.detectJuridicalPersonType(null)).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("9503")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonType("95034")).isSameAs(JuridicalPersonType.RESIDENT);
	}

	@Test
	public void testDetectJuridicalPersonAttribute() throws Exception {
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute(null)).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("9503")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("95034")).isNull();
		assertThat(PersonIdUtils.detectJuridicalPersonAttribute("950341")).isSameAs(JuridicalPersonAttribute.AFFILIATED_BRANCH);
	}
}