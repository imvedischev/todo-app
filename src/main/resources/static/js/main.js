function loadNotes() {
    var req = new XMLHttpRequest();
    req.overrideMimeType('application/json');
    req.open('GET', '/notes');
    req.onload = function () {
        var notes = JSON.parse(req.responseText);
        var html = '<tr>\n' +
            '<th>ID</th>\n' +
            '<th>Title</th>\n' +
            '<th>Status</th>\n' +
            '</tr>';
        for (var i = 0; i < notes._embedded.noteList.length; i++) {
            var note = notes._embedded.noteList[i];
            html = html + '<tr><td>' + note.id + '</td>\n' +
                '<td>' + note.title + '</td>\n' +
                '<td>' + note.status + '</td></tr>';
        }
        document.getElementById('noteList').innerHTML = html;
    }
    req.send();
}

function createNote() {
    var title = document.getElementById('title').value;

    var req = new XMLHttpRequest();
    req.open('POST', '/notes');
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify({title:title}));

    loadNotes();
}

loadNotes();