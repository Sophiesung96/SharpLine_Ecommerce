package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.*;
import com.example.demo01.src.Exception.OrderNotFoundExcption;
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
import java.util.Arrays;
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

    @Autowired
    OrderTrackDAO orderTrackDAO;



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
        //Create a new orderTrack along with a new Order
        OrderTrack orderTrack=new OrderTrack();
        orderTrack.setOrderId(orderId);
        orderTrack.setStatus(OrderStatus.NEW.name());
        orderTrack.setNotes(OrderStatus.NEW.defaultdscription());
        orderTrack.setUpdatedTime(new Date());
        orderTrackDAO.createOrderTrack(orderTrack);
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

    @Override
    public void updateTrackStatus(String trackStatus,Order order) {
        orderDAO.updateTrackStatus(trackStatus,order);
    }

    @Override
    public List<TableOrderDetail> getTrackStatusList(int orderId) {
        List<TableOrderDetail> list=orderDAO.getTrackStatusList(orderId);
        return list;
    }

    @Override
    public List<Order> getOrderTrackByKeyword(String keyword,int pageNo) {
        List<Order> list=orderDAO.getOrderTrackByKeyword(keyword,pageNo);
        return list;
    }

    @Override
    public void updateStatus(int orderId, String status) {
        Order orderInDB=orderDAO.getOrderById(orderId);
        OrderStatus orderStatus=OrderStatus.valueOf(status);
        List<OrderTrack>orderTrackList=orderTrackDAO.findOrderTrackById(orderId);
        log.info("test orderInDb's has status:{}",orderInDB.getOrderTrackList());
        // Check if the list contains an OrderTrack with a status
        // that matches the input status. If found, assign it to orderTrack,
        // otherwise, set orderTrack to null.
        OrderTrack orderTrack = orderTrackList.stream()
                .filter(track -> track.getStatus().equals(status))
                .findFirst()
                .orElse(null);
        //Check if a new order Track has been added
        if(orderTrack==null && orderTrackList!=null){
                orderInDB.setStatus(status);
                OrderTrack NeworderTrack=new OrderTrack();
                NeworderTrack.setOrderId(orderId);
                NeworderTrack.setStatus(status);
                NeworderTrack.setNotes(orderStatus.defaultdscription());
                NeworderTrack.setUpdatedTime(new Date());
                orderTrackList.add(NeworderTrack);
                orderTrackDAO.createOrderTrack(NeworderTrack);
                orderDAO.updateTrackStatus(status,new Order(orderId));

            }

        }


        @Override
        public List<CombinedOrderListForCustomer>  getOrderListForCustomer(int customerId) {
            List<CombinedOrderListForCustomer>  list=orderDAO.getOrderListForCustomer(customerId);
            return list;
    }


    @Override
    public List<Order> getOrderByCustomerId(int customerid) {
        List<Order> list=orderDAO.getOrderByCustomerId(customerid);
        return list;
    }

    @Override
    public List<Integer> getTotalPageForCustomerOrderList(int customerid) {
        Integer number=orderDAO.getTotalPageForCustomerOrderList(customerid);
        number=(number/5)+1;
        List<Integer> pageList=new ArrayList<>();
        for(int i=1;i<=number;i++){
            pageList.add(i);
        }
        return pageList;
    }


    @Override
    public List<ProductListForCustomer> getCustomerOrderDetailList(int customerId,int orderId) {
        List<ProductListForCustomer> list=orderDAO.getCustomerOrderDetailList(customerId,orderId);
        for(ProductListForCustomer customer:list){
            String UnsortedProductName=customer.getProductName();
            String SortedProductName[]=UnsortedProductName.split(",");
            String UnsortedQuantity=customer.getQuantity();
            String SortedQuantity[]=UnsortedQuantity.split(",");
            String UnsortedSubTotal=customer.getSubtotal();
            String SortedSubTotal[]=UnsortedSubTotal.split(",");
            String UnsortedUnitPrice=customer.getUnitprice();
            String SortedUnitPrice[]=UnsortedUnitPrice.split(",");
            String UnsortedProductId=customer.getProductId();
            String SortedProductId[]=UnsortedProductId.split(",");
            String UnsortedShippingCost=customer.getShippingCost();
            String SortedShippingCost[]=UnsortedShippingCost.split(",");
            String UnsortedProductCost=customer.getProductCost();
            String SortedProductCost[]=UnsortedProductCost.split(",");
            customer.setSortedProductName(Arrays.asList(SortedProductName));
            customer.setSortedQuantity(Arrays.asList(SortedQuantity));
            customer.setSortedSubtotal(Arrays.asList(SortedSubTotal));
            customer.setSortedUnitprice(Arrays.asList(SortedUnitPrice));
            customer.setSortedProductId(Arrays.asList(SortedProductId));
            customer.setSortedShippingCost(Arrays.asList(SortedShippingCost));
            customer.setSortedProductCost(Arrays.asList(SortedProductCost));
        }
        return list;


    }


    @Override
    public Order getOrderDetailByIdAndCustomer(int customerId, int orderId) {
      Order order= orderDAO.getOrderDetailByIdAndCustomer(customerId, orderId);
        return order;
    }


    @Override
    public void setOrderReturnRequested(OrderReturnRequest returnRequest, Customer customer, Order order) {
        if(order==null){

         throw new OrderNotFoundExcption("Order ID:"+returnRequest.getId()+"Not Found");
        }
        if(order.isReturnRequested()){
            return;
        }
        OrderTrack orderTrack=new OrderTrack();
        orderTrack.setOrderId(returnRequest.getId());
        orderTrack.setUpdatedTime(new Date());
        orderTrack.setStatus("RETURN_REQUESTED");
        String notes="Reason:"+returnRequest.getReason();
        if(!"".equals(returnRequest.getNote())){
            notes+=". "+returnRequest.getNote();
        }
        orderTrack.setNotes(notes);
        orderDAO.updateTrackStatus("RETURN_REQUESTED",order);
        orderTrackDAO.createOrderTrack(orderTrack);
    }

    @Override
    public List<TableOrderDetail> getCustomerTrackStatusList(int CustomerId, int OrderId) {
        List<TableOrderDetail> list=orderDAO.getCustomerTrackStatusList(CustomerId,OrderId);
        return list;
    }

    @Override
    public List<Order> findByOrderTimeBetween(Date startTime, Date endTime) {
        List<Order> list=orderDAO.findByOrderTimeBetween(startTime, endTime);
        return list;
    }
}


