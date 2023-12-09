package ru.em.taskmanagersystem.service.PriorityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.taskmanagersystem.model.Priority;
import ru.em.taskmanagersystem.repository.PriorityRepo;

import java.util.List;


@Service
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepo priorityRepo;

    @Autowired
    public PriorityServiceImpl(PriorityRepo priorityRepo) {
        this.priorityRepo = priorityRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Priority> getAllPriorities() {
        return priorityRepo.findAll();
    }
}
