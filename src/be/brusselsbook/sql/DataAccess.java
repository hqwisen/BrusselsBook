package be.brusselsbook.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.brusselsbook.sql.exception.DatabaseAccessException;
import be.brusselsbook.utils.AccessUtils;

public abstract class DataAccess<T> implements Indexable<T>{

	private static String wildCards(int n){
		String result = "";
		for(int i = 0; i < n - 1; i++){
			result += "?, ";
		}
		result += "?";
		return result;
	}
	
	private static String parameters(String[] array){
		String result = "";
		for(int i = 0; i < array.length - 1; i++){
			result += array[i]+", ";
		}
		result += array[array.length-1];
		return result;		
	}
	
	protected final AccessFactory accessFactory;

	protected DataAccess(AccessFactory accessFactory) {
		this.accessFactory = accessFactory;
	}
	
	protected abstract T map(ResultSet resultSet) throws SQLException;

	protected abstract String getTable();
	
	protected abstract String[] getParameters();
	
	protected abstract int getNumberOfParameters();
	
	public String SELECTBY(String by) {
		return "SELECT * FROM " + getTable() + " WHERE " + by + " = ?";
	}

	public String SELECTALL(){
		return "SELECT * FROM " + getTable();
	}
	
	public String INSERT() {
		String wildCards = wildCards(getNumberOfParameters());
		String parameters = parameters(getParameters());
		return "INSERT INTO " + getTable() + " (" + parameters + ") VALUES (" + wildCards + ")";
	}
	
	protected T safeMap(ResultSet resultSet) throws DatabaseAccessException{
		try {
			return map(resultSet);
		} catch (SQLException e) {
			throw new DatabaseAccessException(e);
		}
	}
	
	public List<T> getObjects() throws DatabaseAccessException{
		List<T> objects = new ArrayList<T>();
		ResultSet resultSet = AccessUtils.executeQuery(accessFactory, SELECTALL());
		while(AccessUtils.next(resultSet)){
			objects.add(safeMap(resultSet));	
		}
		return objects;
	}
	
	protected T with(String sqlQuery, Object... values) throws DatabaseAccessException {
		T data = null;
		ResultSet resultSet = AccessUtils.executeQuery(accessFactory, sqlQuery, values);
		if (AccessUtils.next(resultSet)) {
			data = safeMap(resultSet);
		}
		AccessUtils.close(resultSet);
		return data;
	}
	
	@Override
	public T create(Object... values){
		return create(null, values);
	}
	
	@Override
	public T create(Long id, Object... values) {
		T data = null;
		ResultSet autoGeneratedValues = AccessUtils.executeInsert(accessFactory, INSERT(), values);
		if (id == null && AccessUtils.next(autoGeneratedValues)) {
			id = AccessUtils.getLongFirstColumn(autoGeneratedValues);
		} else if(id == null){
			throw new DatabaseAccessException("Failed to generate the Data ID.");
		}
		data = withId(id);
		AccessUtils.close(autoGeneratedValues);
		return data;
	}

}
