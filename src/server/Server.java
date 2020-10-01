package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
	public static final int PORT = 2000;

	private ServerSocket listener;
	private Socket serverSideSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	public Server()
	{

	}

	public void start()
	{
		try
		{
			listener = new ServerSocket(PORT);
			System.out.println("Server created in port " + PORT);

			while (true)
			{
				try
				{

					serverSideSocket = listener.accept();

					crearFlujos();

					ArrayList<String> names = (ArrayList<String>) receive();

					System.out.println(names);

					String answer = "ok";

					send(answer);
				} catch (IOException | ClassNotFoundException e)
				{
					System.err.println(e);
				}
			}
		} catch (IOException e1)
		{
			System.err.println(e1);
		}
	}

	public Object receive() throws ClassNotFoundException, IOException
	{
		return reader.readObject();
	}

	public void crearFlujos() throws IOException
	{
		writer = new ObjectOutputStream(serverSideSocket.getOutputStream());
		reader = new ObjectInputStream(serverSideSocket.getInputStream());
	}

	public void send(Object answer) throws IOException
	{
		writer.writeObject(answer);
		writer.flush();
	}
}