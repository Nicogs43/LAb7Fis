package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.model.vendite.Vendite;
import application.utils.TipoCialda;

@TestMethodOrder(OrderAnnotation.class)

public class TestVendite {

	Vendite venditeEmpty;
	Vendite vendite = new Vendite();
	Date actualDate = new Date();
	Vendita vd1= new Vendita (new Persona("Siji"), 100, TipoCialda.fromString("camomilla"), true);
	Vendita vd2= new Vendita(new Persona("Bon"), 150, TipoCialda.fromString("cioccolata"), false, actualDate);
	Vendita vd3= new Vendita (new Persona("Roll"), 15, TipoCialda.fromString("caffè"), false);
	
	@BeforeEach
	void setUp() throws Exception {
		venditeEmpty = new Vendite();
		vendite.load();
	}
	
	@Test
	@Order(1)
	void TestVenditeIsEmpty() {
		assertTrue(venditeEmpty.getVendite().isEmpty());
	}
	
	@Test
	@Order(2)
	void TestVenditeLoadVendita() {
		//Load presente in @BeforeEach
		assertNotEquals(venditeEmpty.getVendite().size(), vendite.getVendite().size());
	}
	
	@Test
	@Order(3)
	void TestVenditeIsNotEmpty() {
		assertFalse(vendite.getVendite().isEmpty());
	}
	
	@Test
	@Order(4)
	void TestVenditeAddVenditaBySize() {
		int i = vendite.getVendite().size();
		vendite.addVendita(vd1.getCliente(), vd1.getQuantita(), vd1.getTipoCialda(), vd1.isContanti(), vd1.getData());
		vendite.addVendita(vd2.getCliente(), vd2.getQuantita(), vd2.getTipoCialda(), vd2.isContanti(), vd2.getData());
		vendite.addVendita(vd3.getCliente(), vd3.getQuantita(), vd3.getTipoCialda(), vd3.isContanti(), vd3.getData());
		assertEquals(vendite.getVendite().size(),i+3);
		vendite.deleteVendita(vd1);
		vendite.deleteVendita(vd2);
		vendite.deleteVendita(vd3);
		assertEquals(vendite.getVendite().size(),i);
	}
	
	@Test
	@Order(5)
	void TestVenditeDeleteVendita() {
		Vendita last = null;
		for(Vendita i:vendite.getVendite()) 
			last=i;
		assertNotEquals(last, vd3);

	}
	
	@Test
	@Order(6)
	void TestVenditeAddVenditaByGetClient() {
		vendite.addVendita(vd1.getCliente(), vd1.getQuantita(), vd1.getTipoCialda(), vd1.isContanti(), vd1.getData());
		int newSize = vendite.getVendite().size();
		assertEquals((Persona)vendite.getVendite().get(newSize-1).getCliente(), new Persona("Siji"));
		vendite.deleteVendita(vd1);
	}
}
