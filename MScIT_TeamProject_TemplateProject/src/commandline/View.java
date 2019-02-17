package commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class View {

	static BufferedWriter bw = null;

	Model model;

	public View(Model model) {
		this.model = model;
	}

	public static void write(String s) throws IOException {
		bw.write(s);
		bw.newLine();
	}

	public static void preparation() throws IOException {
		bw = new BufferedWriter(new FileWriter(new File("./toptrumps.log")));
	}
	
	public static void close() throws IOException {
		if(bw != null) {
			bw.close();
		}
	}
}
