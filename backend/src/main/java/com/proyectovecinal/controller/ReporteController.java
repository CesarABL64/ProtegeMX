package com.proyectovecinal.controller;

import com.proyectovecinal.dto.ReporteRequestDTO;
import com.proyectovecinal.dto.ReporteResponseDTO;
import com.proyectovecinal.entity.enums.EstatusReporte;
import com.proyectovecinal.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Endpoints para la gestión de reportes de incidentes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo reporte", description = "Registra un reporte de incidente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReporteResponseDTO> crear(
            @Parameter(description = "Datos del reporte a crear", required = true)
            @Valid @RequestBody ReporteRequestDTO dto,
            @Parameter(description = "ID del usuario que crea el reporte", required = true)
            @RequestParam Integer idUsuario) {
        ReporteResponseDTO response = reporteService.crear(dto, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar reportes", description = "Obtiene una lista paginada de reportes con filtros opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Page<ReporteResponseDTO>> listar(
            @Parameter(description = "Filtrar por estatus del reporte")
            @RequestParam(required = false) EstatusReporte estatus,
            @Parameter(description = "Filtrar por ID de colonia")
            @RequestParam(required = false) Integer idColonia,
            @Parameter(description = "Paginación y ordenamiento")
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reporteService.listar(pageable, estatus, idColonia));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reporte por ID", description = "Recupera un reporte específico por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                    content = @Content(schema = @Schema(implementation = ReporteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReporteResponseDTO> obtenerPorId(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estatus")
    @Operation(summary = "Actualizar estatus de un reporte", description = "Cambia el estatus de un reporte existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatus actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estatus no válido"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReporteResponseDTO> actualizarEstatus(
            @Parameter(description = "ID del reporte", required = true, example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Nuevo estatus del reporte", required = true)
            @RequestBody Map<String, String> body) {
        EstatusReporte nuevoEstatus = EstatusReporte.valueOf(body.get("estatus").toUpperCase());
        return ResponseEntity.ok(reporteService.actualizarEstatus(id, nuevoEstatus));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte", description = "Elimina un reporte existente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte a eliminar", required = true, example = "1")
            @PathVariable Integer id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
