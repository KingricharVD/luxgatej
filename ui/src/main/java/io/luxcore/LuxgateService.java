package io.luxcore;

import io.luxcore.dto.rs.StatusResponse;

public interface LuxgateService {
    StatusResponse status(LuxgateInstance instance);
}
