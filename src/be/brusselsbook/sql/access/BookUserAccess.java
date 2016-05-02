package be.brusselsbook.sql.access;

import be.brusselsbook.data.BookUser;

public abstract class BookUserAccess<T extends BookUser> extends DataAccess<T>{

	protected BookUserAccess(AccessFactory accessFactory) {
		super(accessFactory);
	}
	
	public abstract T withEmail(String email);

	public abstract T withUsername(String username);

	public abstract T withUid(Long uid);

	public abstract T withUid(String uid);

}