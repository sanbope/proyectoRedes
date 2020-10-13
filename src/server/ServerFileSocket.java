package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileSocket
{
	private String dir;

	private ServerSocket serverSocket;

	public ServerFileSocket(int port, String dir) throws IOException
	{
		this.dir = dir;

		serverSocket = new ServerSocket(port);

		File file = new File(dir);
		if (!file.exists())
		{
			System.out.println("carpeta en (" + dir + ") creada");
			file.mkdirs();
		}
	}

	public boolean recibir() throws IOException
	{
		Socket socket = serverSocket.accept();

		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		DataInputStream dis = new DataInputStream(bis);

		int filesCount = dis.readInt();
		File[] files = new File[filesCount];

		for (int i = 0; i < filesCount; i++)
		{
			long fileLength = dis.readLong();
			String fileName = dis.readUTF();

			files[i] = new File(dir + "/" + fileName);
			crearDirectorio(files[i]);

			FileOutputStream fos = new FileOutputStream(files[i]);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			for (int j = 0; j < fileLength; j++)
			{
				bos.write(bis.read());
			}

			System.out.println(files[i].getAbsolutePath()+" recibido");
			bos.close();
		}

		dis.close();
		return true;
	}
	
	private void crearDirectorio(File file)
	{
		String path = file.getParent();
		
		File f = new File(path);
		
		if(!f.exists())
		{
			//System.out.println(f.getAbsolutePath()+" created");
			f.mkdirs();
		}
	}
}