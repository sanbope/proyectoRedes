package client;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Run
{

	public static void main(String[] args)
	{
		changeLookAndFeel();

		//		Client client = new Client("0.0.0.0", 3000);
		//		client.start();

		ClientFileSocket cfs = new ClientFileSocket("0.0.0.0", 3000);

		boolean ok;
		
		do
		{
			JFileChooser fc = new JFileChooser();

			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
			fc.showOpenDialog(null);

			File file = fc.getSelectedFile();

			if (file != null)
			{
				try
				{
					ok = cfs.enviar(file, "");
					System.out.println(ok ? "enviado" : "no enviado");
				} catch (IOException e)
				{
					System.err.println("Cliente: " + e.getMessage());
				}
			} else
			{
				System.err.println("No seleccionó un archivo valido");
			}
		} while (true);
	}

	private static void changeLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			System.err.println(e.getMessage());
		}
	}

}