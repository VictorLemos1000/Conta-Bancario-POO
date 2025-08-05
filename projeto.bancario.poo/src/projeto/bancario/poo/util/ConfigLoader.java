package projeto.bancario.poo.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Classe para carregamento de arquivo de configuração.
public class ConfigLoader {

	private final static String ARQUIVO = "configure.properties";
	private static Properties PROP = new Properties();
	
	static {
		load(ARQUIVO);
	}
	
	public static void load(String arquivo) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = new FileInputStream(arquivo);
			PROP = new Properties();
			PROP.load(fis);
		} catch (IOException e) {
			// TODO: handle exception
			System.err.println("\n Erro ao ler o arquivo de configuração.");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public static String getValor(String chave) {
		// TODO Auto-generated method stub
		return PROP.getProperty(chave);
	}
}
