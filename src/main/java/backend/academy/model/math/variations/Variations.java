package backend.academy.model.math.variations;

import backend.academy.model.math.variations.implementations.Cross;
import backend.academy.model.math.variations.implementations.Ex;
import backend.academy.model.math.variations.implementations.Handkerchief;
import backend.academy.model.math.variations.implementations.Horseshoe;
import backend.academy.model.math.variations.implementations.Identity;
import backend.academy.model.math.variations.implementations.Sinusoidal;
import backend.academy.model.math.variations.implementations.Spherical;
import backend.academy.model.math.variations.implementations.Swirl;
import backend.academy.model.math.variations.implementations.Tangent;
import it.unimi.dsi.fastutil.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@SuppressWarnings("checkstyle:FinalClass")
// Задумано, так, что объект вариаций строиться на основе уже известного набора вариаций
public class Variations {

    private static final Variations ALL = new Variations(List
        .of(Pair.of("identity", new Identity()),
            Pair.of("sinusoidal", new Sinusoidal()),
            Pair.of("spherical", new Spherical()),
            Pair.of("swirl", new Swirl()),
            Pair.of("horseshoe", new Horseshoe()),
            Pair.of("handkerchief", new Handkerchief()),
            Pair.of("ex", new Ex()),
            Pair.of("cross", new Cross()),
            Pair.of("tangent", new Tangent())
        ));

    public static Variations getFull() {
        return ALL;
    }

    private final List<Pair<String, SimpleVariation>> variations = new LinkedList<>();

    public static Variations get(Set<String> transformationsToChoose) {
        return new Variations(
            ALL.variations.stream()
                .filter(transformation -> transformationsToChoose.contains(transformation.left()))
                .toList());
    }

    private Variations(List<Pair<String, SimpleVariation>> variations) {
        this.variations.addAll(variations);
        this.size = this.variations.size();
    }

    @Getter
    private final int size;

    public SimpleVariation getRandom() {
        Random random = ThreadLocalRandom.current();
        return variations.get(random.nextInt(0, size)).right();
    }

    public Stream<SimpleVariation> getVariations(int start, int endExclusive) {
        return variations.stream()
            .skip(start)
            .limit(endExclusive - start)
            .map(Pair::right);

    }

    public Stream<SimpleVariation> getVariations() {
        return variations
            .stream()
            .map(Pair::right);
    }

    public SimpleVariation getVariation(String name) {
        for (var pair : variations) {
            if (pair.left().equalsIgnoreCase(name)) {
                return pair.right();
            }
        }
        return new Identity();
    }

    private List<SimpleVariation> collectToArrayList(Stream<SimpleVariation> stream, int size) {
        return stream.collect(Collectors.toCollection(() -> new ArrayList<>(size)));
    }
}
