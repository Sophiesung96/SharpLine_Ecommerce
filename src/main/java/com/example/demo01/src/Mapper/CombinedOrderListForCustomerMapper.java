package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.CombinedOrderListForCustomer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CombinedOrderListForCustomerMapper implements RowMapper<CombinedOrderListForCustomer> {
    @Override
    public CombinedOrderListForCustomer mapRow(ResultSet resultSet, int i) throws SQLException {
        CombinedOrderListForCustomer order=new CombinedOrderListForCustomer();
        order.setOrderId(resultSet.getInt("OrderId"));
        order.setProductName(resultSet.getString("ProductName"));
        return order;
    }
}
