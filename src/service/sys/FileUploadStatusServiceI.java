package service.sys;

import model.sys.TfileUploadStatus;

public interface FileUploadStatusServiceI {
	public String add(String fileName, int chunk, int chunks);
	
	public TfileUploadStatus get(String fileName);
	
	public void updateChunk(String fileName, int chunk);
	
	
	public int getChunk(String fileName);
	
	public void del(String fileName);
	
	public void delById(String id);
}
