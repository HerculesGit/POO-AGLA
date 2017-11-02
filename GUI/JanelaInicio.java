package GUI;
import Sistema.Sistema;
import exception.NaoExisteException;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class JanelaInicio extends JFrame implements MouseListener, ActionListener{
	ImageIcon imagem = new ImageIcon(getClass().getResource("/Imagens/JanelaInicio.png"));
	JLabel rotulo = new JLabel(imagem);
	JPanel painel = new JPanel();
	JTextField caixa = new JTextField("Matrícula");
	JPasswordField caixa2 = new JPasswordField("Senha");
	JButton entrar = new JButton();
	JButton registrar = new JButton();
	Sistema sistema = new Sistema();
	boolean bloq1 = false;
	boolean bloq2 = false;
	
	public JanelaInicio(){
		new JFrame();
		painel.setLayout(null);
		
		entrar.setBounds(485, 478, 61, 21);
		entrar.setOpaque(false);
		entrar.setContentAreaFilled(false);
		
		registrar.setBounds(840, 132, 143, 35);
		registrar.setOpaque(false);
		registrar.setContentAreaFilled(false);

		caixa.setBounds(405, 392, 222, 25);
		caixa2.setBounds(405, 436, 222, 25);
		rotulo.setBounds(-3, -100, 1030, 768);
		
		entrar.addActionListener(this);
		registrar.addActionListener(this);
		
		caixa.addMouseListener(this);
		caixa2.addMouseListener(this);
				
		rotulo.add(caixa);
		rotulo.add(caixa2);
		rotulo.add(entrar);
		rotulo.add(registrar);
		painel.add(rotulo);
		add(painel);
		
		setSize(1030, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == entrar){
			try {
				if(sistema.entra(caixa.getText(), caixa2.getText())){
					setVisible(false);
					new JanelaPrincipal(sistema);
				} else {
					new JanelaAvisos().confirmarExclusao();
				}
			}catch(NaoExisteException e1){
				new JanelaAvisos().confirmarExclusao();
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == caixa && bloq1 == false) {
			caixa.setText("");
			bloq1 = true;
		}else if(e.getSource() == caixa2 && bloq2 == false) {
			caixa2.setText("");
			bloq2 = true;
		}
	}
	public void mouseClicked(MouseEvent e) {
		//Método não utilizado.
	}
	public void mouseEntered(MouseEvent e) {
		//Método não utilizado.
	}
	public void mouseExited(MouseEvent e) {
		//Método não utilizado.
	}
	public void mouseReleased(MouseEvent e) {
		//Método não utilizado.
	}

	public static void main(String[] args){
		new JanelaInicio();
	}
}
