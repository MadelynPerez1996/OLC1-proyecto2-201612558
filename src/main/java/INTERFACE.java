import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class INTERFACE extends JFrame{
	
	public JButton[] botones = new JButton[4];
	private JPanel contentPane;
	public JTextArea archivo = new JTextArea();
	public int G = 0;
	public int N = 0;
	Open open = new Open();
	Guardar guardar = new Guardar();
	GuardarComo GuardarComo = new GuardarComo();
	Herramientas Herramientas = new Herramientas();
	public String NombreArchivo = "";
	public JComboBox Herramienta = new JComboBox();
	//ReporteAnalizadorLexico ReporteLexico = new ReporteAnalizadorLexico();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					INTERFACE frame = new INTERFACE();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
						    
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 * @return 
	 * @return 
	 */
	public INTERFACE() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 200, 900, 600);
	    setLayout(null);
	    Herramienta.setBounds(260,30,100, 60);
		Herramienta.addItem("Nuevo");
		Herramienta.addItem("Ejecutar");
		add(Herramienta);
		Herramienta.setVisible(false);
		ImageIcon[] iconos = {
                new ImageIcon("IMAGE/abrir.png"),
                new ImageIcon("IMAGE/SAVE.jpg"),
                new ImageIcon("IMAGE/GuardarComo.png"),
                new ImageIcon("IMAGE/Herramientas.png")
        };	


		
		JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		for (int i = 0; i < botones.length; i++) {
            botones[i] = new JButton();
            Dimension nuevoTamaño = new Dimension(50,50);
            ImageIcon imagen = new ImageIcon(iconos[i].getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_DEFAULT));
            botones[i].setIcon(imagen);
            botones[i].setPreferredSize(nuevoTamaño);
            panel.add(botones[i]);
        }
		
		panel.setBounds(10, 10, 500, 100);
		add(panel);
		
		
		JScrollPane direccion = new JScrollPane(archivo);
		direccion.setBounds(20,130,500,300);
		add(direccion);
	
		botones[0].addActionListener(open);
		botones[1].addActionListener(guardar);
		botones[2].addActionListener(GuardarComo);
		botones[3].addActionListener(Herramientas);
			
	}

  /*Método para abrir un archivo*/
	public class Open implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser Abriendo = new JFileChooser();
			FileNameExtensionFilter nombreArchivo = new FileNameExtensionFilter("Archivos .javañ","javañ");
			Abriendo.setFileFilter(nombreArchivo);
			int selection = Abriendo.showOpenDialog(null);
			if(selection == JFileChooser.APPROVE_OPTION) {
				File FileSelected = Abriendo.getSelectedFile();
				if(FileSelected.exists()) {
					try (FileReader fr = new FileReader(FileSelected)){
						BufferedReader Br = new BufferedReader(fr);
						String Linea = "";
						String Texto = "";
						while((Linea = Br.readLine())!= null) {
							Texto = Texto + Linea + "\n"; 
						}
						archivo.setText(Texto);
					}catch(Exception er) {
						er.printStackTrace();
					}
				}
			}
	
		}
	}
		
	/*Método para abrir la ventana guardar por si no se ha guardado ninguna vez*/
	public class GuardarComo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser Guardando = new JFileChooser();
			FileNameExtensionFilter nombreArchivo = new FileNameExtensionFilter("Archivos .javañ","javañ");
			Guardando.setFileFilter(nombreArchivo);
			int selection = Guardando.showSaveDialog(null);
			if(selection == JFileChooser.APPROVE_OPTION) {
				try {
					File FileSelected = Guardando.getSelectedFile();
					NombreArchivo = FileSelected.toString() + ".javañ";
					BufferedWriter bw = new BufferedWriter(new FileWriter(NombreArchivo));
					String texto = archivo.getText();
					bw.write(texto);
					bw.close();
					G = 1;
				}catch (IOException er) {
					er.printStackTrace();
				}
			}
		}			
	
	}

	/*Método para guardar el archivo modificado*/
	public class Guardar implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (G==0) {
				GuardarComo.actionPerformed(e);
			}else {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(NombreArchivo));
					String texto = archivo.getText();
					bw.write(texto);
					bw.close();
				}catch (IOException er) {
					er.printStackTrace();
				}	
			}
	}
	}
	
	public class Herramientas implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Herramienta.setVisible(true);
			
			Herramienta.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				if (e.getSource()==Herramienta) {
					String seleccionado=(String)Herramienta.getSelectedItem();
					if(seleccionado=="Ejecutar") {
						/*String dato = archivo.getText();
                                            try {
                                                ReporteLexico.HtmlLexico(dato);
                                                ReporteLexico.ErroresLexicos(dato);
                                            } catch (IOException ex) {
                                                //Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (Exception ex) {
                                                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                                            }*/
					}else if(seleccionado == "Nuevo") {
						archivo.setText("Nuevo");
					}
					Herramienta.setVisible(false);
				}
			}
				
			});
			
		}
	}
	
	
}