package org.aipim.web.service.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aipim.web.service.domain.*;
import org.apache.log4j.Logger;
import org.aipim.web.service.connection.*;

public class ProductManager {
    private final static Logger logger = Logger.getLogger(ProductManager.class);
	private java.sql.Connection connection;

	public ProductManager() {
        this(null, true);
    }
    
    public ProductManager(String _appToken, boolean _staging) {
        this.connection = Connection.getConnection(_appToken, _staging);
    }

	public ArrayList<Product> getProducts(int ownerUid, int startUid) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from products "
					+ "where owner_uid=? or ?=0 "
					+ "order by owner_uid, aisle_label, aisle, aisle_order, likes desc, uid limit ?,1000;");
			preparedStatement.setInt(1, ownerUid);
			preparedStatement.setInt(2, ownerUid); //using two times
			preparedStatement.setInt(3, startUid);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				setProductResult(product, rs);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		
		return products;
	}
	
	public Product getNewInsertedProductByLabel(String label) {
		Product product = new Product();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from products where label=? and updated_at is null order by uid desc limit 1;");
			preparedStatement.setString(1, label);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				setProductResult(product, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		return product;
	}

	public boolean insertProduct(Product product) {
		boolean inserted = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into products( "
					+ "url, label, last_price, price, "
					+ "likes, name, brand, type_description, "
					+ "subtype_description, packing_type, packing_capacity, packing_measure, "
					+ "iq_code, aisle_label, aisle, aisle_order, "
					+ "stock, status, owner_uid, created_at) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now());");

			System.out.println("Product added: [" + product.getLabel() + "]");
			preparedStatement.setString(1, product.getUrl() != null ? product.getUrl() : "");
			preparedStatement.setString(2, product.getLabel() != null ? product.getLabel() : "");
			preparedStatement.setDouble(3, product.getLastPrice());
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setInt(5, product.getLikes());
			preparedStatement.setString(6, product.getName() != null ? product.getName() : "");
			preparedStatement.setString(7, product.getBrand() != null ? product.getBrand() : "");
			preparedStatement.setString(8, product.getTypeDescription() != null ? product.getTypeDescription() : "");
			preparedStatement.setString(9, product.getSubtypeDescription() != null ? product.getSubtypeDescription() : "");
			preparedStatement.setString(10, product.getPackingType() != null ? product.getPackingType() : "");
			preparedStatement.setInt(11, product.getPackingCapacity());
			preparedStatement.setString(12, product.getPackingMeasure() != null ? product.getPackingMeasure() : "");
			preparedStatement.setString(13, product.getIqCode() != null ? product.getIqCode() : "");
			preparedStatement.setString(14, product.getAisleLabel() != null ? product.getAisleLabel() : "");
			preparedStatement.setInt(15, product.getAisle());
			preparedStatement.setInt(16, product.getAisleOrder());
			preparedStatement.setInt(17, product.getStock());
			preparedStatement.setString(18, product.getStatus() != null ? product.getStatus() : "");
			preparedStatement.setLong(19, product.getOwnerUid());
			preparedStatement.executeUpdate();

			inserted = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		
		return inserted;
	}

	public boolean updateProduct(Product product) {
		boolean updated = false;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update products set url=?, label=?, last_price=?, price=?, "
							+ "likes=?, name=?, brand=?, type_description=?, "
							+ "subtype_description=?, packing_type=?, packing_capacity=?, packing_measure=?, "
							+ "iq_code=?, aisle_label=?, aisle=?, aisle_order=?, "
							+ "stock=?, status=?, owner_uid=?, updated_at=now() " + "where uid=?");

			System.out.println("Product updated: [" + product.getLabel() + "]");
			preparedStatement.setString(1, product.getUrl());
			preparedStatement.setString(2, product.getLabel());
			preparedStatement.setDouble(3, product.getLastPrice());
			preparedStatement.setDouble(4, product.getPrice());
			preparedStatement.setInt(5, product.getLikes());
			preparedStatement.setString(6, product.getName());
			preparedStatement.setString(7, product.getBrand());
			preparedStatement.setString(8, product.getTypeDescription());
			preparedStatement.setString(9, product.getSubtypeDescription());
			preparedStatement.setString(10, product.getPackingType());
			preparedStatement.setInt(11, product.getPackingCapacity());
			preparedStatement.setString(12, product.getPackingMeasure());
			preparedStatement.setString(13, product.getIqCode());
			preparedStatement.setString(14, product.getAisleLabel());
			preparedStatement.setInt(15, product.getAisle());
			preparedStatement.setInt(16, product.getAisleOrder());
			preparedStatement.setInt(17, product.getStock());
			preparedStatement.setString(18, product.getStatus());
			preparedStatement.setLong(19, product.getOwnerUid());
			preparedStatement.setLong(20, product.getUid());
			preparedStatement.executeUpdate();

			updated = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		
		return updated;
	}

	public Product getProductById(int uid) {
		Product product = new Product();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from products where uid=?");
			preparedStatement.setInt(1, uid);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				setProductResult(product, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (Exception e) {
					/* handle close exception, quite usually ignore */ }
			}
		}
		
		return product;
	}
	
	public boolean setProductResult(final Product product, final ResultSet resultSet) {
        boolean done = false;
		try {
        	product.setUid(resultSet.getInt("uid"));
			product.setUrl(resultSet.getString("url"));
			product.setLabel(resultSet.getString("label"));
			product.setLastPrice(resultSet.getDouble("last_price"));
			product.setPrice(resultSet.getDouble("price"));
			product.setLikes(resultSet.getInt("likes"));
			product.setName(resultSet.getString("name"));
			product.setBrand(resultSet.getString("brand"));
			product.setTypeDescription(resultSet.getString("type_description"));
			product.setSubtypeDescription(resultSet.getString("subtype_description"));
			product.setPackingType(resultSet.getString("packing_type"));
			product.setPackingCapacity(resultSet.getInt("packing_capacity"));
			product.setPackingMeasure(resultSet.getString("packing_measure"));
			product.setIqCode(resultSet.getString("iq_code"));
			product.setAisleLabel(resultSet.getString("aisle_label"));
			product.setAisle(resultSet.getInt("aisle"));
			product.setAisleOrder(resultSet.getInt("aisle_order"));
			product.setStock(resultSet.getInt("stock"));
			product.setStatus(resultSet.getString("status"));
			product.setOwnerUid(resultSet.getInt("owner_uid"));
			product.setCreatedAt(resultSet.getDate("created_at"));
			product.setUpdatedAt(resultSet.getDate("updated_at"));
            
			done = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
		
        return done;
    }
}