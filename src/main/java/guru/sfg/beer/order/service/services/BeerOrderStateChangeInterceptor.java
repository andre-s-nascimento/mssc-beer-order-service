package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.domain.BeerOrder;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

  private final BeerOrderRepository beerOrderRepository;

  @Override
  public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state,
      Message<BeerOrderEventEnum> message,
      Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition,
      StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine,
      StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> rootStateMachine) {
    Optional.ofNullable(message).flatMap(msg->Optional.ofNullable(
        (UUID) msg.getHeaders().getOrDefault(BeerOrderManagerImpl.ORDER_ID_HEADER, " ")
    )).ifPresent(beerOrderId -> {
      log.debug("Saving state for order id: "+ beerOrderId + "Status: "+ state.getId());
      BeerOrder beerOrder = beerOrderRepository.getReferenceById(beerOrderId);
      beerOrder.setOrderStatus(state.getId());
      beerOrderRepository.saveAndFlush(beerOrder);
    });
  }
}
