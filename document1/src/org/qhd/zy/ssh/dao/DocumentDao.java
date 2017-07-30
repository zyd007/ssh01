package org.qhd.zy.ssh.dao;


import org.qhd.zy.ssh.model.Document;
import org.springframework.stereotype.Repository;
@Repository("documentDao")
public class DocumentDao extends BaseDao<Document> implements
		IDocumentDao {
	
}
