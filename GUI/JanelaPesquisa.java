package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Sistema.Aluno;
import Sistema.Sistema;
import Sistema.Verificar;
import exception.NaoExisteException;
import Imagens.*;

public class JanelaPesquisa extends JFrame implements MouseListener, ActionListener{
	
	ImageIcon imagem = new ImageIcon(getClass().getResource("/Imagens/JanelaPesquisa1.png"));
	JLabel rotulo = new JLabel(imagem);
	JPanel painel = new JPanel();
	JTextField caixa1 = new JTextField("Nome do indivíduo");
	JButton botao1 = new JButton();
	boolean apagar = true;
	Sistema sistema;
	
	public JanelaPesquisa(Sistema sistema) {
		this.sistema = sistema;
		painel.setLayout(null);

		botao1.setContentAreaFilled(false);
		botao1.addActionListener(this);
		caixa1.addMouseListener(this);

		caixa1.setBounds(82, 130, 255, 26);
		botao1.setBounds(337, 128, 33, 30);

		rotulo.add(caixa1);
		rotulo.add(botao1);
		
		rotulo.setBounds(-3, -95, 1030, 768);

		painel.add(rotulo);
		add(painel);
		setSize(442, 126);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void mousePressed(MouseEvent e) {
		if(apagar) {
			caixa1.setText("");
			apagar = false;
		}
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	public static void main(String[] args) {
		new JanelaPesquisa(new Sistema());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botao1) {
			try {
				Aluno aluno = sistema.verificarAluno(caixa1.getText());
				new Verificar(sistema, aluno);
				rotulo.setIcon(new ImageIcon(getClass().getResource("/Imagens/JanelaPesquisa2.png")));
				setSize(442, 497);
			} catch(NaoExisteException e1) {
				new JanelaAvisos().naoExiste();
				rotulo.setIcon(new ImageIcon(getClass().getResource("/Imagens/JanelaPesquisa1.png")));
				setSize(442, 126);
			} finally {
				setLocationRelativeTo(null);
			}
		}		
	}

}


	
