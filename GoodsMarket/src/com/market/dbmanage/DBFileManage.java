package com.market.dbmanage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import com.market.bean.Sku_list;
import com.market.utils.Constants;
import com.market.utils.Log;

import android.content.Context;

public class DBFileManage {
	private String filePath;
	private File file;
	private final String ENDCODE = "###";
	private IFileReadOperation ifo;
	private IFileWriteOperation iwfo;

	public DBFileManage(Context context) {
		filePath = context.getCacheDir().getPath();
	}

	public File OpenFile(String filename) {
		filePath += "/" + filename;
		file = new File(filePath);
		return file;
	}

	/**
	 * Using this Method can control i/o operation with IFileOperation while
	 * doing reading
	 * 
	 * @param filename
	 * @return
	 */
	public void readFileContext(String filename) {
		File file = OpenFile(filename);
		FileReader filereader;
		try {
			filereader = new FileReader(file);
			BufferedReader br = new BufferedReader(filereader);
			String dataLine;
			if (ifo != null)
				ifo.doBeforeReaded(br);
			while (!(dataLine = br.readLine()).equals(ENDCODE)
					&& dataLine != null) {
				if (ifo != null)
					ifo.doInReading(dataLine);
			}
			if (ifo != null)
				ifo.doAfterReading(br);
			br.close();
			filereader.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Recommended Reading File which content few lines
	 * 
	 * @param filename
	 * @return
	 */
	public String readFileAllContext(String filename) {
		File file = OpenFile(filename);
		FileReader filereader;
		StringBuilder sb = new StringBuilder();
		try {
			filereader = new FileReader(file);
			BufferedReader br = new BufferedReader(filereader);
			String dataLine;
			while (!(dataLine = br.readLine()).equals(ENDCODE)
					&& dataLine != null) {
				sb.append(dataLine);
			}
			br.close();
			filereader.close();
		} catch (IOException e) {

		}
		return sb.toString();
	}

	public interface IFileReadOperation {
		/**
		 * do before Reading. Using this method, you can skip over the words
		 * that are useless to you
		 * 
		 * @param br
		 *            is a BufferedReader which adapted to the file it has
		 *            initialized
		 */
		public void doBeforeReaded(BufferedReader br);

		/**
		 * do in Reading. Get a line of the file and do what you want
		 * 
		 * @param line
		 *            is a line of the file text which end with '\n'
		 */
		public void doInReading(String line);

		/**
		 * do after Reading. Do something after Reading
		 * 
		 * @param line
		 *            is a line of the file text which end with '\n'
		 */
		public void doAfterReading(BufferedReader br);
	}

	public void writeFile(String filename) {
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(filewriter);
			if (iwfo != null) {
				iwfo.doBeforeWrited(bw);
				iwfo.doInWritting(bw);
				iwfo.doAfterWritting(bw);
			}
			bw.write(ENDCODE);
			bw.flush();
			bw.close();
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeFile(String filename, String[] stringlist) {
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(filewriter);
			if (stringlist != null) {
				for (int i = 0; i < stringlist.length; i++) {
					bw.write(stringlist[i]);
					bw.write('\n');
				}
			}
			bw.write(ENDCODE);
			bw.flush();
			bw.close();
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public interface IFileWriteOperation {
		/**
		 * do before writing.
		 * 
		 * @param br
		 *            is a BufferedReader which adapted to the file it has
		 *            initialized
		 */
		public void doBeforeWrited(BufferedWriter bw);

		/**
		 * do in writing.
		 * 
		 * @param line
		 *            is a line of the file text which end with '\n'
		 */
		public void doInWritting(BufferedWriter bw);

		/**
		 * do after writing.
		 * 
		 * @param line
		 *            is a line of the file text which end with '\n'
		 */
		public void doAfterWritting(BufferedWriter bw);
	}

	public String readAccessFile(int begin, int length,
			RandomAccessFile randomFile) {
		byte[] text = null;
		try {
			if (randomFile.length() > (begin + length)) {
				text = new byte[length];
				randomFile.seek(begin);
			}
			randomFile.read(text);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(text);
	}

	public String readAccessFileEnd(int length, RandomAccessFile randomFile) {
		int byteread = 0;
		byte[] bytes = null;
		long fileLength;
		try {
			fileLength = randomFile.length();
			// ENDCODE长度+换行+结束
			int tailblength = ENDCODE.length() + 1;
			if (fileLength > tailblength) {
				bytes = new byte[length];
				randomFile.seek(fileLength - tailblength);
				// 检查文本的完整性
				while ((byteread = randomFile.read(bytes)) != -1)
					;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(bytes);
	}

	private Object readFileObjct(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object tem = ois.readObject();
			ois.close();
			fis.close();
			return tem;
		} catch (Exception e) {
			Log.d(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private void writeFileObjct(String filename, Serializable s) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(s);
			oos.close();
			fos.close();
		} catch (Exception e) {
			Log.d(e.getMessage());
			e.printStackTrace();
		}
	}

	public Sku_list getSkulistByDB() {
		return (Sku_list) this.readFileObjct(Constants.FILE_SKULIST_NATIVE);
	}

	public void putSkulistToDB(Sku_list sl) {
		this.writeFileObjct(Constants.FILE_SKULIST_NATIVE, sl);
	}
	
	
}
