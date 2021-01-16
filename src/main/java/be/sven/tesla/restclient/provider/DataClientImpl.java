package be.sven.tesla.restclient.provider;

import be.sven.tesla.data.mongo.VehicleDataRepository;
import be.sven.tesla.restclient.json.GenericWrapper;
import be.sven.tesla.core.*;
import be.sven.tesla.restclient.util.Command;
import be.sven.tesla.restclient.util.HttpHeadersBuilder;
import be.sven.tesla.restclient.util.UrlBuilder;
import be.sven.tesla.restclient.DataClient;
import be.sven.tesla.restclient.VehicleClient;
import be.sven.tesla.data.mongo.ChargeStateRepository;
import be.sven.tesla.data.mongo.ClimateStateRepository;
import be.sven.tesla.data.mongo.DriveStateRepositoy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Component
public class DataClientImpl implements DataClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataClientImpl.class);

    private final RestTemplate template;
    private final HttpHeadersBuilder headersBuilder;
    private final UrlBuilder urlBuilder;
    private final VehicleClient vehicleClient;
    private final VehicleDataRepository repository;
    private final ChargeStateRepository chargeStateRepository;
    private final DriveStateRepositoy driveStateRepositoy;
    private final ClimateStateRepository climateStateRepository;

    @Autowired
    public DataClientImpl(RestTemplate template, HttpHeadersBuilder headersBuilder, UrlBuilder urlBuilder,
                          VehicleClient vehicleClient, VehicleDataRepository repository, ChargeStateRepository chargeStateRepository,
                          DriveStateRepositoy driveStateRepositoy, ClimateStateRepository climateStateRepository) {
        this.template = template;
        this.headersBuilder = headersBuilder;
        this.urlBuilder = urlBuilder;
        this.vehicleClient = vehicleClient;
        this.repository = repository;
        this.chargeStateRepository = chargeStateRepository;
        this.driveStateRepositoy = driveStateRepositoy;
        this.climateStateRepository = climateStateRepository;
    }

    @Override
    public VehicleData getVehicleData(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.VEHICLE_DATA);
        ResponseEntity<GenericWrapper<VehicleData>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<VehicleData>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<VehicleData> wrapper = response.getBody();
            if (wrapper == null) {
                LOGGER.error("Unable to get VehicleData: {}", response.getStatusCode());
                return null;
            }
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("VehicleData {}", wrapper.getResponse());
            VehicleData data = wrapper.getResponse();
            saveData(data);
            return data;
        }
        LOGGER.error("Unable to get VehicleData: {}", response.getStatusCode());
        return null;
    }

    private void saveData(VehicleData data) {
        long timestamp = Instant.now().getEpochSecond();
        UUID uuid = UUID.randomUUID();
        data.setUuid(uuid);
        data.setTimestamp(timestamp);
        ChargeState chargeState = data.getChargeState();
        chargeState.setUuid(uuid);
        chargeState.setTimestamp(timestamp);
        chargeStateRepository.save(chargeState);
        DriveState driveState = data.getDriveState();
        driveState.setUuid(uuid);
        driveState.setTimestamp(timestamp);
        driveStateRepositoy.save(driveState);
        ClimateState climateState = data.getClimateState();
        climateState.setUuid(uuid);
        climateState.setTimestamp(timestamp);
        climateStateRepository.save(climateState);
        repository.save(data);
    }

    @Override
    public VehicleState getVehicleState(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.VEHICLE_STATE);
        ResponseEntity<GenericWrapper<VehicleState>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<VehicleState>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<VehicleState> data = response.getBody();
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("VehicleState {}", data.getResponse());
            return data.getResponse();
        }
        LOGGER.error("Unable to get VehicleState: {}", response.getStatusCode());
        return null;
    }

    @Override
    public ChargeState getChargeState(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.CHARGE_STATE);
        ResponseEntity<GenericWrapper<ChargeState>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<ChargeState>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<ChargeState> data = response.getBody();
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("ChargeState {}", data.getResponse());
            return data.getResponse();
        }
        LOGGER.error("Unable to get ChargeState: {}", response.getStatusCode());
        return null;
    }

    @Override
    public ClimateState getClimateState(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.CLIMATE_STATE);
        ResponseEntity<GenericWrapper<ClimateState>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<ClimateState>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<ClimateState> data = response.getBody();
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("ClimateState {}", data.getResponse());
            return data.getResponse();
        }
        LOGGER.error("Unable to get ClimateState: {}", response.getStatusCode());
        return null;
    }

    @Override
    public DriveState getDriveState(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.DRIVE_STATE);
        ResponseEntity<GenericWrapper<DriveState>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<DriveState>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<DriveState> data = response.getBody();
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("DriveState {}", data.getResponse());
            return data.getResponse();
        }
        LOGGER.error("Unable to get DriveState: {}", response.getStatusCode());
        return null;
    }

    @Override
    public GUISettings getGuiSettings(Token token, Long id) {
        HttpHeaders headers = headersBuilder.getHeaders(token);
        String url = urlBuilder.buildUrl(id, Command.GUI_SETTINGS);
        ResponseEntity<GenericWrapper<GUISettings>> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>("", headers),
                new ParameterizedTypeReference<GenericWrapper<GUISettings>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            GenericWrapper<GUISettings> data = response.getBody();
            LOGGER.info("----------------------------------- {}", response.getStatusCode());
            LOGGER.info("GuiSettings {}", data.getResponse());
            return data.getResponse();
        }
        LOGGER.error("Unable to get GuiSettings: {}", response.getStatusCode());
        return null;
    }
}
