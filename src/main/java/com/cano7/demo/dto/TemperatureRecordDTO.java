package com.cano7.demo.dto;

import com.cano7.demo.domain.model.TemperatureLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO para registrar un valor de temperatura de un lote")
@Data
public class TemperatureRecordDTO {
    @Schema(description = "Valor de temperatura registrado en grados Celsius", example = "42.5")
    private Double temperature;
    @Schema(description = "Tipo de registro de temperatura")
    private TemperatureLog.LogType type;
}

