package guru.sfg.beer.order.service.sm;

import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class BeerOrderStateMachineConfig
    extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

  private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validateOrderAction;
  private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocateOrderAction;

  @Override
  public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states)
      throws Exception {
    states
        .withStates()
        .initial(BeerOrderStatusEnum.NEW) // começa aqui
        .states(EnumSet.allOf(BeerOrderStatusEnum.class)) // todos os validos
        .end(BeerOrderStatusEnum.PICKED_UP) // estados terminais
        .end(BeerOrderStatusEnum.DELIVERED) // estados terminais
        .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION) // estados terminais
        .end(BeerOrderStatusEnum.VALIDATION_EXCEPTION) // estados terminais
        .end(BeerOrderStatusEnum.ALLOCATION_EXCEPTION); // estados terminais
  }

  @Override
  public void configure(
      StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(BeerOrderStatusEnum.NEW)
        .target(BeerOrderStatusEnum.VALIDATION_PENDING)
        .event(BeerOrderEventEnum.VALIDATE_ORDER)
        .action(validateOrderAction)
        .and()
        .withExternal()
        .source(BeerOrderStatusEnum.NEW)
        .target(BeerOrderStatusEnum.VALIDATED)
        .event(BeerOrderEventEnum.VALIDATION_PASSED)
        .and()
        .withExternal()
        .source(BeerOrderStatusEnum.NEW)
        .target(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
        .event(BeerOrderEventEnum.VALIDATION_FAILED)
            .and().withExternal()
            .source(BeerOrderStatusEnum.VALIDATED)
            .target(BeerOrderStatusEnum.ALLOCATION_PENDING)
            .event(BeerOrderEventEnum.ALLOCATE_ORDER)
            .action(allocateOrderAction)
        .and().withExternal()
        .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATED)
        .event(BeerOrderEventEnum.ALLOCATION_SUCCESS)
        .and().withExternal()
        .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATION_EXCEPTION)
        .event(BeerOrderEventEnum.ALLOCATION_FAILED)
        .and().withExternal()
        .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.PENDING_INVENTORY)
        .event(BeerOrderEventEnum.ALLOCATION_NO_INVENTORY);

  }
}
