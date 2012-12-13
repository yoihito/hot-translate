package main;

import java.awt.*;
import java.awt.event.*;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.melloware.jintellitype.*;
import javax.swing.*;



public class Main extends JDialog  implements HotkeyListener{

	private JTextArea textToTranslate;
	private JButton buttonGetTranslate;
	private JTextArea textTranslation;
	
	private String requestTranslation(String text) {
		Translate.setClientId("c62af6ab-f3ae-4100-9f5a-c69909421a98");
		Translate.setClientSecret("djIZBP3ohshuUHQy+sYcC3i0bPhmNr6/Payt/d6r800=");
		try {
			return Translate.execute(text, Language.ENGLISH, Language.RUSSIAN);
		} catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 new Main();     
	}
	
	public Main() {
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		textToTranslate = new JTextArea();
		buttonGetTranslate = new JButton("Translate");
		textTranslation = new JTextArea();
		
		
		buttonGetTranslate.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				translate();
				
			}
		});
		buttonGetTranslate.getInputMap(buttonGetTranslate.WHEN_IN_FOCUSED_WINDOW).
		put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.CTRL_MASK), "translate");
		buttonGetTranslate.getActionMap().put("translate", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				translate();
			}
		});
		
		textTranslation.setEditable(false);
		
		
		add(new JScrollPane(textToTranslate));
		add(buttonGetTranslate);
		add(new JScrollPane(textTranslation));
		
		JIntellitype.getInstance();
		if (JIntellitype.checkInstanceAlreadyRunning("hot-translate")) {
			System.out.println("ERROR");
			System.exit(0);
		}
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int)'T');
		JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT, (int)'H');
		//JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_CONTROL, (int)'T');
		//assign this class to be a HotKeyListener
		JIntellitype.getInstance().addHotKeyListener(this);
		
		this.setSize(400,300);
		setVisible(true);
	}
	
	public void onHotKey(int ind) {
		if (ind == 1) {
			setVisible(true);
			toFront();
		} else 
		if (ind == 2) {
			setVisible(false);
		}
	}


	public void translate() {
		textTranslation.setText(requestTranslation(textToTranslate.getText()));
		textToTranslate.setText("");
	}
}
