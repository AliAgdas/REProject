package org.example.reproject.consumer;

import org.example.reproject.config.RabbitMQConfig;
import org.example.reproject.entity.Trucks;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.example.reproject.service.SendTrucksToFactoriesService;

@Service
public class TrucksConsumer {

    private boolean firstMessageProcessed = false;

    @RabbitListener(queues = RabbitMQConfig.FIFO_QUEUE_NAME)
    public void consumeTruckData(Trucks truck) {
        if (!firstMessageProcessed) {
            System.out.println("Processing first truck data: " + truck);
            firstMessageProcessed = true;
            SendTrucksToFactoriesService.setChoosenTruck(truck);
        } else {
            System.out.println("Processing others truck data: " + truck);
        }
    }
}


