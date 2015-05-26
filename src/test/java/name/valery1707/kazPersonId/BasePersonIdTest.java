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

	private BasePersonId id1() {
		return new JuridicalPersonId("940140000385");
	}

	private BasePersonId id2() {
		return new JuridicalPersonId("950341000052");
	}

	@Test
	@SuppressWarnings("EqualsWithItself")
	public void testEquals() throws Exception {
		BasePersonId idNull = new JuridicalPersonId(null);
		BasePersonId id1 = id1();
		BasePersonId id2 = id2();

		assertThat(idNull).isEqualTo(idNull);
		assertThat(idNull.equals(idNull)).isTrue();
		assertThat(idNull).isEqualTo(new JuridicalPersonId(null));
		assertThat(idNull).isNotEqualTo("");
		assertThat(idNull).isNotEqualTo(id1);
		assertThat(idNull).isNotEqualTo(id2);

		assertThat(id1).isNotEqualTo(idNull);
		assertThat(id1).isEqualTo(id1);
		assertThat(id1).isEqualTo(id1());
		assertThat(id1).isNotEqualTo(id2);

		assertThat(id2).isNotEqualTo(idNull);
		assertThat(id2).isNotEqualTo(id1);
		assertThat(id2).isEqualTo(id2);
		assertThat(id2).isEqualTo(id2());
	}
}