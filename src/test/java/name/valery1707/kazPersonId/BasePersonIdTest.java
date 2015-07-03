package name.valery1707.kazPersonId;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

	private JuridicalPersonId idNull() {
		return new JuridicalPersonId(null);
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
		BasePersonId idNull = idNull();
		BasePersonId id1 = id1();
		BasePersonId id2 = id2();

		assertThat(idNull).isEqualTo(idNull);
		assertThat(idNull.equals(idNull)).isTrue();
		assertThat(idNull).isEqualTo(idNull());
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

	@Test
	public void testHashcode() throws Exception {
		Set<BasePersonId> ids = new HashSet<BasePersonId>();
		ids.add(id1());
		ids.add(id1());
		ids.add(id2());
		ids.add(idNull());
		assertThat(ids).hasSize(3);
		assertThat(ids).containsOnly(id1(), id2(), idNull());
	}
}