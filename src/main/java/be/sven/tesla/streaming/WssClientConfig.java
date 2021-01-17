package be.sven.tesla.streaming;

import be.sven.tesla.core.Token;
import com.neovisionaries.ws.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WssClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WssClientConfig.class);

    private boolean subscribed = false;

    public WebSocket createSocket(Token token, Long id, boolean on) {
        LOGGER.info("*************  CREATING WEBSOCKET ****************************************");
        try {
            new WebSocketFactory().createSocket("wss://streaming.vn.teslamotors.com/streaming/").addListener(new WebSocketListener() {

                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception {
                    LOGGER.info("onTextMessage: {}", text);
                }

                @Override
                public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
                    LOGGER.info("OnStateChanged {} ", newState);
                }

                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    LOGGER.info("onConnected: {}", headers);
                    websocket.sendText("{\n" +
                            "    \"msg_type\": \"data:subscribe\",\n" +
                            "    \"tag\":" + id + ",\n" +
                            "    \"token\": \"" + token.getAccessToken() + ",\n" +
                            "    \"value\": \"power,shift_state,range,est_range,heading\"\n" +
                            "}");
                }

                @Override
                public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
                    LOGGER.info("onConnectError.",  cause);
                }

                @Override
                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                    LOGGER.info("onDisconnected: by Server {}", closedByServer);
                }

                @Override
                public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onFrame: {} ", frame);
                }

                @Override
                public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onContinuationFrame: {}", frame);
                }

                @Override
                public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onTextFrame: {}", frame);
                }

                @Override
                public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onBinaryFrame: {}", frame);
                }

                @Override
                public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onCloseFrame: {}", frame);
                }

                @Override
                public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onPingFrame: " + frame);
                }

                @Override
                public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onPongFrame: {}", frame);
                }

                @Override
                public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {
                    LOGGER.info("onTextMessage: {}", data);
                }

                @Override
                public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                    LOGGER.info("onBinaryMessage : {}" + new String(binary, StandardCharsets.UTF_8));
                    if (!subscribed) {
                        String subscribe = "{\"msg_type\": \"data:subscribe\",\"tag\":" + id + ",\"token\": \"" + token.getAccessToken() + "\", \"value\": \"power,shift_state,range,est_range,heading\"}";
                        websocket.sendBinary(subscribe.getBytes(StandardCharsets.UTF_8));
                    }
                }

                @Override
                public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

                }

                @Override
                public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

                }

                @Override
                public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

                }

                @Override
                public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                    LOGGER.info("onError.", cause);
                }

                @Override
                public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
                    LOGGER.info("onMessageError", cause);
                    frames.forEach(f -> LOGGER.info("Frame: {}", f));
                }

                @Override
                public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

                }

                @Override
                public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
                    LOGGER.info("onTextMessageError: ", cause);
                    LOGGER.info("onTextMessageError : {}", new String(data, StandardCharsets.UTF_8));
                }

                @Override
                public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
                    LOGGER.info("onSendError: ", cause);
                    LOGGER.info("onSendError : {}", frame);
                }

                @Override
                public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

                }

                @Override
                public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {

                }

                @Override
                public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
                    LOGGER.info("onSendingHandshake: {}", requestLine);
                    headers.forEach(h -> LOGGER.info("Header: {}", Arrays.toString(h)));
                }
            }).connect();
        } catch (Exception ex) {

        }
        return null;
    }
}
