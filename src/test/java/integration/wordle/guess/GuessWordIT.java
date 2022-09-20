package wordle.guess;

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
public class GuessWordIT {
    @Inject
    MongoClient mongoClient;

    @BeforeEach
    void removeAllDocuments() {
        Document allDocuments = new Document();
        mongoClient.getDatabase("wordle").getCollection("words").deleteMany(allDocuments);
    }

    @Test
    void guessWordReturns404IfWordWithIdDoesNotExist() {
        when().get("/api/wordle/{id}/guess?word=möhre", 0)
                .then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void guessWordReturnsScore() {
        ObjectId objectId = new ObjectId();
        Document wordDocument = new Document(ID, objectId);
        wordDocument.append("word", "möhre");
        mongoClient.getDatabase("wordle").getCollection("words").insertOne(wordDocument);

        when().get("/api/wordle/{id}/guess?word=möhre", objectId.toHexString())
                .then().statusCode(HttpStatus.SC_OK)
                .body("[0].position", equalTo(1),
                        "[0].result", equalTo("CORRECT_SPOT"),
                        "[1].position", equalTo(2),
                        "[1].result", equalTo("CORRECT_SPOT"),
                        "[2].position", equalTo(3),
                        "[2].result", equalTo("CORRECT_SPOT"),
                        "[3].position", equalTo(4),
                        "[3].result", equalTo("CORRECT_SPOT"),
                        "[4].position", equalTo(5),
                        "[4].result", equalTo("CORRECT_SPOT"));
    }
}
