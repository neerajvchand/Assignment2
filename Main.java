import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

	public static void main(String[] args) {

		Scanner input = new Scanner(System. in);
		Graph<String> actors = new Graph<String>();

//		String csvFile = "tmdb_5000_credits.csv";
		File csvFile;
		csvFile = new File(args[0]);
		String line = "";
		String cvsSplitBy = ",\\\"\\[\\{|\\}\\]\\\"";
		int lineNo = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null) {
				if (lineNo > 0) {
					List<String> arr = new ArrayList<String>();	
					arr.addAll(Arrays.asList(line.split(cvsSplitBy)));
					
					if (arr.size() >= 2 && arr.get(1).contains("cast_id")) {

						String cast = "[{" + arr.get(1) + "}]";
						cast = cast.replace("\"\"", "\"");
						
						JsonArray jsonCast = new JsonParser().parse(cast).getAsJsonArray();
						final int[] index = {0};
						jsonCast.forEach(obj -> {

							if (index[0] < jsonCast.size()) {
								for (int i = index[0]+1; i < jsonCast.size(); i++) {
									if(i!=index[0] && !actors.hasEdge(jsonCast.get(index[0]).getAsJsonObject().get("name").toString().toLowerCase(), jsonCast.get(i).getAsJsonObject().get("name").toString().toLowerCase()))
										actors.addEdge(jsonCast.get(index[0]).getAsJsonObject().get("name").toString().toLowerCase(), jsonCast.get(i).getAsJsonObject().get("name").toString().toLowerCase(), true);

								}
							}

							index[0]++;

						});

					}
				}
				lineNo++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Enter 1 in any name to exit");

		while (true) {
			System.out.println("Actor 1 name");
			String name1 = "\"" + input.nextLine() + "\"";
			if(name1.equals("\"1\""))
				break;
			name1 = name1.toLowerCase();
			if (!actors.hasVertex(name1))
				return;

			System.out.println("Actor 2 name");
			String name2 = "\"" + input.nextLine() + "\"";
			if(name2.equals("\"1\""))
				break;
			name2 = name2.toLowerCase();
			if (!actors.hasVertex(name2))
				return;

			if (!actors.IDDFS(name1, name2, 6))
				System.out.println("Not able to find path between: " + name1 + " and " + name2);

			System.out.println();

		}

	}

}
