package Sistema;

import Imagens.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import GUI.JanelaAvisos;

public class Verificar extends JFrame implements ActionListener{

	ImageIcon imagem = new ImageIcon(getClass().getResource("/Imagens/JanelaVerificação.png"));
	JPanel painel = new JPanel();
	JLabel rotulo = new JLabel(imagem);
	JTextField[] caixas = new JTextField[13];
	JTextArea area = new JTextArea();
	JButton botao1 = new JButton();
	JButton botao2 = new JButton();
	JButton botao3 = new JButton();
	JButton botao4 = new JButton();
	JRadioButton rbotao1 = new JRadioButton();
	JRadioButton rbotao2 = new JRadioButton();
	ButtonGroup grupo = new ButtonGroup();
	boolean cond1 = false;
	boolean cond2 = false;
	Sistema sistema;
	Aluno aluno;
	
	public Verificar(Sistema sistema, Aluno aluno) {
		this.sistema = sistema;
		this.aluno = aluno;
		new JFrame();
		painel.setLayout(null);
		
		for(int k = 0; k < 13; k++) {
			caixas[k] = new JTextField();
		}
		
		caixas[0].setBounds(26, 28, 250, 24);
		caixas[1].setBounds(300, 28, 160, 24);
		caixas[2].setBounds(26, 72, 250, 24);
		caixas[3].setBounds(300, 72, 160, 24);
		caixas[4].setBounds(26, 116, 110, 24);
		caixas[5].setBounds(160, 116, 116, 24);
		caixas[6].setBounds(300, 116, 160, 24);
		caixas[7].setBounds(160, 158, 116, 24);
		caixas[8].setBounds(300, 158, 160, 24);
		caixas[9].setBounds(160, 202, 116, 24);
		caixas[10].setBounds(300, 202, 160, 24);
		caixas[11].setBounds(26, 246, 110, 24);
		caixas[12].setBounds(160, 246, 300, 24);
		
		caixas[0].setText(aluno.getDadosPessoais().getNome());
		caixas[0].setText(aluno.getMatricula());
		caixas[0].setText(aluno.getDadosPessoais().getEmail());
		caixas[0].setText(aluno.getDadosPessoais().getCpf());
		caixas[0].setText(aluno.getDadosPessoais().getRg());
		caixas[0].setText(aluno.getDadosPessoais().getDataNascimento());;
		caixas[0].setText(aluno.getDadosPessoais().getTelefone().toString());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getEstado());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getCidade());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getBairro());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getLogradouro());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getNumero());
		caixas[0].setText(aluno.getDadosPessoais().getEndereco().getComplemento());
		
		//area.setBounds(555, 60, 146, 293);
		
		botao1.setBounds(26, 291, 127, 23);
		botao2.setBounds(180, 290, 164, 23);
		botao3.setBounds(26, 335, 127, 23);
		botao4.setBounds(180, 335, 164, 23);
		
		rbotao1.setBounds(43, 189, 17, 20);
		rbotao2.setBounds(90, 189, 17, 20);
		
		for(JTextField j: caixas) {
			j.setEnabled(false);
		}
		
		botao1.setContentAreaFilled(false);
		botao2.setContentAreaFilled(false);
		botao3.setContentAreaFilled(false);
		botao4.setContentAreaFilled(false);
		
		botao1.addActionListener(this);
		botao2.addActionListener(this);
		botao3.addActionListener(this);
		botao4.addActionListener(this);

		for(JTextField j: caixas) {
			rotulo.add(j);
		}

		rotulo.add(area);
		rotulo.add(botao1);
		rotulo.add(botao2);
		rotulo.add(botao3);
		rotulo.add(botao4);
		rotulo.add(rbotao1);
		rotulo.add(rbotao2);

		rbotao1.setSelected(true);
		grupo.add(rbotao1);
		grupo.add(rbotao2);
		
		rotulo.setBounds(0, 0, 1025, 579);
		painel.add(rotulo);
		add(painel);
		
		setTitle("Verificar");
		setSize(745, 417);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void editor() {
		for(JTextField j: caixas) {
			j.setEnabled(true);
		}
		cond1 = true;
	}
	
	public void contraEditor() {
		for(JTextField j: caixas) {
			j.setEnabled(false);
		}
		cond1 = false;
	}
	
	public void alterador() {
		for(JTextField j: caixas) {
			j.setText(j.getText());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botao1 && cond1 == false) {
			setTitle("Alterar");
			rotulo.setIcon(new ImageIcon(getClass().getResource("/Imagens/JanelaAlteração.png")));
			botao3.setVisible(false);
			botao4.setVisible(false);
			editor();
			if(e.getSource() == botao2) {
				contraEditor();
			}
		}
		else if(e.getSource() == botao1 && cond1) {
			alterador();
			setTitle("Verificar");
			rotulo.setIcon(imagem);
			botao3.setVisible(true);
			botao4.setVisible(true);
			contraEditor();
		}
		if(e.getSource() == botao3) {
			JanelaAvisos janela = new JanelaAvisos();
			janela.confirmarExclusao();
			if(janela.getRetorno()) {
				/**Colocar método que remove a pessoa aqui
				 * 
				 * 
				 */
			}
		}

	}
	
	public static void main(String[] args) {
		new Verificar(new Sistema(), new Aluno());
	}
}
