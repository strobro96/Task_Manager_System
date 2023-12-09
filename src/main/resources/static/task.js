$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const taskId = urlParams.get('id');

    return drawTable(taskId);
});

async function drawTable(taskId) {
    const response = await fetch('/api/tasks/' + taskId, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    });
    const {id, header, description, statusEnum, priorityEnum, author, employee, commentList} = await response.json();

    $('#task-tbody').append(`<tr>
        <td>${id}</td>
        <td>${header}</td>
        <td>${description}</td>
        <td>${statusEnum.value}</td>
        <td>${priorityEnum.value}</td>
        <td>${author.name} ${author.surname}</td>
        <td>${employee.name} ${employee.surname}</td>
      </tr>`);

    commentList.sort((a, b) => a.id > b.id ? -1 : 1);

    $('#comments-list').html(`${commentList.map(({author, value, zonedDateTime: timestamp}) => {
        const date = new Date(timestamp * 1000);
        const formattedHours = date.getHours().toString().length === 2 ? date.getHours() : `0${date.getHours()}`;
        const formattedMinutes = date.getMinutes().toString().length === 2 ? date.getMinutes() : `0${date.getMinutes()}`;
        const formattedSeconds = date.getSeconds().toString().length === 2 ? date.getSeconds() : `0${date.getSeconds()}`;
        const dateAsString = `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()} ${formattedHours}:${formattedMinutes}:${formattedSeconds}`

        return dateAsString + ' ' + author.name + ' ' + author.surname + ' : ' + value;
    }).join('<br>')}`);
}

async function addNewComment() {
    const urlParams = new URLSearchParams(window.location.search);
    const taskId = urlParams.get('id');
    const comment = $('#task-new-comment').val();
    const response = await fetch(`/api/tasks/${taskId}/comments`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(comment)
    });

    const {value, author, zonedDateTime: timestamp} = await response.json();
    const date = new Date(timestamp * 1000);
    const formattedHours = date.getHours().toString().length === 2 ? date.getHours() : `0${date.getHours()}`;
    const formattedMinutes = date.getMinutes().toString().length === 2 ? date.getMinutes() : `0${date.getMinutes()}`;
    const formattedSeconds = date.getSeconds().toString().length === 2 ? date.getSeconds() : `0${date.getSeconds()}`;
    const dateAsString = `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()} ${formattedHours}:${formattedMinutes}:${formattedSeconds}`
    const newCommentString = `${dateAsString} ${author.name} ${author.surname} : ${value}`;
    const oldComments = $('#comments-list').html();

    $('#comments-list').html(`${newCommentString}<br>${oldComments}`);
    $('#task-new-comment').val('');
}