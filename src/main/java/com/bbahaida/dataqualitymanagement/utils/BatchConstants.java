package com.bbahaida.dataqualitymanagement.utils;

public interface BatchConstants {

    String JOB_NAME = "${batch.job.name:}";
    String TABLE_NAME = "${batch.job.table-name:}";
}
