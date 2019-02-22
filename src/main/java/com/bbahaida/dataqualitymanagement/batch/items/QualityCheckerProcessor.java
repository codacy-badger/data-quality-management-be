package com.bbahaida.dataqualitymanagement.batch.items;

import com.bbahaida.dataqualitymanagement.entities.AppDBItem;
import org.springframework.batch.item.ItemProcessor;

import java.util.Map;

public class QualityCheckerProcessor implements ItemProcessor<Map<String, Object>, AppDBItem> {

    @Override
    public AppDBItem process(Map<String, Object> item) throws Exception {
        return null;
    }
}
