package server_rmi;

import java.io.IOException;

public interface CommonTask<T> {
    T execute() throws IOException;
}
