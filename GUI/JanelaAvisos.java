package GUI;

import Imagens.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaAvisos extends JFrame{

	ImageIcon imagem = new ImageIcon(getClass().getResource("/Imagens/JanelaConfirmação.png"));
	JPanel painel = new JPanel();
	JLabel rotulo = new JLabel(imagem);
	JButton[] botoes = new JButton[2];
	boolean remover = false;

	public JanelaAvisos() {
		new JFrame();
		painel.setLayout(null);
		
		for(int k = 0; k < 2; k++) {
			botoes[k] = new JButton();
			botoes[k].setContentAreaFilled(false);
			botoes[k].setVisible(false);
			rotulo.add(botoes[k]);
		}
		
		botoes[0].setBounds(70, 110, 86, 23);
		botoes[1].setBounds(189, 110, 86, 23);
		
		rotulo.setBounds(0, 0, 1025, 579);

		painel.add(rotulo);
		add(painel);
		setSize(354, 201);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void confirmarExclusao() {
		botoes[0].setVisible(true);
		botoes[1].setVisible(true);
		setVisible(true);
		rotulo.setIcon(new ImageIcon(getClass().getResource("/Imagens/ConfirmarExclusão.png")));
		botoes[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remover = true;
				setVisible(false);
			}
		});
		botoes[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remover = false;
				setVisible(false);
			}
		});
	}
	
	public boolean getRetorno() {
		return remover;
	}
	
	public static void main(String[] args) {
		new JanelaAvisos().confirmarExclusao();
	}

	public void naoExiste() {
 		rotulo.setIcon(new ImageIcon(getClass().getResource("/Imagens/NaoEncontrado.png")));
 		setVisible(true);
	}
	
}
