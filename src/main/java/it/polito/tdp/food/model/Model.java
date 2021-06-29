package it.polito.tdp.food.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Map<Integer,Portion>idMap;
	private Graph <Portion,DefaultWeightedEdge> grafo;
	private FoodDao dao;
	
	public Model() {
		dao = new FoodDao();
		idMap = new HashMap<Integer,Portion>();
		dao.listAllPortions(idMap);
	}
	
	
}
