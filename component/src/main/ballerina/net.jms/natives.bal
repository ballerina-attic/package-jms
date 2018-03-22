package ballerina.net.jms;

import ballerina/util;

public struct Message {
}

@Description { value:"Message acknowledgement action implementation for jms connector when using jms client acknowledgement mode"}
@Param { value:"message: message" }
@Param { value:"deliveryStatus: Specify whether message delivery is SUCCESS or ERROR" }
public native function <Message msg> acknowledge (string deliveryStatus);

@Description { value:"Session rollback action implementation for jms connector when using jms session transaction mode"}
@Param { value:"message: message" }
public native function <Message msg> rollback ();

@Description { value:"Session commit action implementation for jms connector when using jms session transaction mode"}
@Param { value:"message: message" }
public native function <Message msg> commit ();

//@Description { value:"Create JMS Text Message based on client connector"}
//@Param { value:"jmsClient: clientConnector" }
//public native function createTextMessage (ClientProperties jmsClient) (JMSMessage);
//
//@Description { value:"Create JMS Bytes Message based on client connector"}
//@Param { value:"jmsClient: clientConnector" }
//public native function createBytesMessage (ClientProperties jmsClient) (JMSMessage);

@Description { value:"Value for persistent JMS message delivery mode"}
public const int PERSISTENT_DELIVERY_MODE = 2;

@Description { value:"Value for non persistent JMS message delivery mode"}
public const int NON_PERSISTENT_DELIVERY_MODE = 1;

@Description { value:"Value to use when acknowledge jms messages for success"}
public const string DELIVERY_SUCCESS = "Success";

@Description { value:"Value to use when acknowledge jms messages for errors"}
public const string DELIVERY_ERROR = "Error";

@Description { value:"Value for auto acknowledgement mode (default)"}
public const string AUTO_ACKNOWLEDGE = "AUTO_ACKNOWLEDGE";

@Description { value:"Value for client acknowledge mode"}
public const string CLIENT_ACKNOWLEDGE = "CLIENT_ACKNOWLEDGE";

@Description { value:"Value for dups ok acknowledgement mode"}
public const string DUPS_OK_ACKNOWLEDGE = "DUPS_OK_ACKNOWLEDGE";

@Description { value:"Value for Session transacted mode"}
public const string SESSION_TRANSACTED = "SESSION_TRANSACTED";

@Description { value:"Value for XA transcted mode"}
public const string XA_TRANSACTED = "XA_TRANSACTED";

@Description { value:"Value for JMS Queue type"}
public const string TYPE_QUEUE = "queue";

@Description { value:"Value for JMS Topic type"}
public const string TYPE_TOPIC = "topic";

@Description { value:"Sets a JMS transport string property from the message"}
@Param { value:"key: The string property name" }
@Param { value:"value: The string property value" }
public native function <Message msg> setStringProperty (string key, string value);

@Description { value:"Gets a JMS transport string property from the message"}
@Param { value:"key: The string property name" }
@Return { value:"string: The string property value" }
public native function <Message msg> getStringProperty (string key) returns (string);

@Description { value:"Sets a JMS transport integer property from the message"}
@Param { value:"key: The integer property name" }
@Param { value:"value: The integer property value" }
public native function <Message msg> setIntProperty (string key, int value);

@Description { value:"Gets a JMS transport integer property from the message"}
@Param { value:"key: The integer property name" }
@Return { value:"int: The integer property value" }
public native function <Message msg> getIntProperty (string key) returns (int);

@Description { value:"Sets a JMS transport boolean property from the message"}
@Param { value:"key: The boolean property name" }
@Param { value:"value: The boolean property value" }
public native function <Message msg> setBooleanProperty (string key, boolean value);

@Description { value:"Gets a JMS transport boolean property from the message"}
@Param { value:"key: The boolean property name" }
@Return { value:"boolean: The boolean property value" }
public native function <Message msg> getBooleanProperty (string key) returns (boolean);

@Description { value:"Sets a JMS transport float property from the message"}
@Param { value:"key: The float property name" }
@Param { value:"value: The float property value" }
public native function <Message msg> setFloatProperty (string key, float value);

@Description { value:"Gets a JMS transport float property from the message"}
@Param { value:"key: The float property name" }
@Return { value:"float: The float property value" }
public native function <Message msg> getFloatProperty (string key) returns (float);

@Description { value:"Sets text content for the JMS message"}
@Param { value:"content: Text Message Content" }
public native function <Message msg> setTextMessageContent (string content);

@Description { value:"Gets text content of the JMS message"}
@Return { value:"string: Text Message Content" }
public native function <Message msg> getTextMessageContent () returns (string);

@Description { value:"Sets bytes content for the JMS message"}
@Param { value:"content: Bytes Message Content" }
public native function <Message msg> setBytesMessageContent (blob content);

@Description { value:"Get bytes content of the JMS message"}
@Return { value:"string: Bytes Message Content" }
public native function <Message msg> getBytesMessageContent () returns (blob);

@Description { value:"Get JMS transport header MessageID from the message"}
@Return { value:"string: The header value" }
public native function <Message msg> getMessageID () returns (string);

@Description { value:"Get JMS transport header Timestamp from the message"}
@Return { value:"int: The header value" }
public native function <Message msg> getTimestamp () returns (int);

@Description { value:"Sets DeliveryMode JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <Message msg> setDeliveryMode (int i);

@Description { value:"Get JMS transport header DeliveryMode from the message"}
@Return { value:"int: The header value" }
public native function <Message msg> getDeliveryMode () returns (int);

@Description { value:"Sets Expiration JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <Message msg> setExpiration (int i);

@Description { value:"Get JMS transport header Expiration from the message"}
@Return { value:"int: The header value" }
public native function <Message msg> getExpiration () returns (int);

@Description { value:"Sets Priority JMS transport header to the message"}
@Param { value:"i: The header value" }
public native function <Message msg> setPriority (int i);

@Description { value:"Get JMS transport header Priority from the message"}
@Return { value:"int: The header value" }
public native function <Message msg> getPriority () returns (int);

@Description { value:"Get JMS transport header Redelivered from the message"}
@Return { value:"boolean: The header value" }
public native function <Message msg> getRedelivered () returns (boolean);

@Description { value:"Sets CorrelationID JMS transport header to the message"}
@Param { value:"s: The header value" }
public native function <Message msg> setCorrelationID (string s);

@Description { value:"Get JMS transport header CorrelationID from the message"}
@Return { value:"string: The header value" }
public native function <Message msg> getCorrelationID () returns (string);

@Description { value:"Sets Type JMS transport header to the message"}
@Param { value:"s: The header value" }
public native function <Message msg> setType (string s);

@Description { value:"Get JMS transport header Type from the message"}
@Return { value:"string: The header value" }
public native function <Message msg> getType () returns (string);

@Description { value:"Sets ReplyTo JMS destinaiton name to the message"}
@Param { value:"s: The header value" }
public native function <Message msg> setReplyTo (string s);

@Description { value:"Get ReplyTo JMS destinaiton name from the message"}
@Return { value:"string: The header value" }
public native function <Message msg> getReplyTo () returns (string);

@Description { value:"Clear JMS properties of the message"}
public native function <Message msg> clearProperties ();

@Description { value:"Clear body JMS of the message"}
public native function <Message msg> clearBody ();
