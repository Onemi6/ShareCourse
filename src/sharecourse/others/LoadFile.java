package sharecourse.others;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.os.Environment;

public class LoadFile {

	@SuppressLint("SdCardPath")
	public static void upload(String path) {
		HttpURLConnection connection = null;
		DataOutputStream dos = null;
		FileInputStream fin = null;

		String boundary = "---------------------------265001916915724";
		// ������ԵĻ�������url��Ҫ�ĳɵ���ip
		String urlServer = "http://115.159.214.57:8080/FileOperation/UploadServlet";
		String lineEnd = "\r\n";
		// String path = path;
		int bytesAvailable, bufferSize, bytesRead;
		int maxBufferSize = 2 * 1024 * 1024;
		byte[] buffer = null;

		try {
			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();

			// ������url���ж�д����
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(true);

			// ����post����
			connection.setRequestMethod("POST");

			// ��������ͷ����
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type", "text/plain");

			// α������ͷ
			connection.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);

			// ��ʼα��POST Data���������
			dos = new DataOutputStream(connection.getOutputStream());
			fin = new FileInputStream(path);

			// --------------------��ʼα���ϴ�images.jpg����Ϣ-----------------------------------
			String fileMeta = "--"
					+ boundary
					+ lineEnd
					+ "Content-Disposition: form-data; name=\"uploadedPicture\"; filename=\""
					+ path + "\"" + lineEnd + "Content-Type: image/jpeg"
					+ lineEnd + lineEnd;
			// ������д��fileMeta
			dos.write(fileMeta.getBytes());

			// ȡ�ñ���ͼƬ���ֽ�������url����д��ͼƬ�ֽ���
			bytesAvailable = fin.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			bytesRead = fin.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fin.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fin.read(buffer, 0, bufferSize);
			}
			dos.writeBytes(lineEnd + lineEnd);
			// --------------------α��images.jpg��Ϣ����-----------------------------------

			// POST Data����
			dos.writeBytes("--" + boundary + "--");

			// Server�˷��ص���Ϣ
			System.out.println("" + connection.getResponseCode());
			System.out.println("" + connection.getResponseMessage());

			if (dos != null) {
				dos.flush();
				dos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void download(String filepath) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		URL url = null;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(
					"http://115.159.214.57:8080/FileOperation/DownloadServlet?filename="
							+ URLEncoder.encode(filepath, "UTF-8"));
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(20000);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				//
			}
			in = new BufferedInputStream(urlConnection.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(new File(
					Environment.getExternalStorageDirectory() + "/1.mp4")));

			byte[] buffer = new byte[1024 * 20];
			int read = 0;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
