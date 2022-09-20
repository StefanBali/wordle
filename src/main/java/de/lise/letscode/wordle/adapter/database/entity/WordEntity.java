package de.lise.letscode.wordle.adapter.database.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MongoEntity(collection = "words")
@RegisterForReflection
public final class WordEntity {
    private final ObjectId id;
    private final String word;

    @BsonCreator
    public WordEntity(@BsonProperty("id") ObjectId id, @BsonProperty("word") String word) {
        this.id = id;
        this.word = word;
    }

    public ObjectId getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
}
