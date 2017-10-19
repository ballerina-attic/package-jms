package ballerina.net.jms;

import ballerina.doc;

public struct JMSMessage {
}

@doc:Description { value:"Message acknowledgement action implementation for jms connector when using jms client acknowledgement mode"}
@doc:Param { value:"message: message" }
@doc:Param { value:"deliveryStatus: Specify whether message delivery is SUCCESS or ERROR" }
public native function <JMSMessage msg> acknowledge (string deliveryStatus);

@doc:Description { value:"Session rollback action implementation for jms connector when using jms session transaction mode"}
@doc:Param { value:"message: message" }
public native function <JMSMessage msg> rollback ();

@doc:Description { value:"Session commit action implementation for jms connector when using jms session transaction mode"}
@doc:Param { value:"message: message" }
public native function <JMSMessage msg> commit ();

@doc:Description { value:"JMS client connector to send messages to the JMS provider."}
@doc:Param { value:"connection and optional properties for the connector"}
public connector ClientConnector (map properties) {

    string connectorID = "EMPTY_ID";

    @doc:Description {value:"SEND action implementation of the JMS Connector"}
    @doc:Param {value:"destinationName: Destination Name"}
    @doc:Param {value:"message: Message"}
    native action send (string destinationName, JMSMessage m) (boolean);

}

@doc:Description { value:"Create JMS Message based on client connector"}
@doc:Param { value:"ClientConnector: clientConnector" }
public native function createTextMessage (ClientConnector clientConnector) (JMSMessage);

@doc:Description { value:"Header key for message ID. This is not a required header and jms client application might set the value when sending the message to the JMS broker."}
const string HEADER_MESSAGE_ID = "JMS_MESSAGE_ID";

@doc:Description { value:"JMS priority value header for the message. Priority value can range from 0 to 9. 0 is the lowest priority. 9 is the highest priority"}
const string HEADER_PRIORITY = "JMS_PRIORITY";

@doc:Description { value:"Header for JMS message expiry. "}
const string HEADER_EXPIRATION = "JMS_EXPIRATION";

@doc:Description { value:"Header to check the message delivery status. Whether the message is being redelivered from the JMS broker. This cannot be used to set the redilvered header for the message"}
const string HEADER_REDELIVERED = "JMS_REDELIVERED";

@doc:Description { value:"JMS correlation id message header"}
const string HEADER_CORRELATION_ID = "JMS_CORRELATION_ID";

@doc:Description { value:"JMS destination message header"}
const string HEADER_DESTINATION = "JMS_DESTINATION";

@doc:Description { value:"JMS timestamp message header"}
const string HEADER_TIMESTAMP = "JMS_TIMESTAMP";

@doc:Description { value:"JMS reply to message header"}
const string HEADER_REPLY_TO = "JMS_REPLY_TO";

@doc:Description { value:"Header to set the jms message type. The JMS API does not define a standard message definition repository and it is upto the user to set this header. Some JMS providers may have restricted set of message type values."}
const string HEADER_MESSAGE_TYPE = "JMS_TYPE";

@doc:Description { value:"JMS delivery mode message header. Can select either persistent or non persitent message delivery for the message"}
const string HEADER_DELIVERY_MODE = "JMS_DELIVERY_MODE";

@doc:Description { value:"Value for persistent JMS message delivery mode"}
const string PERSISTENT_DELIVERY_MODE = "2";

@doc:Description { value:"Value for non persistent JMS message delivery mode"}
const string NON_PERSISTENT_DELIVERY_MODE = "1";

const string DELIVERY_SUCCESS = "Success";

const string DELIVERY_ERROR = "Error";

@doc:Description { value:"JMS Message type TextMessage"}
const string JMS_TEXT_MESSGAE = "TextMessage";

@doc:Description { value:"JMS Message type MapMessage"}
const string JMS_MAP_MESSGAE = "MapMessage";

@doc:Description { value:"JMS Message type BytesMessage"}
const string JMS_BYTES_MESSGAE = "BytesMessage";

@doc:Description { value:"JMS Message type StreamMessage"}
const string JMS_STREAM_MESSGAE = "StreamMessage";

@doc:Description { value:"JMS Message type ObjectMessage"}
const string JMS_OBJECT_MESSGAE = "ObjectMessage";

@doc:Description { value:"Sets a JMS transport string property from the message"}
@doc:Param { value:"key: The string property name" }
@doc:Param { value:"value: The string property value" }
public native function <JMSMessage msg> setStringProperty (string key, string value);

