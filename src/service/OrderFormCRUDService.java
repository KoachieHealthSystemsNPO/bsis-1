package service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backingform.OrderFormBackingForm;
import factory.OrderFormFactory;
import model.order.OrderForm;
import model.order.OrderStatus;
import repository.OrderFormRepository;

@Service
@Transactional
public class OrderFormCRUDService {

  @Autowired
  private OrderFormFactory orderFormFactory;

  @Autowired
  private OrderFormRepository orderFormRepository;

  public OrderForm createOrderForm(OrderFormBackingForm backingForm) {
    OrderForm entity = orderFormFactory.createEntity(backingForm);
    entity.setStatus(OrderStatus.CREATED);
    orderFormRepository.save(entity);
    return entity;
  }
}
