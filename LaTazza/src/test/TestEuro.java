package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.utils.Euro;

public class TestEuro {

	Euro firstEuro;
	Euro secondEuro;
	
	@BeforeEach
	public void setUp() {
		firstEuro=new Euro(42);
		secondEuro=new Euro(10);
	}
	
	
	@Test
	void testEuroLongLongAndGetValore() {
		firstEuro=new Euro(42,42);
		assertEquals(4242,firstEuro.getValore());
		Euro secondoEuro=new Euro(10,0);
		assertEquals(1000,secondoEuro.getValore());
	}
	
	@Test
	void testEuroNegativoLongLongAndGetValore() {
		firstEuro=new Euro(-42,42);
		assertEquals(-4242,firstEuro.getValore());
		Euro secondoEuro=new Euro(-10,0);
		assertEquals(-1000,secondoEuro.getValore());
	}

	@Test
	void testEuroDoubleAndGetValore() {
		assertEquals(4200,firstEuro.getValore());
		assertEquals(1000,secondEuro.getValore());
	}

	@Test
	void testSomma() {
		firstEuro=firstEuro.somma(secondEuro);
		assertEquals(5200,firstEuro.getValore());
	}

	@Test
	void testSottrai() {
		firstEuro=firstEuro.sottrai(secondEuro);
		assertEquals(3200,firstEuro.getValore());	}

	@Test
	void testUgualeAvero() {
		Euro secondoEuro=new Euro(42);
		assertTrue(firstEuro.ugualeA(secondoEuro));
	}
	
	@Test
	void testUgualeAfalso() {
		assertFalse(firstEuro.ugualeA(secondEuro));
	}

	@Test
	void testMinoreDiVero() {
		assertTrue(secondEuro.minoreDi(firstEuro));
	}
	
	@Test
	void testMinoreDiFalso() {
		assertFalse(firstEuro.minoreDi(secondEuro));
	}

	@Test
	void testToString() {
		assertEquals("42.00€",firstEuro.toString());
	}
	
	@Test
	void testEqualsNull() {
		assertFalse(firstEuro.equals(null));
	}
	
	@Test
	void testEqualsNotInstance() {
		assertFalse(firstEuro.equals("pippo"));
	}
	
	@Test
	void testEquals() {
		assertTrue(firstEuro.equals(new Euro(42)));
	}
	
	
}
