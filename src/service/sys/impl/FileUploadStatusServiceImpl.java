package service.sys.impl;

import dao.BaseDaoI;
import model.sys.TfileUploadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sys.FileUploadStatusServiceI;
import utils.IDGenerator;
import utils.StringUtils;

@Service
public class FileUploadStatusServiceImpl implements FileUploadStatusServiceI {
	
	@Autowired
	private BaseDaoI<TfileUploadStatus> statusDao;

	@Override
	public String add(String fileName, int chunk, int chunks) {
		TfileUploadStatus t = new TfileUploadStatus();
		t.setObjId(IDGenerator.UUIDgenerate());
		t.setFileName(fileName);
		t.setChunks(chunks);
		t.setChunk(chunk);
		this.statusDao.save(t);
		return t.getObjId();
	}

	@Override
	public TfileUploadStatus get(String fileName) {
		String hql = "from TfileUploadStatus t where t.fileName='" + fileName + "'";
		
		return statusDao.get(hql);
	}

	@Override
	public void updateChunk(String fileName, int chunk) {
		TfileUploadStatus t = this.get(fileName);
		if(t != null){
			t.setChunk(chunk);
			statusDao.update(t);
		}

	}

	
	
	@Override
	public int getChunk(String fileName){
		int chunk = 0;
		TfileUploadStatus t = this.get(fileName);
		if(t != null){
			chunk = t.getChunk();
		}
		return chunk;
	}
	
	@Override
	public void del(String fileName){
		TfileUploadStatus t = this.get(fileName);
		if(t != null){
			statusDao.delete(t);
		}
	}
	
	@Override
	public void delById(String id){
		if(StringUtils.isNotEmpty(id)){
			TfileUploadStatus t = this.statusDao.get(TfileUploadStatus.class, id);
			if(t != null){
				statusDao.delete(t);
			}
			
		}
	}

}
