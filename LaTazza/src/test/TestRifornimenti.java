package test;

import static org.junit.jupiter.api.Assertions.*;



import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.rifornimenti.Rifornimenti;
import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

class TestRifornimenti {
	
	Rifornimenti rifornimenti;
	int size =1 ,  numScatola = 21;
	Rifornimento rf;
	
	@BeforeEach
	void setUp() throws Exception {
		
		
		rifornimenti= new Rifornimenti();
		rifornimenti.load();
		
		
	}
	@Test
	void testAddRifornimentoTrue() {
		Rifornimento test = new Rifornimento(numScatola, TipoCialda.caffè);
		int testsize = rifornimenti.getRifornimenti().size();
		assertTrue(rifornimenti.addRifornimento(test.getNumeroScatole(), test.getTipoCialda()));
		Rifornimento rf2 = rifornimenti.getRifornimenti().get(testsize);
		rifornimenti.rimuoviRifornimentoTest(rf2);
	
	}
	/*@Test
	void testRimuoviTrue() {
		Rifornimento test = new Rifornimento(numScatola, TipoCialda.caffè);
		rifornimenti.addRifornimento(test.getNumeroScatole(), test.getTipoCialda());
		int testsize = rifornimenti.getRifornimenti().size();
		rifornimenti.rimuoviRifornimentoTest(test);
		assertEquals(rifornimenti.getRifornimenti().size(),size-1);
	}
	
	@Test
	void testGetRifornimentiByTipoCialda() {
		Rifornimento last = null;
		rifornimenti.addRifornimento(numScatola, TipoCialda.caffè);
		for(Rifornimento i:rifornimenti.getRifornimenti()) 
			last=i;
		
		assertEquals(last.getTipoCialda(),TipoCialda.caffè);
		assertEquals(last.getNumeroScatole(),numScatola);
		rifornimenti.rimuoviRifornimentoTest(last);
	}*/
	
	@Test
	void testGetRifornimentiSize() {
		assertNotEquals(rifornimenti.getRifornimenti().size(),size);
	}
	
	/*@Test
	void testLoad() {

		rifornimenti.addRifornimento(numScatola, TipoCialda.caffè);
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/test.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=rifornimenti.print());
			printWriter.close();
		} catch (IOException e) {
		}
		rifornimentiEmpty.load("res/test.txt");
        file.delete();
		assertEquals(rifornimentiEmpty.print(),str);
	}
	
	
	@Test
	void testLoadFileNotExists() {
		rifornimentiEmpty.load("res/test5.txt");
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() {
		rifornimentiEmpty.load("res/test.txt");
        file.delete();
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
	*/
}