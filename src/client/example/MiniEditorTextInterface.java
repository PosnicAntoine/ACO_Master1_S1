package client.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniEditorTextInterface
{
	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	// Direct reference to MiniEditor (for V1 only)
	static EditorEngine editorEngine = new EditorEngineStub() ;

	public static void main(String[] args)
	{
		String inputLine;
		char commandLetter;

		System.out.println("Welcome to MiniEditor V1.0") ;
		System.out.println("-----------------------------------------------------------") ;

		System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;
		try
		{
			inputLine = keyboard.readLine();
		} catch (IOException e)
		{
			System.out.println("Unable to read standard input");
			inputLine = "W";
		}
		if (inputLine.isEmpty())
		{
			commandLetter = '0';
		}
		else
		{
			commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
		}
		while (commandLetter != 'Q') /* Quit */
		{
			switch (commandLetter)
			{
				case '0': break ;
				case 'I': /* Insert */
					editorEngine.editorInsert(inputLine.substring(2));
					break;
				case 'S': /* Select */
					String numberString="";
					try
					{
						String[] arguments = inputLine.substring(2).split("\\s+");
						numberString = arguments[0];
						int start  = Integer.parseInt(numberString);
						numberString = arguments[1];
						int stop  = Integer.parseInt(numberString);
						editorEngine.editorSelect(start, stop);
					}
					catch (Exception e)
					{
						System.out.println("Invalid number: " + numberString);
					}
					break;
				case 'C': /* Copy */
					editorEngine.editorCopy();
					break;
				case 'X': /* cut */
					editorEngine.editorCut();
					break;
				case 'V': /* paste */
					editorEngine.editorPaste();
					break;
				case 'D': /* Delete, i.e. insert empty string */
					editorEngine.editorInsert("");
					break;
				case 'R': /* start Recording */
					// Insert your code here (V2)
					break;
				case 'E': /* End recording */
					// Insert your code here (V2)
					break;
				case 'P': /* Play recording */
					// Insert your code here (V2)
					break;
				case 'Z': /* undo */
					// Insert your code here (V3)
					break;
				case 'Y': /* redo */
					// Insert your code here (V3)
					break;
				default: System.out.println("Unrecognized command, please try again:") ;
					break;
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + editorEngine.getBuffer() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + editorEngine.getSelection() + "]");
			System.out.println("-----------------------------------------------------");
			System.out.println("[" + editorEngine.getClipboard() + "]");
			System.out.println("-----------------------------------------------------");

			System.out.println("Enter command (I/S/C/X/V/D/R/E/P/Z/Y/Q) > ") ;
			try
			{
				inputLine = keyboard.readLine();
			} catch (IOException e)
			{
				System.out.println("Unable to read standard input");
				inputLine = "W";
			}
			if (inputLine.isEmpty())
			{
				commandLetter = '0';
			}
			else
			{
				commandLetter = Character.toUpperCase(inputLine.charAt(0)) ;
			}
		}
		System.out.println ("Goodbye") ;
	}
}
