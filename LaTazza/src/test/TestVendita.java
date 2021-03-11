package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;

public class TestVendita {

	Vendita vendita1, vendita2;
	Date actualDate = new Date();

	@BeforeEach
	public void setUp() throws Exception{
		vendita1=new Vendita(new Persona("Siji"), 100, TipoCialda.fromString("camomilla"), true);
		vendita2=new Vendita(new Persona("Bon"), 150, TipoCialda.fromString("cioccolata"), false, actualDate);
	}
	
	@Test
	public void TestIsContanti() {
		assertTrue(vendita1.isContanti());
		assertFalse(vendita2.isContanti());
	}
	
	@Test
	public void TestGetTc() {
		assertEquals(TipoCialda.fromString("camomilla"), vendita1.getTipoCialda());
		assertEquals(TipoCialda.fromString("cioccolata"), vendita2.getTipoCialda());
	}

	@Test
	public void TestGetQuantita() {
		assertEquals(100, vendita1.getQuantita());
		assertEquals(150, vendita2.getQuantita());
	}
	
	@Test
	public void TestGetCliente() {
		assertEquals(new Persona("Siji"), (Persona)vendita1.getCliente());
		assertEquals(new Persona("Bon"), (Persona)vendita2.getCliente());
	}
	
	@Test
	public void TestGetData() {
		assertEquals(vendita2.getData(),actualDate);
	}

	@Test
	public void TestGetEpoch() {
		assertEquals(vendita2.getEpoch(),actualDate.getTime());
	}
}
