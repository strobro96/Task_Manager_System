const action = {
    create: 1,
    update: 2,
    delete: 3
}

$(document).ready(function () {
    return drawTable();
});

async function drawTable() {
    const response = await fetch('/api/users/', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });
    const users = await response.json();
    const tbody = $('#users-tbody');

    tbody.empty();
    users.forEach((user) => {
        const {id, name, surname, age, nickname, email, roles} = user;

        tbody.append(`<tr>
            <td>${id}</td>
            <td>${name}</td>
            <td>${surname}</td>
            <td>${age}</td>
            <td>${nickname}</td>
            <td>${email}</td>
            <td>${roles.map(({name}) => name)}</td>
            <td><button type="button" class="btn btn-info" onclick="openModalHandler(${action.update}, ${id})">Редактировать</button></td>
            <td><button type="button" class="btn btn-danger" onclick="openModalHandler(${action.delete}, ${id})">Удалить</button></td>
        </tr>`);
    });
}

async function openModalHandler(actionId, userId = null) {
    const modal = $('#exampleModal');

    modal.modal();

    const modalFields = modal.find('.form-group');
    const idField = modalFields.children('#id').first();
    const nameField = modalFields.children('#name').first();
    const surnameField = modalFields.children('#surname').first();
    const emailField = modalFields.children('#email').first();
    const ageField = modalFields.children('#age').first();
    const nicknameField = modalFields.children('#nickname').first();
    const passwordField = modalFields.children('#password').first();
    const rolesField = modalFields.children('#roles').first();
    const submitBtn = $('#submit-action');
    const modalLabel = $('#exampleModalLabel');
    const roles = await getRoles();

    rolesField.prop('disabled', false).empty();
    roles.forEach(({id, name}) => {
        rolesField.append(`<option value="${id}">${name}</option>`);
    });

    if (actionId === action.create) {
        idField.parent().prop('hidden', true);
        nameField.val('').prop('disabled', false);
        surnameField.val('').prop('disabled', false);
        emailField.val('').prop('disabled', false);
        ageField.val('').prop('disabled', false);
        nicknameField.val('').prop('disabled', false);
        passwordField.val('').prop('disabled', false);
        submitBtn
            .removeClass()
            .addClass('btn btn-primary')
            .text('Добавить пользователя')
            .off('click')
            .click(() => submitBtnHandler(addUser));
        modalLabel.text('Add new user');

        return;
    }

    const {id, name, surname, age, nickname, email, password} = await getUser(userId);

    idField.val(id);
    idField.parent().prop('hidden', false);
    nameField.val(name).prop('disabled', false);
    surnameField.val(surname).prop('disabled', false);
    emailField.val(email).prop('disabled', false);
    ageField.val(age).prop('disabled', false);
    nicknameField.val(nickname).prop('disabled', false);
    passwordField.val(password).prop('disabled', false);

    switch (actionId) {
        case action.delete:
            modalLabel.text('Удалить пользователя');
            submitBtn
                .removeClass()
                .addClass('btn btn-danger')
                .text('Delete')
                .off('click')
                .click(() => submitBtnHandler(deleteUser));
            nameField.prop('disabled', true);
            surnameField.prop('disabled', true);
            emailField.prop('disabled', true);
            ageField.prop('disabled', true);
            nicknameField.prop('disabled', true);
            passwordField.prop('disabled', true);
            rolesField.prop('disabled', true);
            break;
        case action.update:
            modalLabel.text('Редактировать пользователя');
            submitBtn
                .removeClass()
                .addClass('btn btn-info')
                .text('Edit')
                .off('click')
                .click(() => submitBtnHandler(editUser));
            break;
    }
}

async function getUser(userId) {
    const response = await fetch(`/api/users/${userId}`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function getRoles() {
    const response = await fetch(`/api/users/roles/`, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });

    return response.json();
}

async function submitBtnHandler(callback) {
    const modal = $('#exampleModal');
    const user = collectUserData();

    modal.modal('hide');
    await callback(user);
    drawTable();
}

function collectUserData() {
    const modalFields = $('#exampleModal').find('.form-group');
    const idField = modalFields.children('#id').first();
    const nameField = modalFields.children('#name').first();
    const surnameField = modalFields.children('#surname').first();
    const emailField = modalFields.children('#email').first();
    const ageField = modalFields.children('#age').first();
    const nicknameField = modalFields.children('#nickname').first();
    const passwordField = modalFields.children('#password').first();
    const rolesField = modalFields.children('#roles').first();

    const user = {
        roles: []
    };

    if (idField.val()) {
        user.id = parseInt(idField.val());
    }

    if (nameField.val()) {
        user.name = nameField.val();
    }

    if (surnameField.val()) {
        user.surname = surnameField.val();
    }

    if (emailField.val()) {
        user.email = emailField.val();
    }

    if (ageField.val()) {
        user.age = parseInt(ageField.val());
    }

    if (nicknameField.val()) {
        user.nickname = nicknameField.val();
    }

    if (passwordField.val()) {
        user.password = passwordField.val();
    }

    rolesField.find(':selected').each(function () {
        user.roles.push({
            id: parseInt($(this).val()),
            name: $(this).text(),
            authority: $(this).text()
        });
    });

    console.log(user);
    return user;
}

const addUser = async (user) => {
    delete user.id;

    await fetch(`/api/users/`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });
}

const deleteUser = async ({id}) => fetch(`/api/users/${id}`, {
    method: 'DELETE',
    headers: {'Content-Type': 'application/json'}
});

const editUser = async (user) => fetch(`/api/users/`, {
    method: 'PATCH',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(user)
});
