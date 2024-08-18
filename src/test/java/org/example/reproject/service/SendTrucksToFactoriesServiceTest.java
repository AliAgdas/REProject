package org.example.reproject.service;

import org.example.reproject.entity.Factories;
import org.example.reproject.entity.Trucks;
import org.example.reproject.producer.TrucksProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class SendTrucksToFactoriesServiceTest {

    @Mock
    private TrucksProducer trucksProducer;

    @Mock
    private TrucksService trucksService;

    @Mock
    private FactoriesService factoriesService;

    @InjectMocks
    private SendTrucksToFactoriesService sendTrucksToFactoriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessAndSendTruckData_whenFactoryHasNoTruck_shouldSendTrucksData() {
        Factories factory = new Factories();
        factory.setTruck(null);

        Trucks truck = new Trucks();
        List<Trucks> trucksList = Collections.singletonList(truck);
        given(trucksService.findByIsFullFalseAndCountry(factory.getCountry_factories(), sendTrucksToFactoriesService.getRequiredKg())).willReturn(trucksList);

        assertTrue(sendTrucksToFactoriesService.processAndSendTruckData(factory));

        verify(trucksProducer).sendTrucksData(trucksList, sendTrucksToFactoriesService.getRequiredKg());
    }

    @Test
    void testProcessAndSendTruckData_whenFactoryHasTruck_shouldNotSendTrucksData() {
        Factories factory = new Factories();
        factory.setTruck(new Trucks());

        assertFalse(sendTrucksToFactoriesService.processAndSendTruckData(factory));
        verify(trucksProducer, org.mockito.Mockito.never()).sendTrucksData(org.mockito.Mockito.anyList(), org.mockito.Mockito.anyDouble());
    }


}
