package org.aipim.web.service.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aipim.web.service.connection.*;
import org.aipim.web.service.domain.*;
import org.apache.log4j.Logger;

public class CartManager {
	private final static Logger logger = Logger.getLogger(CartManager.class);
	private java.sql.Connection connection;

	public CartManager() {
		this(null, true);
	}

	public CartManager(String _appToken, boolean _staging) {
		this.connection = Connection.getConnection(_appToken, _staging);
	}

	public Cart getNewInsertedCartByLabel(String label) throws SQLException {
		Cart cart = new Cart();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select * from carts where label=? and updated_at is null order by uid desc limit 1;");
			preparedStatement.setString(1, label);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				setCartResult(cart, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();

			throw e;
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}

		return cart;
	}

	public boolean insertCart(Cart cart) {
		boolean inserted = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into carts( "
					+ "label, likes, type, price, " + "price_save, ship_price, ship_code, status, "
					+ "owner_uid, created_at) " + "values (?,?,?,?,?,?,?,'ENABLE',?,now())");

			System.out.println("cart inserted: [" + cart.getLabel() + "]");
			preparedStatement.setString(1, cart.getLabel());
			preparedStatement.setInt(2, cart.getLikes());
			preparedStatement.setString(3, cart.getType());
			preparedStatement.setDouble(4, cart.getPrice());
			preparedStatement.setDouble(5, cart.getPriceSave());
			preparedStatement.setDouble(6, cart.getShipPrice());
			preparedStatement.setString(7,
					cart.getShipCode() != null && (!cart.getShipCode().isEmpty()) ? cart.getShipCode() : "NONE");
			preparedStatement.setInt(8, cart.getOwnerUid());

			preparedStatement.executeUpdate();

			inserted = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		return inserted;
	}

	public void updateCart(Cart cart) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update carts set label=?, likes=?, type=?, price=?,"
							+ " price_save=?, ship_price=?, ship_code=?, status=?," + " updated_at=now()"
							+ " where uid=?");
			System.out.println("cart updated: [" + cart.getLabel() + "]");

			preparedStatement.setString(1, cart.getLabel());
			preparedStatement.setInt(2, cart.getLikes());
			preparedStatement.setString(3, cart.getType());
			preparedStatement.setDouble(4, cart.getPrice());
			preparedStatement.setDouble(5, cart.getPriceSave());
			preparedStatement.setDouble(6, cart.getShipPrice());
			preparedStatement.setString(7,
					cart.getShipCode() != null && (!cart.getShipCode().isEmpty()) ? cart.getShipCode() : "NONE");
			preparedStatement.setString(8,
					cart.getStatus() != null && (!cart.getStatus().isEmpty()) ? cart.getStatus() : "DISABLE");

			preparedStatement.setInt(9, cart.getUid());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
	}

	public Cart getCart(int uid) {
		Cart cart = new Cart();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from carts where uid=?");
			preparedStatement.setInt(1, uid);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				setCartResult(cart, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		return cart;
	}

	public ArrayList<Cart> getCartsByOwnerUid(int ownerUid) {
		ArrayList<Cart> carts = new ArrayList<Cart>();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from carts where owner_uid=?);");
			preparedStatement.setInt(1, ownerUid);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Cart cart = new Cart();
				setCartResult(cart, rs);
				carts.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		return carts;
	}

	public boolean setCartResult(final Cart cart, final ResultSet resultSet) {
		boolean done = false;
		try {
			cart.setUid(resultSet.getInt("uid"));
			cart.setLabel(resultSet.getString("label"));
			cart.setLikes(resultSet.getInt("likes"));
			cart.setType(resultSet.getString("type"));
			cart.setPrice(resultSet.getDouble("price"));
			cart.setPriceSave(resultSet.getDouble("price_save"));
			cart.setShipPrice(resultSet.getDouble("ship_price"));
			cart.setShipCode(resultSet.getString("ship_code"));
			cart.setStatus(resultSet.getString("status"));
			cart.setOwnerUid(resultSet.getInt("owner_uid"));
			cart.setCreatedAt(resultSet.getDate("created_at"));
			cart.setUpdatedAt(resultSet.getDate("updated_at"));

			done = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());

		}

		return done;
	}
}