package server;

import java.io.IOException;

public class Run
{

	public static void main(String[] args)
	{
		//		Server server = new Server(3000);
		//		server.start();

		try
		{
			ServerFileSocket sfs = new ServerFileSocket(3000, "files");
			System.out.println("server started");
			do
			{
				try
				{
					sfs.recibir();
				} catch (IOException e)
				{
					System.err.println("Servidor: " + e.getMessage());
				}
			} while (true);
		} catch (

		IOException e)
		{
			System.err.println("Servidor: " + e.getMessage());
		}
	}

}