package name.valery1707.kazPersonId;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BasePersonIdTest {

	@Test
	public void testIsValid() throws Exception {
		BasePersonId id;

		id = new JuridicalPersonId(null);
		assertThat(id.isValidPattern()).isFalse();
		assertThat(id.isValidCRC()).isFalse();
		assertThat(id.isValid()).isFalse();

		id = new JuridicalPersonId("");
		assertThat(id.isValidPattern()).isFalse();
		assertThat(id.isValidCRC()).isFalse();
		assertThat(id.isValid()).isFalse();

		id = new JuridicalPersonId("0123456789AB");
		assertThat(id.isValidPattern()).isFalse();
		assertThat(id.isValidCRC()).isFalse();
		assertThat(id.isValid()).isFalse();

		id = new JuridicalPersonId("940140000380");
		assertThat(id.isValidPattern()).isTrue();
		assertThat(id.isValidCRC()).isFalse();
		assertThat(id.isValid()).isFalse();
	}
}