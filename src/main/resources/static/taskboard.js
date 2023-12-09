const action = {
    create: 1,
    update: 2,
    delete: 3
}

$(document).ready(function () {
    return drawTable();
});

async function drawTable() {
    const response = await fetch('/api/tasks/', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });
    const tasks = await response.json();
    const tbody = $('#tasks-tbody');

    tbody.empty();
    tasks.forEach((task) => {
        const {id, header, description, statusEnum, priorityEnum, author, employee} = task;

        tbody.append(`<tr>
            <td>${id}</td>
            <td>${header}</td>
            <td>${description}</td>
            <td>${statusEnum.value}</td>
            <td>${priorityEnum.value}</td>
            <td>${author.name} ${author.surname}</td>
            <td>${employee.name} ${employee.surname}</td>
            <td><button type="button" class="btn btn-warning" onclick="location.href='/task?id=${id}'">Открыть</button></td>
             <td><button type="button" class="btn btn-info" onclick="openModalHandler(${action.update}, ${id})">Редактировать</button></td>
            <td><button type="button" class="btn btn-danger" onclick="openModalHandler(${action.delete}, ${id})">Удалить</button></td>
        </tr>`);
    });
}

async function openModalHandler(actionId, taskId = null) {
    const modal = $('#exampleModal1');

    modal.modal();

    const modalFields = modal.find('.form-group');
    const idField = modalFields.children('#id').first();
    const headerField = modalFields.children('#header').first();
    const descriptionField = modalFields.children('#description').first();
    const statusEnumField = modalFields.children('#statusEnum').first();
    const priorityEnumField = modalFields.children('#priorityEnum').first();
    const employeeField = modalFields.children('#employee').first();

    const submitBtn = $('#submit-action');
    const modalLabel = $('#exampleModalLabel');

    const statusEnums = await getStatusEnums();
    const priorityEnums = await getPriorityEnums();
    const employees = await getUsers();

    statusEnumField.prop('disabled', false).empty();
    statusEnums.forEach(({id, value}) => {
        statusEnumField.append(`<option value="${id}">${value}</option>`);
    });

    priorityEnumField.prop('disabled', false).empty();
    priorityEnums.forEach(({id, value}) => {
        priorityEnumField.append(`<option value="${id}">${value}</option>`);
    });

    employeeField.prop('disabled', false).empty();
    employees.forEach(({id, username}) => {
        employeeField.append(`<option value="${id}">${username}</option>`);
    });

    if (actionId === action.create) {
        idField.parent().prop('hidden', true);
        headerField.val('').prop('disabled', false);
        descriptionField.val('').prop('disabled', false);
        statusEnumField.val('').prop('disabled', false);
        priorityEnumField.val('').prop('disabled', false);
        employeeField.val('').prop('disabled', false);
        submitBtn
            .removeClass()
            .addClass('btn btn-primary')
            .text('Добавить задачу')
            .off('click')
            .click(() => submitBtnHandler(addTask));
        modalLabel.text('Создание задачи');

        return;
    }

    const {id, header, description, statusEnum, priorityEnum, employee} = await getTask(taskId);

    idField.val(id);
    idField.parent().prop('hidden', false);
    headerField.val(header).prop('disabled', false);
    descriptionField.val(description).prop('disabled', false);
    statusEnumField.val(statusEnum).prop('disabled', false);
    priorityEnumField.val(priorityEnum).prop('disabled', false);
    employeeField.val(employee).prop('disabled', false);

    switch (actionId) {
        case action.delete:
            modalLabel.text('Удалить задачу');
            submitBtn
                .removeClass()
                .addClass('btn btn-danger')
                .text('Удалить')
                .off('click')
                .click(() => submitBtnHandler(deleteTask, false));
            headerField.prop('disabled', true);
            descriptionField.prop('disabled', true);
            statusEnumField.prop('disabled', true);
            priorityEnumField.prop('disabled', true);
            employeeField.prop('disabled', true);
            break;
        case action.update:
            modalLabel.text('Изменить задачу');
            submitBtn
                .removeClass()
                .addClass('btn btn-info')
                .text('Редактировать')
                .off('click')
                .click(() => submitBtnHandler(editTask));
            break;
    }
}

async function getTask(id) {
    const response = await fetch(`/api/tasks/${id}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function getStatusEnums() {
    const response = await fetch(`/api/enums/statuses/`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function getPriorityEnums() {
    const response = await fetch(`/api/enums/priorities/`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function getUsers() {
    const response = await fetch(`/api/enums/users/`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function submitBtnHandler(callback, checkFields = true) {
    const modal = $('#exampleModal1');
    const task = collectTaskData();

    if (checkFields) {
        const emptyFieldMsgByName = {
            header: 'Заполните заголовок задачи',
            description: 'Заполните описание задачи',
            statusEnum: 'Выберите статус задачи',
            priorityEnum: 'Выберите приоритет задачи',
            employee: 'Выберите исполнителя задачи'
        };
        let errMsg = '';

        for (const field of Object.keys(emptyFieldMsgByName)) {
            if (!task[field]) {
                errMsg += `${emptyFieldMsgByName[field]}\n`;
            }
        }

        if (errMsg) {
            alert(errMsg);

            return;
        }
    }

    modal.modal('hide');
    await callback(task);
    drawTable();
}

function collectTaskData() {
    const modalFields = $('#exampleModal1').find('.form-group');
    const idField = modalFields.children('#id').first();
    const headerField = modalFields.children('#header').first();
    const descriptionField = modalFields.children('#description').first();
    const statusEnumField = modalFields.children('#statusEnum').first();
    const priorityEnumField = modalFields.children('#priorityEnum').first();
    const employeeField = modalFields.children('#employee').first();

    const task = {};

    if (idField.val()) {
        task.id = parseInt(idField.val());
    }

    if (headerField.val()) {
        task.header = headerField.val();
    }

    if (descriptionField.val()) {
        task.description = descriptionField.val();
    }


    // Для statusEnum
    const selectedStatus = statusEnumField.find(':selected').first();
    if (selectedStatus.length > 0) {
        task.statusEnum = {
            id: parseInt(selectedStatus.val()),
            value: selectedStatus.text(),
        };
    }

// Для priorityEnum
    const selectedPriority = priorityEnumField.find(':selected').first();
    if (selectedPriority.length > 0) {
        task.priorityEnum = {
            id: parseInt(selectedPriority.val()),
            value: selectedPriority.text(),
        };
    }

// Для employee
    const selectedEmployee = employeeField.find(':selected').first();
    if (selectedEmployee.length > 0) {
        task.employee = {
            id: parseInt(selectedEmployee.val()),
            username: selectedEmployee.text(),
        };
    }

    console.log(task);
    return task;
}

const addTask = async (task) => {
    delete task.id;

    await fetch(`/api/tasks/`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(task)
    });
}

const deleteTask = async ({id}) => fetch(`/api/tasks/${id}`, {
    method: 'DELETE',
    headers: {'Content-Type': 'application/json'}
});

const editTask = async (task) => fetch(`/api/tasks/`, {
    method: 'PATCH',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(task)
});



