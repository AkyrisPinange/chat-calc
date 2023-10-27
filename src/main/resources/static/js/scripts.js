'use strict';
var stompClient = null;


$("#connect").on('click', (e) => {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha3lyaXNAYWRtaW4uY29tIiwiaWF0IjoxNjk4NDM4NTU3LCJleHAiOjE2OTg1MjQ5NTd9.bmK8cnqZLm5hhc52LwBTSopSpndfFDcZ-7pwuZdFoUk',
    }, function (frame) {
        console.log('Conectado: ' + frame);
    });
})//

//create new chat
$("#btnChatId").on('click', (e) => {
    let userId = $("#userId").val()
    let title = $("#title").val()

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + userId, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/createChat",
        {}, JSON.stringify({
            'userId': userId,
            'title': title
        }));
})

//Join in new chat
$("#btnJoin").on('click', (e) => {
    let roomId = $("#roomId").val()
    let chat

    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/chat/getChatByRoomId?roomId=" + roomId, requestOptions)
        .then(response => response.text())
        .then(result => {
            chat = result;
            joinChat(JSON.parse(chat))
            console.log('Chat:', chat);
        })
        .catch(error => console.log('error', error))

})

function joinChat(chat) {
    let userId = $("#userIdJoin").val()

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + chat.data.id, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/" + chat.data.id + "/joinChat",
        {}, JSON.stringify({
            'userId': userId,
            'chatId': chat.data.id
        }));
}

//sende message
$("#btnChat").on('click', (e) => {
    let chatId = $("#chatMsgId").val()
    let message = $("#message").val()
    let userId = $("#userIdSend").val()

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + chatId, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/" + chatId + "/sendMessage",
        {}, JSON.stringify({
            'chatId': chatId,
            'content': message,
            'userId': userId
        }));
})

//sende message
$("#btnSpend").on('click', (e) => {
    let chatId = $("#chatCostId").val();
    let value = $("#value").val();
    let quantity = $("#quantity").val();
    let product = $("#product").val();
    let totalSpend = $("#totalSpend").val();


    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/' + chatId, function (message) {
        console.log('Recebido mensagem: ' + message.body);
    });
    // Tell your username to the server
    stompClient.send("/app/chat/" + chatId + "/costs",
        {}, JSON.stringify({
            'chatId': chatId,
            'cost': value,
            'quantity': quantity,
            'product': product,
            'totalSpend': totalSpend
        }));
})

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}