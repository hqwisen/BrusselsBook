package be.brusselsbook.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Restaurants")
public class Restaurants {
	
	List<Restaurant> restaurantList;
	
	private Restaurants(){	
	}

	@XmlElement(name = "Restaurant")
	public List<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(List<Restaurant> restauranList) {
		this.restaurantList = restauranList;
	}
	
	
}