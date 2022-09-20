package de.lise.letscode.wordle.adapter.database;

import de.lise.letscode.wordle.adapter.database.entity.WordEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.sample;

public class WordRepository implements PanacheMongoRepository<WordEntity> {
    private static final List<Bson> ONE_RANDOM_DOCUMENT = List.of(sample(1));

    public Optional<String> pickId() {
        throw new UnsupportedOperationException();
    }
}
