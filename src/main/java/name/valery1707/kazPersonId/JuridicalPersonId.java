package name.valery1707.kazPersonId;

import java.sql.Date;
import java.util.regex.Pattern;

import static name.valery1707.kazPersonId.PersonIdUtils.*;

/**
 * <a href="http://egov.kz/wps/portal/Content?contentPath=/egovcontent/bus_business/for_businessmen/article/business_identification_number&amp;lang=ru">Juridical person ID/БИН</a>
 */
public class JuridicalPersonId extends BasePersonId {

	private final Date registrationDate;
	private final JuridicalPersonType type;
	private final JuridicalPersonAttribute attribute;

	public JuridicalPersonId(String id) {
		super(id);
		registrationDate = detectRegistrationDate(id);
		type = detectJuridicalPersonType(id);
		attribute = detectJuridicalPersonAttribute(id);
	}

	@Override
	protected Pattern getIdPattern() {
		return JURIDICAL_ID_PATTERN;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public JuridicalPersonType getType() {
		return type;
	}

	public JuridicalPersonAttribute getAttribute() {
		return attribute;
	}
}
