package org.aipim.web.service.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aipim.web.service.connection.*;
import org.aipim.web.service.domain.*;
import org.apache.log4j.Logger;

public class CartProductManager {
    private final static Logger logger = Logger.getLogger(CartProductManager.class);
    private java.sql.Connection connection;
    
    public CartProductManager() {
        this(null, true);
    }
    
    public CartProductManager(String _appToken, boolean _staging) {
        this.connection = Connection.getConnection(_appToken, _staging);
    }
    
    public boolean insertCartProduct(CartProduct cartProduct) {
        boolean inserted = false;
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into cartProducts( "
                            + "cart_uid, product_uid, quantity, price, "
                            + "price_save, ship_price, ship_code) "
                            + "values (?,?,?,?,?,?,?)");

            System.out.println("cartProduct inserted: [cart#" +cartProduct.getCartUid()+ ", product#" +cartProduct.getProductUid()+ "]");
            preparedStatement.setInt(1, cartProduct.getCartUid());
            preparedStatement.setInt(2, cartProduct.getProductUid());
            preparedStatement.setInt(3, cartProduct.getQuantity());
            preparedStatement.setDouble(4, cartProduct.getPrice());
            preparedStatement.setDouble(5, cartProduct.getPriceSave());
            preparedStatement.setDouble(6, cartProduct.getShipPrice());
            preparedStatement.setString(7, cartProduct.getShipCode() != null && (!cartProduct.getShipCode().isEmpty()) ? cartProduct.getShipCode() :  "NONE");
            
            preparedStatement.executeUpdate();
            inserted = true;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    	return inserted;
    }
    
    public void deleteCartProduct(CartProduct cartProduct) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from cartProducts "
                            + " where cart_uid=? and product_uid=?");
            System.out.println("cartProduct removed: [cart#" +cartProduct.getCartUid()+ ", product#" +cartProduct.getProductUid()+ "]");
            
            preparedStatement.setInt(1, cartProduct.getCartUid());
            preparedStatement.setInt(2, cartProduct.getProductUid());
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
    }
    
    public ArrayList<CartProduct> getCartProductsByCartUid(int cartUid) {
    	ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from cartProducts where cart_uid=?);");
            preparedStatement.setInt(1, cartUid);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                CartProduct cartProduct = new CartProduct();
                setCartProductResult(cartProduct, rs);
                cartProducts.add(cartProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return cartProducts;
    }
    
    public boolean setCartProductResult(final CartProduct cartProduct, final ResultSet resultSet) {
        try {
        	cartProduct.setCartUid(resultSet.getInt("cart_uid"));
        	cartProduct.setProductUid(resultSet.getInt("product_uid"));
        	cartProduct.setQuantity(resultSet.getInt("quantity"));
        	cartProduct.setPrice(resultSet.getDouble("price"));
        	cartProduct.setPriceSave(resultSet.getDouble("price_save"));
        	cartProduct.setShipPrice(resultSet.getDouble("ship_price"));
        	cartProduct.setShipCode(resultSet.getString("ship_code"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return false;
    }
}