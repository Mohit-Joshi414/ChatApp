'use strict';
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const  messageInput= document.querySelector('#message');
const connectingElement = document.querySelector('#connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;

function connect(event){
	nickname = document.querySelector('#nickname').value.trim();
	fullname = document.querySelector('#fullname').value.trim();
	
	console.log(nickname);
	console.log(fullname);
	if(nickname && fullname){
		usernamePage.classList.add('hidden');
		chatPage.classList.remove('hidden');
		
		const socket = new SockJS('/ws');
		stompClient = Stomp.over(socket);
		
		stompClient.connect({},onConnected,onError);
	}
	
	event.preventDefault();
}

function onConnected(){
	stompClient.subscribe(`/user/${nickname}/queue/messages`,onMessageReceived);
	stompClient.subscribe(`/user/public`,onMessageReceived);
	
	//register the connected user
	stompClient.send('/app/user.addUser',{},JSON.stringify({nickname:nickname,username:fullname,status:'ONLINE'}));
	
	//find and display connected user
	document.querySelector('#connected-user-fullname').textContent = fullname;
	findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers(){
	const connectedUserResponse = await fetch('/users');
	let connectedUsers = await connectedUserResponse.json();
	console.log("connected users : "+connectedUsers);
	console.log(connectedUsers);
	connectedUsers = connectedUsers.filter(user => user.nickname !== nickname);
	
	const connectedUsersList = document.getElementById('connectedUsers');
	connectedUsersList.innerHTML = '';
	
	connectedUsers.forEach(user => {
		appendUserElement(user,connectedUsersList);
		//console.log(connectedUsers.index(user));
		console.log(connectedUsers.length);
		//if(connectedUsers.index(user) < connectedUsers.length -1){
			const separator = document.createElement('li');
			separator.classList.add('separator');
			connectedUsersList.appendChild(separator);
		//}
	});
}

function appendUserElement(user,connectedUsersList){
	const listItem = document.createElement('li');
	listItem.classList.add('user-item');
	console.log(user);
	listItem.id = user.nickname;
	
	const userImage = document.createElement('img');
	userImage.src = 'img/user_icon.png';
	
	userImage.alt = user.username;
	
	const usernameSpan = document.createElement('span');
	usernameSpan.textContent = user.username;
	
	const recievedMsgs = document.createElement('span');
	recievedMsgs.textContent = '0';
	recievedMsgs.classList.add('nbr-msg','hidden');
	
	listItem.appendChild(userImage);
	listItem.appendChild(usernameSpan);
	listItem.appendChild(recievedMsgs);
	
	listItem.addEventListener('click',userItemClick);
	
	connectedUsersList.appendChild(listItem);
}

function userItemClick(event){
	document.querySelectorAll('.user-item').forEach(item=>{
		item.classList.remove('active');
	});
	
	messageForm.classList.remove('hidden');
	const clickedUser = event.currentTarget;
	clickedUser.classList.add('active');
	
	selectedUserId = clickedUser.getAttribute('id');
	fetchAndDisplayUserChat().then();
	
	const nbrMsg = clickedUser.querySelector('.nbr-msg');
	nbrMsg.classList.add('hidden');
}

async function fetchAndDisplayUserChat(){
	const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
	const userChat = await userChatResponse.json();
	chatArea.innerHTML = '';
	userChat.forEach(chat=>{
		displayMessage(chat.senderId,chat.content);
	});
	chatArea.scrollTop = chatArea.scrollHeight;
}

function displayMessage(senderId,content){
	const messageContainer = document.createElement('div');
	messageContainer.classList.add('message');
	console.log(senderId);
	console.log(content);
	if(senderId === nickname){
		messageContainer.classList.add('sender');
		
	}else{
		messageContainer.classList.add('receiver');
	}
	
	const msg = document.createElement('p');
	msg.textContent = content;
	messageContainer.appendChild(msg);
	console.log(messageContainer);
	chatArea.appendChild(messageContainer);
	
}

function onError(){
	console.log("error happened")
}

function sendMessage(event){
	const messageContent = messageInput.value.trim();
	if(messageContent && stompClient){
		const chatMessage = {
			senderId:nickname,
			recipientId:selectedUserId,
			content:messageContent,
			timestamp:new Date()
		};
		stompClient.send('/app/chat',{},JSON.stringify(chatMessage));
		displayMessage(nickname,messageInput.value.trim());
		messageInput.value='';
		
	}
	console.log("line 157");
	chatArea.scrollTop = chatArea.scrollHeight;
	event.preventDefault();
}

async function onMessageReceived(payload){
	await findAndDisplayConnectedUsers();
	const message = JSON.parse(payload.body);
	if(selectedUserId && selectedUserId === message.senderId){
		displayMessage(message.senderId,message.content);
		chatArea.scrollTop = chatArea.scrollHeight;
	}
	if(selectedUserId){
		document.querySelector(`#${selectedUserId}`).classList.add('active');
	}
	else{
		messageForm.classList.add('hidden');
	}
	
	const notifyUser = document.querySelector(`#${message.senderId}`);
	if(notifyUser && !notifyUser.classList.contains('active')){
		const nbrMsg = notifyUser.querySelector('.nbr-msg');
		nbrMsg.classList.remove('hidden');
		nbrMsg.textContent = '';
	}
}

function logoutUser(){
	stompClient.send('/app/user.disconnect',{},JSON.stringify({nickname:nickname,username:fullname,status:'OFFLINE'}));
	window.location.reload();
}

usernameForm.addEventListener('submit',connect,true);

messageForm.addEventListener('submit',sendMessage,true);

logout.addEventListener('click',logoutUser,true);
window.onbeforeunload = () => logoutUser();