package com.bbahaida.dataqualitymanagement.web;

import com.bbahaida.dataqualitymanagement.entities.DBConfiguration;
import com.bbahaida.dataqualitymanagement.exceptions.InvalidDataSourceTypeException;
import com.bbahaida.dataqualitymanagement.services.DataSourceService;
import com.bbahaida.dataqualitymanagement.utils.RoutingConstants;
import com.bbahaida.dataqualitymanagement.web.dto.DataSourceDTO;
import com.bbahaida.dataqualitymanagement.web.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(RoutingConstants.DB_CONFIG_ROUTE)
public class DbConfigurationController {

    private DataSourceService dataSourceService;

    public DbConfigurationController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @PostMapping(RoutingConstants.DB_SETUP_ROUTE)
    public List<String> setup(@RequestBody DataSourceDTO dto) throws SQLException {

        if (dataSourceService.setup(dto.getDatabaseName(), dto.getUsername(),dto.getPassword(),dto.getHost(),dto.getPort(),dto.getType())){
            return dataSourceService.getTables();
        }else {
            throw new InvalidDataSourceTypeException("Please check the database configuration !!");
        }

    }

    @GetMapping(RoutingConstants.DB_ALL_ROUTE)
    public List<DBConfiguration> getAll(){
        return dataSourceService.getConfigurations();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDataSourceTypeException.class)
    public MessageDTO handleDBError(InvalidDataSourceTypeException ex){
        return new MessageDTO(ex.getMessage());
    }
}
