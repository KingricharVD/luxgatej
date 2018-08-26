package io.luxcore;

import io.luxcore.dto.rs.Asset;
import io.luxcore.dto.rs.StatusResponse;

import java.util.List;

public interface LuxgateService {
    StatusResponse status();
    List<Asset> listAssets();
    boolean login();
}
