package org.example.reproject.producer;

import org.example.reproject.config.RabbitMQConfig;
import org.example.reproject.entity.Trucks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;

@Service
public class TrucksProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TrucksProducer.class);

    private PriorityQueue<TruckPriority> priorityQueue = new PriorityQueue<>();

    private static class TruckPriority implements Comparable<TruckPriority> {
        Trucks truck;
        int priority;

        TruckPriority(Trucks truck, int priority) {
            this.truck = truck;
            this.priority = priority;
        }

        @Override
        public int compareTo(TruckPriority o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    public void sendTrucksData(List<Trucks> trucks, double requiredKg) {
        for (Trucks truck : trucks) {
            int priority = (int)(truck.getMaxCapacityKg() - requiredKg) / 10;
            priorityQueue.add(new TruckPriority(truck, priority));
        }

        while (!priorityQueue.isEmpty()) {
            TruckPriority truckPriority = priorityQueue.poll();
            Trucks truck = truckPriority.truck;
            int priority = truckPriority.priority;

            rabbitTemplate.convertAndSend(RabbitMQConfig.FIFO_QUEUE_NAME, truck, message -> {
                message.getMessageProperties().setPriority(priority);
                return message;
            });
        }
    }
}




