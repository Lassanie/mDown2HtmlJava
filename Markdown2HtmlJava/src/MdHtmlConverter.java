

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MdHtmlConverter {

    public static String conversionToHtml(String markdown) {
        StringBuilder html = new StringBuilder();

        String[] lines = markdown.split("\n");
        boolean isHeading = false;
        int headingLvl = 0;

        for (String line : lines) {
            // Converts to heading
            if (line.startsWith("#")) {
                headingLvl = getHeadingLvl(line);
                String headingText = line.substring(headingLvl + 1).trim();
                html.append("<h").append(headingLvl).append(">").append(headingText).append("</h").append(headingLvl).append(">");
                isHeading = true; // Sets that this line is a heading
            } else {
                if (isHeading) {
                    html.append("</h").append(headingLvl).append(">");
                    isHeading = false; // Just to make sure the next line isn't considered a heading
                }

                if (line.matches("\\s*\\*\\s+.*")) { // Regex for whitespace at beginning, then "*", then whitespace, then anything else
                    html.append("<li>").append(line.substring(line.indexOf('*') + 1).trim()).append("</li>");
                } else {
                    html.append("<p>").append(line.trim()).append("</p>");
                }
            }
        }

        if (isHeading) {
            html.append("</h").append(headingLvl).append(">"); // Close the last heading if it's not followed by another line
        }

        return html.toString();
    }
    


    private static int getHeadingLvl(String line) {
        int level = 0;
        while (line.charAt(level) == '#') {
            level++;
        }
        return level;
    }

	public static void main(String[] args) {
		
	BufferedReader reader =
			new BufferedReader(new InputStreamReader(System.in));
	
	try {
		
		System.out.println("Enter the text in markdown...");
		String markdown = reader.readLine();
		
		String html = conversionToHtml(markdown);
		System.out.println("Your converted HTML: ");
		System.out.println(html);
	} catch (IOException e) {
		System.err.println("An error occurred while reading the input: "
		+ e.getMessage());				
		}
				
			
	}
}
