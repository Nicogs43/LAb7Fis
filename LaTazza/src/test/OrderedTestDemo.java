package test;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import application.model.utenti.Persona;
import application.model.utenti.Personale;
import application.utils.Euro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class OrderedTestsDemo {

	Personale personale;
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
		assertEquals(personale.getPersonale().size(), i);

	}

	@Test
	@Order(2)
	void testAddPersonale() {
		i = personale.getPersonale().size();
		Persona p = new Persona("persona1");
		personale.addPersona(p.getNome());
		assertEquals(personale.getPersonale().size(), i + 1);
		personale.removePersona(p);

	}

	@Test
	@Order(5)
	void testGetIndebitatiSize() {
		i = personale.getIndebitati().size();
		Persona p = new Persona("persona1");
		personale.addPersona(p.getNome());
		for (Persona persona : personale.getPersonale()) {
			if (persona.getNome() == p.getNome()) {
				p.aumentaDebito(new Euro(5));
			}
		}
		assertEquals(personale.getIndebitati().size(), i + 1);
		personale.diminuisciDebito(p, new Euro(5));
		personale.removePersona(p);
	}

	@Test
	@Order(4)
	void testDiminuisciDebitoTrue() {
		i = personale.getIndebitati().size();
		Persona p = new Persona("persona1");
		personale.addPersona(p.getNome());
		for (Persona persona : personale.getPersonale()) {
			if (persona.getNome() == p.getNome()) {
				personale.diminuisciDebito(p, new Euro(0));
			}
		}
		assertEquals(p.getDebito(), new Euro(0));
		personale.removePersona(p);
	}

	@Test
	@Order(3)
	void testRemovePersonale() {
		i = personale.getPersonale().size();
		Persona p = new Persona("persona1");
		personale.addPersona(p.getNome());
		personale.removePersona(p);
		assertEquals(personale.getPersonale().size(), i);
	}

}