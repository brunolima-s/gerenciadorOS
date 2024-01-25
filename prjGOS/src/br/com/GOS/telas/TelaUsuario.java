package br.com.GOS.telas;

import java.awt.EventQueue;
import java.sql.*;
import br.com.GOS.dal.*;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;



public class TelaUsuario extends JInternalFrame {
	private JTextField txtUsuId;
	private JTextField txtUsuNome;
	private JTextField txtUsuLogin;
	private JTextField txtUsuSenha;
	private JTextField txtUsuFone;

	private JComboBox<String> cboUsuPer = new JComboBox<String>();
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuario frame = new TelaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaUsuario() {
		setMaximizable(true);
		setIconifiable(true);
		setPreferredSize(new Dimension(500, 420));
		getContentPane().setPreferredSize(new Dimension(500, 420));
		getContentPane().setSize(new Dimension(500, 420));
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("ID*");
		lblNewLabel.setBounds(101, 50, 34, 17);
		getContentPane().add(lblNewLabel);

		JLabel lblNome = new JLabel("NOME*");
		lblNome.setBounds(101, 97, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblLogin = new JLabel("LOGIN*");
		lblLogin.setBounds(252, 150, 46, 14);
		getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("SENHA*");
		lblSenha.setBounds(252, 204, 46, 14);
		getContentPane().add(lblSenha);

		JLabel lblPerfil = new JLabel("PERFIL");
		lblPerfil.setBounds(101, 204, 46, 14);
		getContentPane().add(lblPerfil);

		txtUsuId = new JTextField();
		txtUsuId.setBounds(146, 47, 40, 20);
		getContentPane().add(txtUsuId);
		txtUsuId.setColumns(10);

		txtUsuNome = new JTextField();
		txtUsuNome.setColumns(10);
		txtUsuNome.setBounds(146, 94, 237, 20);
		getContentPane().add(txtUsuNome);

		txtUsuLogin = new JTextField();
		txtUsuLogin.setColumns(10);
		txtUsuLogin.setBounds(297, 144, 86, 20);
		getContentPane().add(txtUsuLogin);

		txtUsuSenha = new JTextField();
		txtUsuSenha.setColumns(10);
		txtUsuSenha.setBounds(297, 201, 86, 20);
		getContentPane().add(txtUsuSenha);

		JLabel lblFone = new JLabel("FONE");
		lblFone.setBounds(101, 150, 46, 14);
		getContentPane().add(lblFone);

		txtUsuFone = new JTextField();
		txtUsuFone.setColumns(10);
		txtUsuFone.setBounds(146, 144, 86, 20);
		getContentPane().add(txtUsuFone);

		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o método adicionar
				adicionar();
			}
		});
		btnUsuCreate.setToolTipText("Adicionar Usu\u00E1rio");
		btnUsuCreate.setSize(new Dimension(64, 640));
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/GOS/icones/add.png")));
		btnUsuCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuCreate.setSelectedIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/GOS/icones/add.png")));
		btnUsuCreate.setBounds(48, 264, 73, 73);
		getContentPane().add(btnUsuCreate);

		JButton btnUsuUpdate = new JButton("");
		btnUsuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		btnUsuUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/GOS/icones/write.png")));
		btnUsuUpdate.setToolTipText("Alterar Usu\u00E1rio");
		btnUsuUpdate.setPreferredSize(new Dimension(64, 64));
		btnUsuUpdate.setBounds(159, 264, 73, 73);
		getContentPane().add(btnUsuUpdate);

		JButton btnUsuRead = new JButton("");
		btnUsuRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultar();
			}
		});
		btnUsuRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuRead.setToolTipText("Procurar Usu\u00E1rio");
		btnUsuRead.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/GOS/icones/search.png")));
		btnUsuRead.setBounds(265, 264, 73, 73);
		getContentPane().add(btnUsuRead);

		JButton btnUsuDelete = new JButton("");
		btnUsuDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnUsuDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/GOS/icones/delete.png")));
		btnUsuDelete.setToolTipText("Deletar Usu\u00E1rio");
		btnUsuDelete.setBounds(377, 264, 73, 73);
		getContentPane().add(btnUsuDelete);

		cboUsuPer.setModel(new DefaultComboBoxModel<String>(new String[] { "admin", "user" }));
		cboUsuPer.setBounds(146, 201, 53, 20);
		getContentPane().add(cboUsuPer);

		JLabel lblCamposObrigatrios = new JLabel("Campos obrigat\u00F3rios*");
		lblCamposObrigatrios.setForeground(Color.GRAY);
		lblCamposObrigatrios.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblCamposObrigatrios.setBounds(279, 232, 104, 14);
		getContentPane().add(lblCamposObrigatrios);
		setTitle("Usu\u00E1rios");
		setBounds(0, 0, 500, 420);

		// Colocando o conector na Classe Construtor:
		conexao = ModuloConexao.conector();

	}

	private void consultar() {
		String sql = "select * from tbusuario where id=?";
		try {
			// Procurar o usuário de acordo com a id fornecida:
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuId.getText());
			rs = pst.executeQuery();

			// Caso encronte o usuario correspondente:
			if (rs.next()) {
				// Pega o segundo campo da tabela, que se refere ao NOME
				txtUsuNome.setText(rs.getString(2));
				txtUsuFone.setText(rs.getString(3));
				txtUsuLogin.setText(rs.getString(4));
				txtUsuSenha.setText(rs.getString(5));
				cboUsuPer.setSelectedItem(rs.getString(6));
				;

			} else {

				txtUsuNome.setText(null);
				txtUsuFone.setText(null);
				txtUsuLogin.setText(null);
				txtUsuSenha.setText(null);
				cboUsuPer.setSelectedItem(null);
				JOptionPane.showMessageDialog(null, "Usuário não encontrado!");

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void adicionar() {
		// instrução sql que ira criar a entrada para os dados:
		String sql = "insert into tbusuario(id,usuario,fone,login,senha,perfil) values (?,?,?,?,?,?)";

		try {
			pst = conexao.prepareStatement(sql);
			// Colocar no anco o que está nos campos de texto:
			pst.setString(1, txtUsuId.getText());
			pst.setString(2, txtUsuNome.getText());
			pst.setString(3, txtUsuFone.getText());
			pst.setString(4, txtUsuLogin.getText());
			pst.setString(5, txtUsuSenha.getText());
			pst.setString(6, cboUsuPer.getSelectedItem().toString());

			// validação dos campos obrigatórios
			if ((((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())
					|| (txtUsuLogin.getText().isEmpty()) || ((txtUsuSenha.getText().isEmpty()))))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				// a linha abixo atualiza a tabela usuario
				int adicionado = pst.executeUpdate();
				// a estrutura abaixo é para confirmar a adição, quando 1 ou mais linhas forem
				// adicionadas (>0)
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário " + txtUsuNome.getText() + " adicionado!");
					txtUsuNome.setText(null);
					txtUsuFone.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void alterar() {
		String sql = "update tbusuario set usuario=?,fone=?,login=?,senha=?,perfil=? where id=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuNome.getText());
			pst.setString(2, txtUsuFone.getText());
			pst.setString(3, txtUsuLogin.getText());
			pst.setString(4, txtUsuSenha.getText());
			pst.setString(5, cboUsuPer.getSelectedItem().toString());
			pst.setString(6, txtUsuId.getText());

			if ((((txtUsuId.getText().isEmpty()) || (txtUsuNome.getText().isEmpty())
					|| (txtUsuLogin.getText().isEmpty()) || ((txtUsuSenha.getText().isEmpty()))))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				// a linha abixo atualiza a tabela usuario
				int adicionado = pst.executeUpdate();
				// a estrutura abaixo é para confirmar a adição, quando 1 ou mais linhas forem
				// adicionadas (>0)
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário " + txtUsuNome.getText() + " alterado!");
					txtUsuNome.setText(null);
					txtUsuFone.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void remover() {

		// Confirma a remoção do usuario:
		int confirma = JOptionPane.showConfirmDialog(null, "Remover o usuário?");
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbusuario where id=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtUsuId.getText());
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário " + txtUsuNome.getText() + " removido!");
					txtUsuNome.setText(null);
					txtUsuFone.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

		}

	}
}
