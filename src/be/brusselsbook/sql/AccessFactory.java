package be.brusselsbook.sql;

import java.sql.Connection;
import java.sql.SQLException;

import be.brusselsbook.data.BookUser;
import be.brusselsbook.servs.AdministratorAccess;
import be.brusselsbook.sql.exception.DatabaseAccessException;

public class AccessFactory {

	private static AccessFactory accessFactory;
	private DatabaseAccess databaseAccess;

	private AccessFactory(DatabaseAccess databaseAccess) {
		this.databaseAccess = databaseAccess;
	}

	public static AccessFactory getInstance() throws DatabaseAccessException {
		if (accessFactory == null) {
			try {
				accessFactory = new AccessFactory(new DatabaseAccess());
			} catch (SQLException e) {
				throw new DatabaseAccessException(
						"Cannot create the DatabaseAccess due to pool configurations issues: " + e.getMessage());
			}
		}
		return accessFactory;
	}

	public Connection getConnection() throws SQLException {
		return databaseAccess.getConnection();
	}

	public BookUserAccess<BookUser> getBookUserAccess() {
		return new BookUserAccessImpl(this);
	}

	public AdministratorAccess getAdminstratorAccess() {
		return new AdministratorAccessImpl(this);
	}

}