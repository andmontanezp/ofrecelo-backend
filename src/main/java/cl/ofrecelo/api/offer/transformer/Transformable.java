package cl.ofrecelo.api.offer.transformer;

import java.util.List;

public interface Transformable<T, R> {

    R fromRequestToDomainObject(T t) throws Exception;

    T fromDomainObjectToResponse(R r) throws Exception;

    List<T> fromDomainListToResponseList(List<R> r) throws Exception;

}
