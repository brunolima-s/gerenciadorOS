package br.com.GOS.telas;

import java.sql.*;
import br.com.GOS.dal.*;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("unused")
public class TelaCliente extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txtCliNome;
	private JTextField txtCliEndereco;
	private JTextField txtCliTelefone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	private JButton btnCliAdicionar = new JButton();
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtCliId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
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
	public TelaCliente() {
		setClosable(true);

		conexao = ModuloConexao.conector();

		setTitle("Cliente");
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 500, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome *");
		lblNome.setBounds(20, 173, 46, 14);
		contentPane.add(lblNome);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(20, 203, 46, 14);
		contentPane.add(lblEndereo);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(20, 233, 46, 14);
		contentPane.add(lblTelefone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 263, 46, 14);
		contentPane.add(lblEmail);

		JLabel lblCamposObrigatrios = new JLabel("* Campos Obrigat\u00F3rios");
		lblCamposObrigatrios.setForeground(Color.GRAY);
		lblCamposObrigatrios.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblCamposObrigatrios.setBounds(354, 233, 116, 14);
		contentPane.add(lblCamposObrigatrios);

		txtCliNome = new JTextField();
		txtCliNome.setBounds(75, 170, 395, 20);
		contentPane.add(txtCliNome);
		txtCliNome.setColumns(10);

		txtCliEndereco = new JTextField();
		txtCliEndereco.setColumns(10);
		txtCliEndereco.setBounds(75, 200, 395, 20);
		contentPane.add(txtCliEndereco);

		txtCliTelefone = new JTextField();
		txtCliTelefone.setColumns(10);
		txtCliTelefone.setBounds(75, 230, 86, 20);
		contentPane.add(txtCliTelefone);

		txtCliEmail = new JTextField();
		txtCliEmail.setColumns(10);
		txtCliEmail.setBounds(75, 260, 196, 20);
		contentPane.add(txtCliEmail);

		btnCliAdicionar.setEnabled(true);
		btnCliAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnCliAdicionar.setToolTipText("Adicionar Cliente");
		btnCliAdicionar.setSize(new Dimension(73, 73));
		btnCliAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliAdicionar.setBounds(424, 311, 40, 40);

		ImageIcon icon4 = new ImageIcon(TelaCliente.class.getResource("/br/com/GOS/icones/add.png"));
		Image img4 = icon4.getImage();
		Image newimg4 = img4.getScaledInstance(btnCliAdicionar.getWidth() - 15, btnCliAdicionar.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon4 = new ImageIcon(newimg4);
		btnCliAdicionar.setIcon(icon4);

		contentPane.add(btnCliAdicionar);

		JButton btnCliUpdate = new JButton("");
		btnCliUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		btnCliUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliUpdate.setToolTipText("Alterar Cliente");

		btnCliUpdate.setBounds(372, 311, 40, 40);
		ImageIcon icon3 = new ImageIcon(TelaCliente.class.getResource("/br/com/GOS/icones/update.png"));
		Image img3 = icon3.getImage();
		Image newimg3 = img3.getScaledInstance(btnCliUpdate.getWidth() - 15, btnCliUpdate.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon3 = new ImageIcon(newimg3);
		btnCliUpdate.setIcon(icon3);

		contentPane.add(btnCliUpdate);

		JButton btnCliDelete = new JButton("");
		btnCliDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});

		btnCliDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnCliDelete.setToolTipText("Deletar Cliente");
		btnCliDelete.setBounds(20, 311, 40, 40);

		ImageIcon icon5 = new ImageIcon(TelaCliente.class.getResource("/br/com/GOS/icones/delete.png"));
		Image img5 = icon5.getImage();
		Image newimg5 = img5.getScaledInstance(btnCliDelete.getWidth() - 15, btnCliDelete.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon5 = new ImageIcon(newimg5);
		btnCliDelete.setIcon(icon5);

		contentPane.add(btnCliDelete);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				pesquisarcliente();
			}
		});
		txtCliPesquisar.setBounds(24, 11, 416, 20);
		contentPane.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel lblSearch = new JLabel("");
		lblSearch.setBounds(447, 11, 23, 25);

		ImageIcon icon = new ImageIcon("C:\\Users\\Usuário\\Desktop\\GOS0\\icones\\search.png");
		Image img = icon.getImage();
		Image imgscale = img.getScaledInstance(lblSearch.getWidth(), lblSearch.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledicon = new ImageIcon(imgscale);
		lblSearch.setIcon(scaledicon);
		contentPane.add(lblSearch);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(20, 42, 444, 88);
		contentPane.add(scrollPane);

		tblClientes = new javax.swing.JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}

		};
		tblClientes.getTableHeader().setReorderingAllowed(false);
		tblClientes.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "ID", "NOME", "ENDERE\u00C7O", "TELEFONE", "E-MAIL" }));

		tblClientes.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// chamando o método quando clica na tabela
				setar_campos();
			}
		});

		scrollPane.setViewportView(tblClientes);

		tblClientes.setPreferredScrollableViewportSize(new Dimension(500, 100));

		txtCliId = new JTextField();
		txtCliId.setEnabled(false);
		txtCliId.setColumns(10);
		txtCliId.setBounds(75, 140, 63, 20);
		contentPane.add(txtCliId);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(20, 143, 46, 14);
		contentPane.add(lblId);

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
				btnCliAdicionar.setEnabled(true);
			}
		});
		btnLimparCampos.setBounds(354, 141, 116, 23);
		contentPane.add(btnLimparCampos);

	}

	private void adicionar() {
		// instrução sql que ira criar a entrada para os dados:
		String sql = "insert into tbcliente(nomecliente,endcliente,fonecli,emailclie) values (?,?,?,?)";

		try {
			pst = conexao.prepareStatement(sql);

			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliTelefone.getText());
			pst.setString(4, txtCliEmail.getText());

			// validação dos campos obrigatórios
			if ((((txtCliNome.getText().isEmpty()) || (txtCliTelefone.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				// a linha abixo atualiza a tabela usuario
				int adicionado = pst.executeUpdate();
				// a estrutura abaixo é para confirmar a adição, quando 1 ou mais linhas forem
				// adicionadas (>0)
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente " + txtCliNome.getText() + " adicionado!");
					limpar();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void pesquisarcliente() {
		// Pesquisa de acordo com o que está sendo escrito
		String sql = "select idcliente as ID, nomecliente as NOME, endcliente as ENDERECO,fonecli as TELEFONE,emailclie as EMAIL from tbcliente where nomecliente like ?";
		try {
			pst = conexao.prepareStatement(sql);
			// passando o conteudo da caixa para o ?
			// atenção ao % que é a continuacao da string sql:
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			// a rs2xml.jar preenche a tabela tblClientes:
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void alterar() {
		String sql = "update tbcliente set nomecliente=?,endcliente=?,fonecli=?,emailclie=? where idcliente=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliTelefone.getText());
			pst.setString(4, txtCliEmail.getText());
			pst.setString(5, txtCliId.getText());
			btnCliAdicionar.setEnabled(true);

			if ((((txtCliNome.getText().isEmpty()) || (txtCliTelefone.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				// a linha abixo atualiza a tabela usuario
				int adicionado = pst.executeUpdate();
				// a estrutura abaixo é para confirmar a adição, quando 1 ou mais linhas forem
				// adicionadas (>0)
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente " + txtCliNome.getText() + " alterado!");
					limpar();

				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void remover() {

		// Confirma a remoção do cliente:
		int confirma = JOptionPane.showConfirmDialog(null, "Remover " + txtCliNome.getText() + "?");
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbcliente where idcliente=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtCliId.getText());
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente " + txtCliNome.getText() + " removido!");
					txtCliNome.setText(null);
					txtCliEmail.setText(null);
					txtCliEndereco.setText(null);
					txtCliTelefone.setText(null);

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

		}

	}

	// método para setar os campos do formulario com o conteudo da tabela
	public void setar_campos() {
		// pegar a linha que está selecionada
		int setar = tblClientes.getSelectedRow();
		// setar o campo nome com o conteudo da coluna 1 da tabela na linha da váriavel
		txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		txtCliTelefone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
		txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
		btnCliAdicionar.setEnabled(false);

	}

	private void limpar() {
		txtCliNome.setText(null);
		txtCliEmail.setText(null);
		txtCliEndereco.setText(null);
		txtCliTelefone.setText(null);
		txtCliPesquisar.setText(null);

	}
}
