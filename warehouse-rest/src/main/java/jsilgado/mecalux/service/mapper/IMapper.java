package jsilgado.mecalux.service.mapper;

import java.util.List;

public interface IMapper<I, O> {

	O map (I in);

	List<O> map (List<I> in);

}
