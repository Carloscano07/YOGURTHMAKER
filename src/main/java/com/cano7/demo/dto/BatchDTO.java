package com.cano7.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class BatchDTO {
    
    @Schema(description = "Solicitud para iniciar un lote de yogurt")
    @Data
    public static class StartBatchRequest {
        @Schema(description = "ID de la receta a utilizar", example = "1")
        private Long recipeId;
        @Schema(description = "Volumen de leche personalizado en litros", example = "2.5")
        private Double customMilkVolume;
        @Schema(description = "Cantidad de cultivo personalizada en gramos", example = "0.05")
        private Double customStarterAmount;
    }
    
    @Schema(description = "Solicitud para marcar un lote como fallido")
    @Data
    public static class FailRequest {
        @Schema(description = "Razón por la cual el lote ha fallado", example = "Temperatura fuera de rango")
        private String reason;
    }
}
