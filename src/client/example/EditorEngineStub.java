package client.example;


public class EditorEngineStub implements EditorEngine
{

	@Override
	public String getBuffer()
	{
		return "buffer content here" ;
	}

	@Override
	public String getSelection()
	{
		return "selected text here" ;
	}

	@Override
	public String getClipboard()
	{
		return "clipboard content here";
	}

	@Override
	public void editorInsert(String substring)
	{
		System.out.println("DEBUG: inserting text [" + substring + "]");
	}

	@Override
	public void editorSelect(int start, int stop)
	{
		System.out.println("DEBUG: selecting interval [" + start + "," + stop + "]");
	}

	@Override
	public void editorCopy()
	{
		System.out.println("DEBUG: performing Copy") ;
	}

	@Override
	public void editorCut()
	{
		System.out.println("DEBUG: performing Cut") ;
	}

	@Override
	public void editorPaste()
	{
		System.out.println("DEBUG: performing Paste") ;
	}
}
