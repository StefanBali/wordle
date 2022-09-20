package de.lise.letscode.wordle.adapter.database;

import de.lise.letscode.wordle.adapter.database.entity.WordEntity;
import de.lise.letscode.wordle.usecase.guess.port.WordByIdPort;
import de.lise.letscode.wordle.usecase.pick.port.RandomWordPort;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.sample;

@ApplicationScoped
public class WordRepository implements PanacheMongoRepository<WordEntity>, RandomWordPort, WordByIdPort {
    private static final List<Bson> ONE_RANDOM_DOCUMENT = List.of(sample(1));

    @Override
    public Optional<String> pickId() {
        Optional<WordEntity> possibleWord = Optional.ofNullable(mongoCollection().aggregate(ONE_RANDOM_DOCUMENT).first());
        return possibleWord.map(word -> word.getId().toHexString());
    }

    @Override
    public Optional<String> findWordById(String id) {
        throw new UnsupportedOperationException();
    }
}
