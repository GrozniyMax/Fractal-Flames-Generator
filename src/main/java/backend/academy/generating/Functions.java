package backend.academy.generating;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Functions {

    private final List<Function> functions = new ArrayList<>();

    public Functions add(Function f){
        functions.add(f);
        return this;
    }

    public Function getRandom(){
        SecureRandom r = new SecureRandom();
        return functions.get(r.nextInt(functions.size()));
    }

}
