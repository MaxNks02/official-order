package uz.davrbank.officialorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import uz.davrbank.officialorder.service.BaseService;

@SuppressWarnings("ALL")
@CrossOrigin
public abstract class BaseController<S extends BaseService> {
    public static final String API_PATH = "/api";
    public static final String V_1 = "/v1";
    public static final String SLUJEBKA = API_PATH + V_1 + "/slujebka";

//    FILE_DB CONTROLLER BEGIN
public static final String FILE = SLUJEBKA + "/file";
public static final String DOWNLOAD = FILE + "/download";
public static final String UPLOAD = FILE + "/upload";
//    FILE_DB CONTROLLER END

}
