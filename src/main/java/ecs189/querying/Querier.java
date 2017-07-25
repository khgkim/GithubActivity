package ecs189.querying;

import java.io.IOException;

public interface Querier {
    String query(String ID) throws IOException;
}
