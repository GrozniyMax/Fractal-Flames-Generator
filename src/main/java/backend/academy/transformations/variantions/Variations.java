package backend.academy.transformations.variantions;

import backend.academy.transformations.variantions.simpleTransformations.Cosine;
import backend.academy.transformations.variantions.simpleTransformations.Cross;
import backend.academy.transformations.variantions.simpleTransformations.Ex;
import backend.academy.transformations.variantions.simpleTransformations.Handkerchief;
import backend.academy.transformations.variantions.simpleTransformations.Horseshoe;
import backend.academy.transformations.variantions.simpleTransformations.Identity;
import backend.academy.transformations.variantions.simpleTransformations.Sinusoidal;
import backend.academy.transformations.variantions.simpleTransformations.Spherical;
import backend.academy.transformations.variantions.simpleTransformations.Swirl;
import backend.academy.transformations.variantions.simpleTransformations.Tangent;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Variations {

    private final List<Pair<String, SimpleVariation>> variations = List
        .of(Pair.of("identity", new Identity()),
            Pair.of("sinusoidal", new Sinusoidal()),
            Pair.of("spherical", new Spherical()),
            Pair.of("swirl", new Swirl()),
            Pair.of("horseshoe", new Horseshoe()),
            Pair.of("handkerchief", new Handkerchief()),
            Pair.of("ex", new Ex())
//            Pair.of("cross", new Cross())
//            Pair.of("tangent", new Tangent())
        );

    @Getter
    private final int size = variations.size();

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
        for (var pair: variations) {
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
