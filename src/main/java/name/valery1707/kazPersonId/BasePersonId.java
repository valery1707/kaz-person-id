package name.valery1707.kazPersonId;

import java.util.regex.Pattern;

public abstract class BasePersonId {
	private final String id;

	public BasePersonId(String id) {
		this.id = id;
	}

	protected abstract Pattern getIdPattern();

	public String getId() {
		return id;
	}

	public boolean isValid() {
		return id != null && isValidPattern() && isValidCRC();
	}

	public boolean isValidPattern() {
		return id != null && getIdPattern().matcher(id).matches();
	}

	public boolean isValidCRC() {
		return id != null && PersonIdUtils.isValidCRC(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BasePersonId)) {
			return false;
		}

		BasePersonId that = (BasePersonId) o;

		return getId() == null
				? that.getId() == null
				: getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
