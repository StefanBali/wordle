package wordle.pick;

import com.mongodb.client.MongoClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.Mongo5TestResource;

import javax.inject.Inject;

import static io.quarkus.mongodb.panache.common.runtime.MongoOperations.ID;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(Mongo5TestResource.class)
public class RandomWordIT {
    @Inject
    MongoClient mongoClient;

    @BeforeEach
    void removeAllDocuments() {
        Document allDocuments = new Document();
        mongoClient.getDatabase("wordle").getCollection("words").deleteMany(allDocuments);
    }

    @Test
    void pickRandomWordIdReturns404IfNoWordsExist() {
        when().get("/api/wordle/pick")
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void pickRandomWordIdReturnsId() {
        ObjectId objectId = new ObjectId();
        Document wordDocument = new Document(ID, objectId);
        wordDocument.append("word", "möhre");
        mongoClient.getDatabase("wordle").getCollection("words").insertOne(wordDocument);

        when().get("/api/wordle/pick")
                .then().statusCode(HttpStatus.SC_OK)
                .body(equalTo(objectId.toHexString()));
    }
}
