package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Food> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}

	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Condiment> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public void listAllPortions(Map<Integer,Portion>idMap){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;


			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					if(!idMap.containsKey(res.getInt("portion_id"))) {
						Portion p =	new Portion(res.getInt("portion_id"),
								res.getDouble("portion_amount"),
								res.getString("portion_display_name"), 
								res.getDouble("calories"),
								res.getDouble("saturated_fats"),
								res.getInt("food_code")
								);
						idMap.put(res.getInt("portion_id"), p);
					}
				}catch (Throwable t) {
					t.printStackTrace();
				}
			}


			conn.close();


		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public List<Portion> getVertici(Map<Integer,Portion>idMap,double soglia){

		String sql="SELECT distinct portion_id " + 
				"FROM `portion` " + 
				"WHERE calories<?";
		List<Portion> result = new LinkedList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, soglia);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(idMap.get(res.getInt("portion_id")));
			}
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}



}
