package factory;



import model.location.Location;
import model.order.OrderForm;
import model.order.OrderFormItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.LocationRepository;
import viewmodel.LocationViewModel;
import viewmodel.OrderFormViewModel;
import backingform.OrderFormBackingForm;
import backingform.OrderFormItemBackingForm;

/**
 * A factory for creating OrderForm related objects.
 */
@Service
public class OrderFormFactory {

  @Autowired
  private LocationRepository locationRepository;
  
  @Autowired
  private OrderFormItemFactory orderFormItemFactory;

  public OrderForm createEntity(OrderFormBackingForm backingForm) {
    OrderForm entity = new OrderForm();
    Location from = locationRepository.getLocation(backingForm.getDispatchedFrom().getId());
    Location to = locationRepository.getLocation(backingForm.getDispatchedTo().getId());
    entity.setDispatchedFrom(from);
    entity.setDispatchedTo(to);
    entity.setOrderDate(backingForm.getOrderDate());
    entity.setStatus(backingForm.getStatus());
    entity.setType(backingForm.getType());
    if (backingForm.getItems() != null) {
      for (OrderFormItemBackingForm item : backingForm.getItems()) {
        entity.getItems().add(orderFormItemFactory.createEntity(entity, item));
      }
    }
    return entity;
  }

  public OrderFormViewModel createViewModel(OrderForm entity) {
    OrderFormViewModel viewModel = new OrderFormViewModel();
    viewModel.setId(entity.getId());
    viewModel.setDispatchedFrom(new LocationViewModel(entity.getDispatchedFrom()));
    viewModel.setDispatchedTo(new LocationViewModel(entity.getDispatchedTo()));
    viewModel.setOrderDate(entity.getOrderDate());
    viewModel.setStatus(entity.getStatus());
    viewModel.setType(entity.getType());
    viewModel.setIsDeleted(entity.getIsDeleted());
    for (OrderFormItem item : entity.getItems()) {
      viewModel.getItems().add(orderFormItemFactory.createViewModel(item));
    }
    return viewModel;
  }

}
