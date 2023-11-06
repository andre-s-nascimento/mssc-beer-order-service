package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.web.model.BeerOrderDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
@Component
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
