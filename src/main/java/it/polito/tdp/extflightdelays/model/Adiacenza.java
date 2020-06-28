package it.polito.tdp.extflightdelays.model;

public class Adiacenza implements Comparable<Adiacenza> {
 private Airport a1;
 private Airport a2;
 private Double peso;
public Airport getA1() {
	return a1;
}
public void setA1(Airport a1) {
	this.a1 = a1;
}
public Airport getA2() {
	return a2;
}
public void setA2(Airport a2) {
	this.a2 = a2;
}
public Double getPeso() {
	return peso;
}
public void setPeso(Double peso) {
	this.peso = peso;
}
public Adiacenza(Airport a1, Airport a2, Double peso) {
	super();
	this.a1 = a1;
	this.a2 = a2;
	this.peso = peso;
}
@Override
public int compareTo(Adiacenza o) {
	// TODO Auto-generated method stub
	return -Double.compare(this.peso, o.peso);
}
@Override
public String toString() {
	return  a2 + " distanza " + peso;
}
 
 
}
