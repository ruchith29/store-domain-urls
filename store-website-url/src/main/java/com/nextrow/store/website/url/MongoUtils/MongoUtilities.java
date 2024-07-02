package com.nextrow.store.website.url.MongoUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class MongoUtilities {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoDatabase getDatabase(){
        return mongoTemplate.getDb();
    }

    public MongoCollection<Document> getCollectionName(String collectionName){
        return getDatabase().getCollection(collectionName);
    }

    public String insertData(Map<String,String> data, String collectionName){
        Document domainData=new Document(data);
        try
        {
            mongoTemplate.insert(domainData,collectionName);
        }
        catch (Exception exception){
            return null;
        }

        return domainData.get("_id").toString();
    }

    public List<Document> getAllData(Query query,String collectionName){
        return mongoTemplate.find(query, Document.class, collectionName);
    }

    public Document getById(Query query,String collectionName) {
        return mongoTemplate.findOne(query, Document.class, collectionName);
    }

    public Boolean updateData(String id,Document query, Document updateDocument,String collectionName){
        Query query1=new Query(Criteria.where("_id").is(id));
        if(mongoTemplate.findOne(query1, Document.class, collectionName)!=null){
            mongoTemplate.getCollection(collectionName).updateOne(query,updateDocument);
            return true;
        }
        return false;
    }

    public Boolean deleteData(Document query,String collectionName){
        DeleteResult result=mongoTemplate.getCollection(collectionName).deleteOne(query);
        return result.getDeletedCount() != 0;
    }

    public Document getByData(Query query,String collectionName) {
        return mongoTemplate.findOne(query, Document.class, collectionName);
    }
}