package com.common;

import retrofit2.Retrofit;
import com.utils.AutomationConfig;

public class RequestHelper {

    public Retrofit getRequestInstance() {
        Retrofit retro = new Retrofit.Builder().baseUrl(AutomationConfig.URL).build();
        return retro;
    }

    {

    }
}
