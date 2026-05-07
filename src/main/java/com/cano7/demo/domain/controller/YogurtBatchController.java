package com.cano7.demo.domain.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cano7.demo.domain.model.YogurtBatch;
import com.cano7.demo.domain.service.YogurtMakingService;
import com.cano7.demo.dto.BatchDTO;
import com.cano7.demo.dto.TemperatureRecordDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Yogurt Batches", description = "Gestión de lotes de yogurt")
@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class YogurtBatchController {
    
    private final YogurtMakingService yogurtMakingService;
    
    @Operation(summary = "Iniciar un nuevo lote de yogurt", description = "Crea un nuevo lote usando una receta existente y parámetros opcionales de volumen y cultivo.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lote creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @PostMapping
    public ResponseEntity<YogurtBatch> startNewBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos para iniciar el lote de yogurt", required = true, content = @Content(schema = @Schema(implementation = BatchDTO.StartBatchRequest.class))) @RequestBody BatchDTO.StartBatchRequest request) {
        YogurtBatch batch = yogurtMakingService.startNewBatch(
            request.getRecipeId(), 
            request.getCustomMilkVolume(), 
            request.getCustomStarterAmount()
        );
        return new ResponseEntity<>(batch, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Iniciar calentamiento", description = "Cambia el estado del lote a la etapa de calentamiento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote en calentamiento", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @PostMapping("/{batchId}/heating")
    public ResponseEntity<YogurtBatch> startHeating(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startHeating(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Iniciar inoculación", description = "Marca el lote como listo para inoculación con cultivos de yogurt.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote en inoculación", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @PostMapping("/{batchId}/inoculating")
    public ResponseEntity<YogurtBatch> startInoculating(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startInoculating(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Iniciar incubación", description = "Comienza el periodo de incubación del lote.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote incubando", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @PostMapping("/{batchId}/incubation")
    public ResponseEntity<YogurtBatch> startIncubation(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startIncubation(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Iniciar refrigeración", description = "Cambia el lote a refrigeración para completar el proceso.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote en refrigeración", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @PostMapping("/{batchId}/refrigeration")
    public ResponseEntity<YogurtBatch> startRefrigeration(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.startRefrigeration(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Completar lote", description = "Finaliza el lote y lo marca como completado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote completado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @PostMapping("/{batchId}/complete")
    public ResponseEntity<YogurtBatch> completeBatch(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.completeBatch(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Marcar lote fallido", description = "Marca un lote como fallido y registra la causa.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote marcado como fallido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/{batchId}/fail")
    public ResponseEntity<YogurtBatch> markAsFailed(
            @Parameter(description = "ID del lote", required = true) @PathVariable Long batchId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Razón del fallo del lote", required = true, content = @Content(schema = @Schema(implementation = BatchDTO.FailRequest.class))) @RequestBody BatchDTO.FailRequest request) {
        YogurtBatch batch = yogurtMakingService.markAsFailed(batchId, request.getReason());
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Obtener lotes", description = "Devuelve todos los lotes o los filtra por estado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de lotes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "400", description = "Parámetro de consulta inválido")
    })
    @GetMapping
    public ResponseEntity<List<YogurtBatch>> getAllBatches(
            @Parameter(description = "Estado del lote para filtrar", required = false) @RequestParam(required = false) YogurtBatch.BatchStatus status) {
        if (status != null) {
            return ResponseEntity.ok(yogurtMakingService.getBatchesByStatus(status));
        }
        return ResponseEntity.ok(yogurtMakingService.getAllBatches());
    }
    
    @Operation(summary = "Obtener lote por ID", description = "Devuelve los datos de un lote específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lote encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = YogurtBatch.class))),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado")
    })
    @GetMapping("/{batchId}")
    public ResponseEntity<YogurtBatch> getBatch(@Parameter(description = "ID del lote", required = true) @PathVariable Long batchId) {
        YogurtBatch batch = yogurtMakingService.getBatch(batchId);
        return ResponseEntity.ok(batch);
    }
    
    @Operation(summary = "Registrar temperatura", description = "Añade un registro de temperatura al lote.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro guardado"),
        @ApiResponse(responseCode = "404", description = "Lote no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de temperatura inválidos")
    })
    @PostMapping("/{batchId}/temperature")
    public ResponseEntity<Void> recordTemperature(
            @Parameter(description = "ID del lote", required = true) @PathVariable Long batchId, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de temperatura del lote", required = true, content = @Content(schema = @Schema(implementation = TemperatureRecordDTO.class))) @RequestBody TemperatureRecordDTO request) {
        yogurtMakingService.recordTemperature(batchId, request.getTemperature(), request.getType());
        return ResponseEntity.ok().build();
    }
}
