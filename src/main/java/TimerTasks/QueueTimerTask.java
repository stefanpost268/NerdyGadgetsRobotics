package TimerTasks;

import repositories.OrderRepository;

import javax.swing.*;
import java.util.TimerTask;

public class QueueTimerTask extends TimerTask {

    OrderRepository orderRepository;

    public QueueTimerTask(OrderRepository orderRepository, JTable queueTable) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run() {

    }
}
