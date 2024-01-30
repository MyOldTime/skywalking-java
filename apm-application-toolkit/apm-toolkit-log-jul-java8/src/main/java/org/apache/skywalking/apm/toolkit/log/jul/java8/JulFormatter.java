/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.toolkit.log.jul.java8;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class JulFormatter extends Formatter {

    private static final String DEFAULT_FORMAT = "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL] - [pid: %8$s] [%4$s] [%7$s] [%9$s] - %3$s: %5$s%6$s%n";

    private final String pid = getOrUseDefault("PID", "N/A");

    private final Date date = new Date();

    private final String format;

    public JulFormatter() {
        String cname = this.getClass().getName();
        LogManager manager = LogManager.getLogManager();
        String property = manager.getProperty(cname + ".format");
        if (property == null) {
            this.format = DEFAULT_FORMAT;
        } else {
            this.format = property;
        }
    }

    @Override
    public synchronized String format(LogRecord record) {
        this.date.setTime(record.getMillis());
        String source = record.getLoggerName();
        String className = record.getSourceClassName();
        String message = formatMessage(record);
        String throwable = getThrowable(record);
        String thread = getThreadName();
        return String.format(format, this.date, source, record.getLoggerName(),
                record.getLevel().toString(), message, throwable, thread, this.pid, className);
    }

    private String getThrowable(LogRecord record) {
        if (record.getThrown() == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println();
        record.getThrown().printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private String getThreadName() {
        String name = Thread.currentThread().getName();
        return (name != null) ? name : "";
    }

    private static String getOrUseDefault(String key, String defaultValue) {
        String value = null;
        try {
            value = System.getenv(key);
        }
        catch (Exception ex) {
            // ignore
        }
        if (value == null) {
            value = defaultValue;
        }
        return System.getProperty(key, value);
    }
}
