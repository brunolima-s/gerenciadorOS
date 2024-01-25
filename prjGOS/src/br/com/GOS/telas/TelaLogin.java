package br.com.GOS.telas;

import java.sql.*;

import br.com.GOS.dal.ModuloConexao;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TelaLogin extends JFrame {
	
	
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	Connection conexao = null;
	PreparedStatement pst = null;// Biblioteca que manipula codigo sql
	ResultSet rs = null; // vai exibir o resultado das execucoes sql
	//private final Action action = new Action();

	// criar o METODO LOGAR:
	// =? é substituido pelo valor que está digitado na caixa de texto de login
	@SuppressWarnings("deprecation")
	
	public void logar() {
		String sql = "select * from tbusuario where login =? and senha =?";
		try {

			// as linhas abaixo preparam a consulta ao banco em função do que foi digitado
			// nas caixas de texto
			// o ? é substituido pelo conteudo das variáveis

			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, txtSenha.getText());

			// a linha abaixo executa a query (select)

			rs = pst.executeQuery();

			// veriica se existir usuario e senha correspondente, é liberada a tela
			// principal
			if (rs.next()) {
				// a linha abaixo obtém o oconteudo do campo "perfil" da tabela tbusuario, que é
				// o campo 6
				String perfil = rs.getString(6);
				
				// a estrutura abaixo faz o tratamento do perfil do usuário:
				if (perfil.equals("admin")) {
					// abre a tela principal:
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.menuRel.setEnabled(true);
					TelaPrincipal.menuCadUsu.setEnabled(true);

					// Mostra em lblUsuário quem é o usuario do campo 2 da tabela do banco
					TelaPrincipal.lblUsuario.setText(rs.getString(2));
					// para que a tela de login desapareça depois de o usuario ter logado
					this.dispose();
					// fecha a conexao com o Banco:
					conexao.close();
				} else {
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.lblUsuario.setText(rs.getString(2));
					this.dispose();
					conexao.close();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido");

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlLogin);

		JLabel lblNewLabel = new JLabel("Usu\u00E1rio");

		JLabel lblSenha = new JLabel("Senha");

		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);

		JButton btnLogin = new JButton("Login");
		btnLogin.setMnemonic(KeyEvent.VK_ENTER);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logar();
			}
		});

		txtSenha = new JPasswordField();
		
		JLabel lblStatus1 = new JLabel("");
		lblStatus1.setLabelFor(pnlLogin);
		lblStatus1.setInheritsPopupMenu(false);
		lblStatus1.setFocusTraversalKeysEnabled(false);
		lblStatus1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblStatus1.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus1.setIconTextGap(0);
		
		conexao = ModuloConexao.conector();

		if (conexao != null) {
			
			
			lblStatus1.setHorizontalTextPosition(SwingConstants.CENTER);
			lblStatus1.setHorizontalAlignment(SwingConstants.CENTER);
			lblStatus1.setIconTextGap(0);
			lblStatus1.setBounds(92, 139, 90, 90);
			lblStatus1.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/GOS/icones/connected.png")));
			
		} else {
			lblStatus1.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/GOS/icones/not-connected.png")));
		}
		GroupLayout gl_pnlLogin = new GroupLayout(pnlLogin);
		gl_pnlLogin.setHorizontalGroup(
			gl_pnlLogin.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLogin.createSequentialGroup()
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_pnlLogin.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlLogin.createSequentialGroup()
							.addGap(77)
							.addGroup(gl_pnlLogin.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStatus1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlLogin.createSequentialGroup()
									.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)))))
					.addGap(74))
				.addGroup(Alignment.TRAILING, gl_pnlLogin.createSequentialGroup()
					.addContainerGap(284, Short.MAX_VALUE)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(72))
		);
		gl_pnlLogin.setVerticalGroup(
			gl_pnlLogin.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLogin.createSequentialGroup()
					.addGap(68)
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(27)
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnlLogin.createSequentialGroup()
							.addComponent(btnLogin)
							.addGap(38))
						.addGroup(gl_pnlLogin.createSequentialGroup()
							.addComponent(lblStatus1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		pnlLogin.setLayout(gl_pnlLogin);
	

}
	}

