package br.com.GOS.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.awt.Color;

public class TelaSobre extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
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
	public TelaSobre() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JEditorPane dtrpnSistemaParaControle = new JEditorPane();
		dtrpnSistemaParaControle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		dtrpnSistemaParaControle.setOpaque(false);
		dtrpnSistemaParaControle.setText("Sistema para controle de ordem de servi\u00E7os");
		dtrpnSistemaParaControle.setBounds(51, 11, 339, 69);
		contentPane.add(dtrpnSistemaParaControle);
		
		JTextPane txtpnDesenvolvidoPorBruno = new JTextPane();
		txtpnDesenvolvidoPorBruno.setOpaque(false);
		txtpnDesenvolvidoPorBruno.setText("Desenvolvido por Bruno Lima");
		txtpnDesenvolvidoPorBruno.setBounds(155, 145, 144, 20);
		contentPane.add(txtpnDesenvolvidoPorBruno);
		
		JTextPane txtpnRecursoopensourceUtilizado = new JTextPane();
		txtpnRecursoopensourceUtilizado.setContentType("text");
		txtpnRecursoopensourceUtilizado.setCaretColor(Color.GRAY);
		txtpnRecursoopensourceUtilizado.setForeground(Color.GRAY);
		txtpnRecursoopensourceUtilizado.setOpaque(false);
		txtpnRecursoopensourceUtilizado.setText("Recurso (open-source) utilizado: rs2xml");
		txtpnRecursoopensourceUtilizado.setBounds(125, 231, 235, 20);
		contentPane.add(txtpnRecursoopensourceUtilizado);
	}
}
