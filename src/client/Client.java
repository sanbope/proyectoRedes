package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client
{
	public final String SERVER;
	public final int PORT;

	private Socket clientSideSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	private static final Scanner SCANNER = new Scanner(System.in);
	
	public Client(String ip, int port)
	{
		this.SERVER = ip;
		this.PORT = port;
	}

	public void start()
	{
		System.out.println("Conecting to port " + PORT);

		while (true)
		{
			try
			{
				clientSideSocket = new Socket(SERVER, PORT);
				
				crearFlujos();
				
				ArrayList<String> names = new ArrayList<String>();
				
				boolean writing = true;
				do
				{
					System.out.print("name: ");
					names.add(SCANNER.nextLine());

					System.out.print("Do u want to add more? y/n: ");
					writing = SCANNER.nextLine().toLowerCase().charAt(0) == 'y';
				}while(writing);
				
				send(names);
				
				System.out.println(receive());

				String answer = "ok";

				send(answer);
			} catch (IOException | ClassNotFoundException e)
			{
				System.err.println(e);
			}
		}
	}

	public Object receive() throws ClassNotFoundException, IOException
	{
		return reader.readObject();
	}

	public void crearFlujos() throws IOException
	{
		writer = new ObjectOutputStream(clientSideSocket.getOutputStream());
		reader = new ObjectInputStream(clientSideSocket.getInputStream());
	}

	public void send(Object answer) throws IOException
	{
		writer.writeObject(answer);
		writer.flush();
	}
}