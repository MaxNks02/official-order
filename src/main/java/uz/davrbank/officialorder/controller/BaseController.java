package uz.davrbank.officialorder.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import uz.davrbank.officialorder.service.BaseService;

@SuppressWarnings("ALL")
@CrossOrigin
public abstract class BaseController<S extends BaseService> {
}
