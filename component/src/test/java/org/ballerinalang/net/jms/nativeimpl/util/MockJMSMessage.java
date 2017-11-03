/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.jms.nativeimpl.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Class to mock {@link Message}
 */
public class MockJMSMessage implements Message {

    // Property maps
    private Map<String, String> stringPropertyMap = new HashMap<>();
    private Map<String, Integer> integerPropertyMap = new HashMap<>();
    private Map<String, Boolean> booleanPropertyMap = new HashMap<>();
    private Map<String, Float> floatPropertyMap = new HashMap<>();

    //Headers
    private String jmsMessageID;
    private long jmsTimestamp;
    private String jmsCorrelationID;
    private int jmsDeliveryMode;
    private int jmsPriority;
    private boolean jmsRedelivered;
    private long jmsExpiration;
    private String jmsType;



    @Override
    public String getJMSMessageID() throws JMSException {
        return this.jmsMessageID;
    }

    @Override
    public void setJMSMessageID(String id) throws JMSException {
        this.jmsMessageID = id;
    }

    @Override
    public long getJMSTimestamp() throws JMSException {
        return this.jmsTimestamp;
    }

    @Override
    public void setJMSTimestamp(long timestamp) throws JMSException {
        this.jmsTimestamp = timestamp;
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        return new byte[0];
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] correlationID) throws JMSException {

    }

    @Override
    public String getJMSCorrelationID() throws JMSException {
        return this.jmsCorrelationID;
    }

    @Override
    public void setJMSCorrelationID(String correlationID) throws JMSException {
        this.jmsCorrelationID = correlationID;
    }

    @Override
    public Destination getJMSReplyTo() throws JMSException {
        return null;
    }

    @Override
    public void setJMSReplyTo(Destination replyTo) throws JMSException {

    }

    @Override
    public Destination getJMSDestination() throws JMSException {
        return null;
    }

    @Override
    public void setJMSDestination(Destination destination) throws JMSException {

    }

    @Override
    public int getJMSDeliveryMode() throws JMSException {
        return this.jmsDeliveryMode;
    }

    @Override
    public void setJMSDeliveryMode(int deliveryMode) throws JMSException {
        this.jmsDeliveryMode = deliveryMode;
    }

    @Override
    public boolean getJMSRedelivered() throws JMSException {
        return this.jmsRedelivered;
    }

    @Override
    public void setJMSRedelivered(boolean redelivered) throws JMSException {
        this.jmsRedelivered = redelivered;
    }

    @Override
    public String getJMSType() throws JMSException {
        return this.jmsType;
    }

    @Override
    public void setJMSType(String type) throws JMSException {
        this.jmsType = type;
    }

    @Override
    public long getJMSExpiration() throws JMSException {
        return this.jmsExpiration;
    }

    @Override
    public void setJMSExpiration(long expiration) throws JMSException {
        this.jmsExpiration = expiration;
    }

    @Override
    public long getJMSDeliveryTime() throws JMSException {
        return 0;
    }

    @Override
    public void setJMSDeliveryTime(long deliveryTime) throws JMSException {
    }

    @Override
    public int getJMSPriority() throws JMSException {
        return this.jmsPriority;
    }

    @Override
    public void setJMSPriority(int priority) throws JMSException {
        this.jmsPriority = priority;
    }

    @Override
    public void clearProperties() throws JMSException {
        stringPropertyMap.clear();
        integerPropertyMap.clear();
        booleanPropertyMap.clear();
        floatPropertyMap.clear();
    }

    @Override
    public boolean propertyExists(String name) throws JMSException {
        return false;
    }

    @Override
    public boolean getBooleanProperty(String name) throws JMSException {
        return booleanPropertyMap.get(name);
    }

    @Override
    public byte getByteProperty(String name) throws JMSException {
        throw new UnsupportedOperationException();
    }

    @Override
    public short getShortProperty(String name) throws JMSException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIntProperty(String name) throws JMSException {
        return integerPropertyMap.get(name);
    }

    @Override
    public long getLongProperty(String name) throws JMSException {
        return 0;
    }

    @Override
    public float getFloatProperty(String name) throws JMSException {
        return floatPropertyMap.get(name);
    }

    @Override
    public double getDoubleProperty(String name) throws JMSException {
        return 0;
    }

    @Override
    public String getStringProperty(String name) throws JMSException {
        return stringPropertyMap.get(name);
    }

    @Override
    public Object getObjectProperty(String name) throws JMSException {
        return null;
    }

    @Override
    public Enumeration getPropertyNames() throws JMSException {
        return null;
    }

    @Override
    public void setBooleanProperty(String name, boolean value) throws JMSException {
        booleanPropertyMap.put(name, value);
    }

    @Override
    public void setByteProperty(String name, byte value) throws JMSException {

    }

    @Override
    public void setShortProperty(String name, short value) throws JMSException {

    }

    @Override
    public void setIntProperty(String name, int value) throws JMSException {
        integerPropertyMap.put(name, value);
    }

    @Override
    public void setLongProperty(String name, long value) throws JMSException {

    }

    @Override
    public void setFloatProperty(String name, float value) throws JMSException {
        floatPropertyMap.put(name, value);
    }

    @Override
    public void setDoubleProperty(String name, double value) throws JMSException {

    }

    @Override
    public void setStringProperty(String name, String value) throws JMSException {
        stringPropertyMap.put(name, value);
    }

    @Override
    public void setObjectProperty(String name, Object value) throws JMSException {

    }

    @Override
    public void acknowledge() throws JMSException {

    }

    @Override
    public void clearBody() throws JMSException {

    }

    @Override
    public <T> T getBody(Class<T> c) throws JMSException {
        return null;
    }

    @Override
    public boolean isBodyAssignableTo(Class c) throws JMSException {
        return false;
    }
}
