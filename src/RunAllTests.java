/**
 * @author Carla Ferreira
 *
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * A classe Tests especifica um conjunto de testes implementado recorrendo 'a ferramenta 
 * JUnit. Estes testes usam como input os ficheiros de teste do Mooshak, gerando, como
 * output, o resultado esperado na execucao desses testes.
 */
public class RunAllTests {
	/**
	 * Use as linhas que se seguem para especificar os testes que vai realizar.
	 * Por exemplo, o resultado esperado para o teste 
	 * 1_in_base.txt 'e 1_out_base.txt . Nao tem de fazer mais nada no resto da classe.
	 * Basta configurar esta sequencia de testes!
	 */
	
	/**
	 * Testa os comandos help e exit.
	 */
	@Test public void test01() { test("01_in_help_exit.txt","01_out_help_exit.txt"); }
	
	/**
	 * Testa o comando user e users, sem erros.
	 */
	@Test public void test02() { test("02_in_users_base.txt","02_out_users_base.txt"); }
	
	/**
	 * Testa o comando user e users, com erros.
	 */
	@Test public void test03() { test("03_in_users_pre.txt","03_out_users_pre.txt"); }
	
	/**
	 * Testa o comando property e properties, sem erros.
	 */
	@Test public void test04() { test("04_in_properties_base.txt","04_out_properties_base.txt"); }
	
	/**
	 * Testa o comando property e properties, com erros.
	 */
	@Test public void test05() { test("05_in_properties_pre.txt","05_out_properties_pre.txt");  }

	/**
	 * Testa o comando book, sem erros.
	 */
	@Test public void test06() { test("06_in_book_base.txt","06_out_book_base.txt"); }
	
	/**
	 * Testa o comando book, com erros.
	 */
	@Test public void test07() { test("07_in_book_pre.txt","07_out_book_pre.txt");  }
	
	/**
	 * Testa o comando confirm, sem erros.
	 */
	@Test public void test08() { test("08_in_confirm_base.txt","08_out_confirm_base.txt"); }
	
	/**
	 * Testa o comando confirm, com erros.
	 */
	@Test public void test09() { test("09_in_confirm_pre.txt","09_out_confirm_pre.txt");  }

	/**
	 * Testa o comando reject, sem erros.
	 */
	@Test public void test10() { test("10_in_reject_base.txt","10_out_reject_base.txt"); }
	
	/**
	 * Testa o comando reject, com erros.
	 */
	@Test public void test11() { test("11_in_reject_pre.txt","11_out_reject_pre.txt");  }
	
	/**
	 * Testa o comando rejections, sem erros.
	 */
	@Test public void test12() { test("12_in_rejections_base.txt","12_out_rejections_base.txt"); }
	
	/**
	 * Testa o comando rejections, com erros.
	 */
	@Test public void test13() { test("13_in_rejections_pre.txt","13_out_rejections_pre.txt");  }
	
	/**
	 * Testa o comando pay, sem erros.
	 */
	@Test public void test14() { test("14_in_pay_base.txt","14_out_pay_base.txt"); }
	
	/**
	 * Testa o comando pay, com erros.
	 */
	@Test public void test15() { test("15_in_pay_pre.txt","15_out_pay_pre.txt");  }
	
	/**
	 * Testa o comando review, sem erros.
	 */
	@Test public void test16() { test("16_in_review_base.txt","16_out_review_base.txt"); }
	
	/**
	 * Testa o comando review, com erros.
	 */
	@Test public void test17() { test("17_in_review_pre.txt","17_out_review_pre.txt");  }
	
	/**
	 * Testa o comando guest.
	 */
	@Test public void test18() { test("18_in_guest.txt","18_out_guest.txt");  }
	
	/**
	 * Testa o comando stays.
	 */
	@Test public void test19() { test("19_in_stays.txt","19_out_stays.txt");  }
	
	/**
	 * Testa o comando search.
	 */
	@Test public void test20() { test("20_in_search.txt","20_out_search.txt");  }
	
	/**
	 * Testa o comando best.
	 */
	@Test public void test21() { test("21_in_best.txt","21_out_best.txt");  }
	
	/**
	 * Testa o comando globetrotter.
	 */
	@Test public void test22() { test("22_in_globetrotter.txt","22_out_globetrotter.txt");  }
	
	private static final File BASE = new File("tests");

	private PrintStream consoleStream;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		consoleStream = System.out;
		System.setOut(new PrintStream(outContent));
	}

	public void test(String intput, String output) {
		test(new File(BASE, intput), new File(BASE, output));
	}

	public void test(File input, File output) {
		consoleStream.println("Testing!");
		consoleStream.println("Input: " + input.getAbsolutePath());
		consoleStream.println("Output: " + output.getAbsolutePath());

		String fullInput = "", fullOutput = "";
		try {
			fullInput = new String(Files.readAllBytes(input.toPath()));
			fullOutput = new String(Files.readAllBytes(output.toPath()));
			consoleStream.println("INPUT ============");
			consoleStream.println(new String(fullInput));
			consoleStream.println("OUTPUT ESPERADO =============");
			consoleStream.println(new String(fullOutput));
			consoleStream.println("OUTPUT =============");
		} catch(Exception e) {
			e.printStackTrace();
			fail("Erro a ler o ficheiro");
		}

		try {
			Locale.setDefault(Locale.US);
			System.setIn(new FileInputStream(input));
			Class<?> mainClass = Class.forName("Main");
			mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro no programa");
		} finally {
			byte[] outPrintBytes = outContent.toByteArray();
			consoleStream.println(new String(outPrintBytes));

			assertEquals(removeCarriages(fullOutput), removeCarriages(new String(outContent.toByteArray())));
		}
	}

	private static String removeCarriages(String s) {
		return s.replaceAll("\r\n", "\n");
	}

}