package com.mygdx.game;

import com.badlogic.gdx.utils.Json;
import com.github.czyzby.websocket.AbstractWebSocketListener;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.mygdx.game.Objects.Juego;
import main.java.Mensaje;

public class Cliente {
    private WebSocket socket;
    static Json json = new Json();
    Juego juego;
    public void conectar(){
        String host = "localhost";
        socket = ExtendedNet.getNet().newWebSocket(host, 12345);

        socket.addListener(new AbstractWebSocketListener() {

            @Override
            public boolean onOpen(final WebSocket webSocket) {
                juego.conectar();
                return FULLY_HANDLED;
            }

            @Override
            public boolean onClose(final WebSocket webSocket, final WebSocketCloseCode code, final String reason) {
                juego.desconectar(code, reason);
                return FULLY_HANDLED;
            }

            @Override
            public boolean onMessage(final WebSocket webSocket, final String packet) {
                juego.mensaje(json.fromJson(Mensaje.class, packet));
                return FULLY_HANDLED;
            }

            @Override
            protected boolean onMessage(WebSocket webSocket, Object packet) throws WebSocketException {
                return false;
            }
        });
    }

    public void enviar(){}
}
