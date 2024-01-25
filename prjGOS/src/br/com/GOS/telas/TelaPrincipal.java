package br.com.GOS.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JDesktopPane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Label;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Rectangle;


public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	public static JMenuItem menuCadUsu;
	public static JMenu menuRel;
	public static JLabel lblUsuario = new JLabel();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setVisible(true);
		
		setPreferredSize(new Dimension(900, 650));
		setSize(new Dimension(900, 650));
		setResizable(false);
		setTitle("x - Sistema para controle de OS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		
		JMenu menuCad = new JMenu("Cadastro");
		menu.add(menuCad);
		
		
		
		
		
		
		
		menuRel = new JMenu("Relat\u00F3rio");
		menuRel.setEnabled(false);
		menu.add(menuRel);
		
		JMenuItem menuRelSer = new JMenuItem("Servi\u00E7os");
		menuRelSer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		menuRel.add(menuRelSer);
		
		JMenu menuOpc = new JMenu("Op\u00E7\u00F5es");
		menu.add(menuOpc);
		
		JMenuItem menuOpcSai = new JMenuItem("Sair");
		menuOpcSai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//exibe caixa de diálogo SIM ou NÃO
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menuOpcSai.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		menuOpc.add(menuOpcSai);
		
		JMenu menuAju = new JMenu("Ajuda");
		menu.add(menuAju);
		
		JMenuItem menuAjuSob = new JMenuItem("Sobre");
		menuAjuSob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Chamando a tela SOBRE
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		menuAjuSob.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_MASK));
		menuAju.add(menuAjuSob);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(900, 650));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JDesktopPane desktop = new JDesktopPane();
		desktop.setBounds(new Rectangle(0, 0, 500, 420));
		desktop.setAlignmentX(Component.LEFT_ALIGNMENT);
		desktop.setBounds(0, 0, 500, 420);
		contentPane.setLayout(null);
		contentPane.add(desktop);
		
		JMenuItem menCadOS = new JMenuItem("OS");
		menCadOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaOS os = new TelaOS();
				os.setVisible(true);
				desktop.add(os);
			}
		});
		menCadOS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		menuCad.add(menCadOS);
		
		JMenuItem menuCadCli = new JMenuItem("Cliente");
		menuCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente cliente = new TelaCliente();
				cliente.setVisible(true);
				desktop.add(cliente);
			}
		});
		menuCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		menuCad.add(menuCadCli);
		
		menuCadUsu = new JMenuItem("Usu\u00E1rios");
		menuCadUsu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//as lnhas abaixo abrem o for TelaUsuário dentro do desktoppane
				TelaUsuario usuario = new TelaUsuario();
				usuario.setVisible(true);
				
				desktop.add(usuario);
				usuario.setBounds(0, 0, 500, 420);
			}
			
		});
		menuCadUsu.setEnabled(false);
		menuCadUsu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK));
		menuCad.add(menuCadUsu);
		
		JLabel lblIconTelaPrincipal = new JLabel("");
		lblIconTelaPrincipal.setBounds(526, 5, 136, 143);
		contentPane.add(lblIconTelaPrincipal);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/br/com/GOS/icones/x.png"));
		
		Image img = icon.getImage();
		Image imgscale = img.getScaledInstance(lblIconTelaPrincipal.getWidth(), lblIconTelaPrincipal.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledicon = new ImageIcon(imgscale);
		lblIconTelaPrincipal.setIcon(scaledicon);
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblData.setBounds(510, 184, 112, 16);
		contentPane.add(lblData);
		lblUsuario.setText("USUARIO");
		
		
		lblUsuario.setBounds(510, 159, 102, 14);
		contentPane.add(lblUsuario);
		
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				//As linahs abaixo substituem a label DATA pela data atual do sistema
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT); 
				lblData.setText(formatador.format(data));
				
			}
		});
		
	}
}
