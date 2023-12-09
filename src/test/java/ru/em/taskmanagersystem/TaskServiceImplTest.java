package ru.em.taskmanagersystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import ru.em.taskmanagersystem.model.Task;
import ru.em.taskmanagersystem.model.User;
import ru.em.taskmanagersystem.repository.TaskRepo;
import ru.em.taskmanagersystem.service.TaskService.TaskServiceImpl;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepo taskRepo;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepo.getById(1L)).thenReturn(task);
        assertEquals(task, taskService.getTaskById(1L));
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepo.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    @WithMockUser(username = "user")
    public void testSaveTask() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setAuthor(user);
        when(taskRepo.save(task)).thenReturn(task);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        taskService.saveTask(task);
        verify(taskRepo, times(1)).save(task);
    }

    @Test
    @WithMockUser(username = "user")
    public void testUpdateTask() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setAuthor(user);
        when(taskRepo.getById(1L)).thenReturn(task);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        taskService.updateTask(task);
        verify(taskRepo, times(1)).save(task);
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteTask() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setAuthor(user);
        when(taskRepo.getById(1L)).thenReturn(task);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        taskService.deleteTask(1L);
        verify(taskRepo, times(1)).deleteById(1L);
    }


    @Test
    @WithMockUser(username = "user")
    public void testUpdateTaskNotAuthor() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setAuthor(user);
        when(taskRepo.getById(1L)).thenReturn(task);

        User notAuthor = new User();
        notAuthor.setId(2L);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(notAuthor);

        taskService.updateTask(task);
        verify(taskRepo, times(0)).save(task);
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteTaskNotAuthor() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.setId(1L);
        task.setAuthor(user);
        when(taskRepo.getById(1L)).thenReturn(task);

        User notAuthor = new User();
        notAuthor.setId(2L);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(notAuthor);

        taskService.deleteTask(1L);
        verify(taskRepo, times(0)).deleteById(1L);
    }

}

