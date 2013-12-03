package Interfaces;

import com.rdsd.shoppinglist.DataClasses.Product;
import com.rdsd.shoppinglist.DataClasses.ShoppingList;

public interface DatabaseInterface {
	
	public void saveProductToDatabase(Product p);
	public ShoppingList getShoppingListFromDatabase();
	public Product getProductByName(String productName);
	

}
