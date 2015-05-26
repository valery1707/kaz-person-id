package name.valery1707.kazPersonId;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class EnumTest {

	@Test
	public void testEnumCoverage() throws Exception {
		for (Sex value : Sex.values()) {
			assertThat(Sex.valueOf(value.toString())).isSameAs(value);
		}
		for (JuridicalPersonType value : JuridicalPersonType.values()) {
			assertThat(JuridicalPersonType.valueOf(value.toString())).isSameAs(value);
		}
		for (JuridicalPersonAttribute value : JuridicalPersonAttribute.values()) {
			assertThat(JuridicalPersonAttribute.valueOf(value.toString())).isSameAs(value);
		}
	}
}
