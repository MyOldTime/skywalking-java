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

package org.apache.skywalking.apm.toolkit.log.jul.java8.log;

import org.apache.skywalking.apm.toolkit.log.jul.java8.JulFormatter;

import java.lang.reflect.Method;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Filter;
import java.util.logging.Formatter;

public class GRPCLogClientHandler extends Handler {

    public GRPCLogClientHandler() {
        try {
            Class<LogManager> logManagerClass = LogManager.class;
            LogManager manager = LogManager.getLogManager();
            String cname = getClass().getName();
            Method getLevelProperty = logManagerClass.getDeclaredMethod("getLevelProperty", String.class, Level.class);
            Method getFilterProperty = logManagerClass.getDeclaredMethod("getFilterProperty", String.class, Filter.class);
            Method getFormatterProperty = logManagerClass.getDeclaredMethod("getFormatterProperty", String.class, Formatter.class);
            getLevelProperty.setAccessible(true);
            getFilterProperty.setAccessible(true);
            getFormatterProperty.setAccessible(true);

            setLevel((Level) getLevelProperty.invoke(manager, cname + ".level", Level.INFO));
            setFilter((Filter) getFilterProperty.invoke(manager, cname + ".filter", null));
            setFormatter((Formatter) getFormatterProperty.invoke(manager, cname + ".formatter", new JulFormatter()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }

}
