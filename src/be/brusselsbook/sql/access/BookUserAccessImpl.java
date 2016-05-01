package be.brusselsbook.sql.access;

import java.sql.ResultSet;
import java.sql.SQLException;

import be.brusselsbook.data.BookUser;
import be.brusselsbook.sql.exception.DatabaseAccessException;
import be.brusselsbook.utils.BrusselsBookUtils;

public class BookUserAccessImpl extends BookUserAccess<BookUser> {

	protected static final String EMAILADDRESS = "EmailAddress";
	protected static final String USERNAME = "Username";
	protected static final String PASSWORD = "Pwd";
	protected static final String UID = "UID";
	private static final String REGISTRATIONDATE = "RegistrationDate";
	
	// UID and RegistrationDate generated by sql
	private static final String[] PARAMETERS = BrusselsBookUtils.createArrayFrom(EMAILADDRESS, USERNAME, PASSWORD);
	private static final String TABLE = "BookUser";
	
	public BookUserAccessImpl(AccessFactory accessFactory) {
		super(accessFactory);
	}

	@Override
	protected String getTable() {
		return TABLE;
	}
	
	@Override
	protected String[] getCreationParameters() {
		return PARAMETERS;
	}
	
	@Override
	protected int getNumberOfCreationParameters() {
		return PARAMETERS.length;
	}
	
	@Override
	public BookUser withId(Long id) {
		return withUid(id);
	}
	
	@Override
	public BookUser withEmail(String email) throws DatabaseAccessException {
		return withQuery(SELECTBY(EMAILADDRESS), email);
	}

	@Override
	public BookUser withUsername(String username) {
		return withQuery(SELECTBY(USERNAME), username);
	}

	@Override
	public BookUser withUid(Long uid) {
		return withUid(uid.toString());
	}

	@Override
	public BookUser withUid(String uid) {
		return withQuery(SELECTBY(UID), uid);
	}

	@Override
	protected BookUser map(ResultSet resultSet) throws SQLException {
		BookUser bookUser = new BookUser();
		bookUser.setUid(resultSet.getLong(UID));
		bookUser.setEmailAddress(resultSet.getString(EMAILADDRESS));
		bookUser.setUsername(resultSet.getString(UID));
		bookUser.setPassword(resultSet.getString(PASSWORD));
		bookUser.setRegistrationDate(resultSet.getTimestamp(REGISTRATIONDATE));
		return bookUser;
	}

}
