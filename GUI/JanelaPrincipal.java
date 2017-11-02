package GUI;
import Imagens.*;
import Sistema.Sistema;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class JanelaPrincipal extends JFrame implements ActionListener{
	ImageIcon imagem = new ImageIcon(getClass().getResource("/Imagens/JanelaPrincipal.png"));
	JLabel rotulo = new JLabel(imagem);
	JPanel painel = new JPanel();
	JButton botao1 = new JButton();
	JButton botao2 = new JButton();
	JButton botao3 = new JButton();
	JButton botao4 = new JButton();
	JComboBox<String> caixa1 = new JComboBox<String>();
	JComboBox<String> caixa2 = new JComboBox<String>();
	JComboBox<String> caixa3 = new JComboBox<String>();
	BasicComboBoxRenderer.UIResource UIResource = new BasicComboBoxRenderer.UIResource();
	Sistema sistema;

	
	public JanelaPrincipal(Sistema sistema) {
		this.sistema = sistema;

		new JFrame();
		painel.setLayout(null);
		
		botao1.setBounds(3, 229, 203, 50);
		botao1.setOpaque(false);
		botao1.setContentAreaFilled(false);
		
		botao2.setBounds(3, 278, 203, 50);
		botao2.setOpaque(false);
		botao2.setContentAreaFilled(false);
		
		botao3.setBounds(3, 327, 203, 52);
		botao3.setOpaque(false);
		botao3.setContentAreaFilled(false);
		
		botao4.setBounds(3, 378, 203, 50);
		botao4.setOpaque(false);
		botao4.setContentAreaFilled(false);
		
		caixa1.setBounds(206, 229, 203, 0);
		caixa2.setBounds(206, 278, 250, 0);
		caixa3.setBounds(206, 327, 203, 0);
		
		caixa1.addItem("");
		caixa1.addItem("Verificar algum aluno");
		caixa1.addItem("Cadastrar um aluno");
		caixa1.addItem("Verificar alguma turma");

		caixa2.addItem("");
		caixa2.addItem("Verificar algum professor");
		caixa2.addItem("Cadastrar um professor");
		caixa2.addItem("Verificar professores de alguma disciplina");

		caixa3.addItem("");
		caixa3.addItem("Cadastrar alguma disciplina");
		caixa3.addItem("About!");
		
		caixa1.setBackground(new Color(20, 140, 170));
		caixa2.setBackground(new Color(20, 140, 170));
		caixa3.setBackground(new Color(20, 140, 170));

	    UIResource.setHorizontalAlignment(SwingConstants.CENTER);

	    caixa1.setRenderer(UIResource);
	    caixa2.setRenderer(UIResource);
	    caixa3.setRenderer(UIResource);
	    
		caixa1.setForeground(getForeground().WHITE);
		caixa2.setForeground(getForeground().WHITE);
		caixa3.setForeground(getForeground().WHITE);

		botao1.addActionListener(this);
		botao2.addActionListener(this);
		botao3.addActionListener(this);
		botao4.addActionListener(this);
		
		rotulo.setBounds(-3, -100, 1030, 768);

		rotulo.add(botao1);
		rotulo.add(botao2);		
		rotulo.add(botao3);
		rotulo.add(botao4);
		rotulo.add(caixa1);
		rotulo.add(caixa2);
		rotulo.add(caixa3);
		painel.add(rotulo);
		add(painel);
		
		setSize(1030, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botao1) {
			caixa1.setPopupVisible(true);
		}
		if(e.getSource() == botao2) {
			caixa2.setPopupVisible(true);
		}
		if(e.getSource() == botao3) {
			caixa3.setPopupVisible(true);
		}
		if(e.getSource() == botao4) {
			System.exit(0);
		}
	}

	public static void main(String[] ags) {
		new JanelaPrincipal(new Sistema());
	}
}