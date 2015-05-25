package name.valery1707.kazPersonId;

import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

import static com.google.common.truth.Truth.assertThat;

public class JuridicalPersonIdTest {

	/**
	 * <a href="http://www.halykbank.kz/ru/requisites/affiliates">Public source</a>
	 */
	@Test
	public void testPublicIds() throws Exception {
		JuridicalPersonId id;

		id = new JuridicalPersonId("940140000385");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getRegistrationDate().toString()).isEqualTo(registrationDate(1994, 1));
		assertThat(id.getType()).isSameAs(JuridicalPersonType.RESIDENT);
		assertThat(id.getAttribute()).isSameAs(JuridicalPersonAttribute.HEAD_UNIT);

		id = new JuridicalPersonId("950341000052");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getRegistrationDate().toString()).isEqualTo(registrationDate(1995, 3));
		assertThat(id.getType()).isSameAs(JuridicalPersonType.RESIDENT);
		assertThat(id.getAttribute()).isSameAs(JuridicalPersonAttribute.AFFILIATED_BRANCH);

		id = new JuridicalPersonId("961141000023");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getRegistrationDate().toString()).isEqualTo(registrationDate(1996, 11));
		assertThat(id.getType()).isSameAs(JuridicalPersonType.RESIDENT);
		assertThat(id.getAttribute()).isSameAs(JuridicalPersonAttribute.AFFILIATED_BRANCH);

		id = new JuridicalPersonId("070941024691");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getRegistrationDate().toString()).isEqualTo(registrationDate(2007, 9));
		assertThat(id.getType()).isSameAs(JuridicalPersonType.RESIDENT);
		assertThat(id.getAttribute()).isSameAs(JuridicalPersonAttribute.AFFILIATED_BRANCH);
	}

	@SuppressWarnings("MagicConstant")
	private String registrationDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		return new Date(calendar.getTimeInMillis()).toString();
	}
}