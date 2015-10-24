package aes.boundary;

public interface ConvertListener
{
	public void onFinish(int exitValue);
	/* Progress is a number between 0 and 1 */
	public void onProgress(double progress);
	public void onCancel();
}
