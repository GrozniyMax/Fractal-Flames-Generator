package backend.academy.model.math.variations;


import backend.academy.model.math.variations.implementations.*;
import backend.academy.model.math.variations.SimpleVariation;
import it.unimi.dsi.fastutil.Pair;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

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
            Pair.of("tangent", new Tangent()),
            Pair.of("disc", new Disc()),
            Pair.of("spiral", new Spiral()),
            Pair.of("hyperbolic", new Hyperbolic()),
            Pair.of("diamond", new Diamond()),
            Pair.of("julia", new Julia()),
            Pair.of("polar", new Polar()),
            Pair.of("heart", new Heart()),
            Pair.of("exponential", new Exponential())
        ));

    public static Variations getFull() {
        return ALL;
    }

    public static Variations get(Set<String> transformationsToChoose) {
        return new Variations(
            ALL.variations.stream()
                .filter(transformation -> transformationsToChoose.contains(transformation.left()))
                .toList());
    }

    private final List<Pair<String, SimpleVariation>> variations = new LinkedList<>();

    private Variations(List<Pair<String, SimpleVariation>> variations) {
        this.variations.addAll(variations);
        this.size = this.variations.size();
    }

    @Getter
    private final int size;

    public SimpleVariation getRandom() {
        SecureRandom random = new SecureRandom();
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
