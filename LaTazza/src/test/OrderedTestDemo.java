package test;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import application.model.utenti.Persona;
import application.model.utenti.Personale;
import application.utils.Euro;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class OrderedTestsDemo {

	Personale personale;
	//Persona p = new Persona("persona1");
	int i;
	
	@BeforeEach
	void setUp() throws Exception { 
		personale = new Personale();
		personale.load();
	}
	
	@Test
    @Order(1)
	void testGetPersonale() {
		i = personale.getPersonale().size();
		assertEquals(personale.getPersonale().size(),i);
		
	}
	
	@Test
    @Order(2)
	void testAddPersonale() {
		i = personale.getPersonale().size();
		personale.addPersona("persona1");
		assertEquals(personale.getPersonale().size(),i+1);
		//Persona p = new Persona("persona1");
		//personale.removePersona(p);
		//assertEquals(personale.getPersonale().size(),i);
	}

	@Test
    @Order(3)
	void testGetIndebitatiSize() {
		i = personale.getIndebitati().size();
		Persona p = new Persona("persona1");
		//p.aumentaDebito(new Euro(5));
		for(Persona persona : personale.getPersonale()) {
			if(persona.getNome()==p.getNome()) {
				p.aumentaDebito(new Euro(5));
			}
		}
		assertEquals(personale.getIndebitati().size(),i+1);
	}
	
	@Test
	@Order(4)
	void testDiminuisciDebitoTrue() {
		i = personale.getIndebitati().size();
		Persona p = new Persona("persona1");
		for(Persona persona : personale.getPersonale()) {
			if(persona.getNome()==p.getNome()) {
				personale.diminuisciDebito(p, new Euro(5));
			}
		}
		/*
		for(Persona persona : personale.getPersonale()) {
			persona.aumentaDebito(new Euro(5));
			assertTrue(personale.diminuisciDebito(persona, new Euro(5)));
		}*/
		assertEquals(p.getDebito(),new Euro(0));
	}
	
	
	@Test
    @Order(5)
	void testRemovePersonale() {
		i = personale.getPersonale().size();
		Persona p = new Persona("persona1");
		personale.removePersona(p);
		assertEquals(personale.getPersonale().size(),i-1);
	}

}