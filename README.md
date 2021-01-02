# teslaApiTest


I am writing a c++ library for the API. REST stuff is cracked, but I'm struggling to get anything out of a websocket (using IXWebSocket) to the streaming endpoint. I've taken hints from the code here and elsewhere. Here is the debugging output from the test code I have. Perhaps someone could look through this and point out what I have missed/overlooked - because the streaming API, whilst seemingly connected is sending no data..... all I get is a stream of messages containing:
{"msg_type":"control:hello","connection_timeout":0}

( Note: in this excerpt, the encoded personal/private tokens have been corrupted for the purposes of security, in this public forum)


*****************************
starting streaming connection
with wss://streaming.vn.teslamotors.com/streaming/
++++++++++++++++++++++++++++++++++++++++++++++	<< waiting for websocket to open
received message headers
Cache-Control: no-cache, no-store, private, s-max-age=0
Connection: upgrade
Date: Mon, 26 Oct 2020 23:20:35 GMT
Sec-WebSocket-Accept: xQqNlBaRbkcSi+NZcQesCfIrMXQ=
Strict-Transport-Security: max-age=31536000; includeSubDomains
Upgrade: websocket
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
Connection established
received message:
{"msg_type":"control:hello","connection_timeout":0}

>> sending >>
{
"msg_type": "data:subscribe",
"tag": 151375879647,
"token": "Y97sZWjlQGdtYWlsLPNvbkpVbWTlfnQvMKkwOQ==",
"value": "speed,odometer,soc,elevation,est_heading,est_lat,est_lng,power,shift_state,range,est_range,heading"
}

received message headers
Cache-Control: no-cache, no-store, private, s-max-age=0
Connection: upgrade
Date: Mon, 26 Oct 2020 23:20:36 GMT
Sec-WebSocket-Accept: n9FpT3plLPKk7l3kn3tWSx+xeJg=
Strict-Transport-Security: max-age=31536000; includeSubDomains
Upgrade: websocket
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
Connection established

received message:
{"msg_type":"control:hello","connection_timeout":0}
@ddaddy

ddaddy commented on Oct 27, 2020
When you send the subscribe command, the car will send you a payload when one of the values changes. So sometimes you might get 1 response and nothing else if the car is not doing anything.

Also, last week I had a period of aro4 days where my car sent absolutely nothing through the stream, then started working again 4 days later 🤷🏼‍♂️

@coleged

Author
coleged commented on Oct 27, 2020
I left the connection open and went drove the car about a bit. Nothing had arrived when I returned to look at the console except the string of control:hello messages.

The code is at coleged/tesmon see bottom half of the main.cpp with receive callback in websocket.cpp

@ddaddy

ddaddy commented on Oct 27, 2020
That's strange, I would have expected you to get a timeout before then. I get a timeout after 10 seconds if no data is received.

@jdemeyer1

jdemeyer1 commented 12 days ago
I also had this happen where I would provide the credentials in response to the hello message, and no data would stream. I reviewed this thread and tried something different and I started getting data again.

My original value field contained:
speed,odometer,soc,elevation,est_heading,est_lat,est_lng,power,shift_state,range,est_range,heading,est_corrected_lat,est_corrected_lng,native_latitude,native_longitude,native_heading,native_type,native_location_supported

When I shortened it to:
speed,odometer,soc,elevation,est_heading,est_lat,est_lng,power,shift_state,range,est_range,heading

I started getting data again.

Joe