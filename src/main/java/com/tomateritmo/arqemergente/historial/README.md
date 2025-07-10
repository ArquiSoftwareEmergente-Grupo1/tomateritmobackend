# Sistema de Auditoría e Historial (Audit Trail)

Este módulo proporciona un sistema de registro histórico de eventos para la aplicación TomateRitmo, permitiendo rastrear acciones importantes como la creación y eliminación de cultivos, así como los análisis de visión artificial.

## Arquitectura

El sistema de auditoría sigue el enfoque de Domain Driven Design (DDD) y está estructurado como un bounded context independiente con las siguientes capas:

- **Domain**: Contiene el modelo de dominio, comandos, consultas y servicios de dominio.
- **Application**: Implementa los servicios de dominio y proporciona servicios de integración para otros bounded contexts.
- **Infrastructure**: Contiene los repositorios y la configuración de persistencia.
- **Interface**: Expone la API REST para acceder al historial.

## Modelo de Eventos Históricos

El sistema registra los siguientes tipos de eventos:

- **CREACIÓN**: Cuando se crea un nuevo cultivo
- **ELIMINACIÓN**: Cuando se elimina un cultivo
- **ANALIZAR**: Cuando se realiza un análisis de visión artificial

Cada evento histórico incluye:
- ID (generado automáticamente)
- Fecha y hora del evento
- Tipo de evento
- Nombre del cultivo (opcional)
- Detalles adicionales

## API REST

### Endpoints

- `GET /api/v1/eventosHistoricos`: Obtiene todos los eventos históricos con filtros opcionales
  - Parámetros: `tipo`, `zona`, `fechaInicio`, `fechaFin`
- `GET /api/v1/eventosHistoricos/all`: Obtiene todos los eventos históricos ordenados por fecha descendente (más recientes primero)
- `GET /api/v1/eventosHistoricos/{id}`: Obtiene un evento histórico específico por su ID
- `POST /api/v1/eventosHistoricos`: Crea un nuevo evento histórico manualmente

### Filtrado

El endpoint principal admite los siguientes filtros:
- `tipo`: Filtra por tipo de evento (CREACIÓN, ELIMINACIÓN, ANALIZAR)
- `zona`: Filtra por nombre o parte del nombre del cultivo
- `fechaInicio`: Filtra eventos desde una fecha determinada
- `fechaFin`: Filtra eventos hasta una fecha determinada

## Integración con otros Bounded Contexts

El historial se actualiza automáticamente cuando ocurren eventos en:

1. **Cultivos**: 
   - Creación de nuevos cultivos
   - Eliminación de cultivos

2. **Análisis**:
   - Análisis de visión artificial

La integración se realiza a través del servicio `HistorialEventLogger` que es inyectado en los servicios correspondientes.

## Uso en el Frontend

El frontend puede consumir esta API para mostrar un historial de actividades en la aplicación, permitiendo:

1. Ver cronológicamente todos los eventos del sistema
2. Filtrar eventos por tipo, cultivo o rango de fechas
3. Acceder a detalles específicos de cada evento

## Ejemplo de Implementación

Para registrar un evento manualmente:

```java
// Inyectar el servicio
@Autowired
private HistorialCommandService historialCommandService;

// Crear y manejar el comando
CreateEventoHistoricoCommand command = new CreateEventoHistoricoCommand(
    LocalDateTime.now(),
    "ACCIÓN_PERSONALIZADA",
    "Nombre del cultivo",
    "Detalles sobre la acción realizada"
);
historialCommandService.handle(command);
```
