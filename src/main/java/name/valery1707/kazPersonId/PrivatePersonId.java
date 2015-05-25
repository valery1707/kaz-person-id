package name.valery1707.kazPersonId;

import java.sql.Date;
import java.util.regex.Pattern;

import static name.valery1707.kazPersonId.PersonIdUtils.*;

/**
 * <a href="http://egov.kz/wps/portal/Content?contentPath=/egovcontent/passport_id_card/article/iin_info&amp;lang=ru">Private person ID/ИИН</a>
 */
public class PrivatePersonId extends BasePersonId {
	private final Date birthDate;
	private final Sex sex;

	public PrivatePersonId(String id) {
		super(id);
		birthDate = detectBirthDate(id);
		sex = detectSex(id);
	}

	@Override
	protected Pattern getIdPattern() {
		return PRIVATE_ID_PATTERN;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public Sex getSex() {
		return sex;
	}
}
