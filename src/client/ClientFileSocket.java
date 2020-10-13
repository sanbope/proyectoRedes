package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFileSocket
{
	private final String ip;
	private final int port;

	public ClientFileSocket(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	public boolean enviar(File file, String dir) throws UnknownHostException, IOException
	{
		File[] files;
		if (file.isDirectory())
		{
			dir = dir + file.getName() + "/";
			files = file.listFiles();
		} else
		{
			dir = "";
			files = new File[1];
			files[0] = file;
		}

		Socket socket;

		BufferedOutputStream bos = null;
		DataOutputStream dos = null;

		boolean ya = false; //controla si ya se enviaron los datos del archivo
		int directorios = 0;
		
		for (File f : files)
		{
			if (f.isDirectory())
			{
				enviar(f, dir);
				directorios++;
			} else
			{
				if (!ya)
				{
					ya = true;
					socket = new Socket(ip, port);
					bos = new BufferedOutputStream(socket.getOutputStream());
					dos = new DataOutputStream(bos);
					
					dos.writeInt(files.length-directorios);
				}
				long length = f.length();
				dos.writeLong(length);

				String name = dir + f.getName();
				dos.writeUTF(name);

				FileInputStream fis = new FileInputStream(f);
				BufferedInputStream bis = new BufferedInputStream(fis);

				int theByte = 0;
				System.out.println("enviando: "+f.getAbsolutePath());
				while ((theByte = bis.read()) != -1)
				{
					//System.out.println(theByte);
					bos.write(theByte);
				}

				bis.close();
			}
			System.out.println(f.getAbsolutePath() + " enviado");
		}

		if (dos != null)
		{
			dos.close();
		}
		return true;
	}
}