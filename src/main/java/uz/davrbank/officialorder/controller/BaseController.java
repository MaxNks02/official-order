package uz.davrbank.officialorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import uz.davrbank.officialorder.service.BaseService;

@SuppressWarnings("ALL")
@CrossOrigin
public abstract class BaseController<S extends BaseService> {
    public static final String API_PATH = "/api";
    public static final String V_1 = "/v1";
    public static final String SLUJEBKA = API_PATH + V_1 + "/slujebka";

    //    OFFICIAL ORDER CONTROLLER BEGIN
    public static final String FILE = SLUJEBKA + "/file";
    public static final String DOWNLOAD = "/download";
    public static final String UPLOAD = "/upload";
    public static final String DELETE = "/delete";
    public static final String GET_ALL = "/getAll";
    //    OFFICIAL ORDER CONTROLLER END

    //    TRANSACTION CONTROLLER BEGIN
    public static final String TRANSACTION = SLUJEBKA + "/transaction";
    public static final String CREATE = "/create";
    //    TRANSACTION CONTROLLER END


    protected S service;

    public BaseController(S service) {
        this.service = service;
    }
}
