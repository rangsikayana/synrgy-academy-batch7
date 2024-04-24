package org.example.service;

import org.example.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceImplTest {

    private OrderService orderService;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        orderList = new ArrayList<>();
        orderService = new OrderServiceImpl(orderList);
    }

    @Test
    void addOrder_addOrderToList() {
        Order order = new Order("Nasi Goreng", 2, 30000);
        orderService.addOrder(order);
        assertEquals(1, orderList.size());
        assertEquals(order, orderList.get(0));
    }

    @Test
    void addOrder_handleMultipleOrders() {
        Order order1 = new Order("Nasi Goreng", 2, 30000);
        Order order2 = new Order("Mie Goreng", 1, 13000);
        orderService.addOrder(order1);
        orderService.addOrder(order2);
        assertEquals(2, orderList.size());
        assertEquals(order1, orderList.get(0));
        assertEquals(order2, orderList.get(1));
    }

    @Test
    void calculateTotal_returnTotalPriceOfOrders() {
        Order order1 = new Order("Nasi Goreng", 2, 30000);
        Order order2 = new Order("Mie Goreng", 1, 13000);
        orderService.addOrder(order1);
        orderService.addOrder(order2);
        assertEquals(43000, orderService.calculateTotal());
    }

    @Test
    void calculateTotal_handleEmptyOrderList() {
        assertEquals(0, orderService.calculateTotal());
    }

    @Test
    void addOrder() {
        Order order = new Order("Nasi Goreng", 2, 30000);
        orderService.addOrder(order);
        assertEquals(1, orderList.size());
        assertEquals(order, orderList.get(0));
    }

    @Test
    void calculateTotal() {
        Order order1 = new Order("Nasi Goreng", 2, 30000);
        Order order2 = new Order("Mie Goreng", 1, 13000);
        orderService.addOrder(order1);
        orderService.addOrder(order2);
        assertEquals(43000, orderService.calculateTotal());
    }

    private static class MockFileWriter extends Writer {
        private StringBuilder content;

        MockFileWriter() {
            content = new StringBuilder();
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            content.append(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }

        public String getContent() {
            return content.toString();
        }
    }
}