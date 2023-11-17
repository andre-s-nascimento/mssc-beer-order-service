package guru.sfg.beer.order.service.sm;

import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import java.util.EnumSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

@Configuration
@EnableStateMachineFactory
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

  @Override
  public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states)
      throws Exception {
    states.withStates()
        .initial(BeerOrderStatusEnum.NEW) // começa aqui
        .states(EnumSet.allOf(BeerOrderStatusEnum.class)) // todos os validos
        .end(BeerOrderStatusEnum.PICKED_UP) // estados terminais
        .end(BeerOrderStatusEnum.DELIVERED) // estados terminais
        .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION) // estados terminais
        .end(BeerOrderStatusEnum.VALIDATION_EXCEPTION) // estados terminais
        .end(BeerOrderStatusEnum.ALLOCATION_EXCEPTION);  // estados terminais
  }
}
