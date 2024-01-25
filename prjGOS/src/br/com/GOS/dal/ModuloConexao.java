package br.com.GOS.dal;
import org.sqlite.SQLite;
import java.sql.*;
 



public class ModuloConexao {
//método que estabelece a conexão com o banco
	public static Connection conector() {
		java.sql.Connection conexao = null; // variavel recebe a string de conexão
		
		// a linha abaixo chama o drive importado
		String driver = "com.mysql.cj.jdbc.Driver";
		
		//Armazennado informações referentes ao banco:
		String url = "jdbc:mysql://localhost:3306/dbGOS?characterEncoding=utf-8";
		String user = "DBA";
		String password = "123";
		
		//Estabelecendo a conexão com o banco:
		try {
			Class.forName(driver); // executa o arquivo do driver
			conexao = DriverManager.getConnection(url, user, password); // obtem a conexao usando os parametros
			return conexao;
		} catch (Exception e) {
			System.out.println(e); // Caso o try conecction falhe, imprimir o erro
			return null;
		}
	}
}
