package at.noah.jpa.jpaRental.persistance;

import java.util.List;
import java.util.Optional;

public interface Repository<T, P> {

    public List<T> findAll();

    public Optional<T> findById(P id);

    public T save(T entity);


}
