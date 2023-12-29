package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.AddressDAO;
import com.example.demo01.src.DAO.CountryDao;
import com.example.demo01.src.DAO.CustomerDao;
import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.DAO.AddressDAO;
import com.example.demo01.src.DAO.CountryDao;
import com.example.demo01.src.DAO.CustomerDao;
import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    CountryDao countryDao;



    @Override
    public List<Order> findAllByKeyword(int pageNo, String keyword) {
        List<Order> list=orderDAO.findAllByKeyword(pageNo,keyword);
        return list;
    }

    @Override
    public List<Order> findAll(int pageNo) {
        List<Order> list=orderDAO.findAll(pageNo);
        return list;
    }

    @Override
    public List<Integer> getTotalPage() {
       int total=orderDAO.getTotalPage();
        int page=(total/10)+1;
        List<Integer>list=new ArrayList<>();
        for(int i=1;i<=page;i++){
            list.add(i);
        }
        return list;
    }

    @Override
    public Order getOrderById(int orderId) {
        Order order=orderDAO.getOrderById(orderId);
        return order;
    }

    @Override
    public OrderDetailForm getOrderDetailById(int orderId) {
        OrderDetailForm order=orderDAO.getOrderDetailById(orderId);
        return order;
    }

    @Override
    public void DeleteOrderById(int orderId) {
        orderDAO.DeleteOrderById(orderId);
    }

    @Override
    public void updatePaymentMethod(String method,int id) {
        orderDAO.updatePaymentMethod(method,id);
    }

    @Override
    public OrderDetailForm createorder(Customer customer, Address address, List<CartItem> list, PaymentMethod paymentMethod, CheckOutInfo checkOutInfo) {
        Order order=new Order();
        order.setCustomerId(customer.getId());
        //if the customer did not offer address
        //we then choose customer's default address instead
        if(address==null){
            copyAddressFromCustomer(order,customer.getId());
        }else{
            log.info("shipping address id:{},address.customerId{}",address.getId(),address.getCustomerId());
            copyShippingAddress(order,address,customer);
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=dateFormat.format(new Date());
        order.setOrderTime(date);
        if(paymentMethod.equals(PaymentMethod.PAYPAL)){
            order.setStatus(OrderStatus.PAID.name());
        }else{
            order.setStatus(OrderStatus.NEW.name());
        }
        order.setDeliverDays(checkOutInfo.getDeliverDays());
        order.setDeliverDate(checkOutInfo.getDeliverDate());
        order.setPostalCode(address.getPostalCode());
        order.setTotal(checkOutInfo.getPaymentTotal());
        order.setProductCost(checkOutInfo.getProductCost());
        order.setPaymentMethod(paymentMethod.name());
        order.setSubTotal(checkOutInfo.getPaymentTotal());
        order.setTax(0.0F);
        order.setShippingCost(checkOutInfo.getShippingCostTotal());
       int orderId= orderDAO.createorder(customer,order);
        List<OrderDetails>detailListlist=new ArrayList<>();
        //create orderDetail coming from Shopping cart
        for(int i=0;i<list.size();i++){
            List<CartItemPName> cartItemPNameLIst=list.get(i).getList();
          OrderDetails orderDetails=new OrderDetails();
            orderDetails.setOrderId(orderId);
            orderDetails.setSubTotal(cartItemPNameLIst.get(i).getSubTotal());
            orderDetails.setProductId(cartItemPNameLIst.get(i).getProductId());
            orderDetails.setQuantity(cartItemPNameLIst.get(i).getQuantity());
            orderDetails.setUnitPrice(cartItemPNameLIst.get(i).getDetailPrice());
            orderDetails.setProductCost(checkOutInfo.getProductCost());
            orderDetails.setShippingCost(checkOutInfo.getShippingCostTotal());
            detailListlist.add(orderDetails);
        }
        orderDAO.createOrderDetail(order,detailListlist,customer);

        OrderDetailForm orderDetailForm=orderDAO.getOrderDetailById(orderId);

        return orderDetailForm;

    }

    @Override
    public void copyAddressFromCustomer(Order order,int customerId) {
       Customer cusomer=customerDao.findCustomerById(customerId);
        order.setFirstName(cusomer.getFirstName());
        order.setLastName(cusomer.getLastName());
        order.setPhoneNumber(cusomer.getPhoneNumber());
        order.setAddressline1(cusomer.getAddressline1());
        order.setAddressline2(cusomer.getAddressline2());
        order.setCity(cusomer.getCity());
        order.setCountry(cusomer.getCountryName());
        order.setPostalCode(cusomer.getPostalCode());
        order.setState(cusomer.getState());



    }

    @Override
    public void copyShippingAddress(Order order,Address address,Customer customer) {
        Country countryforAddress=new Country();
        countryforAddress.setId(address.getCountryId());
        Country country=countryDao.getByCountryId(countryforAddress.getId());
        log.info("customer's firstNAme:{}, PhoneNUmber:{},country:{}",address.getFirstName(),address.getPhoneNumber(),country.getName());
        order.setFirstName(address.getFirstName());
        order.setLastName(address.getLastName());
        order.setPhoneNumber(address.getPhoneNumber());
        order.setAddressline1(address.getAddressline1());
        order.setAddressline2(address.getAddressline2());
        order.setCity(address.getCity());
        order.setCountry(country.getName());
        order.setPostalCode(address.getPostalCode());
        order.setState(address.getState());

    }

    @Override
    public List<Country> listAllCountries() {
        List<Country> list=orderDAO.listAllCountries();
        return list;
    }


    @Override
    public List<TableOrderDetail> getOrderDetailsList(int orderId) {
        List<TableOrderDetail> list=orderDAO.getOrderDetailsList(orderId);
        return list;
    }


    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetails> list=orderDAO.getOrderDetailsByOrderId(orderId);
        return list;
    }

    @Override
    public void createOrderDetail(Order order, Iterable<OrderDetails> list, Customer customer) {
        orderDAO.createOrderDetail(order,list,customer);
    }

    @Override
    public void updateOrderDetailsByOrderId(OrderDetails orderDetails) {
        orderDAO.updateOrderDetailsByOrderId(orderDetails);
    }

    @Override
    public void updateOriginalOrderById(Order order) {
        orderDAO.updateOriginalOrderById(order);
    }
}
