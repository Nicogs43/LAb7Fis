package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.model.vendite.Vendite;
import application.utils.TipoCialda;


public class TestVendite {

	Vendite vendite, venditeEmpty;
	Date actualDate = new Date();
	
	@BeforeEach
	void setUp() throws Exception {
		venditeEmpty = new Vendite();
		vendite = new Vendite();
		vendite.getVendite().add(new Vendita (new Persona("Siji"), 100, TipoCialda.fromString("camomilla"), true));
		vendite.getVendite().add(new Vendita(new Persona("Bon"), 150, TipoCialda.fromString("cioccolata"), false, actualDate));
		vendite.getVendite().add(new Vendita (new Persona("Roll"), 15, TipoCialda.fromString("caffè"), false));
	}
	
	@Test
	void TestVenditeIsEmpty() {
		assertTrue(venditeEmpty.getVendite().isEmpty());
	}
	
	@Test
	void TestVenditeIsNotEmpty() {
		assertFalse(vendite.getVendite().isEmpty());
	}
	
	@Test 
	void testLoad() {
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/pluto.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=vendite.print());
			printWriter.close();
		} catch (IOException e) {
		}
		venditeEmpty.load("res/pluto.txt");
		new File("res/pluto.txt").delete();
		assertTrue(vendite.print().equals(str));
	}
	
	@Test
	void testLoadFileNotExists() {
		int oldSize = vendite.getVendite().size();
		vendite.load("res/test5.txt");
		assertEquals(vendite.getVendite().size(),oldSize);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void TestAddVenditaSize(TipoCialda tc) {
		int oldSize = vendite.getVendite().size();
		vendite.addVendita(new Persona("NewAdded"), 75, tc, true);
		int newSize = vendite.getVendite().size();
		assertEquals(oldSize+1,newSize);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void TestAddVenditaByGetCliente(TipoCialda tc) {
		int oldSize = vendite.getVendite().size();
		vendite.addVendita(new Persona("NewAdded"), 75, tc, true, actualDate);
		int newSize = oldSize+1;
		assertEquals((Persona)vendite.getVendite().get(newSize-1).getCliente(), new Persona("NewAdded"));
	}
	
	@Test
	void testPrintEmpty() {
		assertEquals(venditeEmpty.print(),"VENDITE\n\n");
		
	}
	
	@Test
	void testPrint() {
		venditeEmpty.addVendita(new Persona("NewAdded"), 75, TipoCialda.fromString("caffèArabica"), true, actualDate);
		assertEquals(venditeEmpty.print(),	"VENDITE\n" +
											actualDate.getTime() + " NewAdded 75 caffèArabica true\n\n");
	}
}
