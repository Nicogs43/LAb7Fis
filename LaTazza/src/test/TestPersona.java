package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.Persona;
import application.utils.Euro;

class TestPersona {

	Persona persona;
	Euro debito;
	
	
	@BeforeEach
	void setUp() {
		debito = new Euro(10.0);
		persona = new Persona("test", debito);
	}
	
	
	@Test
	void testGetNome() {
		assertEquals(persona.getNome(),"test");
	}

	@Test
	void testPersonaStringEuroPos() {
		assertEquals(persona.getNome(),"test");
		assertEquals(persona.getDebito(), debito);
	}
	
	@Test
	void testPersonaStringEuroNeg() {		
		persona = new Persona("test", new Euro(-10.0));
		assertEquals(persona.getNome(),"test");
		assertEquals(persona.getDebito().getValore(), -1000);
	}

	@Test
	void testGetDebito() {
		assertEquals(persona.getDebito(), debito);
	}

	/*@Test
	void testAumentaDebitoPos() {
		Euro pagamento = new Euro(5.0);
		assertTrue(persona.aumentaDebito(pagamento));
		assertEquals(persona.getDebito(), debito.somma(pagamento));
	}
	
	@Test
	void testAumentaDebitoNeg() {
		assertFalse(persona.aumentaDebito(new Euro(-5.0)));
	}
	
	@Test
	void testDiminuisciDebitoPos() {
		Euro pagamento = new Euro(5.0);
		assertTrue(persona.diminuisciDebito(pagamento));
		assertEquals(persona.getDebito(), debito.sottrai(pagamento));
	}
	
	@Test
	void testDiminuisciDebitoNeg() {
		assertFalse(persona.diminuisciDebito(new Euro(-5.0)));
	}
*/
	@Test
	void testToString() {
		assertEquals(persona.toString(), "test");
	}
	
	@Test
	void testSort() {
		ArrayList<Persona> personale = new ArrayList<Persona>(Arrays.asList(new Persona("persona2"), new Persona("persona1")));
		Collections.sort(personale, Persona.SortListPersona);
		assertEquals(personale.toString(),"[persona1, persona2]");
	}
	
	@Test
	void testEquals() {
		Persona persona2 = persona;
		assertEquals(persona,persona2);
	}
	
	@Test
	void testNotInstance() {
		assertNotEquals(persona,"test");
	}
	
	@Test
	void testEqualName() {
		assertEquals(persona,new Persona("test"));
	}
	
	@Test
	void testDifferentName() {
		assertNotEquals(persona,new Persona("diverso"));
	}
	
	@Test
	void testHashCode() {
		assertEquals(persona.hashCode(),persona.getNome().hashCode());
	}

}