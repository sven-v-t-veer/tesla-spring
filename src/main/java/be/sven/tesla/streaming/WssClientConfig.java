package be.sven.tesla.streaming;

import be.sven.tesla.core.Token;
import com.neovisionaries.ws.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WssClientConfig {

    public WebSocket createSocket(Token token, Long id, boolean on) {
        System.out.println("*****************************************************");
        try {
            new WebSocketFactory().createSocket("wss://streaming.vn.teslamotors.com/streaming/").addListener(new WebSocketListener() {

                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception {
                    System.out.println("Message: " + text);
                }

                @Override
                public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {

                }

                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    System.out.println("Connected: " + headers);
                    websocket.sendText("{\n" +
                            "    \"msg_type\": \"data:subscribe\",\n" +
                            "    \"tag\":" + id + ",\n" +
                            "    \"token\": \"" + token.getAccessToken() + ",\n" +
                            "    \"value\": \"power,shift_state,range,est_range,heading\"\n" +
                            "}");
                }

                @Override
                public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
                    System.out.println("Error: " + cause);
                }

                @Override
                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {

                }

                @Override
                public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                    System.out.println("Frame: " + frame);
                }

                @Override
                public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {
                    System.out.println("Text: " + data);
                }

                @Override
                public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                    String message = "{\"msg_type\": \"data:subscribe\",\"tag\":" + id + ",\"token\": \"" + token.getAccessToken() + "\", \"value\": \"power,shift_state,range,est_range,heading\"}";
                    System.out.println("Binary : " + new String(binary, StandardCharsets.UTF_8));
                    websocket.sendBinary(message.getBytes(StandardCharsets.UTF_8));
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
                    System.out.println("error: " + cause);
                }

                @Override
                public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

                }

                @Override
                public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
                    System.out.println("Message Error: " + cause);
                    System.out.println("Frames: " + frames);
                }

                @Override
                public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

                }

                @Override
                public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
                    System.out.println("Text Message Error: " + cause);
                    System.out.println("Frames: " + data);
                }

                @Override
                public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
                    System.out.println("Send Error: " + cause);
                    System.out.println("Frames: " + frame);
                }

                @Override
                public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

                }

                @Override
                public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {

                }

                @Override
                public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
                    System.out.println("Sending Handshake: " + requestLine);
                    headers.forEach(h -> Arrays.toString(h));
                }
            }).connect();
        } catch (Exception ex) {

        }
        return null;
    }
}
