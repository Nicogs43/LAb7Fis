package test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.Persona;
import application.model.utenti.Personale;
import application.utils.Euro;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)

class TestPersonaDebito {
	Personale personale;
	int i;

	@BeforeEach
	void setUp() throws Exception {
		personale = new Personale();
		personale.load();
	}

	 @Test
	 @Order(1)
		void testAumentaDebitoPos() {
		 	Persona test = new Persona("test1");
		 	personale.addPersona(test.getNome());
			Euro pagamento = new Euro(5.0);
			assertTrue(test.aumentaDebito(pagamento));
			assertEquals(test.getDebito(), new Euro(5.0));	
			test.diminuisciDebito(pagamento);
			personale.removePersona(test);
			}
		
		@Test
		@Order(2)
		void testAumentaDebitoNeg() {
			Persona test = new Persona("test1", new Euro(5));
			assertFalse(test.aumentaDebito(new Euro(-5.0)));
		}
		
		
		@Test
		@Order(3)
		void testDiminuisciDebitoPos() {
			Persona test = new Persona("test2");
		 	personale.addPersona(test.getNome());
			Euro pagamento = new Euro(5.0);
			test.aumentaDebito(pagamento);
			assertTrue(test.diminuisciDebito(pagamento));
			assertEquals(test.getDebito(), test.getDebito().sottrai(pagamento));	
			personale.removePersona(test);
			}
		
		@Test
		@Order(4)
		void testDiminuisciDebitoNeg() {
			Persona test = new Persona("test2", new Euro(5));
			assertFalse(test.diminuisciDebito(new Euro(-5.0)));
		}

}
