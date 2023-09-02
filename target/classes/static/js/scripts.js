'use strict';
var stompClient = null;


$("#connect").on('click', (e) => {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Conectado: ' + frame);
    });

})

$("#btnChatId").on('click', (e) => {
    let chatId = $("#chatId").val()
    let userId = $("#idUser").val()

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + chatId, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/" + chatId + "/joinChat",
        {}, JSON.stringify({
            'user_id': userId,
            'chat_id': chatId
        }));
})


$("#btnChat").on('click', (e) => {
    let chatId = $("#chatMsgId").val()
    let message = $("#message").val()

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + chatId, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/" + chatId + "/sendMessage",
        {}, JSON.stringify({
            'chat_id': chatId,
            'content': message
        }));
})

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}