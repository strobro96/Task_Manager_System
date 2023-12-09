package ru.em.taskmanagersystem.service.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.Status;
import ru.em.taskmanagersystem.repository.StatusRepo;

import java.util.List;


@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepo statusRepo;

    @Autowired
    public StatusServiceImpl(StatusRepo statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Status> getAllStatuses() {
        return statusRepo.findAll();
    }
}
