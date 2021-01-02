package be.sven.tesla.spi;

import be.sven.tesla.model.Seat;
import be.sven.tesla.model.SeatHeaterLevel;
import be.sven.tesla.model.Token;

import java.math.BigDecimal;

public interface ClimateControl {
    boolean start(Token token, Long id);
    boolean stop(Token token, Long id);
    boolean setTemp(Token token, Long id, BigDecimal temp);
    boolean defrost(Token token, Long id, boolean on);
    boolean seatHeater(Token token, Long id, Seat seat, SeatHeaterLevel level);
    boolean steeringWheelHeater(Token token, Long id, boolean on);
}