@doc:Description { value:"Gets a JMS transport string property from the message"}
@doc:Param { value:"key: The string property name" }
@doc:Return { value:"string: The string property value" }
public native function <JMSMessage msg> getStringProperty (string key) (string);

@doc:Description { value:"Sets a JMS transport integer property from the message"}
@doc:Param { value:"key: The integer property name" }
@doc:Param { value:"value: The integer property value" }
public native function <JMSMessage msg> setIntProperty (string key, int value);

@doc:Description { value:"Gets a JMS transport integer property from the message"}
@doc:Param { value:"key: The integer property name" }
@doc:Return { value:"int: The integer property value" }
public native function <JMSMessage msg> getIntProperty (string key) (int);

@doc:Description { value:"Sets a JMS transport boolean property from the message"}
@doc:Param { value:"key: The boolean property name" }
@doc:Param { value:"value: The boolean property value" }
public native function <JMSMessage msg> setBooleanProperty (string key, boolean value);

@doc:Description { value:"Gets a JMS transport boolean property from the message"}
@doc:Param { value:"key: The boolean property name" }
@doc:Return { value:"boolean: The boolean property value" }
public native function <JMSMessage msg> getBooleanProperty (string key) (boolean);

@doc:Description { value:"Sets a JMS transport float property from the message"}
@doc:Param { value:"key: The float property name" }
@doc:Param { value:"value: The float property value" }
public native function <JMSMessage msg> setFloatProperty (string key, float value);

@doc:Description { value:"Gets a JMS transport float property from the message"}
@doc:Param { value:"key: The float property name" }
@doc:Return { value:"float: The float property value" }
public native function <JMSMessage msg> getFloatProperty (string key) (float);

@doc:Description { value:"Sets text content for the JMS message"}
@doc:Param { value:"content: Text Message Content" }
public native function <JMSMessage msg> setTextMessageContent (string content);

@doc:Description { value:"Gets text content of the JMS message"}
@doc:Return { value:"string: Text Message Content" }
public native function <JMSMessage msg> getTextMessageContent () (string);

@doc:Description { value:"Get JMS transport header MessageID from the message"}
@doc:Return { value:"string: The header value" }
public native function <JMSMessage msg> getMessageID() (string);

@doc:Description { value:"Get JMS transport header Timestamp from the message"}
@doc:Return { value:"int: The header value" }
public native function <JMSMessage msg> getTimestamp() (int);

@doc:Description { value:"Sets DeliveryMode JMS transport header to the message"}
@doc:Param { value:"i: The header value" }
public native function <JMSMessage msg> setDeliveryMode(int i);

@doc:Description { value:"Get JMS transport header DeliveryMode from the message"}
@doc:Return { value:"int: The header value" }
public native function <JMSMessage msg> getDeliveryMode() (int);

@doc:Description { value:"Sets Expiration JMS transport header to the message"}
@doc:Param { value:"i: The header value" }
public native function <JMSMessage msg> setExpiration(int i);

@doc:Description { value:"Get JMS transport header Expiration from the message"}
@doc:Return { value:"int: The header value" }
public native function <JMSMessage msg> getExpiration() (int);

@doc:Description { value:"Sets Priority JMS transport header to the message"}
@doc:Param { value:"i: The header value" }
public native function <JMSMessage msg> setPriority(int i);

@doc:Description { value:"Get JMS transport header Priority from the message"}
@doc:Return { value:"int: The header value" }
public native function <JMSMessage msg> getPriority() (int);

@doc:Description { value:"Get JMS transport header Redelivered from the message"}
@doc:Return { value:"boolean: The header value" }
public native function <JMSMessage msg> getRedelivered() (boolean);

@doc:Description { value:"Sets CorrelationID JMS transport header to the message"}
@doc:Param { value:"s: The header value" }
public native function <JMSMessage msg> setCorrelationID(string s);

@doc:Description { value:"Get JMS transport header CorrelationID from the message"}
@doc:Return { value:"string: The header value" }
public native function <JMSMessage msg> getCorrelationID() (string);

@doc:Description { value:"Sets Type JMS transport header to the message"}
@doc:Param { value:"s: The header value" }
public native function <JMSMessage msg> setType(string s);

@doc:Description { value:"Get JMS transport header Type from the message"}
@doc:Return { value:"string: The header value" }
public native function <JMSMessage msg> getType() (string);

@doc:Description { value:"Clear JMS properties of the message"}
public native function <JMSMessage msg> clearProperties();

@doc:Description { value:"Clear body JMS of the message"}
public native function <JMSMessage msg> clearBody();
