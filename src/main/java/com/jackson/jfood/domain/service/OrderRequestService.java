package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.OrderNotFoundException;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.Order;
import com.jackson.jfood.domain.model.PaymentType;
import com.jackson.jfood.domain.model.Product;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.repository.OrderRepository;

@Service
public class OrderRequestService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistration;
	
	@Autowired
	private CityRegistrationService cityRegistration;
	
	@Autowired
	private UserRegistrationService userRegistration;
	
	@Autowired
	private ProductRegistrationService productRegistration;
	
	@Autowired
	private PaymentTypeRegistrationService paymentTypeRegistration;
	
	public Order findOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode)
				.orElseThrow(() -> new OrderNotFoundException(orderCode));
	}
	
	@Transactional
	public Order save(Order order) {
		validateOrder(order);
		validateOrderItems(order);
		
		order.setShippingFee(order.getRestaurant().getShippingFee());
		order.calculateTotalValue();
		
		return orderRepository.save(order);
	}

	private void validateOrderItems(Order order) {
		order.getItems().forEach(orderItem -> {
			Product product = productRegistration.
					findOrFailByRestaurantAndProduct(order.getRestaurant().getId(), orderItem.getProduct().getId());
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setUnitPrice(product.getPrice());
		});
	}

	private void validateOrder(Order order) {
		City city = cityRegistration.findByIdOrFail(order.getDeliveryAddress().getCity().getId());
		User customer = userRegistration.findOrFail(order.getCustomer().getId());
		Restaurant restaurant = restaurantRegistration.findByIdOrFail(order.getRestaurant().getId());
		PaymentType paymentType = paymentTypeRegistration.findByIdOrFail(order.getPaymentType().getId());
		
		order.getDeliveryAddress().setCity(city);
		order.setCustomer(customer);
		order.setRestaurant(restaurant);
		order.setPaymentType(paymentType);
		
		if (restaurant.notAcceptsPaymentType(paymentType)) {
			throw new BusinessException(String.format("Forma de pagamento %s não é aceita por esse restaurante", 
					paymentType.getDescription()));
		}
	}
}
