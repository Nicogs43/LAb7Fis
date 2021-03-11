package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.Persona;
import application.model.utenti.Visitatore;

public class TestVisitatore {

	Visitatore visitatore;
	
	@BeforeEach
	void setUp() {
		visitatore = new Visitatore("test");
	}
	
	
	@Test
	void testGetNome() {
		assertEquals(visitatore.getNome(),"test");
	}
	
	@Test
	void testEquals() {
		Visitatore visitatore2 = visitatore;
		assertEquals(visitatore,visitatore2);
	}
	
	@Test
	void testNotInstance() {
		assertNotEquals(visitatore,"test");
	}
	
	@Test
	void testEqualName() {
		assertEquals(visitatore,new Persona("test"));
	}
	
	@Test
	void testDifferentName() {
		assertNotEquals(visitatore,new Persona("diverso"));
	}
	
	@Test
	void testHashCode() {
		assertEquals(visitatore.hashCode(),visitatore.getNome().hashCode());
	}
	
}
