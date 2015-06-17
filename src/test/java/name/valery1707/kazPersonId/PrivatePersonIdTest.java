package name.valery1707.kazPersonId;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class PrivatePersonIdTest {

	/**
	 * <a href="https://serj.ws/salyk">Public source</a>
	 */
	@Test
	public void testPublicIds() throws Exception {
		PrivatePersonId id;

		id = new PrivatePersonId("871008401427");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1987-10-08");
		assertThat(id.getSex()).isSameAs(Sex.FEMALE);

		id = new PrivatePersonId("760212350620");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1976-02-12");
		assertThat(id.getSex()).isSameAs(Sex.MALE);

		id = new PrivatePersonId("800416300257");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1980-04-16");
		assertThat(id.getSex()).isSameAs(Sex.MALE);

		id = new PrivatePersonId("680503450037");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1968-05-03");
		assertThat(id.getSex()).isSameAs(Sex.FEMALE);
	}

	@Test
	public void testCenturyBit() throws Exception {
		PrivatePersonId id;

		id = new PrivatePersonId("800416100254");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1880-04-16");
		assertThat(id.getSex()).isSameAs(Sex.MALE);

		id = new PrivatePersonId("680503250034");
		assertThat(id.isValid()).isTrue();
		assertThat(id.getBirthDate().toString()).isEqualTo("1868-05-03");
		assertThat(id.getSex()).isSameAs(Sex.FEMALE);
	}
}