package br.com.GOS.telas;

import java.sql.*;
import br.com.GOS.dal.*;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import net.proteanit.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaOS extends JInternalFrame {
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtCliID;
	private JTextField txtOSEquip;
	private JTextField txtOSServ;
	private JTextField txtOSDef;
	private JTextField txtOSTec;
	private JTextField txtOSValor;
	private JButton btnOSDeletar = new JButton();
	private JComboBox<String> cboOS = new JComboBox<String>();
	private JButton btnOSAlterar = new JButton();
	private JRadioButton rdbtnOS = new JRadioButton();
	private JRadioButton rdbtnOrcamento = new JRadioButton();
	private JButton btnOSImprimir = new JButton();
	private JTextField txtCliPesquisar = new JTextField();
	private JTable tblClientes = new JTable();
	JButton btnOSAdd = new JButton();
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	// a linha abaixo cria uma variavel para armazenar um texto de acordo com o
	// radio button selecionado:
	private String tipo; // essa variavel será armazenada no Banco da coluna "tipo"

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOS frame = new TelaOS();
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
	public TelaOS() {
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {

			}
		});
		setTitle("Ordem de Servi\u00E7o");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(0, 0, 500, 420);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 156, 108);
		getContentPane().add(panel);

		JLabel lblNOs = new JLabel("n\u00BA OS");
		lblNOs.setBounds(10, 39, 43, 14);

		txtOS = new JTextField();
		txtOS.setBounds(48, 36, 55, 20);
		txtOS.setEditable(false);
		txtOS.setColumns(10);

		txtData = new JTextField();
		txtData.setHorizontalAlignment(SwingConstants.CENTER);
		txtData.setBounds(10, 8, 136, 20);
		txtData.setFont(new Font("Arial", Font.BOLD, 12));
		txtData.setEditable(false);
		txtData.setColumns(10);
		rdbtnOrcamento.setBounds(10, 65, 102, 20);

		// JRadioButton rdbtnOrcamento = new JRadioButton("Or\u00E7amento");
		rdbtnOrcamento.setText("Orçamento");
		tipo = "Orçamento";
		rdbtnOrcamento.setSelected(true);
		rdbtnOrcamento.setVerticalTextPosition(SwingConstants.BOTTOM);
		rdbtnOS.setBounds(10, 85, 126, 23);

		// JRadioButton rdbtnOS = new JRadioButton();
		rdbtnOS.setText("Ordem de Serviço");
		rdbtnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Ordem de Serviço";
			}
		});

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rdbtnOS);
		grupo.add(rdbtnOrcamento);
		panel.setLayout(null);
		panel.add(rdbtnOS);
		panel.add(rdbtnOrcamento);
		panel.add(txtOS);
		panel.add(lblNOs);
		panel.add(txtData);

		JLabel lblNewLabel = new JLabel("Situa\u00E7\u00E3o");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(63, 120, 82, 20);
		getContentPane().add(lblNewLabel);

		cboOS.setModel(new DefaultComboBoxModel<String>(new String[] { " ", "Na bancada", "Entrega OK",
				"Or\u00E7amento REPROVADO", "Aguardando Aprova\u00E7\u00E3o", "Aguardando Pe\u00E7as",
				"Abandono pelo Cliente", "Retornou" }));

		cboOS.setBounds(20, 138, 143, 20);
		getContentPane().add(cboOS);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(173, 11, 301, 140);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				pesquisar_cliente();
			}
		});

		txtCliPesquisar.setBounds(10, 23, 157, 20);
		panel_1.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);
		txtCliPesquisar.setRequestFocusEnabled(true);

		JLabel lblSearch = new JLabel("");
		lblSearch.setBounds(177, 23, 20, 20);

		ImageIcon icon = new ImageIcon("C:\\Users\\Usuário\\Desktop\\GOS0\\icones\\search.png");
		Image img = icon.getImage();
		Image imgscale = img.getScaledInstance(lblSearch.getWidth(), lblSearch.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledicon = new ImageIcon(imgscale);
		lblSearch.setIcon(scaledicon);
		panel_1.add(lblSearch);

		txtCliID = new JTextField();
		txtCliID.setEditable(false);
		txtCliID.setColumns(10);
		txtCliID.setBounds(247, 23, 44, 20);
		panel_1.add(txtCliID);

		JLabel lblid = new JLabel("*ID");
		lblid.setBounds(207, 26, 30, 14);
		panel_1.add(lblid);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 281, 70);
		panel_1.add(scrollPane);

		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setar_campos();
			}
		});

		scrollPane.setViewportView(tblClientes);
		tblClientes.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, },
				new String[] { "ID", "Nome", "Fone" }));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(20, 171, 454, 151);
		getContentPane().add(panel_2);

		JLabel lblServio = new JLabel("Servi\u00E7o");

		JLabel lblEquipamento = new JLabel("* Equipamento");

		JLabel lbldefeito = new JLabel("* Defeito");

		JLabel lbltcnico = new JLabel("* T\u00E9cnico");

		JLabel lblValorTotal = new JLabel("Valor Total:");

		txtOSEquip = new JTextField();
		txtOSEquip.setColumns(10);

		txtOSServ = new JTextField();
		txtOSServ.setColumns(10);

		txtOSDef = new JTextField();
		txtOSDef.setColumns(10);

		txtOSTec = new JTextField();
		txtOSTec.setColumns(10);

		txtOSValor = new JTextField();
		txtOSValor.setText("0");
		txtOSValor.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_2
								.createParallelGroup(Alignment.TRAILING).addComponent(lblServio)
								.addComponent(
										lbldefeito, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbltcnico).addComponent(lblEquipamento))
						.addGap(18)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel_2
								.createSequentialGroup()
								.addComponent(txtOSTec, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblValorTotal)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtOSValor, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
								.addComponent(txtOSDef).addComponent(txtOSServ)
								.addComponent(txtOSEquip, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
						.addContainerGap(38, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(lblEquipamento).addComponent(
						txtOSEquip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtOSServ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblServio))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtOSDef, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lbldefeito))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtOSTec, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(txtOSValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltcnico).addComponent(lblValorTotal))
				.addContainerGap(21, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 333, 464, 56);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JButton btnOSPesquisar = new JButton();
		btnOSPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_os();
			}
		});
		btnOSPesquisar.setToolTipText("");
		btnOSPesquisar.setBounds(284, 11, 35, 35);
		ImageIcon icon2 = new ImageIcon(TelaOS.class.getResource("/br/com/GOS/icones/search.png"));
		Image img2 = icon2.getImage();
		Image newimg = img2.getScaledInstance(btnOSPesquisar.getWidth() - 15, btnOSPesquisar.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg);
		btnOSPesquisar.setIcon(icon2);
		btnOSPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_3.add(btnOSPesquisar);

		btnOSAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar_os();
			}
		});
		btnOSAlterar.setToolTipText("");
		btnOSAlterar.setBounds(419, 11, 35, 35);
		ImageIcon icon3 = new ImageIcon(TelaOS.class.getResource("/br/com/GOS/icones/update.png"));
		Image img3 = icon3.getImage();
		Image newimg3 = img3.getScaledInstance(btnOSAlterar.getWidth() - 15, btnOSAlterar.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon3 = new ImageIcon(newimg3);
		btnOSAlterar.setIcon(icon3);
		btnOSAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_3.add(btnOSAlterar);

		btnOSAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitir_os();
			}
		});
		btnOSAdd.setToolTipText("");
		btnOSAdd.setBounds(329, 11, 35, 35);

		ImageIcon icon4 = new ImageIcon(TelaOS.class.getResource("/br/com/GOS/icones/add.png"));
		Image img4 = icon4.getImage();
		Image newimg4 = img4.getScaledInstance(btnOSAdd.getWidth() - 15, btnOSAdd.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon4 = new ImageIcon(newimg4);
		btnOSAdd.setIcon(icon4);
		btnOSAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panel_3.add(btnOSAdd);

		btnOSDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar_os();
			}
		});
		btnOSDeletar.setToolTipText("");
		btnOSDeletar.setBounds(374, 11, 35, 35);

		ImageIcon icon5 = new ImageIcon(TelaOS.class.getResource("/br/com/GOS/icones/delete.png"));
		Image img5 = icon5.getImage();
		Image newimg5 = img5.getScaledInstance(btnOSDeletar.getWidth() - 15, btnOSDeletar.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		icon5 = new ImageIcon(newimg5);
		btnOSDeletar.setIcon(icon5);
		btnOSDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panel_3.add(btnOSDeletar);

		btnOSImprimir.setToolTipText("");
		btnOSImprimir.setBounds(240, 11, 35, 35);

		ImageIcon icon6 = new ImageIcon(TelaOS.class.getResource("/br/com/GOS/icones/print.png"));
		Image img6 = icon6.getImage();
		Image newimg6 = img6.getScaledInstance((btnOSImprimir.getWidth() - 15), btnOSImprimir.getHeight() - 15,
				java.awt.Image.SCALE_SMOOTH);
		System.out.println(btnOSImprimir.getWidth());
		icon6 = new ImageIcon(newimg6);
		btnOSImprimir.setIcon(icon6);
		btnOSImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panel_3.add(btnOSImprimir);

		conexao = ModuloConexao.conector();

		desabilitar_botoes();

	}

	private void pesquisar_cliente() {

		String sql = "select idcliente as ID, nomecliente as NOME,fonecli as TELEFONE from tbcliente where nomecliente like ?";
		try {

			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			habilitar_botoes();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void setar_campos() {

		int setar = tblClientes.getSelectedRow();
		txtCliID.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
	}

//Para cadastrar uma OS:
	private void emitir_os() {
		String sql = "insert into tbos (tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcliente) values (?,?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			// Armazena o radiobuton selecionado:
			pst.setString(1, tipo);
			pst.setString(2, cboOS.getSelectedItem().toString());
			pst.setString(3, txtOSEquip.getText());
			pst.setString(4, txtOSDef.getText());
			pst.setString(5, txtOSServ.getText());
			pst.setString(6, txtOSTec.getText());
			pst.setString(7, txtOSValor.getText().replace(",", "."));
			pst.setString(8, txtCliID.getText());

			// validação dos campos obrigatórios
			if ((txtCliID.getText().isEmpty()) || (txtOSEquip.getText().isEmpty()) || (txtOSDef.getText().isEmpty())
					|| (cboOS.getSelectedItem().equals(" "))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
					limpa_campo();
					desabilitar_botoes();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	// método para pesquisar os:
	private void pesquisar_os() {
		String num_os = JOptionPane.showInputDialog("Número da OS");
		String sql = "select os,date_format(data_os,'%d/%m/%Y - %H:%i'),tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcliente from tbos where os=" + num_os;
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			// caso tenha um registro correspondente
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));

				// variavel para setar os radio butons
				String rbtTipo = rs.getString(3);
				if (rbtTipo.equals("Ordem de Serviço")) {
					rdbtnOS.setSelected(true);
					tipo = "Ordem de Serviço";
				} else {
					rdbtnOrcamento.setSelected(true);
					tipo = "Orçamento";
				}
				cboOS.setSelectedItem(rs.getString(4));
				txtOSEquip.setText(rs.getString(5));
				txtOSDef.setText(rs.getString(6));
				txtOSServ.setText(rs.getString(7));
				txtOSTec.setText(rs.getString(8));
				txtOSValor.setText(rs.getString(9));
				txtCliID.setText(rs.getString(10));
				habilitar_botoes();
				btnOSAdd.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada!");
			}
		} catch (java.sql.SQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "OS inválida!");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}

	private void alterar_os() {
		String sql = "update tbos set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=?"
				+ " where os=?";
		try {
			pst = conexao.prepareStatement(sql);
			// Armazena o radiobuton selecionado:
			pst.setString(1, tipo);
			pst.setString(2, cboOS.getSelectedItem().toString());
			pst.setString(3, txtOSEquip.getText());
			pst.setString(4, txtOSDef.getText());
			pst.setString(5, txtOSServ.getText());
			pst.setString(6, txtOSTec.getText());
			pst.setString(7, txtOSValor.getText().replace(",", "."));
			pst.setString(8, txtOS.getText());

			// validação dos campos obrigatórios
			if ((txtCliID.getText().isEmpty()) || (txtOSEquip.getText().isEmpty()) || (txtOSDef.getText().isEmpty())
					|| (cboOS.getSelectedItem().equals(" "))) {
				JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");
					limpa_campo();
					desabilitar_botoes();
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void deletar_os() {

		int confirma = JOptionPane.showConfirmDialog(null, "Excluir OS?", "Atenção!", JOptionPane.YES_NO_CANCEL_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbos where os=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtOS.getText());
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "OS excluída com sucesso!");
					limpa_campo();
					desabilitar_botoes();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	private void limpa_campo() {
		txtOSEquip.setText(null);
		txtCliID.setText(null);
		txtCliPesquisar.setText(null);
		txtOSServ.setText(null);
		txtOSTec.setText(null);
		txtOSValor.setText(null);
		txtOSDef.setText(null);
		txtOS.setText(null);
		((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
		txtData.setText(null);
		cboOS.setSelectedItem(" ");
		txtCliPesquisar.setEnabled(true);
		tblClientes.setVisible(true);

	}

	private void habilitar_botoes() {
		btnOSDeletar.setEnabled(true);
		btnOSAlterar.setEnabled(true);
		btnOSImprimir.setEnabled(true);
		btnOSAdd.setEnabled(true);
	}

	private void desabilitar_botoes() {
		btnOSDeletar.setEnabled(false);
		btnOSAlterar.setEnabled(false);
		btnOSImprimir.setEnabled(false);

	}
}
