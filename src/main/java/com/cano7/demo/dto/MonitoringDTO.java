package com.cano7.demo.dto;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class MonitoringDTO {
    
    @Schema(description = "Resumen de temperaturas de un lote")
    @Data
    @Builder
    public static class TemperatureSummary {
        @Schema(description = "Temperatura actual en grados Celsius", example = "42.8")
        private Double currentTemperature;
        @Schema(description = "Temperatura máxima registrada", example = "43.5")
        private Double maximumTemperature;
        @Schema(description = "Temperatura mínima registrada", example = "41.9")
        private Double minimumTemperature;
        @Schema(description = "Temperatura promedio del periodo", example = "42.3")
        private Double averageTemperature;
    }
    
    @Schema(description = "Panel de control con métricas de producción de yogurt")
    @Data
    @Builder
    public static class Dashboard {
        @Schema(description = "Conteo de lotes por estado")
        private Map<String, Long> batchCounts;
        @Schema(description = "Número de lotes activos actualmente")
        private Long activeBatchesCount;
        @Schema(description = "Cantidad de lotes completados hoy", example = "4")
        private Integer completedToday;
    }
}
