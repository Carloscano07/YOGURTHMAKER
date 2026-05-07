package com.cano7.demo.domain.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.cano7.demo.domain.model.TemperatureLog;
import com.cano7.demo.domain.model.YogurtBatch;
import com.cano7.demo.domain.repository.TemperatureLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureControlService {
    
    private final TemperatureLogRepository temperatureLogRepository;
    private final Random random = new Random();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    
    public void startHeatingProcess(YogurtBatch batch) {
        log.info("Starting heating process for batch: {} to target temperature: {}°C", 
            batch.getBatchCode(), batch.getRecipe().getHeatingTemperature());
        
        Runnable heatingTask = new Runnable() {
            private double currentTemp = 20.0;
            private final double targetTemp = batch.getRecipe().getHeatingTemperature();
            
            @Override
            public void run() {
                currentTemp += (targetTemp - currentTemp) * 0.1 + (random.nextDouble() - 0.5);
                
                if (currentTemp > targetTemp) {
                    currentTemp = targetTemp;
                }
                
                recordTemperature(batch, currentTemp, TemperatureLog.LogType.HEATING);
                log.debug("Heating temperature for batch {}: {}°C", batch.getBatchCode(), currentTemp);
                
                if (currentTemp < targetTemp) {
                    executor.schedule(this, 5, TimeUnit.SECONDS);
                } else {
                    log.info("Heating target temperature reached for batch: {}", batch.getBatchCode());
                    maintainHeatingTemperature(batch);
                }
            }
        };
        
        executor.schedule(heatingTask, 5, TimeUnit.SECONDS);
    }
    
    private void maintainHeatingTemperature(YogurtBatch batch) {
        Runnable maintainTask = new Runnable() {
            private int count = 0;
            private final int durationMinutes = batch.getRecipe().getHeatingDuration();
            private final double targetTemp = batch.getRecipe().getHeatingTemperature();
            
            @Override
            public void run() {
                double temp = targetTemp + (random.nextDouble() - 0.5) * 2;
                recordTemperature(batch, temp, TemperatureLog.LogType.HEATING);
                log.debug("Maintaining temperature for batch {}: {}°C", batch.getBatchCode(), temp);
                
                count++;
                if (count < durationMinutes) {
                    executor.schedule(this, 60, TimeUnit.SECONDS);
                } else {
                    startCoolingProcess(batch);
                }
            }
        };
        
        executor.schedule(maintainTask, 60, TimeUnit.SECONDS);
    }
    
    private void startCoolingProcess(YogurtBatch batch) {
        log.info("Starting cooling process for batch: {} to inoculation temperature: {}°C", 
            batch.getBatchCode(), batch.getRecipe().getInoculationTemperature());
        
        
        batch.setStatus(YogurtBatch.BatchStatus.COOLING);
        
        Runnable coolingTask = new Runnable() {
            private double currentTemp = batch.getRecipe().getHeatingTemperature();
            private final double targetTemp = batch.getRecipe().getInoculationTemperature();
            
            @Override
            public void run() {
                currentTemp -= (currentTemp - targetTemp) * 0.05 + (random.nextDouble() - 0.5);
                
                if (currentTemp < targetTemp) {
                    currentTemp = targetTemp;
                }
                
                recordTemperature(batch, currentTemp, TemperatureLog.LogType.COOLING);
                log.debug("Cooling temperature for batch {}: {}°C", batch.getBatchCode(), currentTemp);
                
                if (currentTemp > targetTemp) {
                    executor.schedule(this, 10, TimeUnit.SECONDS);
                } else {
                    log.info("Inoculation temperature reached for batch: {}", batch.getBatchCode());
                }
            }
        };
        
        executor.schedule(coolingTask, 10, TimeUnit.SECONDS);
    }
    
    public void startIncubationControl(YogurtBatch batch) {
        log.info("Starting incubation control for batch: {} at {}°C", 
            batch.getBatchCode(), batch.getRecipe().getIncubationTemperature());
        
        Runnable incubationTask = new Runnable() {
            private final LocalDateTime endTime = batch.getIncubationEndTime();
            private final double targetTemp = batch.getRecipe().getIncubationTemperature();
            
            @Override
            public void run() {
                if (LocalDateTime.now().isBefore(endTime)) {
                    double temp = targetTemp + (random.nextDouble() - 0.5) * 0.8;
                    recordTemperature(batch, temp, TemperatureLog.LogType.INCUBATION);
                    log.debug("Incubation temperature for batch {}: {}°C", batch.getBatchCode(), temp);
                    
                    executor.schedule(this, 5, TimeUnit.MINUTES);
                } else {
                    log.info("Incubation completed for batch: {}", batch.getBatchCode());
                }
            }
        };
        
        executor.schedule(incubationTask, 5, TimeUnit.MINUTES);
    }
    
    private void recordTemperature(YogurtBatch batch, Double temperature, TemperatureLog.LogType type) {
        TemperatureLog temperatureLog = TemperatureLog.builder()
            .batch(batch)
            .temperature(temperature)
            .recordedAt(LocalDateTime.now())
            .type(type)
            .build();
        
        temperatureLogRepository.save(temperatureLog);
    }
    
    public Double getCurrentTemperature(Long batchId) {
        return temperatureLogRepository.findByBatchAndTypeOrderByRecordedAtDesc(
            batchId, TemperatureLog.LogType.INCUBATION)
            .stream()
            .findFirst()
            .map(TemperatureLog::getTemperature)
            .orElse(null);
    }
}
