package com.bbahaida.dataqualitymanagement.web;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceConnectionException;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import com.bbahaida.dataqualitymanagement.services.DataSourceService;
import com.bbahaida.dataqualitymanagement.utils.RoutingConstants;
import com.bbahaida.dataqualitymanagement.web.dto.DataSourceDTO;
import com.bbahaida.dataqualitymanagement.web.dto.MessageDTO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(RoutingConstants.DB_ROUTE)
@Api(description = "This controller is responsible for configuring the client's database.")
public class DbConfigurationController {

    private final static Logger logger = LoggerFactory.getLogger(DbConfigurationController.class);

    private DataSourceService dataSourceService;

    public DbConfigurationController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @PostMapping(RoutingConstants.DB_SETUP_ROUTE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DBConfiguration> setup(@RequestBody DataSourceDTO dto) throws SQLException {
        DBConfiguration configuration = dataSourceService.setup(
                dto.getDatabaseName(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getHost(),
                dto.getPort(),
                dto.getType());
        if (configuration != null) {
            return new ResponseEntity<>(configuration, HttpStatus.OK);
        } else {
            throw new InvalidDataSourceTypeException("Please check the database configuration !!");
        }

    }

    @GetMapping(RoutingConstants.DB_ALL_ROUTE)
    public ResponseEntity<List<DBConfiguration>> getAll() {
        return new ResponseEntity<>(dataSourceService.getConfigurations(), HttpStatus.OK);
    }

    @GetMapping(RoutingConstants.DB_TABLES_ROUTE)
    public ResponseEntity<List<String>> getTables(@PathVariable("id") Long id) throws SQLException {
        dataSourceService.loadDBConfiguration(id);
        return new ResponseEntity<>(dataSourceService.getTables(), HttpStatus.OK);
    }

    @PostMapping(RoutingConstants.DB_TEST_CONNECTION_ROUTE)
    public ResponseEntity testConnection(@RequestBody DataSourceDTO dto) throws InvalidDataSourceConnectionException {
        logger.info(dto.toString());
        try {
            if (dataSourceService.testConnection(
                    dto.getDatabaseName(),
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getHost(),
                    dto.getPort(),
                    dto.getType())) {
                return new ResponseEntity(HttpStatus.OK);
            }
            throw new InvalidDataSourceConnectionException();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataSourceConnectionException();
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataSourceTypeException.class)
    public MessageDTO handleDBTypeError(InvalidDataSourceTypeException ex) {
        return new MessageDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataSourceConnectionException.class)
    public MessageDTO handleDBConnectionError(InvalidDataSourceConnectionException ex) {
        return new MessageDTO(ex.getMessage());
    }
}
