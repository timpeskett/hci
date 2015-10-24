package aes;

import java.util.LinkedList;
import java.util.HashMap;
import java.io.File;

class ConvertOptions
{
	private LinkedList<File> inFiles;
	private File outFile;

	/* Contains optional variables that need not
	 * be set.
	 */
	private HashMap<String, Object> optionals;

	public ConvertOptions(File outFile, File ... inFiles)
	{
		this.outFile = outFile;

		this.inFiles = new LinkedList<File>();
		for(File file : inFiles)
		{
			this.inFiles.add(file);
		}

		optionals = new HashMap<String, Object>();
	}


	public File getOutFile()
	{
		return outFile;
	}

	public File getInFile(int index)
	{
		return inFiles.get(index);
	}

	public boolean hasInFramerate()
	{
		return optionals.containsKey("INFRAMERATE");
	}

	public Integer getInFramerate()
	{
		return (Integer)optionals.get("INFRAMERATE");
	}

	public void setInFramerate(Integer inFramerate)
	{
		optionals.put("INFRAMERATE", inFramerate);
	}


	public boolean hasOutFramerate()
	{
		return optionals.containsKey("OUTFRAMERATE");
	}

	public Integer getOutFramerate()
	{
		return (Integer)optionals.get("OUTFRAMERATE");
	}

	public void setOutFramerate(Integer outFramerate)
	{
		optionals.put("OUTFRAMERATE", outFramerate);
	}


	public boolean hasFileType()
	{
		return optionals.containsKey("FILETYPE");
	}

	public String getFileType()
	{
		return (String)optionals.get("FILETYPE");
	}

	public void setFileType(String fileType)
	{
		optionals.put("FILETYPE", fileType);
	}
}
