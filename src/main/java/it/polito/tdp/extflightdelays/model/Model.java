package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	ExtFlightDelaysDAO dao;
	Map<Integer, Airport> idMap;
	private ArrayList<Airport> best;
	private int miglia;

	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
		this.dao.loadAllAirports(idMap);
	}

	public void creaGrafo(int x) {
		this.grafo = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Adiacenza> adiacenze = this.dao.loadAllAdiacenze(x, idMap);

		for (Adiacenza a : adiacenze) {
			if (this.grafo.containsVertex(a.getA1()) && this.grafo.containsVertex(a.getA2())) {
				DefaultWeightedEdge e = this.grafo.getEdge(a.getA1(), a.getA2());
				if (e == null) {
					Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
				} else {
					double pesoVecchio = this.grafo.getEdgeWeight(e);
					double pesoNuovo = pesoVecchio + a.getPeso();
					this.grafo.setEdgeWeight(e, pesoNuovo / 2);
				}
			} else {
				Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
			}

		}
	}

	public Set<Airport> vertici() {
		return this.grafo.vertexSet();
	}

	public Set<DefaultWeightedEdge> archi() {
		return this.grafo.edgeSet();
	}

	public List<Adiacenza> connessi(Airport partenza) {
		List<Adiacenza> adiacenze = new ArrayList<>();

		List<Airport> vicini = Graphs.neighborListOf(grafo, partenza);
		for (Airport a : vicini) {
			adiacenze.add(new Adiacenza(partenza, a, grafo.getEdgeWeight(grafo.getEdge(partenza, a))));
		}
		Collections.sort(adiacenze);
		return adiacenze;
	}

	public List<Airport> percorso(Airport partenza, int miglia) {
		List<Airport> parziale = new ArrayList<>();
		this.miglia = 0;
		parziale.add(partenza);
		this.best = new ArrayList<>();
		this.cerca(parziale, miglia, 0);
		return best;
	}

	private void cerca(List<Airport> parziale, int migliaTotali, int migliaPercorse) {

		if (migliaPercorse == migliaTotali || parziale.size() > this.best.size()
				|| (parziale.size() == this.best.size() && migliaPercorse > this.miglia)) {
			this.best = new ArrayList<>(parziale);
			this.miglia = migliaPercorse;
		}

		List<Airport> possibili = Graphs.neighborListOf(grafo, parziale.get(parziale.size() - 1));
		

		for (Airport a : possibili) {
			double distanza = this.grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size() - 1), a));
			
			if (!parziale.contains(a) && (migliaTotali-migliaPercorse) >= distanza) {
				migliaPercorse += distanza;
				parziale.add(a);
				cerca(parziale, migliaTotali, migliaPercorse);
				migliaPercorse -= distanza;
				parziale.remove(a);
			}
		}
	}

	public int getMiglia() {
		return this.miglia;
	}
}
